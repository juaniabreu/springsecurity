FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/vichenzo-0.0.1.jar
COPY ${JAR_FILE} app_vichenzo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","app_vichenzo.jar"]