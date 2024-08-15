FROM amazoncorretto:17-alpine-jdk

# Create a directory inside the container for the app
WORKDIR /app

# Copy everything from the current directory (ie the directory where the Dockerfile is located) to the app directory (which is located inside the container)
COPY . .

# Build the app inside the container. But disable testing (because it is not connected to the db while we are building it)
# Building the app will result in ./build/libs/containerized_project-0.0.1-SNAPSHOT.jar, which will be run in the next-next step
RUN ./gradlew clean build -x test

# Expose port 9000
EXPOSE 9000

# Run the app
CMD ["java", "-jar", "./build/libs/containerized_project-0.0.1-SNAPSHOT.jar"]

