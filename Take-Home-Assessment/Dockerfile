# Stage 1: Build with Maven on JDK 21
FROM maven:3.9.10-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only the POM to cache dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy source and build the fat JAR (skip tests)
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime image
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
