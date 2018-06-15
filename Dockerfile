FROM openjdk:8-jre-alpine
COPY ./target/data-processing-app-1.0-SNAPSHOT.jar /tmp/data-processing-app/
WORKDIR /tmp/data-processing-app
EXPOSE 8080
EXPOSE 5005
CMD ["java", "-jar", "data-processing-app-1.0-SNAPSHOT.jar"]
#CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005", "-jar", "data-processing-app-1.0-SNAPSHOT.jar"]
