# organize-matches


## Comandos básicos
Run backend locally- MacOS/Linux:
```
make dev # Compila con maven y ejecuta localmente el backend con java
```

Build docker image - MacOS/Linux:
```
make build # Crea imagen docker de todos los componentes (backend+frontend)
```

Run docker project - MacOS/Linux:
```
# Levanta componentes del proyecto, buildea en caso de no encontrar la imagen correspondiente. Ver status con ´docker ps´
make prod


# Si no funciona el comando anterior, usar:
docker compose up -d
```

Stop docker project - MacOS/Linux:
```
# Finaliza la ejecución de los componentes del proyecto
make stop

# Si no funciona el comando anterior, usar:
docker compose down
```

---
## URL del front

```
http://localhost:3001
```

## Documentación de la API

```
http://localhost:8081/swagger-ui/index.html
```

## Visión General

Para probar la aplicación se puede ejecutar un curl por ejemplo a:

```shell
curl --location --request GET 'http://localhost:8081/matches'
```

La aplicación es en Java, con el framework Springboot que trae varias facilidades para trabajar en aplicaciones Web.

Para la **persistencia**, se optó por implementar el pattern Repository para tener una aproximación similar 
al manejo de listas para gestionar los partidos. En este caso se creó la interfaz `MatchRepository`, 
cuya única implementación en la primer entrega es `InMemoryMatchRepository`, pero en futuras entregas esta misma 
interfaz entendemos servirá para gestionar una BBDD noSql.

## MongoDB

Se decidió usar la base de datos noSQL MongoDB porque nos parece que la funcionalidad puede variar a futuro, y
esta DDBB es más flexible para agregar nuevas funcionalidades.

Para que funcione la creación de la base de datos, se deberá crear en la raiz del proyecto `/organize-matches`,
un archivo llamado `mongo-pass.txt` con la password del usuario `root` de la base de datos.

### Mongo-Express
http://localhost:8082/

## Testing

Para ver los test de carga HTTP disponibles, ejecutar el siguiente comando:

```shell
make help | grep lt
```