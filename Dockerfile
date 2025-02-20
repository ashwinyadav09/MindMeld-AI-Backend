# ---- Step 1: Build Stage ----
FROM eclipse-temurin:23-jdk AS build

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# ---- Step 2: Run Stage ----
FROM eclipse-temurin:23-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
