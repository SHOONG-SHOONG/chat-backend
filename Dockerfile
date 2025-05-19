FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY chat-backend/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
