FROM openjdk:alpine

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

COPY ./target/spring-boot-angular-1.0.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]