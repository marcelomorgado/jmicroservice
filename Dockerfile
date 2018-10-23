FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/jmicroservice-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} jmicroservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/jmicroservice.jar"]