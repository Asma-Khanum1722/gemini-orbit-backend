# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the maven project file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Copy the maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Make the maven wrapper executable
RUN chmod +x mvnw

# Build the application
# Skip tests to speed up the build
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Run the jar file
# We use a wildcard to find the jar since the version might change
CMD ["java", "-jar", "target/geminiapi-0.0.1-SNAPSHOT.jar"]
