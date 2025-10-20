# 🧾 Employees API

API REST desarrollada con **Spring Boot 3.5.6** y **Java 17**, que permite gestionar empleados mediante operaciones CRUD.  
El proyecto sigue una arquitectura **en capas** (Controller → Service → Repository).

---

## ⚙️ Tecnologías Utilizadas

| Componente | Versión / Tecnología |
|-------------|----------------------|
| **Java** | 17 |
| **Spring Boot** | 3.5.6 |
| **Maven** | 3.9+ |
| **Spring Data JPA** | Integración ORM con MySQL |
| **MySQL** | 8.x |
| **MapStruct** | 1.5.5.Final (mapeo DTO ↔ Entidad) |
| **Lombok** | Reducción de código repetitivo |
| **Swagger / OpenAPI** | Documentación de endpoints |
| **JUnit 5 + Mockito** | Pruebas unitarias |
| **Logback** | Configuración de logs personalizada |

---

## 🏗️ Estructura del Proyecto

```
employees-api/
├─ src/
│  ├─ main/
│  │  ├─ java/com/challenge/employees_api/
│  │  │  ├─ controller/      # Controladores REST
│  │  │  ├─ dto/             # Clases de Request y Response
│  │  │  ├─ entity/          # Entidades JPA
│  │  │  ├─ exception/       # Excepciones personalizadas
│  │  │  ├─ mapper/          # Mappers (MapStruct)
│  │  │  ├─ repository/      # Interfaces JPA Repository
│  │  │  ├─ service/         # Interfaces de servicio
│  │  │  ├─ service/impl/    # Implementaciones de servicio
│  │  │  └─ config/          # Configuración general y OpenAPI
│  │  └─ resources/
│  │     ├─ application.properties
│  │     ├─ application-dev.properties
│  │     └─ logback-spring.xml
│  └─ test/java/com/challenge/employees_api/
│     ├─ controller/EmployeeControllerTest.java
│     └─ service/EmployeeServiceTest.java
├─ pom.xml
└─ README.md
```

---

## 🚀 Ejecución del Proyecto

### 🧩 Requisitos previos
- Tener instalado:
  - JDK 17
  - Maven 3.9+
  - MySQL 8.x

---

### 🔧 Configuración de base de datos

1. Conéctate como root (o admin)::
   ```sql
   mysql -u root -p
   ```
1.1 Ejecuta en orden los scripts (ajusta rutas):
```sql
   SOURCE /ruta/01-create-database-and-user.sql;
   SOURCE /ruta/02-schema.sql;
   SOURCE /ruta/03-seed.sql;
   SOURCE /ruta/04-verify.sql;
   ```

2. Ajusta las credenciales en `src/main/resources/application-dev.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/employees_db?useSSL=false&characterEncoding=utf8
   spring.datasource.username=appuser
   spring.datasource.password=appsecret
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Activa el perfil de desarrollo:
   ```bash
   mvn -Dspring-boot.run.profiles=dev spring-boot:run
   ```

---

### 🐳 Ejecución con Docker

Crea un archivo `docker-compose.yml`:

```yaml
version: "3.9"

services:
  mysql:
    image: mysql:8.0
    container_name: employees-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: employees_db
      MYSQL_USER: appuser
      MYSQL_PASSWORD: appsecret
    ports:
      - "3306:3306"
    volumes:
      - db_data:/var/lib/mysql

  employees-api:
    build: .
    container_name: employees-api
    depends_on:
      - mysql
    environment:
      SPRING_PROFILES_ACTIVE: dev
      DB_USERNAME: appuser
      DB_PASSWORD: appsecret
      DB_URL: jdbc:mysql://mysql:3306/employees_db?useSSL=false&characterEncoding=utf8
    ports:
      - "8080:8080"

volumes:
  db_data:
```

Ejecuta:
```bash
docker compose up --build
```

---

## 🧪 Pruebas Unitarias

Ejecuta las pruebas con:
```bash
mvn test
```

Las pruebas cubren:
- Lógica de negocio (`EmployeeService`)
- Validaciones y respuestas HTTP (`EmployeeController`)
- Persistencia (`EmployeeRepository` con H2)

---

## 📘 Contrato REST (Documentación de Endpoints)

### 1️⃣ **GET** `/api/v1/employees`

**Descripción:** Obtiene el listado completo de empleados.

**Respuesta (200 OK):**
```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "firstName": "Juan",
      "secondName": "Carlos",
      "lastNamePaternal": "Hernandez",
      "lastNameMaternal": "Lopez",
      "age": 29,
      "gender": "MALE",
      "birthDate": "1996-08-15",
      "position": "Backend Engineer"
    }
  ],
  "message": "Empleados recuperados correctamente"
}
```

---

### 2️⃣ **POST** `/api/v1/employees`

**Descripción:** Inserta uno o varios empleados.

**Petición:**
```json
[
  {
    "firstName": "Ana",
    "lastNamePaternal": "Lopez",
    "lastNameMaternal": "Perez",
    "age": 28,
    "gender": "FEMALE",
    "birthDate": "05-05-1997",
    "position": "QA Analyst"
  }
]
```

**Respuesta (200 OK):**
```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "firstName": "Ana",
      "lastNamePaternal": "Lopez",
      "lastNameMaternal": "Perez",
      "age": 28,
      "gender": "FEMALE",
      "birthDate": "1997-05-05",
      "position": "QA Analyst"
    }
  ],
  "message": "Empleado(s) creado(s) correctamente"
}
```

---

### 3️⃣ **PATCH** `/api/v1/employees/{id}`

**Descripción:** Actualiza parcialmente un empleado existente.

**Petición:**
```json
{
  "position": "QA Lead",
  "age": 30
}
```

**Respuesta (200 OK):**
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "firstName": "Ana",
    "lastNamePaternal": "Lopez",
    "lastNameMaternal": "Perez",
    "age": 30,
    "gender": "FEMALE",
    "birthDate": "1997-05-05",
    "position": "QA Lead"
  },
  "message": "Empleado actualizado correctamente"
}
```

---

### 4️⃣ **DELETE** `/api/v1/employees/{id}`

**Descripción:** Elimina un empleado por su identificador.

**Respuesta (200 OK):**
```json
{
  "status": "success",
  "message": "Empleado eliminado correctamente"
}
```

---

## ⚠️ Manejo de Errores

| Tipo de Error | Código HTTP | Descripción |
|----------------|-------------|--------------|
| `VALIDATION_ERROR` | 400 | Datos de entrada inválidos. |
| `NOT_FOUND` | 404 | El empleado no existe. |
| `INTERNAL_ERROR` | 500 | Error inesperado del servidor. |

**Ejemplo de error 400:**
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Petición inválida",
  "details": [
    {"field": "firstName", "error": "no debe estar vacío"}
  ]
}
```

---

## 🧱 Versionado

| Campo | Valor |
|--------|--------|
| **Versión actual** | v1 |
| **Futuro** | Paginación y búsqueda avanzada en GET y Autorización con AuthApi2. |
| **Estado** | Estable |

---

## 🌐 Swagger UI

La documentación interactiva se encuentra disponible en:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 👥 Autor

| Nombre | Rol | Contacto |
|---------|------|----------|
| **Marìa Martha Quintana López** | Backend Developer | 📧 quintana.m.martha@gmail.com |
