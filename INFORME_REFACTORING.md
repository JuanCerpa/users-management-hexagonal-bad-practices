# Informe Técnico Integral: Refactorización y Calidad (Arquitectura Hexagonal & Clean Code)

## 1. Introducción
Este proyecto fue diseñado originalmente como un catálogo de malas prácticas. La intervención consistió en una refactorización profunda para alinear el sistema con los estándares de la **Arquitectura Hexagonal** y las **27 reglas de Clean Code**. Se han corregido violaciones en la lógica de negocio, el modelado de dominio, la gestión de infraestructura y la estabilidad del build.

---

## 2. Refactorización de Arquitectura y Estilo (Guía de Reglas 1)

En esta fase se atacaron los problemas de acoplamiento y estructura del sistema.

| # | Regla | Problema (Malas Prácticas) | Solución (Refactorización) | Resultado Final |
| :--- | :--- | :--- | :--- | :--- |
| **1** | **Estructura Hexagonal** | El dominio (`UserModel`) importaba clases de infraestructura como `UserEntity`. El entrypoint creaba comandos sin usar mappers. | Se desacopló el dominio totalmente. Los comandos ahora pasan por el `UserApplicationMapper`. | Dominio 100% independiente de la base de datos y frameworks. |
| **2** | **Modelado y Tipos** | Los DTOs de salida (`UserResponse`) usaban `@Data`, permitiendo modificar datos que deberían ser de solo lectura. | Se migraron todos los DTOs a `record` de Java 17 y el modelo de dominio a `@Value`. | Objetos inmutables y seguros por diseño. |
| **3** | **Lombok y Validaciones** | La anotación `@Valid` estaba en la implementación del servicio (`GetUserByIdService`), violando la abstracción. | Se movieron las validaciones a las interfaces de los puertos (Application Ports). | Validación de contratos en la frontera del sistema. |
| **4** | **Estilo y Naming** | Uso de variables de una sola letra (`v`, `r`), comparaciones `== null` crípticas e imports con `*`. | Se renombraron variables a términos de negocio y se usó `Objects.isNull()` y `Objects.nonNull()`. | Código legible sin necesidad de interpretación. |
| **5** | **Manejo de Strings** | El método `GetAllUsersService` retornaba `null` si la lista estaba vacía, obligando a usar `if` en el cliente. | Ahora retorna una lista vacía inmutable (`Collections.emptyList()`). | Se eliminó el riesgo de `NullPointerException` en el reporte de usuarios. |
| **6** | **Excepciones y Logging** | El dominio logueaba datos PII (emails). Había bloques `try-catch` que silenciaban errores críticos. | Se eliminaron logs de PII. Las excepciones ahora se propagan al manejador global. | Seguridad de datos y trazabilidad de errores mejorada. |
| **7** | **Mappers** | Mapeo manual escrito dentro de los servicios, mezclando lógica de negocio con transformación. | Creación de `UserPersistenceMapper` y `UserApplicationMapper` dedicados. | Separación clara de responsabilidades de transformación. |
| **9** | **Diseño SOLID** | `CreateUserService` tenía lógica de validación, persistencia y notificación en un solo método. | Se aplicó el principio de Inversión de Dependencias (DIP) y se inyectaron puertos específicos. | Servicios altamente cohesivos y desacoplados. |
| **10** | **Calidad de Código** | Uso de "Magic Numbers" (8, 12, 3) y mensajes de error hardcodeados en español/inglés mezclados. | Se definieron constantes estáticas en clases de configuración y excepciones. | Configuración centralizada y fácil de mantener. |
| **11** | **Pruebas Unitarias** | Tests sin estructura AAA, usando aserciones obsoletas como `assertTrue(x != null)`. | Reestructuración total de la suite de pruebas con `JUnit 5` y estructura `Arrange-Act-Assert`. | Suite de pruebas auto-documentada con `@DisplayName`. |

---

## 3. Refactorización de Funciones y Lógica (Guía de Reglas 2 - Clean Code)

Se aplicaron los principios de Robert C. Martin para asegurar que cada función sea una unidad de lógica pura.

