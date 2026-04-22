# 📚 bookstore-api

API REST para la gestión de una librería en línea, construida con Spring Boot. Proyecto del curso **Desarrollo Empresarial** — Docente: Javier Charry.

---

## Tecnologías

| Herramienta | Versión mínima |
|---|---|
| Java | 17+ |
| Maven | 3.8+ |
| Spring Boot | 3.x |
| Git | 2.x |
| Docker (opcional) | 20+ |

**Base de datos:** H2 en memoria para desarrollo · PostgreSQL para producción

---

## Requisitos previos

- Java 17 o superior instalado
- Maven 3.8+
- Git
- Postman o Insomnia para probar endpoints
- (Opcional) Docker para levantar PostgreSQL local

---

## Configuración inicial

### 1. Clonar el repositorio

```bash
git clone https://github.com/<org>/bookstore-api.git
cd bookstore-api
```

### 2. Configurar variables de entorno

Crear un archivo `.env` en la raíz del proyecto o exportar la variable antes de ejecutar:

```bash
export JWT_SECRET=mi_clave_secreta_super_segura_de_al_menos_32_chars
```

> ⚠️ El proyecto **no arranca** sin la variable `JWT_SECRET` definida.

### 3. Revisar `application.yml`

El archivo de configuración se encuentra en `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:bookstoredb
  jpa:
    hibernate.ddl-auto: update
    show-sql: true

app:
  jwt:
    secret: ${JWT_SECRET}
    expiration: 86400000   # 24 horas

server:
  servlet:
    context-path: /api/v1
```

---

## Ejecución local

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La API quedará disponible en: `http://localhost:8080/api/v1`

### (Opcional) Levantar PostgreSQL con Docker

```bash
docker run --name bookstore-db \
  -e POSTGRES_DB=bookstoredb \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -p 5432:5432 \
  -d postgres:15
```

Luego cambiar la URL en `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bookstoredb
    username: admin
    password: admin
```

---

## Documentación interactiva (Swagger)

Una vez que la aplicación esté corriendo, acceder a:

```
http://localhost:8080/api/v1/swagger-ui.html
```

Para probar endpoints protegidos, hacer login y pegar el token en el botón **Authorize** con el formato:

```
Bearer <token>
```

---

## Endpoints principales

### Autenticación (públicos)

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/auth/register` | Registro de nuevo usuario |
| `POST` | `/auth/login` | Login — retorna token JWT |

### Catálogo de libros (GET público, escritura solo ADMIN)

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/books` | Listar libros (soporta paginación y filtros) |
| `GET` | `/books/{id}` | Obtener libro por ID |
| `POST` | `/books` | Crear libro — `ADMIN` |
| `PUT` | `/books/{id}` | Actualizar libro — `ADMIN` |
| `DELETE` | `/books/{id}` | Eliminar libro — `ADMIN` |

### Autores y categorías

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/authors` | Listar autores |
| `GET` | `/authors/{id}/books` | Libros de un autor |
| `DELETE` | `/authors/{id}` | Eliminar autor — `ADMIN` |
| `GET` | `/categories/{id}/books` | Libros de una categoría |

### Pedidos

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/orders` | Crear pedido — usuario autenticado |
| `GET` | `/orders/my` | Mis pedidos — usuario autenticado |
| `GET` | `/orders` | Todos los pedidos — solo `ADMIN` |

---

## Estructura del proyecto

```
com.taller.bookstore
├── config/          # BCrypt, CORS, OpenAPI/Swagger
├── controller/      # Endpoints HTTP
├── dto/
│   ├── request/     # Contratos de entrada
│   └── response/    # Contratos de salida
├── entity/          # Modelos JPA (User, Book, Author, Category, Order, OrderItem)
├── exception/
│   ├── custom/      # Excepciones de dominio
│   └── handler/     # @RestControllerAdvice
├── mapper/          # Conversión entidad ↔ DTO (sin librerías externas)
├── repository/      # Spring Data JPA
├── security/        # JWT, filtros y configuración de Spring Security
└── service/
    └── impl/        # Lógica de negocio
```

---

## Formato de respuestas

**Éxito:**
```json
{
  "status": "success",
  "code": 200,
  "message": "Operación completada",
  "data": { },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

**Error:**
```json
{
  "status": "error",
  "code": 404,
  "message": "El recurso solicitado no fue encontrado",
  "errors": ["book with id 99 not found"],
  "timestamp": "2024-01-15T10:30:00Z",
  "path": "/api/v1/books/99"
}
```

---

## Flujo Git

```
main
└── develop
    ├── feature/auth-module
    ├── feature/book-catalog
    ├── feature/order-management
    └── feature/author-category
```

**Reglas:**
- ❌ Nunca hacer push directo a `main` o `develop`
- ✅ Toda feature parte desde `develop`
- ✅ Todo PR requiere al menos un revisor diferente al autor
- ✅ Los conflictos se resuelven en la rama de feature
- ✅ Eliminar la rama tras el merge

**Convención de commits:**
```
feat: add JWT authentication filter
fix: correct price validation in BookRequest
refactor: extract order total calculation to service
docs: add endpoint documentation in AuthController
chore: update application.yml with JWT config
```

---

## Entregables

- [ ] Repositorio GitHub con historial de commits organizado
- [ ] Mínimo 2 Pull Requests cerrados con evidencia de revisión
- [ ] Colección Postman exportada en JSON con todos los endpoints
- [ ] Este README con instrucciones de configuración y ejecución
- [ ] Diagrama ER del modelo de datos

---

## Criterios de evaluación

| Criterio | Peso |
|---|---|
| Arquitectura por capas respetada | 15% |
| Contrato de API (ApiResponse / ApiErrorResponse) | 15% |
| Excepciones personalizadas y handler global | 10% |
| Relaciones JPA correctas y funcionales | 15% |
| Spring Security + JWT operativo | 20% |
| Mappers sin lógica de negocio | 10% |
| Flujo Git: ramas, commits y PRs | 15% |

> ⚠️ Un criterio se evalúa como **0** si la funcionalidad que lo soporta no compila o no funciona.
> El uso de MapStruct o ModelMapper está **penalizado** — los mappers deben ser manuales.