FROM ubuntu:latest

FROM adoptopenjdk/openjdk21:alpine-jre

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/productService.jar /app/productService.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=production

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "productService.jar"]