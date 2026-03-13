<div align="center">

# 🦁 Sistema de Gestión Zoológico

**Microservicio RESTful para la gestión integral de un zoológico**  
Desarrollado con Java 17 · Spring Boot 4 · Spring Security · MySQL

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green?logo=springsecurity)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-8-blue?logo=mysql)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

</div>

---

## 📋 Descripción

Sistema de Gestión Zoológico es un microservicio backend que permite administrar los recursos de un zoológico: animales, registros de alimentación, citas médicas y usuarios del sistema. Expone una API REST segura con autenticación basada en roles (ADMIN, VETERINARIO, CUIDADOR).

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 4.0.3 | Framework base |
| Spring Web MVC | 4.0.3 | API REST |
| Spring Security | 6.x | Autenticación y autorización |
| Spring Data JPA | 4.0.3 | Acceso a datos |
| Hibernate | 6.x | ORM |
| MySQL | 8.x | Base de datos relacional |
| Lombok | Latest | Reducción de boilerplate |
| Bean Validation | 3.x | Validación de DTOs |
| JUnit 5 + Mockito | Latest | Pruebas unitarias |
| Docker | Latest | Contenedorización |

---

## 🏗️ Arquitectura del Proyecto

```
sistema-gestion-zoologico/
└── src/
    ├── main/
    │   ├── java/com/edu/politecnicointernacional/sistema_gestion_zoologico/
    │   │   ├── controller/          # Capa de presentación (REST endpoints)
    │   │   │   ├── AnimalController.java
    │   │   │   ├── AlimentacionController.java
    │   │   │   ├── CitaMedicaController.java
    │   │   │   └── UsuarioController.java
    │   │   ├── service/             # Interfaces de servicio
    │   │   │   └── impl/            # Implementaciones de negocio
    │   │   ├── repository/          # Interfaces JPA Repository
    │   │   ├── entity/              # Entidades JPA
    │   │   │   └── enumeradores/    # Enums del dominio
    │   │   ├── dto/                 # Data Transfer Objects
    │   │   ├── mapper/              # Conversión Entity ↔ DTO
    │   │   ├── exception/           # Excepciones personalizadas
    │   │   │   └── GlobalExceptionHandler.java
    │   │   └── security/            # Configuración de seguridad
    │   └── resources/
    │       └── application.properties
    └── test/                        # Pruebas unitarias
```

**Patrón arquitectónico:** `Controller → Service → Repository → Entity`

---

## 📦 Módulos del Sistema

### 🐾 Animal
Gestiona el catálogo de animales del zoológico. Permite filtrar por nombre, especie, edad, estado de salud y tipo de animal.

### 🍖 Alimentación
Registra los planes de alimentación por animal. Controla el tipo de comida (CARNES, PLANTAS) y la cantidad en kg.

### 🏥 Cita Médica
Administra las citas veterinarias. Vincula un animal con un veterinario (usuario), con fecha y estado de la cita (PENDIENTE, ACTIVA, CANCELADA).

### 👤 Usuario
Gestión de usuarios del sistema con roles diferenciados: ADMIN, VETERINARIO y CUIDADOR.

---

## 🔐 Seguridad

El sistema implementa **Spring Security con HTTP Basic Authentication** y control de acceso basado en roles:

| Rol | Permisos |
|---|---|
| `ADMIN` | Acceso completo a todos los endpoints |
| `VETERINARIO` | Lectura de animales/alimentación · Gestión completa de citas médicas |
| `CUIDADOR` | Lectura de animales/alimentación · Crear y actualizar registros de alimentación |

### Matriz de acceso por endpoint

| Endpoint | GET | POST | PUT | DELETE |
|---|---|---|---|---|
| `/api/animales` | Todos (auth) | ADMIN | ADMIN | ADMIN |
| `/api/alimentaciones` | Todos (auth) | ADMIN, CUIDADOR | ADMIN, CUIDADOR | ADMIN |
| `/api/citas` | ADMIN, VETERINARIO | ADMIN, VETERINARIO | ADMIN, VETERINARIO | ADMIN, VETERINARIO |
| `/api/usuarios` | ADMIN | ADMIN | ADMIN | ADMIN |

