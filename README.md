# organize-matches

## Visión General

La aplicación está escrita en **Java**, con el framework **Springboot** que trae varias facilidades para trabajar en aplicaciones Web. Para la persistencia se optó por implementar el pattern Repository, utilizando **MongoDB**

## Contenido 
- [Setup](#setup)  
- [Comandos básicos: Desarrollo](#commandsdev)  
  - [URLs](#urlsdev)  
- [Comandos básicos: Producción](#commandsprod)  
- [Comandos básicos: Tests de Carga](#loadtesting)  
- [Documentación](#doc)  
  - [Diagrama de arquitectura](#doc-arq)  
  - [MongoDB](#doc-mongo)  


---

<a name="setup"/>

## Setup

1. Instalar Docker + Docker Compose:  https://www.docker.com/

2. En la raiz del proyecto `/organize-matches`, crear archivo `.env` con el siguiente contenido:

```
DOCKER_REGISTRY=tacs2022
# Opcional
# TELEGRAM_BOT_TOKEN=<TOKEN-para-activar-Telegram-Text-Commands>
```

3. Opcional: Loggearse al docker registry (necesario para push de imágenes docker)

```
$ docker login -u tacs2022 -p <registry-password>
```
4. Para que funcione la creación de la base de datos, se deberá crear en la raiz del proyecto `/organize-matches`,
un archivo llamado `mongo-pass.txt` con la password del usuario `root` de la base de datos (Ej: Pass1234!@).

---

<a name="commandsdev"/>

## Comandos básicos: Desarrollo

Build docker image - MacOS/Linux:
```
$ make build # Crea imagen docker de todos los componentes (backend+frontend)
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

Para subir a dockerhub una imagen con la versión a deployar:
```shell
make push-images
```

<a name="urlsdev"/>

### URLs Desarrollo

- Front End http://localhost:3001
- Documentación Back End API http://localhost:8081/swagger-ui/index.html
- UI Mongo-Express http://localhost:8082/

---

<a name="commandsprod"/>

## Comandos básicos: Producción

Dentro de instancia EC2:

1. Descargar ultima imagen taggeada del docker registry
```
make pull-images
```

2. Run docker project with volume - MacOS/Linux:
```
# Detener/Limpiar contenedores antiguos
make stop
```

2. Run docker project with volume - MacOS/Linux:
```
# Levantar contenedores con un volumen dedicado al container de Mongo.
make prod
```

---

<a name="loadtesting"/>

## Tests de carga

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


<a name="doc"/>

## Documentación

<a name="doc-arq"/>

### Diagrama de arquitectura


![Architecture-Diagram](doc/Architecture-Diagram.png)

<a name="doc-mongo"/>

### MongoDB

Se decidió usar la base de datos noSQL MongoDB porque nos parece que la funcionalidad puede variar a futuro, y
esta DDBB es más flexible para agregar nuevas funcionalidades que un Cassandra por ejemplo.