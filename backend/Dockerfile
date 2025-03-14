FROM gradle:8.12-jdk21-alpine AS builder
COPY . /home/source/java
WORKDIR /home/source/java
USER root
RUN chown -R gradle /home/source/java
USER gradle
RUN gradle clean build

FROM openjdk:21-slim
WORKDIR /home/application/java
COPY --from=builder "/home/source/java/build/libs/*.jar" app.jar
COPY openflights openflights
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/home/application/java/app.jar"]
