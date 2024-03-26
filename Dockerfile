FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-idk -y
COPY . .
RUN apt-get install maven -y
# Build the application
RUN mvn clean install

FROM openjdk:17.0.1-jdk-slim

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Copy the JAR file from the build stage
COPY --from=build /target/productService-1.0.0.jar demo.jar

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "demo.jar"]
