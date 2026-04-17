# users-management-hexagonal-java-bad-practices

> Aplicación de escritorio Java 17 con arquitectura hexagonal y DDD, usada como catálogo de malas prácticas de Clean Code para fines educativos.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)
![JUnit 5](https://img.shields.io/badge/JUnit-5.11-green?logo=junit5)
![Mockito](https://img.shields.io/badge/Mockito-5.15-blue)
![Lombok](https://img.shields.io/badge/Lombok-1.18-pink)

---

## Funcionalidades del sistema

El sistema es una aplicación de escritorio por línea de comandos (CLI) que permite gestionar usuarios a través de un menú interactivo. Sus casos de uso son:

| # | Funcionalidad | Descripción |
|---|---|---|
| 1 | **Listar usuarios** | Obtiene y muestra todos los usuarios registrados en la base de datos |
| 2 | **Buscar usuario por ID** | Recupera un usuario específico mediante su identificador único |
| 3 | **Crear usuario** | Registra un nuevo usuario con validaciones de dominio y envía un correo de bienvenida |
| 4 | **Actualizar usuario** | Modifica los datos de un usuario existente y notifica el cambio por correo electrónico |
| 5 | **Eliminar usuario** | Borra un usuario del sistema verificando su existencia antes de la operación |
| 6 | **Login** | Autentica a un usuario validando credenciales y estado de cuenta |

---

## Arquitectura

El proyecto aplica **Arquitectura Hexagonal** (Ports & Adapters) combinada con principios de **Domain-Driven Design (DDD)**.

[Ver Digrama Estructura](lhttps://mermaid.live/view#pako:eNqVVdFumzAU_RXLVaVNImkICQl0q8QIlahIyIBE3cY0OWASVoKRIW2zql-xj9n79mMzBtJ06tqUF-zrc-49PtiXOxiQEEMVRgm5CVaIFsD74Kd-enwM3v_v4auG65mW7b6EDBKU5yMcgTiNKAJRnCTqkRh1FWkgBCQhVD2KokjIC0quMFsRxWF3UE9bN3FYrNRudnu6lwhlWZ2m25clvHgyDe51UPRsmpCsUZzWmTp9RZaVpzJ15H7Uk5_NtCA0xBTUkBDlzEaKtirog_4ujxIgCUWnB1lrTs4djfnrzHRv5mgvMfLNYklRtqp4X3z4Lx9-9dMwpjgoYpICyylFGBPP-cSw_D21zYn3bkFPzt7olglOgE5SpjtJMH3L2dpIm3qGw_D1qAKPt-5Hi8Ev0DUaozhhQ3fsTSuOn-I0PGi_2tQydU03__ycHLxXbTot1ewxH--yOsVT2_Hcb-aEQaeEFjmzqFI-yzHQUY5zJtlMC0wjFOC8Eu4aztzUDZeRXEyvY7bASTrFqMDg9y8wy8J6NMIJrkYWWcYp51dF7Zm3q8rGVVkHZySPC0K3rK6xLi3jiMaxRi9otc5Ao4NPdklfY-zIHpsT0z7YVIbXuFkN8SlPx_bIsBiGWUjHrHkkHDW3WWiOkg0G9uI7I-Q7o6kZCqB88w0LoN1uV_s1JrNx6bKRbtZ7cIckuCK4BSo2tTnGpV4Kq26tcRvgrNSU18ZxUdyoub0_4yUeBS711zh4bs0uXm5y_BLtfSZzsncKeLy-Ng-Hi0e5rIN06JbmGoc1W8DVCHXFqu82S408oZEhPMhkfbWBcV3C3Ba4fQLzrG6XUIBLGodQLegGC3CNKYuyKbzzUwB8WKzwGvtQZcMQ0Ssf-uk942Qo_UzIuqFRslmuoBqhJGezDb9MoxixM7jeRSn7QpjqZJMWUBUlngOqd_AWqi2pPVS6vZ7c6_clWRZFWYBbqPbb0lBRRIktiANFlHv3AvzBq4ptURGVwaDT7XUGQ0lWRAHisLyG4-rnx_-B938B9mYpGA)


### Capas

| Capa | Responsabilidad | Paquete |
|---|---|---|
| **Domain** | Entidades, Value Objects, enums, excepciones de dominio. Sin dependencias a frameworks | `domain/` |
| **Application** | Casos de uso, puertos (interfaces), servicios, DTOs, mappers | `application/` |
| **Entrypoint** | Controlador CLI, handlers de menú, DTOs y mappers de presentación | `infrastructure/entrypoint/` |
| **Adapter** | Repositorio MySQL (JDBC), adaptador de email SMTP (JavaMail) | `infrastructure/adapter/` |
| **Config** | Contenedor de dependencias manual, carga de propiedades | `infrastructure/config/` |

### Principios DDD aplicados

- **Value Objects** inmutables: `UserId`, `UserEmail`, `UserName`, `UserPassword`
- **Agregado raíz**: `UserModel` con invariantes protegidas en el dominio
- **Excepciones de dominio** expresivas con factory methods (`UserNotFoundException.becauseIdWasNotFound(...)`)
- **Puertos** como contratos de entrada (`CreateUserUseCase`) y salida (`SaveUserPort`, `EmailSenderPort`)

---

## Stack tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Maven | 3.x | Gestión de dependencias y build |
| Lombok | 1.18.36 | Reducción de boilerplate (`@Data`, `@Builder`, `@Log`) |
| MySQL Connector/J | 9.3.0 | Acceso a base de datos vía JDBC |
| JavaMail (javax.mail) | 1.6.2 | Envío de correos SMTP |
| BCrypt | 0.10.2 | Hash seguro de contraseñas |
| Jakarta Validation | 3.0.2 | Validación de constraints en DTOs y commands |
| Hibernate Validator | 8.0.0 | Implementación de Jakarta Validation |
| JUnit Jupiter | 5.11.4 | Pruebas unitarias |
| Mockito | 5.15.2 | Mocks en pruebas |
| JaCoCo | 0.8.11 | Cobertura de código |
| SLF4J | 2.0.16 | Fachada de logging |

---

## Calidad y cumplimiento de reglas

El proyecto está alineado con las reglas de arquitectura hexagonal y clean code definidas en `Reglas 1.md` y `Reglas 2.md`.

Se aplicaron mejoras de diseño y calidad en:

- separación clara por capas (dominio, aplicación, entrypoint y adapters),
- uso consistente de tipos de dominio y mappers entre capas,
- validaciones en contratos públicos y comandos/queries,
- manejo de errores con excepciones expresivas y mensajes centralizados,
- logging sin exposición de PII,
- pruebas con estructura AAA, `@DisplayName` y aserciones semánticas.

---

## Ejecución

### Prerrequisitos

- Java 17+
- Maven 3.8+
- MySQL 8+ con el schema aplicado (`src/main/resources/schema.sql`)
- Servidor SMTP accesible (o Mailtrap / MailHog para desarrollo)

### Configuración

Edita `src/main/resources/application.properties`:

```properties
db.host=localhost
db.port=3306
db.name=users_db
db.username=root
db.password=secret

smtp.host=smtp.example.com
smtp.port=587
smtp.username=user@example.com
smtp.password=secret
smtp.from.address=noreply@example.com
smtp.from.name=Users Management
```

### Compilar y ejecutar

```bash
mvn clean package -DskipTests
java -jar target/users-management-1.4.jar
```

### Tests

```bash
mvn test
```

>  193 tests · 0 fallos · 0 errores
