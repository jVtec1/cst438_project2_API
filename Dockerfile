# Multi-stage Docker build for Spring Boot application
# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and build files first (for better caching)
COPY gradlew gradlew.bat ./
COPY gradle/ gradle/
COPY build.gradle.temp build.gradle
COPY settings.gradle.temp settings.gradle

# Make gradlew executable (important for Linux containers)
RUN chmod +x gradlew

# Download dependencies (this layer will be cached if dependencies don't change)
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src/ src/

# Build the application (creates a JAR file)
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime - smaller image for production
FROM eclipse-temurin:17-jre-alpine AS runtime

# Create a non-root user for security
RUN addgroup -g 1001 -S appuser && \
    adduser -S -u 1001 -G appuser appuser

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Change ownership to non-root user
RUN chown appuser:appuser app.jar

# Switch to non-root user
USER appuser

# Expose the port your Spring Boot app runs on (default 8080)
EXPOSE 8080

# Health check to ensure the application is running
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/ || exit 1

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]