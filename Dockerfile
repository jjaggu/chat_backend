# Step 1: Use a base image with Java
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file built by Maven to the container
COPY target/Chat-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port on which your application runs
EXPOSE 8080

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
