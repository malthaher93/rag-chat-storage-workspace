
FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/rag-chat-storage-service.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]