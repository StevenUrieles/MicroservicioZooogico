# 🦁 Sistema de Gestión Zoológico

API REST desarrollada con **Spring Boot** para la gestión de animales, alimentación, citas médicas y usuarios de un zoológico.

---

## 📋 Tabla de Contenidos

- [Tecnologías](#-tecnologías)
- [Arquitectura](#-arquitectura)
- [Entidades](#-entidades)
- [Requisitos previos](#-requisitos-previos)
- [Instalación y ejecución](#-instalación-y-ejecución)
- [Endpoints](#-endpoints)
- [Manejo de errores](#-manejo-de-errores)
- [Ejecución de pruebas](#-ejecución-de-pruebas)
- [Estructura del proyecto](#-estructura-del-proyecto)

---

## 🛠 Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.3.2 |
| Spring Data JPA | 3.3.2 |
| Spring Validation | 3.3.2 |
| MySQL | 8.0 |
| Lombok | latest |
| JUnit 5 | latest |
| Mockito | latest |
| Maven | 3.9+ |

---

## 🏗 Arquitectura

El proyecto sigue una arquitectura en capas:

```
Controller  →  Service (interfaz + impl)  →  Repository  →  Base de datos
                        ↕
                     Mapper
                        ↕
                      DTO
```

Cada entidad cuenta con su propio conjunto de clases organizadas por responsabilidad, siguiendo los principios de separación de concerns y bajo acoplamiento.

---

## 📦 Entidades

### Animal
Representa a los animales del zoológico.

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador único |
| `name` | String | Nombre del animal |
| `species` | String | Especie |
| `age` | Integer | Edad (0–100) |
| `healthStatus` | EstadoSalud | `EXCELENTE`, `MEDIA`, `MALA` |
| `typeAnimals` | TipoAnimal | `FELINO`, `REPTIL` |

### Alimentacion
Registro de alimentación asociado a un animal.

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador único |
| `tipoComida` | TipoComida | `CARNES`, `PLANTAS` |
| `cantidad` | int | Cantidad en kg (1–100) |
| `animal` | Animal | Relación ManyToOne |

### CitaMedica
Cita médica para un animal, asignada a un usuario.

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador único |
| `fecha` | LocalDate | Fecha (presente o futura) |
| `estadoCita` | EstadoCita | `PENDIENTE`, `ACTIVA`, `CANCELADA` |
| `animal` | Animal | Relación ManyToOne |
| `usuario` | Usuario | Relación ManyToOne |

### Usuario
Usuarios del sistema (veterinarios, cuidadores, administradores).

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador único |
| `nombre` | String | Nombre completo |
| `email` | String | Email único |
| `password` | String | Contraseña (8–20 caracteres) |
| `activo` | boolean | Estado del usuario |

---

## ✅ Requisitos previos

- [Java 17+](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/)
- [MySQL 8.0+](https://www.mysql.com/)

---

## 🚀 Instalación y ejecución

**1. Clonar el repositorio:**
```bash
git clone https://github.com/tu-usuario/sistema-gestion-zoologico.git
cd sistema-gestion-zoologico
```

**2. Crear la base de datos en MySQL:**
```sql
CREATE DATABASE `gestor-zoologico`;
```

**3. Configurar credenciales en `src/main/resources/application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestor-zoologico
spring.datasource.username=root
spring.datasource.password=tu_password
```

**4. Compilar y ejecutar:**
```bash
mvn clean install -DskipTests
mvn spring-boot:run
```

La API quedará disponible en: `http://localhost:8001`

---

## 📡 Endpoints

### 🐾 Animales — `/api/animales`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/animales` | Listar todos los animales |
| `GET` | `/api/animales/{id}` | Buscar animal por ID |
| `GET` | `/api/animales/nombre/{nombre}` | Buscar por nombre |
| `GET` | `/api/animales/especie/{especie}` | Filtrar por especie |
| `GET` | `/api/animales/edad/{edad}` | Filtrar por edad |
| `GET` | `/api/animales/estado/{estado}` | Filtrar por estado de salud |
| `GET` | `/api/animales/tipo/{tipo}` | Filtrar por tipo de animal |
| `POST` | `/api/animales` | Crear nuevo animal |
| `PUT` | `/api/animales/{id}` | Actualizar animal |
| `DELETE` | `/api/animales/{id}` | Eliminar animal |

**Ejemplo body POST/PUT:**
```json
{
  "nombre": "Simba",
  "especie": "Panthera leo",
  "edad": 4,
  "estadoSalud": "EXCELENTE",
  "tipoAnimal": "FELINO"
}
```

---

### 🍖 Alimentación — `/api/alimentaciones`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/alimentaciones` | Listar todas las alimentaciones |
| `GET` | `/api/alimentaciones/{id}` | Buscar por ID |
| `GET` | `/api/alimentaciones/tipo/{tipoComida}` | Filtrar por tipo de comida |
| `GET` | `/api/alimentaciones/animal/{animalId}` | Filtrar por animal |
| `POST` | `/api/alimentaciones` | Registrar alimentación |
| `PUT` | `/api/alimentaciones/{id}` | Actualizar alimentación |
| `DELETE` | `/api/alimentaciones/{id}` | Eliminar alimentación |

**Ejemplo body POST/PUT:**
```json
{
  "tipoComida": "CARNES",
  "cantidad": 15,
  "animalId": 1
}
```

---

### 🏥 Citas Médicas — `/api/citas`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/citas` | Listar todas las citas |
| `GET` | `/api/citas/{id}` | Buscar por ID |
| `GET` | `/api/citas/estado/{estadoCita}` | Filtrar por estado |
| `GET` | `/api/citas/animal/{animalId}` | Filtrar por animal |
| `GET` | `/api/citas/usuario/{usuarioId}` | Filtrar por usuario |
| `GET` | `/api/citas/fecha/{fecha}` | Filtrar por fecha (`yyyy-MM-dd`) |
| `POST` | `/api/citas` | Crear nueva cita |
| `PUT` | `/api/citas/{id}` | Actualizar cita |
| `DELETE` | `/api/citas/{id}` | Eliminar cita |

**Ejemplo body POST/PUT:**
```json
{
  "fecha": "2026-03-15",
  "estadoCita": "PENDIENTE",
  "animalId": 1,
  "usuarioId": 2
}
```

---

### 👤 Usuarios — `/api/usuarios`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/usuarios` | Listar todos los usuarios |
| `GET` | `/api/usuarios/{id}` | Buscar por ID |
| `GET` | `/api/usuarios/email/{email}` | Buscar por email |
| `GET` | `/api/usuarios/activo/{activo}` | Filtrar por estado activo |
| `POST` | `/api/usuarios` | Crear nuevo usuario |
| `PUT` | `/api/usuarios/{id}` | Actualizar usuario |
| `DELETE` | `/api/usuarios/{id}` | Eliminar usuario |

**Ejemplo body POST/PUT:**
```json
{
  "nombre": "Dr. García",
  "email": "garcia@zoologico.com",
  "password": "secure123",
  "activo": true
}
```

---

## 🔴 Manejo de errores

La API responde con códigos HTTP estándar. Los errores tienen el siguiente formato:

```json
{
  "error": "Descripción del error"
}
```

| Código | Descripción |
|---|---|
| `200 OK` | Operación exitosa |
| `201 Created` | Recurso creado correctamente |
| `204 No Content` | Eliminación exitosa |
| `400 Bad Request` | Datos de entrada inválidos |
| `404 Not Found` | Recurso no encontrado |
| `409 Conflict` | Conflicto (ej. email duplicado) |

---

## 🧪 Ejecución de pruebas

Ejecutar todas las pruebas unitarias:
```bash
mvn test
```

Ejecutar una clase de prueba específica:
```bash
mvn test -Dtest=AnimalServiceImplTest
mvn test -Dtest=UsuarioServiceImplTest
mvn test -Dtest=CitaMedicaServiceImplTest
mvn test -Dtest=AlimentacionServiceImplTest
```

### Clases de prueba disponibles

| Clase | Descripción |
|---|---|
| `AnimalServiceImplTest` | Pruebas del servicio de animales |
| `AlimentacionServiceImplTest` | Pruebas del servicio de alimentación |
| `CitaMedicaServiceImplTest` | Pruebas del servicio de citas médicas |
| `UsuarioServiceImplTest` | Pruebas del servicio de usuarios |
| `AnimalMapperTest` | Pruebas del mapper de animales |
| `AlimentacionMapperTest` | Pruebas del mapper de alimentación |
| `UsuarioMapperTest` | Pruebas del mapper de usuarios |

---

## 📁 Estructura del proyecto

```
sistema-gestion-zoologico/
├── src/
│   ├── main/
│   │   ├── java/com/edu/politecnicointernacional/sistema_gestion_zoologico/
│   │   │   ├── controller/
│   │   │   │   ├── AnimalController.java
│   │   │   │   ├── AlimentacionController.java
│   │   │   │   ├── CitaMedicaController.java
│   │   │   │   └── UsuarioController.java
│   │   │   ├── dto/
│   │   │   │   ├── AnimalDto.java
│   │   │   │   ├── AlimentacionDto.java
│   │   │   │   ├── CitaMedicaDto.java
│   │   │   │   └── UsuarioDto.java
│   │   │   ├── entity/
│   │   │   │   ├── Animal.java
│   │   │   │   ├── Alimentacion.java
│   │   │   │   ├── CitaMedica.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── enumeradores/
│   │   │   │       ├── EstadoCita.java
│   │   │   │       ├── EstadoSalud.java
│   │   │   │       ├── Rol.java
│   │   │   │       ├── TipoAnimal.java
│   │   │   │       └── TipoComida.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── AnimalExceptionIsPresent.java
│   │   │   │   ├── AnimalExceptionNotNull.java
│   │   │   │   ├── AnimalExceptionValorNoEncontado.java
│   │   │   │   ├── AlimentacionExceptionNotFound.java
│   │   │   │   ├── AlimentacionExceptionNotNull.java
│   │   │   │   ├── CitaMedicaExceptionNotFound.java
│   │   │   │   ├── CitaMedicaExceptionNotNull.java
│   │   │   │   ├── UsuarioExceptionEmailDuplicado.java
│   │   │   │   ├── UsuarioExceptionNotFound.java
│   │   │   │   └── UsuarioExceptionNotNull.java
│   │   │   ├── mapper/
│   │   │   │   ├── AnimalMapper.java
│   │   │   │   ├── AlimentacionMapper.java
│   │   │   │   ├── CitaMedicaMapper.java
│   │   │   │   └── UsuarioMapper.java
│   │   │   ├── repository/
│   │   │   │   ├── AnimalRepository.java
│   │   │   │   ├── AlimentacionRepository.java
│   │   │   │   ├── CitaMedicaRepository.java
│   │   │   │   └── UsuarioRepository.java
│   │   │   ├── service/
│   │   │   │   ├── AnimalService.java
│   │   │   │   ├── AlimentacionService.java
│   │   │   │   ├── CitaMedicaService.java
│   │   │   │   ├── UsuarioService.java
│   │   │   │   └── impl/
│   │   │   │       ├── AnimalServiceImpl.java
│   │   │   │       ├── AlimentacionServiceImpl.java
│   │   │   │       ├── CitaMedicaServiceImpl.java
│   │   │   │       └── UsuarioServiceImpl.java
│   │   │   └── SistemaGestionZoologicoApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/edu/politecnicointernacional/sistema_gestion_zoologico/
│           ├── AnimalServiceImplTest.java
│           ├── AlimentacionServiceImplTest.java
│           ├── CitaMedicaServiceImplTest.java
│           ├── UsuarioServiceImplTest.java
│           ├── AnimalMapperTest.java
│           ├── AlimentacionMapperTest.java
│           └── UsuarioMapperTest.java
└── pom.xml
```

---

## 👨‍💻 Autor

Desarrollado en el **Politécnico Internacional** como proyecto de gestión zoológica.
