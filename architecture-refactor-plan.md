# Architecture Refactoring Plan: From Entity Services to Bounded Contexts

## Executive Summary

Your quiz-guru-server suffers from the **Entity Service Anti-Pattern** with services organized around database tables rather than business capabilities. This has created a **distributed monolith** with 6 separate databases, tight coupling via FeignClient calls, and no clear bounded contexts.

**Proposed Solution**: Refactor to 3 bounded contexts using Hexagonal Architecture with a consolidated database strategy.

---

## Current Architecture Critique

### 1. Entity Service Anti-Pattern

**Problem**: Services are organized by database entities, not business capabilities.

**Evidence**:
- **Records Service**: Pure CRUD wrapper around `records` and `record_items` tables
- **Libraries Service**: Manages `libraries`, `word_sets`, `words` tables without cohesive business capability
- **Quizzes Service**: Manages quiz entities but artificially delegates generation to separate service

**Impact**: 
- No clear business capability ownership
- Logic scattered across services
- Difficult to understand system behavior
- Changes require coordinating multiple services

### 2. Distributed Monolith with Separate Databases

**Current Databases**:
1. `quizguru_quiz` (Quizzes service)
2. `quizguru_record` (Records service)
3. `quizguru_library` (Libraries service)
4. `quizguru_generate` (Generates service)
5. `quizguru_user` (Customers service)
6. `quizguru_auth` (Keycloak)

**Critical Issue**: `word_sets.quiz_id` column references quizzes from another database without foreign key constraints, creating implicit coupling and no referential integrity.

**Impact**:
- No database-level data integrity
- Complex transaction management
- Difficult data migrations
- Operational overhead managing 6 databases
- Cannot use database transactions across contexts

### 3. Tight Synchronous Coupling

**Dependency Graph**:
```
Quizzes → Libraries (fetch word sets, remove bindings)
Quizzes → Records (submit quiz results)
Generates → Quizzes (update with generated questions)
Generates → Libraries (create/bind word sets)
Generates → Customers (verify roles)
Records → Quizzes (fetch provision data)
Records → Libraries (increment review count)
```

**Impact**:
- Cascading failures (one service down affects others)
- Difficult to test in isolation
- Deployment coupling
- Performance bottlenecks from network hops

### 4. Artificial Service Boundaries

**Generates Service Issues**:
- No persistent entities (only a `prompts` table)
- Purely orchestration logic artificially separated from Quizzes
- Consumes queue messages, calls AI providers, updates Quizzes/Libraries
- Queue-based communication adds latency without clear benefits

**Customers Service Issues**:
- Thin wrapper around Keycloak with no business logic
- All operations delegate to `IdentityClient`
- Could be handled at Gateway level
- Extra network hop with no value

---

## Proposed Bounded Contexts

### 1. Quiz Management Context (Core Domain)

**Business Capability**: Complete quiz lifecycle—creation, AI-powered generation, content management, and quiz execution.

**Entities**: 
- Quiz (aggregate root)
- Question
- Choice

**Key Use Cases**:
- Create quiz from text/files/word lists
- Generate quiz content via AI providers (OpenAI, Claude, Gemini)
- Retrieve quiz for taking
- Delete quiz
- Submit quiz answers

**Why Merge Quizzes + Generates**:
- Quiz generation is a core business capability, not a separate domain
- Generates has no persistent entities—it's orchestration logic
- Current queue-based separation adds complexity without benefits
- Tight coupling already exists (Generates updates Quizzes)
- Single transaction boundary for quiz creation + generation

### 2. Vocabulary Library Context (Supporting Domain)

**Business Capability**: Personal vocabulary collection and word set management for language learning.

**Entities**:
- Library (aggregate root, 1:1 with user)
- WordSet
- Word

**Key Use Cases**:
- Create and manage word sets
- Add words with definitions (via external dictionary API)
- Track word set review statistics
- Bind word sets to quizzes (correlation, not ownership)

**Why Keep Separate**:
- Independent business value (vocabulary building without quizzes)
- Different lifecycle (word sets persist independently)
- Clear bounded context boundary
- Can be used without quiz system

### 3. Learning Analytics Context (Supporting Domain)

