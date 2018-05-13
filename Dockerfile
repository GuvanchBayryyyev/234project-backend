FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY /target/project.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar","--imageServer=http://54.186.33.226:1122/images/"]
