FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src ./src
RUN ./mvnw clean package -DskipTests
EXPOSE 8085
CMD ["java", "-jar", "target/generates-0.0.1-SNAPSHOT.jar"]