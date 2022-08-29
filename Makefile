BE_DOCKER_TAG = be-organize-matches:latest

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
build: ## Crea imagen docker del backend
# TODO: Migrar a llamada de docker-compose cuando se cuenten con otros servicios
	cd backend; \
	docker build -t $(BE_DOCKER_TAG) .

.PHONY: prod
prod: ## Ejecuta imagen docker del backend
# TODO: Migrar a llamada de docker-compose cuando se cuenten con otros servicios
	docker run -p 8081:8080 $(BE_DOCKER_TAG)