**Business Capability**: Track learning progress, quiz attempts, and performance metrics.

**Entities**:
- Record (aggregate root)
- RecordItem

**Key Use Cases**:
- Record quiz attempts
- Track scores and performance
- Provide learning analytics and history

**Why Keep Separate**:
- Different lifecycle (records persist after quizzes deleted—audit trail)
- Pure analytics/reporting concern
- Independent scaling needs (read-heavy)
- Different retention policies (long-term historical data)

---

## Database Consolidation Strategy

### Recommended Approach: Single Database with Schema Separation

**New Database Structure**:
```sql
quizguru_db
├── quiz_management (schema)
│   ├── quizzes
│   ├── questions
│   └── choices
│
├── vocabulary (schema)
│   ├── libraries
│   ├── word_sets
│   └── words
│
└── analytics (schema)
    ├── records
    └── record_items
```

**Rationale**:
- **Logical separation**: Schemas provide clear bounded context boundaries
- **Simplified transactions**: Within-context operations can use database transactions
- **Operational simplicity**: One database to backup, monitor, and manage
- **Performance**: Efficient queries without cross-database joins
- **Migration path**: Easier to consolidate than maintain 6 databases
- **Cost reduction**: Single database instance vs 6 separate instances

**Alternative Approach**: Keep 3 separate databases (one per bounded context) if you need:
- Independent scaling per context
- Different backup/retention policies (e.g., analytics data retained longer)
- Strict data isolation for compliance requirements
- Different database technologies per context

### Handling Cross-Context References

**Problem**: `word_sets.quiz_id` currently references quizzes from another database.

**Solution**: Domain Events (Recommended)

```
Quiz Management publishes:
  → QuizCreatedEvent(quizId, wordSetId, userId, timestamp)
  → QuizDeletedEvent(quizId, timestamp)

Vocabulary Library subscribes:
  → Stores quizId as correlation ID (not foreign key)
  → Can query "which word set is associated with this quiz"
  → On QuizDeletedEvent: optionally remove binding or mark as orphaned
```

**Benefits**:
- Loose coupling between contexts
- `quiz_id` becomes a correlation identifier, not a foreign key
- Eventual consistency model (acceptable for this use case)
- Clear ownership: Quiz Management owns quiz lifecycle
- Easy to add new subscribers without modifying Quiz Management

**Implementation**:
- Use Spring Cloud Stream or Kafka for event bus
- Implement idempotent event handlers
- Add event versioning for backward compatibility
- Store events for audit trail and replay capability

---

## Hexagonal Architecture Structure

### Example: Quiz Management Service

