FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src ./src
RUN ./mvnw clean package -DskipTests
EXPOSE 8072
CMD ["java", "-jar", "target/gateway-0.0.1-SNAPSHOT.jar"]