BE_DOCKER_TAG = be-organize-matches:latest
FE_DOCKER_TAG = fe-organize-matches:latest


PHONY: help
help: ## Imprime targets y ayuda 
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: dev
dev: ## Compila y ejecuta localmente el backend
# TODO: Migrar a llamada de docker-compose cuando se cuenten con otros servicios
	cd backend; \
	mvn clean install; \
	java -jar target/matches-organizer-0.0.1-SNAPSHOT.jar

.PHONY: build
build: ## Crea imagen docker del todos los componentes (backend y frontend)
# TODO: Migrar a llamada de docker-compose cuando se cuenten con otros servicios
	cd backend; \
	docker build -t $(BE_DOCKER_TAG) .
	cd frontend; \
	docker build -t $(FE_DOCKER_TAG) .

.PHONY: clean
clean: ## Elimina los containers e imagenes (no borra la cache)
	docker container kill be-organize-matches fe-organize-matches; \
	docker container rm be-organize-matches fe-organize-matches; \
	docker image rm --no-prune be-organize-matches fe-organize-matches;

.PHONY: lt-counter
lt-counter: ## Test de Carga HTTP del endpoint /matches/counter
	docker run --network=host --rm -i peterevans/vegeta sh -c \
	"echo 'GET http://localhost:8081/matches/counter' | vegeta attack -duration=1s | tee results.bin | vegeta report"

.PHONY: prod
prod: ## Levanta componentes del proyecto, buildea en caso de no encontrar la imagen correspondiente.
	docker-compose up -d;

.PHONY: stop
stop: ## Finaliza la ejecución de los componentes del proyecto
	docker-compose down

.PHONY: lt-matches
lt-matches: ## lt-matches token=<jwt-token>. Test de Carga HTTP del endpoint GET '/matches'.
	@if [ -z $(token) ];\
	then \
		echo "¡Token no proporcionado!\n   Uso: make lt-matches token=<jwt-token>" ;\
	else \
		docker run --network=host --rm -i peterevans/vegeta sh -c \
		"echo 'GET http://localhost:8081/matches' | vegeta attack -header 'Cookie: token=$(token)' -duration=1s | tee results.bin | vegeta report" ; \
	fi