# organize-matches

Run backend locally- MacOS/Linux:
```
make dev # Compila y ejecuta localmente el backend
```

Build backend image - MacOS/Linux:
```
make build # Crea imagen docker del backend
```

Run backend image - MacOS/Linux:
```
make prod # Levanta componentes del proyecto
```

Run backend image - MacOS/Linux:
```
make stop # Finaliza la ejecución de los componentes del proyecto
```

---

Para probar la aplicación se puede ejecutar un curl por ejemplo a:

```shell
curl --location --request GET 'http://localhost:8081/matches'
```