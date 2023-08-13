FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/urly-backend.jar"]
EXPOSE 8080

ADD build/libs/backend-*.jar /app/urly-backend.jar
