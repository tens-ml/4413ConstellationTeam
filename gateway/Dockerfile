# Use a base image that supports your Java version
FROM maven:3.8.5-openjdk-17 AS build
COPY /src /home/app/src
COPY //pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17
<<<<<<< HEAD
COPY --from=build /home/app/target/*.jar /usr/src/myapp/app.jar
ARG PORT=8080
ENV SERVER_PORT=$PORT
EXPOSE $PORT
ENTRYPOINT ["java","-jar","/usr/src/myapp/app.jar", "--server.port=${SERVER_PORT}"]
=======
COPY --from=build /home/app/target/demo-0.0.1-SNAPSHOT.jar /usr/src/myapp/demo.jar
ARG PORT=8080
ENV SERVER_PORT=$PORT
EXPOSE $PORT
ENTRYPOINT ["java","-jar","/usr/src/myapp/demo.jar", "--server.port=${SERVER_PORT}"]]
>>>>>>> 0fd62e0e204462a21f92ca800d9910f0df23a7d2