> ⚠️ **Nota:** Para producción se recomienda migrar a JWT/OAuth2. La configuración actual con HTTP Basic es adecuada para entornos internos o demostraciones.

---

## 🚀 Ejecución Local

### Prerrequisitos
- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Clonar el repositorio
```bash
git clone https://github.com/tuusuario/sistema-gestion-zoologico.git
cd sistema-gestion-zoologico
```

### 2. Configurar la base de datos
```sql
CREATE DATABASE `gestor-zoologico`;
```

### 3. Configurar `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestor-zoologico?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_contraseña
```

### 4. Ejecutar
```bash
cd sistema-gestion-zoologico
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8002`

---

## 🐳 Ejecución con Docker

### Construir la imagen
```bash
docker build -t zoo-management:latest .
```

### Ejecutar el contenedor
```bash
docker run -d \
  --name zoo-management \
  -p 8002:8002 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/gestor-zoologico \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=tu_contraseña \
  zoo-management:latest
```

### Con Docker Compose (recomendado)
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8002:8002"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/gestor-zoologico
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: rootpass
    depends_on:
      - db

  db:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: rootpass
      MYSQL_DATABASE: gestor-zoologico
    ports:
      - "3306:3306"
```

```bash
docker-compose up -d
```

---

## 📡 Ejemplos de Endpoints

Todos los endpoints requieren autenticación HTTP Basic.

### Animales

```bash
# Listar todos los animales
GET /api/animales
Authorization: Basic dmV0ZXJpbmFyaW86dmV0MTIzNDU=

# Buscar por ID
GET /api/animales/1

# Filtrar por especie
GET /api/animales/especie/Panthera%20leo

# Filtrar por estado de salud (EXCELENTE | MEDIA | MALA)
GET /api/animales/estado/EXCELENTE

# Crear animal (solo ADMIN)
POST /api/animales
Authorization: Basic YWRtaW46YWRtaW4xMjM=
Content-Type: application/json

{
  "nombre": "Simba",
  "especie": "Panthera leo",
  "edad": 4,
  "estadoSalud": "EXCELENTE",
  "tipoAnimal": "FELINO"
}
```

### Alimentación

```bash
# Listar alimentaciones de un animal
GET /api/alimentaciones/animal/1

# Crear registro de alimentación (ADMIN o CUIDADOR)
POST /api/alimentaciones
Content-Type: application/json

{
  "tipoComida": "CARNES",
  "cantidad": 15,
  "animalId": 1
}
```

### Citas Médicas

```bash
# Listar citas por estado (PENDIENTE | ACTIVA | CANCELADA)
GET /api/citas/estado/PENDIENTE

# Listar citas por fecha
GET /api/citas/fecha/2025-06-15

# Crear cita médica (ADMIN o VETERINARIO)
POST /api/citas
Content-Type: application/json

{
  "fecha": "2025-06-20",
  "estadoCita": "PENDIENTE",
  "animalId": 1,
  "usuarioId": 2
}
```

### Usuarios (solo ADMIN)

```bash
# Crear usuario
POST /api/usuarios
Content-Type: application/json

{
  "nombre": "Dr. García",
  "email": "garcia@zoo.com",
  "password": "Vet2024!!",
  "activo": true,
  "rol": "VETERINARIO"
}
```

---

## 🧪 Pruebas

El proyecto incluye pruebas unitarias para servicios y mappers usando **JUnit 5 + Mockito**.

```bash
# Ejecutar todas las pruebas
./mvnw test

# Ejecutar con reporte de cobertura
./mvnw test jacoco:report
```

### Cobertura de pruebas incluida

| Clase | Tipo |
|---|---|
| `AnimalServiceImplTest` | Servicio |
| `AlimentacionServiceImplTest` | Servicio |
| `CitaMedicaServiceImplTest` | Servicio |
| `UsuarioServiceImplTest` | Servicio |
| `AnimalMapperTest` | Mapper |
| `AlimentacionMapperTest` | Mapper |
| `UsuarioMapperTest` | Mapper |

---

## 👨‍💻 Autor

Desarrollado como proyecto académico en el **Politécnico Internacional**.

---

<div align="center">
  <sub>Sistema de Gestión Zoológico · Spring Boot Microservice · 2025</sub>
</div>
