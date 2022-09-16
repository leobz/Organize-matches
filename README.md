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