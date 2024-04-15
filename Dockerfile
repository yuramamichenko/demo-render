FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-22-jdk -y
COPY . .
RUN ./mvnw package --no-daemon

FROM openjdk:22-jdk
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
