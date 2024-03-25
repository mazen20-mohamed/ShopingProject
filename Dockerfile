FROM maven:3-openjdk-17 AS build

# Copy the Maven project
COPY . .

# Build the application
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim

# Copy the JAR file from the build stage
COPY --from=build /target/productService-1.0.0.jar demo.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "demo.jar"]