```
quiz-management/
├── src/main/java/com/quizguru/quizmanagement/
│   │
│   ├── domain/                          # Core business logic (no framework dependencies)
│   │   ├── model/
│   │   │   ├── Quiz.java                # Aggregate root with business behavior
│   │   │   ├── Question.java
│   │   │   ├── Choice.java
│   │   │   └── valueobjects/
│   │   │       ├── QuizType.java        # Enum: TEXT, VOCABULARY, etc.
│   │   │       ├── Level.java           # Enum: BEGINNER, INTERMEDIATE, ADVANCED
│   │   │       └── QuestionType.java
│   │   │
│   │   ├── service/                     # Domain services for complex operations
│   │   │   ├── QuizDomainService.java   # Business rules (e.g., validation)
│   │   │   └── QuizGenerationService.java # AI generation orchestration
│   │   │
│   │   └── repository/                  # Repository interface (port)
│   │       └── QuizRepository.java      # Domain-defined contract
│   │
│   ├── application/                     # Use cases and orchestration
│   │   ├── port/
│   │   │   ├── in/                      # Driving ports (use cases)
│   │   │   │   ├── CreateQuizUseCase.java
│   │   │   │   ├── GenerateQuizContentUseCase.java
│   │   │   │   ├── GetQuizUseCase.java
│   │   │   │   ├── DeleteQuizUseCase.java
│   │   │   │   └── SubmitQuizUseCase.java
│   │   │   │
│   │   │   └── out/                     # Driven ports (dependencies)
│   │   │       ├── QuizPersistencePort.java      # Database operations
│   │   │       ├── AIProviderPort.java           # AI generation
│   │   │       ├── VocabularyLibraryPort.java    # External context
│   │   │       └── LearningAnalyticsPort.java    # External context
│   │   │
│   │   └── service/                     # Use case implementations
│   │       ├── QuizApplicationService.java
│   │       └── QuizGenerationApplicationService.java
│   │
│   └── infrastructure/                  # Framework-specific implementations
│       ├── adapter/
│       │   ├── in/                      # Driving adapters (entry points)
│       │   │   ├── rest/
│       │   │   │   ├── QuizController.java       # REST API
│       │   │   │   └── dto/                      # API DTOs
│       │   │   │       ├── CreateQuizRequest.java
│       │   │   │       └── QuizResponse.java
│       │   │   │
│       │   │   └── messaging/
│       │   │       └── QuizEventConsumer.java    # Message queue consumer
│       │   │
│       │   └── out/                     # Driven adapters (implementations)
│       │       ├── persistence/
│       │       │   ├── QuizJpaAdapter.java       # Implements QuizPersistencePort
│       │       │   ├── QuizJpaRepository.java    # Spring Data JPA
│       │       │   └── entity/                   # JPA entities (separate from domain)
│       │       │       ├── QuizEntity.java
│       │       │       ├── QuestionEntity.java
│       │       │       └── ChoiceEntity.java
│       │       │
│       │       ├── ai/                           # AI provider adapters
│       │       │   ├── OpenAIAdapter.java        # Implements AIProviderPort
│       │       │   ├── ClaudeAdapter.java
│       │       │   └── GeminiAdapter.java
│       │       │
│       │       ├── vocabulary/                   # External context adapter
│       │       │   └── VocabularyLibraryRestAdapter.java
│       │       │
│       │       └── analytics/                    # External context adapter
│       │           └── LearningAnalyticsRestAdapter.java
│       │
│       └── config/
│           ├── SecurityConfig.java
│           ├── ApplicationConfig.java
│           └── BeanConfiguration.java
```

### Key Hexagonal Architecture Principles

**1. Domain Layer (Innermost)**
- Pure business logic with no framework dependencies
- Rich domain models with behavior (not anemic entities)
- Domain services for operations spanning multiple aggregates
- Repository interfaces defined by domain needs
- No Spring annotations, no JPA annotations

**2. Application Layer (Orchestration)**
- Use cases define application boundaries
- Ports (interfaces) define contracts with outside world
- Application services orchestrate domain services and ports
- Transaction boundaries defined here
- Minimal framework dependencies

**3. Infrastructure Layer (Outermost)**
- Adapters implement ports
- Framework-specific code (Spring, JPA, REST)
- Separate JPA entities from domain models (mapping layer)
- External integrations (AI providers, other contexts)
- All framework annotations here

**Benefits**:
- **Testability**: Domain logic testable without frameworks (fast unit tests)
- **Flexibility**: Swap implementations (e.g., change AI provider without touching domain)
- **Clear dependencies**: Domain depends on nothing, infrastructure depends on domain
- **Maintainability**: Business logic isolated from technical concerns
- **Independent deployment**: Can change infrastructure without touching domain

---

## Step-by-Step Migration Strategy

### Phase 1: Merge Generates into Quiz Management (2-3 weeks)

**Goal**: Consolidate quiz generation logic into Quiz Management context.

**Steps**:

1. **Copy Generates service logic into Quizzes service**
   - Move `GenerateServiceImpl` → `QuizGenerationApplicationService`
   - Move AI provider clients (OpenAI, Claude, Gemini) → infrastructure adapters
   - Move prompt management logic
   - Keep both services running during transition

2. **Replace queue-based communication with direct method calls**
   - Remove `GenerateProducer` from Quizzes service
   - Remove message consumer from Generates service
   - Call `QuizGenerationApplicationService` directly after quiz creation
   - Add feature flag to toggle between old/new flow

3. **Consolidate databases**
   - Migrate `prompts` table from `quizguru_generate` to `quiz_management` schema
   - Update connection configurations
   - Test data migration with rollback plan

4. **Remove Generates service**
   - Decommission service after validation period
   - Update Gateway routing
   - Remove from Eureka registry

