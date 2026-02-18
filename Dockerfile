FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="sikhwalmayank251@gmail.com"
LABEL description="Task Management REST API with Spring Boot"

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]