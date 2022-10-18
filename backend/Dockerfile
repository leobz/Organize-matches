FROM maven:3.8.1-jdk-11 as build
WORKDIR /workspace
COPY pom.xml pom.xml
RUN mvn dependency:go-offline &&\
    mvn clean verify
COPY src/main/java src/main/java
RUN mvn clean package &&\
    ls target/ &&\
    ARTIFACT=$(grep "artifactId" target/maven-archiver/pom.properties | cut -d '=' -f2); \
    VERSION=$(grep "version" target/maven-archiver/pom.properties|cut -d '=' -f2); \
    mv target/${ARTIFACT}-${VERSION}.jar target/app.jar


#################################################################################################

FROM amazoncorretto:11-alpine as prod

VOLUME /opt/logs
EXPOSE 8081/tcp

COPY --from=build /workspace/target/app.jar /opt/
COPY src/main/resources/* /opt/

WORKDIR /opt

# -Xmx1000m: Limita cantidad maxima de consumo de memoria en HEAP a 1000 MB
# -Xms512m:  Limita cantidad maxima de consumo de memoria en HEAP a 512 MB
# -XX:+HeapDUmpOnOutOfMemoryError: Genera un Dump del Heap cuando se propaga un java.lang.OutOfMemoryError
# -XX:HeapDumpPath=/opt/logs: Path destino donde se generará el Dump del Heap
# More info:
# - https://www.oracle.com/java/technologies/javase/vmoptions-jsp.html
# - https://www.javadevjournal.com/java/jvm-parameters/
CMD ["-Xmx1000m", "-Xms512m", "-XX:+HeapDUmpOnOutOfMemoryError", "-XX:HeapDumpPath=/opt/logs"]

ENTRYPOINT java -jar ./app.jar --spring.profiles.active=$ENV --spring.data.mongodb.uri=mongodb://root:$(cat /run/secrets/mongo-pass)@mongo:27017/admin?authMechanism=SCRAM-SHA-256
