# PowerTrack - Backend

Aplicación Inteligente para la Gestión y Seguimiento de Entrenamientos.

## Requisitos previos

- Java 17+
- Maven
- Docker Desktop (debe estar arrancado)
- IntelliJ IDEA

## Cómo ejecutar el proyecto

### Paso 1 — Arrancar la base de datos

Abre un terminal en la raíz del proyecto y ejecuta:

```bash
docker compose up -d
```

Comprueba que está corriendo:

```bash
docker ps
```

Deberías ver `powertrack-db` en la lista.

### Paso 2 — Arrancar el microservicio de IA (opcional)

Si quieres que la IA genere contenido personalizado, necesitas Python 3.9+.

En una terminal nueva, ve a la carpeta del proyecto PowerTrack-IA y ejecuta:

```bash
uvicorn main:app --reload
```

El microservicio arrancará en `http://localhost:8000`.

Si no arrancas FastAPI, el backend usará las reglas manuales de recomendación automáticamente.

### Paso 3 — Arrancar Spring Boot

Abre el proyecto en IntelliJ IDEA y ejecuta `PowerTrackBackendApplication.java`.

El backend arrancará en `http://localhost:8080`.

### Paso 4 — Ver la documentación de la API

Abre en el navegador:
http://localhost:8080/swagger-ui.html

### Paso 5 — Ver los emails de activación

Para ver los emails de activación de cuenta abre en el navegador:
http://localhost:8025

## Credenciales de la BD

| Campo    | Valor           |
|----------|-----------------|
| Host     | localhost       |
| Puerto   | 3306            |
| BD       | powertrack_db   |
| Usuario  | root            |
| Password | root            |

## Usuario administrador

| Campo    | Valor                |
|----------|----------------------|
| Username | admin                |
| Password | password             |
| Email    | admin@powertrack.com |

## Tecnologías utilizadas

- Spring Boot 3
- MySQL 8 + Docker
- JPA / Hibernate + Flyway
- Spring Security + JWT
- FastAPI (Python) - Microservicio IA
- RandomForest (scikit-learn) - Modelo ML
- Gemini API - Generación de contenido
