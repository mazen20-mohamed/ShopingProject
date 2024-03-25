FROM maven:3-openjdk-17 As build

# Set the working directory in the container
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim

COPY --from=build /target/productService-0.0.1-SNAPSHOT.jar demo.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "productService.jar"]