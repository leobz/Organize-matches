FROM amazoncorretto:11.0.16-alpine3.15
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
