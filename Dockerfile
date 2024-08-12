# Build stage
FROM openjdk:21-jdk-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the application
RUN mvn clean package -Pprod -DskipTests

# Package stage
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar demo.jar

# Expose the application port
EXPOSE 5453

# Run the application
ENTRYPOINT ["java", "-jar", "demo.jar"]