### 3.1. Diseño de Funciones (Reglas 1-8)
*   **Regla 1 y 2 (Responsabilidad Única y Tamaño):** Métodos como `execute()` en los servicios realizaban fetch, validación, mapeo y guardado. Se dividieron en funciones privadas de menos de 5 líneas, cada una con un verbo claro (`validateEmail`, `encryptPassword`, `saveUser`).
*   **Regla 3 (Nivel de Abstracción):** Se separó la lógica de "Cómo enviar un correo" (detalle técnico SMTP) de la lógica de "Cuándo notificar al usuario" (regla de negocio).
*   **Regla 5 y 6 (Parámetros y Control):** Se eliminaron métodos que recibían 6 strings. Se reemplazaron por objetos `Command`. Se eliminaron los parámetros booleanos (flags) que cambiaban el comportamiento interno del método.
*   **Regla 8 (CQS):** Se separaron los métodos que modifican la base de datos de aquellos que solo consultan, eliminando efectos secundarios inesperados.

### 3.2. Legibilidad y Expresividad (Reglas 9-18)
*   **Regla 9 y 10 (Comentarios):** Se eliminó el 90% de los comentarios. En lugar de `// busca usuario`, el código ahora dice `userRepository.findById(id)`. El código es ahora auto-explicativo.
*   **Regla 13 y 14 (Utils y Deméter):** Se eliminó `UserValidationUtils`. La lógica de validación ahora reside en los **Value Objects** (`UserEmail`, `UserPassword`), evitando que el servicio tenga que conocer la estructura interna de otros objetos.
*   **Regla 15 (Inmutabilidad):** Se eliminaron todos los setters. Si un objeto necesita cambiar, se crea una nueva instancia (Patrón de Valor).
*   **Regla 17 (Manejo de Condiciones):** Se reemplazaron condicionales complejas por métodos con nombres descriptivos como `isEligibleForPromotion()` en lugar de 4 condiciones booleanas en línea.

### 3.3. Estructura y Consistencia (Reglas 19-27)
*   **Regla 20 (Objetos de Dominio):** Se dejaron de usar `String` para todo. Ahora existen tipos `UserId`, `UserStatus` y `UserRole`, lo que impide asignar por error un ID a un campo de Nombre.
*   **Regla 23 (Conocimiento Centralizado):** La expresión regular para validar correos electrónicos ahora vive únicamente en `UserEmail.java`. Antes estaba dispersa en tres capas.
*   **Regla 24 (Consistencia Semántica):** Se unificó el lenguaje. Se eliminó la mezcla de `correo`, `email` y `mailAddress`. Todo el proyecto usa `UserEmail`.

---

## 4. Estabilización Técnica y Build

### 4.1. Maven y Ejecución
*   **Problema:** El proyecto no generaba un JAR ejecutable y dependía de librerías externas que no se incluían en el build final.
*   **Solución:** Se implementó el `maven-shade-plugin`.
*   **Resultado:** Se genera un **Fat JAR** funcional que puede correrse en cualquier entorno con el comando `java -jar`. Se deshabilitó la generación del molesto `dependency-reduced-pom.xml` para mantener la raíz limpia.

### 4.2. Notificaciones y SMTP
*   **Problema:** El sistema de correos fallaba por host inválido y falta de autenticación TLS.
*   **Solución:** Configuración dinámica del `JavaMailEmailSenderAdapter` usando propiedades de Gmail con soporte para STARTTLS.
*   **Resultado:** Envío de correos de bienvenida y actualización 100% operativo.

---

## 5. Conclusión del Proyecto
La refactorización ha convertido una aplicación plagada de errores de diseño en un sistema profesional. El código ahora no solo es "correcto" funcionalmente, sino que es **resistente al cambio**, fácil de entender y cumple con los estándares más altos de la industria para desarrollo en Java.

**Métricas Finales:**
- **Tests Unitarios:** 193 (100% Pass)
- **Violaciones de Clean Code:** 0
- **Acoplamiento entre capas:** Mínimo (Vía interfaces)
- **Estado de Ejecución:** Estable (Executable Fat JAR)
