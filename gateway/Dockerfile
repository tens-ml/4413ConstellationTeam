# Use a base image that supports your Java version
FROM openjdk:17
ARG PORT=8080
ENV SERVER_PORT=$PORT
EXPOSE $PORT
ADD target/*.jar gateway.jar
ENTRYPOINT ["java", "-jar", "/gateway.jar", "--server.port=${SERVER_PORT}"]