**Verification**:
- Create quiz from text → generates questions → quiz updated
- Create vocabulary quiz → generates questions + creates word sets
- All existing quiz creation flows work
- Performance metrics comparable or better

### Phase 2: Implement Hexagonal Architecture in Quiz Management (3-4 weeks)

**Goal**: Refactor Quiz Management to proper hexagonal structure.

**Steps**:

1. **Extract domain models from JPA entities**
   - Create `domain/model/Quiz.java` (rich domain model)
   - Separate from `QuizEntity.java` (JPA persistence)
   - Add domain behavior (validation, business rules)
   - Create value objects (QuizType, Level, QuestionType)

2. **Define use case interfaces (driving ports)**
   - `CreateQuizUseCase`
   - `GenerateQuizContentUseCase`
   - `GetQuizUseCase`
   - `DeleteQuizUseCase`
   - `SubmitQuizUseCase`

3. **Define driven ports (dependencies)**
   - `QuizPersistencePort` (database operations)
   - `AIProviderPort` (AI generation)
   - `VocabularyLibraryPort` (word set operations)
   - `LearningAnalyticsPort` (record submission)

4. **Implement adapters**
   - `QuizJpaAdapter` implements `QuizPersistencePort`
   - `OpenAIAdapter`, `ClaudeAdapter`, `GeminiAdapter` implement `AIProviderPort`
   - `VocabularyLibraryRestAdapter` implements `VocabularyLibraryPort`
   - `LearningAnalyticsRestAdapter` implements `LearningAnalyticsPort`

5. **Refactor controllers to use use cases**
   - `QuizController` calls use case interfaces
   - Map between API DTOs and domain models
   - Keep API contracts unchanged (backward compatibility)

**Verification**:
- All quiz endpoints work
- Unit tests for domain logic (no Spring context, fast)
- Integration tests for adapters
- API contracts unchanged

### Phase 3: Database Consolidation (2-3 weeks)

**Goal**: Migrate from 6 databases to single database with schemas.

**Steps**:

1. **Create new database structure**
   ```sql
   CREATE DATABASE quizguru_db;
   CREATE SCHEMA quiz_management;
   CREATE SCHEMA vocabulary;
   CREATE SCHEMA analytics;
   
   -- Grant permissions
   GRANT ALL ON SCHEMA quiz_management TO quiz_user;
   GRANT ALL ON SCHEMA vocabulary TO quiz_user;
   GRANT ALL ON SCHEMA analytics TO quiz_user;
   ```

2. **Migrate data (with zero downtime)**
   - Set up database replication from old to new
   - Export data from `quizguru_quiz` → import to `quiz_management` schema
   - Export data from `quizguru_library` → import to `vocabulary` schema
   - Export data from `quizguru_record` → import to `analytics` schema
   - Verify data integrity (row counts, checksums)

3. **Update application configurations**
   - Change `spring.datasource.url` in each service
   - Update schema references in JPA entities (`@Table(schema = "quiz_management")`)
   - Test connections in staging environment

4. **Handle cross-schema references**
   - Remove `quiz_id` foreign key constraint from `word_sets`
   - Implement domain events for quiz-wordset correlation
   - Add event publishing/subscribing infrastructure (Kafka/RabbitMQ)
   - Implement idempotent event handlers

5. **Cutover**
   - Blue-green deployment
   - Monitor for errors
   - Keep old databases for rollback period (1 week)

**Verification**:
- All services connect to new database
- Data integrity checks pass
- Cross-context operations work (quiz creation → word set binding)
- Performance metrics acceptable
- Rollback plan tested

### Phase 4: Refactor Vocabulary Library Context (2-3 weeks)

**Goal**: Apply hexagonal architecture to Libraries service.

**Steps**:

1. **Define bounded context boundary**
   - Identify aggregate roots (Library, WordSet)
   - Define use cases (CreateWordSet, AddWords, BindToQuiz, etc.)
   - Define ports (in/out)

2. **Implement hexagonal structure**
   - Domain models (Library, WordSet, Word)
   - Use case interfaces
   - Adapters (persistence, external dictionary API, quiz management)

3. **Replace FeignClient with ports**
   - Define `QuizManagementPort` for quiz-related operations
   - Implement `QuizManagementRestAdapter`
   - Remove direct FeignClient dependency

