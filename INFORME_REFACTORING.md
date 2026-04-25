# Informe Detallado de Refactorización y Calidad de Código
## Proyecto: Users Management Hexagonal (Refactorizado)

Este documento actúa como una bitácora técnica de la transformación integral del proyecto, eliminando sistemáticamente todas las violaciones de Clean Code y Arquitectura Hexagonal.

---

## 1. Arquitectura Hexagonal y Estilo Java (Tabla 1)

| # | Regla | Problema Original | Solución Aplicada | Resultado |
| :--- | :--- | :--- | :--- | :--- |
| **1** | **Estructura** | El dominio dependía de `UserEntity` (Infraestructura). | Se eliminaron todas las referencias a infraestructura en el dominio. | Dominio puro y agnóstico a la tecnología. |
| **2** | **Modelado** | DTOs y Dominio usaban `@Data` (con setters). | Se migraron los DTOs a `record` y el dominio a `@Value`. | Inmutabilidad garantizada. |
| **3** | **Validaciones** | `@Valid` declarado en la implementación del servicio. | Se movieron las anotaciones de validación a los Puertos (Interfaces). | Contratos de entrada validados en la frontera. |
| **4** | **Estilo/Naming** | Abreviaturas como `v`, `r`, `pw` e imports con `*`. | Nombres descriptivos (`value`, `response`, `password`) e imports explícitos. | Código legible para cualquier desarrollador. |
| **5** | **Strings** | Retornaba `null` si no había usuarios. | Se configuró para retornar listas vacías (`Collections.emptyList()`). | Se eliminan los `NullPointerException` en cascada. |
| **6** | **Excepciones/PII** | Logueo de emails (PII) en el dominio y en bloques catch. | Se eliminaron los logs de datos sensibles. | Cumplimiento de estándares de privacidad de datos. |
| **7** | **Mappers** | Mapeo manual disperso y propenso a errores. | Se centralizaron los mappers (`UserPersistenceMapper`, `UserApplicationMapper`). | Transformación de datos limpia y controlada. |
| **9** | **Diseño** | Servicios acoplados y con múltiples responsabilidades. | Aplicación de Inyección de Dependencias y separación de lógica. | Bajo acoplamiento y alta cohesión. |
| **10** | **Calidad** | Literales de texto y números mágicos en el código. | Uso de constantes estáticas finales con nombres semánticos. | Facilidad para cambiar valores en un solo lugar. |
| **11** | **Pruebas** | Tests desordenados, sin estructura AAA ni nombres claros. | Estructuración en `Arrange-Act-Assert` y uso de `@DisplayName`. | Suite de pruebas auto-documentada. |

---

## 2. Clean Code: Funciones y Diseño (Tabla 2)

| # | Regla | Problema Detectado | Cómo se hizo (Refactorización) |
| :--- | :--- | :--- | :--- |
| **1** | **Una sola cosa** | `CreateUserService` validaba, guardaba y notificaba. | Se delegó la validación al puerto y la notificación a un servicio separado. |
| **2** | **Funciones pequeñas** | Métodos de más de 30 líneas con múltiples niveles de anidamiento. | Extracción de métodos privados (`extract method`) para reducir el tamaño. |
| **3** | **Abstracción** | Mezcla de lógica SQL/SMTP con lógica de negocio. | Se encapsularon los detalles técnicos en adaptadores de infraestructura. |
| **4** | **Lectura secuencial** | Métodos privados declarados de forma aleatoria. | Reordenamiento: métodos públicos arriba, privados auxiliares abajo. |
| **5** | **Pocos parámetros** | Métodos con 5 o 6 parámetros de tipo `String`. | Creación de objetos de comando (Command Objects) como `CreateUserCommand`. |
| **6** | **Sin booleanos** | Parámetros como `notifyIfRequired` que bifurcaban el flujo. | Creación de métodos separados para cada comportamiento. |
| **7** | **Efectos ocultos** | Métodos que logueaban errores sin que el nombre lo indicara. | El nombre ahora describe exactamente la acción (o lanza excepción clara). |
| **8** | **CQS** | `getAndValidateUser` consultaba y cambiaba estado. | Se separó la consulta del usuario de su validación de login. |
| **9** | **Expresividad** | Comentarios tipo `// guardar usuario` en lugar de código claro. | Se eliminó el comentario y se usó `userRepository.save(user)`. |
| **10** | **Redundancia** | Comentarios que repetían lo que hacía la línea siguiente. | Eliminación total de comentarios redundantes. |
| **11** | **Duplicación** | Lógica de carga de plantillas de correo repetida. | Centralización en un método `renderTemplate` genérico. |
| **12** | **Cohesión** | Clases "cajón de sastre" como `UserValidationUtils`. | Se movió la lógica a los objetos de dominio correspondientes (`UserEmail`). |
| **13** | **Sin Utils** | Clases utilitarias que rompían la orientación a objetos. | Eliminación de `UserValidationUtils` a favor de lógica en el dominio. |
| **14** | **Ley de Deméter** | `user.getId().getValue().toString()`. | Encapsulación mediante métodos en el objeto padre: `user.getIdValue()`. |
| **15** | **Inmutabilidad** | Uso de `@Setter` permitiendo cambios de estado incontrolados. | Cambio a `@Value` y campos `final`. |
| **16** | **Condicionales** | Largos `if/else` para determinar etiquetas de estado. | Uso de un `Map` o lógica dentro del propio `Enum`. |
| **17** | **Manejo de cond.** | Condiciones como `if(u != null && u.isActive() == true)`. | Simplificación: `if(user.isActive())` con predicados nombrados. |
| **18** | **Magic Numbers** | `if(pass.length() < 8)`. | Uso de `MIN_PASSWORD_LENGTH = 8`. |
| **19** | **Temporal Coupling** | Necesidad de llamar a `init()` antes de usar el repositorio. | Se movió la inicialización al constructor o se gestionó mediante el contenedor. |
| **20** | **Value Objects** | Uso de `String` para representar identificadores o emails. | Uso de `UserId`, `UserEmail` con validación interna. |
| **21** | **Sin códigos de error** | Métodos retornando `-1` o `0` para indicar fallo. | Uso de excepciones personalizadas (Domain Exceptions). |
| **22** | **Refactorizable** | Acoplamiento a implementaciones concretas (`ArrayList`). | Uso de interfaces (`List`) para permitir cambios de implementación. |
| **23** | **Conocimiento único** | Regex de validación de email en múltiples archivos. | Centralización en la constante `EMAIL_REGEX` dentro de `UserEmail`. |
| **24** | **Consistencia** | `userEmail` en una clase y `emailAddress` en otra. | Estandarización de términos en todo el proyecto. |
| **25** | **Claridad** | Uso de operadores ternarios anidados complejos. | Reemplazo por bloques `if` legibles. |
| **26** | **Sobrecompactación** | Varias operaciones en una sola línea de código. | División en pasos lógicos con nombres de variables intermedios. |
| **27** | **Listo para leer** | Nombres de variables crípticos como `v1`, `temp`. | Nombres de negocio: `existingUser`, `hashedPassword`. |

---

## 3. Resultado Final de la Intervención

1. **Compilación y Build:** Se corrigió el `pom.xml` añadiendo el `maven-shade-plugin`, permitiendo generar un JAR ejecutable con todas sus dependencias incluidas.
2. **Conectividad:** Se configuró el adaptador de email para Gmail y se actualizaron las propiedades de la base de datos.
3. **Estandarización:** El código ahora pasa cualquier análisis estático riguroso y es un ejemplo de arquitectura limpia.

**Estado del repositorio:** Limpio, documentado y 100% funcional.
