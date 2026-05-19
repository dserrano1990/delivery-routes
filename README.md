# Delivery Routes API

Backend REST API desarrollada con Spring Boot para la gestión de rutas de entrega, órdenes, vehículos y usuarios.

El proyecto implementa autenticación JWT, control de roles, generación de reportes PDF y arquitectura basada en capas utilizando buenas prácticas de desarrollo backend.

---

# Tecnologías utilizadas

* Java 17
* Spring Boot 4
* Spring Security
* JWT Authentication
* Spring Data JPA
* MySQL
* Maven
* OpenPDF
* Flying Saucer PDF
* Jsoup

---

# Características principales

* CRUD de órdenes
* CRUD de vehículos
* CRUD de usuarios
* Gestión de puntos de entrega
* Autenticación y autorización con JWT
* Roles de usuario (ADMIN / USER)
* Validaciones con Jakarta Validation
* Generación de reportes PDF
* Arquitectura en capas
* Manejo global de errores
* DTOs para requests y responses

---

# Arquitectura del proyecto

El proyecto está organizado utilizando una arquitectura por capas:

```text
controllers/
services/
repositories/
entities/
dto/
security/
exceptions/
config/
```

## Descripción

* **controllers**: Manejan las peticiones HTTP.
* **services**: Contienen la lógica de negocio.
* **repositories**: Acceso a datos mediante JPA.
* **entities**: Modelos de base de datos.
* **dto**: Objetos para requests y responses.
* **security**: Configuración JWT y filtros de autenticación.
* **exceptions**: Manejo global de excepciones.
* **config**: Configuraciones generales e inicialización de datos.

---

# Seguridad

La aplicación utiliza Spring Security con autenticación basada en JWT.

## Roles disponibles

* ADMIN
* USER

## Funcionalidades protegidas

* Creación y edición de órdenes
* Gestión de usuarios
* Gestión de vehículos
* Generación de reportes PDF

---

# Endpoints principales

## Órdenes

| Método | Endpoint           | Descripción          |
| ------ | ------------------ | -------------------- |
| GET    | `/api/orders`      | Obtener órdenes      |
| GET    | `/api/orders/{id}` | Obtener orden por ID |
| POST   | `/api/orders`      | Crear orden          |
| PUT    | `/api/orders/{id}` | Actualizar orden     |
| DELETE | `/api/orders/{id}` | Eliminar orden       |

## Vehículos

| Método | Endpoint             | Descripción             |
| ------ | -------------------- | ----------------------- |
| GET    | `/api/vehicles`      | Obtener vehículos       |
| GET    | `/api/vehicles/{id}` | Obtener vehículo por ID |
| POST   | `/api/vehicles`      | Crear vehículo          |
| PUT    | `/api/vehicles/{id}` | Actualizar vehículo     |
| DELETE | `/api/vehicles/{id}` | Eliminar vehículo       |

## Usuarios

| Método | Endpoint          | Descripción            |
| ------ | ----------------- | ---------------------- |
| GET    | `/api/users`      | Obtener usuarios       |
| GET    | `/api/users/{id}` | Obtener usuario por ID |
| POST   | `/api/users`      | Crear usuario          |
| PUT    | `/api/users/{id}` | Actualizar usuario     |
| DELETE | `/api/users/{id}` | Eliminar usuario       |

## Reportes

| Método | Endpoint                            | Descripción          |
| ------ | ----------------------------------- | -------------------- |
| GET    | `/api/reports/orders/{orderId}/pdf` | Generar PDF de orden |

---

# Configuración del proyecto

## Requisitos

* Java 17+
* Maven
* MySQL

## Clonar repositorio

```bash
git clone <repository-url>
```

## Configurar base de datos

Editar el archivo:

```text
src/main/resources/application.properties
```

Configurar:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/delivery_routes
spring.datasource.username=root
spring.datasource.password=tu_password
```

---

# Ejecutar el proyecto

## Compilar

```bash
mvn clean install
```

## Ejecutar

```bash
mvn spring-boot:run
```

La aplicación quedará disponible en:

```text
http://localhost:8080
```

---

# Ejemplo de autenticación

## Login

```http
POST /login
```

### Request

```json
{
  "username": "admin",
  "password": "admin123"
}
```

### Response

```json
{
  "token": "jwt_token"
}
```

## Header Authorization

```text
Authorization: Bearer jwt_token
```

---

# Generación de PDF

El proyecto incluye generación de reportes PDF para órdenes utilizando:

* OpenPDF
* Flying Saucer

Los reportes se generan dinámicamente desde plantillas HTML.

---

# Validaciones

La API utiliza Jakarta Validation para validar datos de entrada:

* Campos requeridos
* Validaciones de formato
* Restricciones de longitud
* Manejo global de errores

---

# Futuras mejoras

* Dockerización
* Swagger/OpenAPI
* Tests unitarios e integración
* Paginación y filtros
* Refresh tokens
* Optimización de rutas
* Integración con mapas

---

# Autor

Desarrollado por David Serrano.