4. **Implement event subscribers**
   - Subscribe to `QuizDeletedEvent` → remove word set bindings
   - Subscribe to `QuizCreatedEvent` → store correlation
   - Implement idempotent handlers

**Verification**:
- Word set CRUD operations work
- Dictionary API integration works
- Event-based quiz correlation works
- No direct coupling to Quiz Management

### Phase 5: Refactor Learning Analytics Context (1-2 weeks)

**Goal**: Apply hexagonal architecture to Records service.

**Steps**:

1. **Define bounded context boundary**
   - Identify aggregate root (Record)
   - Define use cases (RecordQuizAttempt, GetUserHistory, etc.)
   - Define ports

2. **Implement hexagonal structure**
   - Domain models (Record, RecordItem)
   - Use case interfaces
   - Adapters (persistence, quiz management, vocabulary library)

3. **Replace FeignClient with ports**
   - Define `QuizManagementPort` for quiz provision data
   - Define `VocabularyLibraryPort` for review count updates
   - Implement adapters

**Verification**:
- Record creation/retrieval works
- Quiz submission flow works end-to-end
- Performance tracking accurate
- Analytics queries performant

### Phase 6: Eliminate Customers Service (1 week)

**Goal**: Remove unnecessary service wrapper around Keycloak.

**Steps**:

1. **Move authentication to Gateway**
   - Configure Gateway to validate JWT tokens
   - Extract user ID from JWT claims
   - Add user context to request headers

2. **Update services to read JWT claims**
   - Remove `CustomerClient` from all services
   - Read user ID from `SecurityContextHolder` or request headers
   - Update security configurations

3. **Decommission Customers service**
   - Remove service
   - Update Gateway routing
   - Remove from Eureka registry

**Verification**:
- Authentication still works
- User ID correctly extracted in all services
- No broken dependencies
- Security not compromised

---

## Critical Files for Implementation

### Current Files to Refactor

**Quiz Management Context**:
- `./quizzes/src/main/java/com/quizguru/quizzes/service/impl/QuizServiceImpl.java`
- `./quizzes/src/main/java/com/quizguru/quizzes/model/Quiz.java`
- `./quizzes/src/main/java/com/quizguru/quizzes/controller/QuizController.java`
- `./generates/src/main/java/com/quizguru/generates/service/impl/GenerateServiceImpl.java`

**Vocabulary Library Context**:
- `./libraries/src/main/java/com/quizguru/libraries/model/WordSet.java` (remove quiz_id FK)
- `./libraries/src/main/java/com/quizguru/libraries/service/impl/LibraryServiceImpl.java`
- `./libraries/src/main/java/com/quizguru/libraries/controller/LibraryController.java`

**Learning Analytics Context**:
- `./records/src/main/java/com/quizguru/records/service/impl/RecordServiceImpl.java`
- `./records/src/main/java/com/quizguru/records/controller/RecordController.java`

**Database Schemas**:
- `./quizzes/src/main/resources/schema.sql`
- `./libraries/src/main/resources/schema.sql`
- `./records/src/main/resources/schema.sql`
- `./docker-compose/default/init-scripts/create_databases.sql`

---

## Expected Outcomes

### Architecture Benefits

1. **Clear Bounded Contexts**: Services organized by business capabilities, not database tables
2. **Reduced Coupling**: Domain events replace synchronous FeignClient calls
3. **Simplified Database**: Single database with schemas instead of 6 separate databases
4. **Testability**: Hexagonal architecture enables testing domain logic without frameworks
5. **Maintainability**: Clear separation of concerns (domain, application, infrastructure)
6. **Flexibility**: Easy to swap implementations (AI providers, persistence, external APIs)

### Operational Benefits

1. **Simplified Deployment**: 3 services instead of 5 (40% reduction)
2. **Easier Monitoring**: Fewer databases and services to monitor
3. **Better Performance**: Reduced network hops, efficient queries
4. **Clearer Ownership**: Each context has clear business capability ownership
5. **Cost Reduction**: Single database instance vs 6 separate instances

### Development Benefits

