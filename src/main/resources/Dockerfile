# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-20-alpine
RUN mkdir -p /app
WORKDIR /app
COPY ../../../pom.xml /app
COPY ../../../src /app/src
RUN mvn package
EXPOSE 8080
CMD ["java", "-jar", "target/schedule-0.0.1-SNAPSHOT.jar"]
