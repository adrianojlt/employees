FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar /app/application.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_ACTIVE_PROFILE:-dev} -jar /app/application.jar"]