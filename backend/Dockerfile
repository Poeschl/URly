FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/urly-backend.jar"]
EXPOSE 8080
RUN sed -i -r '/(\s+)index(\s+)index/a try_files $uri $uri/ /index.html;' /etc/nginx/conf.d/default.conf

ADD build/libs/backend-*.jar /app/urly-backend.jar
