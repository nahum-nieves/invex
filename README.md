# invex

## Requisitos
- Java 17 o superior
- Gradle (opcional, el wrapper está incluido)

## Instalación y compilación

1. Clona el repositorio:
   ```sh
   git clone <url-del-repo>
   cd invex
   ```
2. Compila el proyecto y genera el jar:
   ```sh
   ./gradlew bootJar
   ```

## Ejecución local

Puedes ejecutar la aplicación localmente con:
```sh
./gradlew bootRun
```

Por defecto, usará la configuración de H2 o la que esté en `application.yaml`, puedes apuntar a una base de datos local si lo prefieres.

## Pruebas

Para ejecutar los tests:
```sh
./gradlew test
```

## Documentación OpenAPI/Swagger

- Accede a la documentación interactiva en: http://localhost:8080/swagger-ui.html

## Endpoints de monitoreo (Actuator)

- Accede a información de salud y detalles de la app en: http://localhost:8080/actuator
- Ejemplo de endpoint útil: http://localhost:8080/actuator/info

## Variables importantes

- Las llaves privadas/públicas para JWT deben estar en la carpeta `keys/`. Aún no han sido sobreescritas para los ambientes de dev, qa o prod.
- Puedes configurar perfiles y logging en los archivos `application.yaml`,.


---

¿Dudas? Revisa los archivos de configuración o abre un issue.
