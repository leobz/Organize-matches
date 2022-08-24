# organize-matches

Run backend - MacOS/Linux:
```shell
cd backend
mvn clean install
java -jar target/matches-organizer-0.0.1-SNAPSHOT.jar
```

Para probar la aplicación se puede ejecutar un curl por ejemplo a:

```shell
curl --location --request GET 'http://localhost:8081/matches'
```