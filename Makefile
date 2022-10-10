BE_DOCKER_TAG = be-organize-matches:latest
FE_DOCKER_TAG = fe-organize-matches:latest
VEGETA_DURATION = 5s
VEGETA_RATE = 0
VEGETA_MAX_WORKERS = 1000

PHONY: help
help: ## Imprime targets y ayuda 
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: dev
dev: ## Compila y ejecuta localmente el backend
	cd backend; \
	mvn clean install; \
	docker-compose up -d mongo; \
	java -jar target/matches-organizer-0.0.1-SNAPSHOT.jar \
	--spring.data.mongodb.uri=mongodb://root:$(shell cat mongo-pass.txt)@localhost:27017/admin?authMechanism=SCRAM-SHA-256

.PHONY: build
build: ## Crea imagen docker del todos los componentes (backend y frontend)
	cd backend; \
	docker build -t $(BE_DOCKER_TAG) .
	cd frontend; \
	docker build -t $(FE_DOCKER_TAG) .

.PHONY: clean
clean: ## Elimina los containers e imagenes (no borra la cache)
	docker container kill be-organize-matches fe-organize-matches organize-matches_mongo_1 organiza-matches_mongo-express_1; \
	docker container rm be-organize-matches fe-organize-matches organize-matches_mongo_1 organiza-matches_mongo-express_1; \
	docker image rm --no-prune be-organize-matches fe-organize-matches organize-matches_mongo_1 organiza-matches_mongo-express_1;

.PHONY: prod
prod: ## Levanta componentes del proyecto, buildea en caso de no encontrar la imagen correspondiente.
	docker-compose up -d;

.PHONY: stop
stop: ## Finaliza la ejecución de los componentes del proyecto
	docker-compose down

.PHONY: lt-counter
lt-counter: ## Test de Carga HTTP del endpoint GET '/matches/counter'
	docker run --network=host --rm -i peterevans/vegeta sh -c \
	"echo 'GET http://localhost:8081/matches/counter' | vegeta attack -duration=$(VEGETA_DURATION) -rate=$(VEGETA_RATE) -max-workers=$(VEGETA_MAX_WORKERS) | tee results.bin | vegeta report"

.PHONY: lt-matches
lt-matches: ## lt-matches token=<jwt-token>. Test de Carga HTTP del endpoint GET '/matches'.
	@if [ -z $(token) ];\
	then \
		echo "¡Token no proporcionado!\n   Uso: make lt-matches token=<jwt-token>" ;\
	else \
		docker run --network=host --rm -i peterevans/vegeta sh -c \
		"echo 'GET http://localhost:8081/matches' | vegeta attack -header 'Cookie: token=$(token)' -duration=$(VEGETA_DURATION) -rate=$(VEGETA_RATE) -max-workers=$(VEGETA_MAX_WORKERS) | tee results.bin | vegeta report" ; \
	fi

.PHONY: lt-new-match
lt-new-match: ## lt-new-match token=<jwt-token>. Test de Carga HTTP del endpoint POST '/matches'.
	@if [ -z $(token) ];\
	then \
		echo "¡Token no proporcionado!\n   Uso: make lt-new-match token=<jwt-token>" ;\
	else \
		docker run --network=host --rm -i peterevans/vegeta sh -c\
		"printf 'POST http://localhost:8081/matches\n@./body.json' > target.txt && \
		echo '{ \"name\": \"test\", \"location\": \"test\", \"dateAndTime\": \"2099-01-01T00:00:00.000Z\" }' > body.json && \
		vegeta attack -targets=target.txt \
		-header 'Cookie: token=$(token)' \
		-header 'Content-Type: application/json' \
		-format=http -duration=$(VEGETA_DURATION) -rate=$(VEGETA_RATE) -max-workers=$(VEGETA_MAX_WORKERS) \
		| tee results.bin | vegeta report \
		&& rm body.json && rm target.txt && rm results.bin ";\
	fi