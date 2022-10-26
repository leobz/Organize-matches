# organize-matches

## Visión General

La aplicación está escrita en **Java**, con el framework **Springboot** que trae varias facilidades para trabajar en aplicaciones Web.

Para la persistencia se optó por implementar el pattern Repository, utilizando **MongoDB**

### URL Front End

http://localhost:3001

### URL Documentación de la API

http://localhost:8081/swagger-ui/index.html

### URL UI Mongo-Express
http://localhost:8082/

---
## Comandos básicos

Build docker image - MacOS/Linux:
```
make build # Crea imagen docker de todos los componentes (backend+frontend)
```

Run app locally - MacOS/Linux:
```
# Levanta todos los contenedores localmente.
# Necesita realizar un 'make build' previamente para tomar los ultimos cambios
make dev
```

Stop app locally - MacOS/Linux:
```
make stop # Finaliza la ejecución todos los contenedores
```

## MongoDB

Se decidió usar la base de datos noSQL MongoDB porque nos parece que la funcionalidad puede variar a futuro, y
esta DDBB es más flexible para agregar nuevas funcionalidades que un Cassandra por ejemplo.

**Para que funcione la creación de la base de datos, se deberá crear en la raiz del proyecto `/organize-matches`,
un archivo llamado `mongo-pass.txt` con la password del usuario `root` de la base de datos.**

## Testing

Para ver los test de carga HTTP disponibles, ejecutar el siguiente comando:

```shell
make help | grep lt
```

Para modificar parametros de los tests de carga, modificar las siguientes variables en el Makefile:

```
VEGETA_DURATION = 10s # Cantidad de segundos del test
VEGETA_RATE = 0 # Maxima cantidad de request por segundo. (0 es infinito)
VEGETA_MAX_WORKERS = 1000 # Maxima cantidad de usuarios (Nota: 1 usuario puede hacer N request)
```

## Otros comandos

Run docker project with volume - MacOS/Linux:
```
# Levanta contenedores con un volumen dedicado al container de Mongo.
# Necesita realizar un 'make build' previamente para tomar los ultimos cambios
make prod
```
