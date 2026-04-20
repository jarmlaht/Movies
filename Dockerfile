# Build stage using Maven and Temurin JDK 17
FROM maven:3.8.4-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Run stage using Temurin JRE 17 (smaller and more secure)
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]