FROM openjdk:8-jre-alpine
COPY ./target/data-processing-app-1.0-SNAPSHOT.jar /tmp/data-processing-app/
WORKDIR /tmp/data-processing-app
EXPOSE 8080
CMD ["java", "-jar", "data-processing-app-1.0-SNAPSHOT.jar"]