FROM maven:3.6.1-jdk-11 as mvnpackage

WORKDIR /src

COPY . .
RUN mvn clean install -DskipTests

#========================

FROM openjdk:11-jdk-slim

COPY --from=mvnpackage /src/target/app.jar /

ENTRYPOINT java -jar app.jar
