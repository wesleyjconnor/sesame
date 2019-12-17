# Sesame
This is a Spring Boot application that serves an API to solve the Sesame coding challenge.

## Requirements
* Maven (any version) 
* Java jdk11

## To test run
```shell script
$ mvn clean install
```

## To run locally
```shell script
$ docker build -t sesame:1.0 .
$ docker run --name sesame -p 8080:8080 sesame:1.0
```

## Endpoint
Just hit `http://localhost:8080/api` to get the JSON response
    
## Comments
Went great, few good challenges around the timezone but it was fun.
    
## Todos left
Can generify easily to support more external vendors but seemed out of scope