1. **Faster Onboarding**: Clear architecture patterns
2. **Easier Testing**: Domain logic testable in isolation (fast unit tests)
3. **Better Code Organization**: Hexagonal structure provides clear guidelines
4. **Reduced Cognitive Load**: Bounded contexts align with business understanding
5. **Faster Feature Development**: Clear boundaries reduce coordination overhead

---

## Risks and Mitigations

### Risk 1: Data Migration Complexity

**Risk**: Migrating 6 databases to 1 could cause data loss or corruption.

**Mitigation**:
- Perform migration in staging environment first
- Create comprehensive backup before migration
- Implement data validation scripts (row counts, checksums, referential integrity)
- Use blue-green deployment strategy
- Keep old databases for rollback period (1 week minimum)
- Test rollback procedure before cutover

### Risk 2: Breaking Changes During Refactoring

**Risk**: Refactoring could break existing functionality.

**Mitigation**:
- Implement comprehensive integration tests before refactoring
- Use feature flags for gradual rollout
- Maintain backward compatibility during transition
- Perform incremental refactoring (one context at a time)
- Keep API contracts unchanged
- Monitor error rates and rollback if needed

### Risk 3: Event-Based Communication Complexity

**Risk**: Replacing synchronous calls with events adds eventual consistency complexity.

**Mitigation**:
- Start with synchronous adapters, migrate to events gradually
- Implement idempotent event handlers (handle duplicate events)
- Add event replay capability for debugging
- Monitor event processing lag
- Implement dead letter queues for failed events
- Add compensating transactions for rollback scenarios

### Risk 4: Team Learning Curve

**Risk**: Team unfamiliar with DDD and Hexagonal Architecture.

**Mitigation**:
- Conduct training sessions on DDD and Hexagonal Architecture
- Create reference implementation (Quiz Management context)
- Pair programming during initial implementation
- Document patterns and decisions in ADRs (Architecture Decision Records)
- Code reviews focused on architecture compliance
- Gradual adoption (start with one context)

### Risk 5: Performance Degradation

**Risk**: New architecture could introduce performance issues.

**Mitigation**:
- Establish performance baselines before refactoring
- Monitor key metrics (response time, throughput, error rate)
- Load test each phase before production deployment
- Optimize database queries (indexes, query plans)
- Implement caching where appropriate
- Profile and optimize hot paths

---

## Success Criteria

1. **3 bounded contexts** clearly defined and implemented
2. **Single database** with schema separation (or 3 databases aligned to contexts)
3. **Hexagonal architecture** applied to all contexts
4. **No cross-database foreign keys** (replaced with domain events or correlation IDs)
5. **All existing functionality** works after refactoring (100% feature parity)
6. **Test coverage** maintained or improved (target: 80%+ for domain logic)
7. **Deployment complexity** reduced (3 services instead of 5)
8. **Clear documentation** of bounded contexts and architecture patterns
9. **Performance** maintained or improved (response time within 10% of baseline)
10. **Team confidence** in new architecture (measured via survey)

---

## Timeline Summary

| Phase | Duration | Effort |
|-------|----------|--------|
| Phase 1: Merge Generates into Quiz Management | 2-3 weeks | High |
| Phase 2: Implement Hexagonal Architecture | 3-4 weeks | High |
| Phase 3: Database Consolidation | 2-3 weeks | Medium |
| Phase 4: Refactor Vocabulary Library | 2-3 weeks | Medium |
| Phase 5: Refactor Learning Analytics | 1-2 weeks | Low |
| Phase 6: Eliminate Customers Service | 1 week | Low |
| **Total** | **11-16 weeks** | **~3-4 months** |

**Note**: Timeline assumes 1-2 developers working full-time. Adjust based on team size and availability.

---

## Conclusion

This refactoring plan addresses the fundamental architectural issues in your quiz-guru-server by:

1. **Eliminating the Entity Service Anti-Pattern** through proper Bounded Contexts
2. **Consolidating the distributed monolith** into a cohesive architecture
3. **Reducing coupling** via domain events and clear interfaces
4. **Improving maintainability** through Hexagonal Architecture
5. **Simplifying operations** by reducing services and databases

The migration strategy is incremental and low-risk, with clear verification steps at each phase. The end result will be a more maintainable, testable, and scalable system aligned with business capabilities rather than database tables.
