# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `com.ecommerce.comments.infrastructure.api.dto.CommentResponse`: El import com.ecommerce.comments.infrastructure.api.dto.CommentResponse usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `com.ecommerce.comments.infrastructure.api.dto.CommentResponse`: El import com.ecommerce.comments.infrastructure.api.dto.CommentResponse usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/ecommerce/comments/infrastructure/api/dto/ErrorResponse.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.annotation.JsonInclude pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `io.swagger.v3`: El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `org.hibernate.annotations`: El import org.hibernate.annotations.CreationTimestamp pertenece a org.hibernate.annotations, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/ecommerce/comments/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/ecommerce/comments/infrastructure/config/OpenApiConfig.java` — `io.swagger.v3`: El import io.swagger.v3.oas.models.Components pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/ecommerce/comments/CommentsApplication.java` — `ApplicationProperties.getAllowedOrigins`: Se invoca `getAllowedOrigins` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/CommentsApplication.java` — `ApplicationProperties.getStorage`: Se invoca `getStorage` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/domain/ports/CommentValidator.java` — `Comment.rating`: Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/domain/ports/CommentValidator.java` — `Comment.productId`: Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/domain/ports/CommentValidator.java` — `Comment.userId`: Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.id`: Se invoca `id` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.productId`: Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.userId`: Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.content`: Se invoca `content` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.rating`: Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `CommentEntity.setVisible`: Se invoca `setVisible` sobre `CommentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.isVisible`: Se invoca `isVisible` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.idempotencyKey`: Se invoca `idempotencyKey` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.createdAt`: Se invoca `createdAt` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.updatedAt`: Se invoca `updatedAt` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `CommentEntity.isVisible`: Se invoca `isVisible` sobre `CommentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.productId`: Se invoca `productId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.userId`: Se invoca `userId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.rating`: Se invoca `rating` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.content`: Se invoca `content` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.idempotencyKey`: Se invoca `idempotencyKey` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.id`: Se invoca `id` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.productId`: Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.userId`: Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.content`: Se invoca `content` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.rating`: Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.isVisible`: Se invoca `isVisible` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.createdAt`: Se invoca `createdAt` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `CreateCommentRequest.productId`: Se invoca `productId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `StorageException.getMessage`: Se invoca `getMessage` sobre `StorageException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.id`: Se invoca `id` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.productId`: Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.userId`: Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.content`: Se invoca `content` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.rating`: Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.isVisible`: Se invoca `isVisible` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.createdAt`: Se invoca `createdAt` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `Comment.content`: Se invoca `content` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `Comment.productId`: Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `Comment.userId`: Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `Comment.rating`: Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `DuplicateCommentException.getMessage`: Se invoca `getMessage` sobre `DuplicateCommentException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `CreateCommentRequest.productId`: Se invoca `productId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `CreateCommentRequest.userId`: Se invoca `userId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `CreateCommentRequest.content`: Se invoca `content` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `CreateCommentRequest.rating`: Se invoca `rating` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Spring, Tecnología Java, Senior L2

### Brecha de conocimiento
Comprueba manejo de errores y validaciones

### Reto
- Tema: E2E prod comments 1790905766
- Seniority: senior-l2
- Tipo: practical
- Título: Manejo de Comentarios de Producción en un Sistema de E-commerce
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Implementación del Canal de Entrada — objetivo: Crear un canal de entrada que acepte comentarios de los usuarios y los valide según las reglas definidas. — entregable (NO resolver): Canal de entrada operativo que valida y acepta comentarios de los usuarios.
- Fase 2: Almacenamiento Idempotente de Comentarios — objetivo: Implementar el almacenamiento idempotente de comentarios en el sistema. — entregable (NO resolver): Sistema que almacena comentarios de manera idempotente.
- Fase 3: Manejo de Errores y Respuesta al Usuario — objetivo: Implementar el manejo de errores y proporcionar una respuesta coherente al usuario. — entregable (NO resolver): Sistema que maneja errores y proporciona respuestas coherentes al usuario.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.ecommerce</groupId>
    <artifactId>comments</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>comments</name>
    <description>Sistema de manejo de comentarios para e-commerce</description>

    <properties>
        <java.version>21</java.version>
        <springdoc-openapi-ui.version>2.6.0</springdoc-openapi-ui.version>
        <lombok.version>1.18.34</lombok.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
            <version>42.7.3</version>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc-openapi-ui.version}</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/ecommerce/comments/CommentsApplication.java ===
package com.ecommerce.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
@EnableRetry
public class CommentsApplication {

    private final ApplicationProperties applicationProperties;

    public CommentsApplication(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        validateProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(CommentsApplication.class, args);
    }

    private void validateProperties() {
        if (applicationProperties.getAllowedOrigins() == null || applicationProperties.getAllowedOrigins().isEmpty()) {
            throw new IllegalStateException("La propiedad 'app.allowed-origins' debe estar configurada");
        }
        if (applicationProperties.getStorage().getMaxContentLength() <= 0) {
            throw new IllegalStateException("La propiedad 'app.storage.max-content-length' debe ser mayor que 0");
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(applicationProperties.getAllowedOrigins().toArray(new String[0]))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Configuration
    public static class ApplicationProperties {
        private java.util.List<String> allowedOrigins;
        private StorageProperties storage;

        public java.util.List<String> getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(java.util.List<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        public StorageProperties getStorage() {
            return storage;
        }

        public void setStorage(StorageProperties storage) {
            this.storage = storage;
        }

        public static class StorageProperties {
            private int maxContentLength;
            private int maxTitleLength;

            public int getMaxContentLength() {
                return maxContentLength;
            }

            public void setMaxContentLength(int maxContentLength) {
                this.maxContentLength = maxContentLength;
            }

            public int getMaxTitleLength() {
                return maxTitleLength;
            }

            public void setMaxTitleLength(int maxTitleLength) {
                this.maxTitleLength = maxTitleLength;
            }
        }
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: comments-service
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce_comments
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        jdbc:
          time_zone: UTC
        default_schema: comments
  flyway:
    enabled: true
    locations: classpath:db/migration
    schemas: comments
    baseline-on-migrate: true
    validate-on-migrate: true

server:
  port: 8080
  servlet:
    context-path: /api
  error:
    include-message: always
    include-binding-errors: always
    include-stacktrace: on_param
    include-exception: false

app:
  allowed-origins:
    - "http://localhost:3000"
    - "https://ecommerce.example.com"
  storage:
    max-content-length: 2000
    max-title-length: 100
    idempotency-key-prefix: "comment_"
    forbidden-words:
      - "inapropiado"
      - "ofensivo"
      - "spam"
      - "violento"

// === ARCHIVO: src/main/java/com/ecommerce/comments/domain/model/Comment.java ===
package com.ecommerce.comments.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public record Comment(
    @NotNull
    UUID commentId,

    @NotNull
    UUID productId,

    @NotNull
    UUID userId,

    @NotBlank
    @Size(min = 10, max = 1000)
    String content,

    @NotNull
    LocalDateTime createdAt,

    @NotNull
    Boolean isVisible,

    @NotNull
    Integer rating
) {
    public Comment {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (content.length() < 10 || content.length() > 1000) {
            throw new IllegalArgumentException("Content must be between 10 and 1000 characters");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (isVisible == null) {
            isVisible = true;
        }
    }

    public static Comment create(
            UUID commentId,
            UUID productId,
            UUID userId,
            String content,
            Integer rating
    ) {
        return new Comment(
                commentId != null ? commentId : UUID.randomUUID(),
                productId,
                userId,
                content,
                LocalDateTime.now(),
                true,
                rating
        );
    }

    public boolean isContentAppropriate() {
        String[] inappropriateWords = {"inapropiado", "ofensivo", "malo", "horrible"};
        String lowerContent = content.toLowerCase();
        for (String word : inappropriateWords) {
            if (lowerContent.contains(word)) {
                return false;
            }
        }
        return true;
    }

    public Comment withVisibility(boolean isVisible) {
        return new Comment(
                this.commentId,
                this.productId,
                this.userId,
                this.content,
                this.createdAt,
                isVisible,
                this.rating
        );
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/domain/ports/CommentRepository.java ===
package com.ecommerce.comments.domain.ports;

import com.ecommerce.comments.domain.model.Comment;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(UUID commentId);
    Optional<Comment> findByProductIdAndUserId(UUID productId, UUID userId);
    void deleteById(UUID commentId);
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/domain/ports/CommentValidator.java ===
package com.ecommerce.comments.domain.ports;

import com.ecommerce.comments.domain.model.Comment;
import java.util.UUID;

public interface CommentValidator {
    void validateCommentContent(Comment comment);
    void validateCommentUniqueness(UUID productId, UUID userId);
    void validateCommentRating(Integer rating);

    class ValidationResult {
        private final boolean isValid;
        private final String errorMessage;

        public ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }

        public boolean isValid() {
            return isValid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    default ValidationResult validateComment(Comment comment) {
        try {
            validateCommentContent(comment);
            validateCommentRating(comment.rating());
            validateCommentUniqueness(comment.productId(), comment.userId());
            return new ValidationResult(true, null);
        } catch (Exception e) {
            return new ValidationResult(false, e.getMessage());
        }
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/infrastructure/api/dto/CreateCommentRequest.java ===
package com.ecommerce.comments.infrastructure.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateCommentRequest(
    @NotNull(message = "El ID del producto es obligatorio")
    UUID productId,
    @NotNull(message = "El ID del usuario es obligatorio")
    UUID userId,
    @NotBlank(message = "El contenido del comentario no puede estar vacío")
    @Size(min = 10, max = 2000, message = "El contenido debe tener entre 10 y 2000 caracteres")
    String content,
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    Integer rating,
    String idempotencyKey
) {
    public CreateCommentRequest {
        if (content != null) {
            content = content.trim();
        }
    }

    public boolean hasIdempotencyKey() {
        return idempotencyKey != null && !idempotencyKey.isBlank();
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/infrastructure/api/dto/ErrorResponse.java ===
package com.ecommerce.comments.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    int status,
    String error,
    String message,
    String path,
    Instant timestamp,
    List<FieldError> details
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(status, error, message, path, Instant.now(), new ArrayList<>());
    }

    public ErrorResponse(int status, String error, String message, String path, List<FieldError> details) {
        this(status, error, message, path, Instant.now(), details);
    }

    public static ErrorResponse badRequest(String message, String path) {
        return new ErrorResponse(400, "Bad Request", message, path);
    }

    public static ErrorResponse notFound(String message, String path) {
        return new ErrorResponse(404, "Not Found", message, path);
    }

    public static ErrorResponse conflict(String message, String path) {
        return new ErrorResponse(409, "Conflict", message, path);
    }

    public static ErrorResponse internalError(String message, String path) {
        return new ErrorResponse(500, "Internal Server Error", message, path);
    }

    public ErrorResponse withDetails(List<FieldError> newDetails) {
        return new ErrorResponse(this.status(), this.error(), this.message(), this.path(), this.timestamp(), newDetails);
    }

    public record FieldError(
        String field,
        String message,
        Object rejectedValue
    ) {
        public FieldError(String field, String message) {
            this(field, message, null);
        }
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java ===
package com.ecommerce.comments.infrastructure.persistence;

import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.domain.ports.CommentRepository;
import com.ecommerce.comments.domain.exceptions.StorageException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment save(Comment comment) {
        try {
            CommentEntity entity = toEntity(comment);
            
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID());
                entity.setCreatedAt(java.time.Instant.now());
            }
            entity.setUpdatedAt(java.time.Instant.now());
            
            if (entityManager.contains(entity)) {
                entityManager.merge(entity);
            } else {
                entityManager.persist(entity);
            }
            
            entityManager.flush();
            entityManager.refresh(entity);
            
            return toDomain(entity);
        } catch (Exception e) {
            throw new StorageException("Error al guardar el comentario: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(UUID commentId) {
        try {
            CommentEntity entity = entityManager.find(CommentEntity.class, commentId);
            return Optional.ofNullable(entity).map(this::toDomain);
        } catch (Exception e) {
            throw new StorageException("Error al buscar el comentario por ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findByProductIdAndUserId(UUID productId, UUID userId) {
        try {
            Query query = entityManager.createQuery(
                "SELECT c FROM CommentEntity c WHERE c.productId = :productId AND c.userId = :userId",
                CommentEntity.class
            );
            query.setParameter("productId", productId);
            query.setParameter("userId", userId);
            query.setMaxResults(1);
            
            var result = query.getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(toDomain(result.get(0)));
        } catch (Exception e) {
            throw new StorageException("Error al buscar comentario por producto y usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(UUID commentId) {
        try {
            CommentEntity entity = entityManager.getReference(CommentEntity.class, commentId);
            entityManager.remove(entity);
            entityManager.flush();
        } catch (Exception e) {
            throw new StorageException("Error al eliminar el comentario: " + e.getMessage(), e);
        }
    }

    private CommentEntity toEntity(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.setId(comment.id());
        entity.setProductId(comment.productId());
        entity.setUserId(comment.userId());
        entity.setContent(comment.content());
        entity.setRating(comment.rating());
        entity.setVisible(comment.isVisible());
        entity.setIdempotencyKey(comment.idempotencyKey());
        entity.setCreatedAt(comment.createdAt());
        entity.setUpdatedAt(comment.updatedAt());
        return entity;
    }

    private Comment toDomain(CommentEntity entity) {
        return Comment.restore(
            entity.getId(),
            entity.getProductId(),
            entity.getUserId(),
            entity.getContent(),
            entity.getRating(),
            entity.isVisible(),
            entity.getIdempotencyKey(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/domain/exceptions/CommentValidationException.java ===
package com.ecommerce.comments.domain.exceptions;

import java.util.List;
import java.util.UUID;

public class CommentValidationException extends RuntimeException {

    private final String fieldName;
    private final Object rejectedValue;
    private final List<String> validationErrors;

    public CommentValidationException(String message) {
        super(message);
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationErrors = List.of(message);
    }

    public CommentValidationException(String message, Throwable cause) {
        super(message, cause);
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationErrors = List.of(message);
    }

    public CommentValidationException(String message, String fieldName, Object rejectedValue) {
        super(message);
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.validationErrors = List.of(message);
    }

    public CommentValidationException(List<String> validationErrors) {
        super(String.join("; ", validationErrors));
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationErrors = validationErrors;
    }

    public CommentValidationException(String message, UUID productId, UUID userId) {
        super(message);
        this.fieldName = "comment";
        this.rejectedValue = String.format("productId=%s, userId=%s", productId, userId);
        this.validationErrors = List.of(message);
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public boolean hasFieldErrors() {
        return fieldName != null && !fieldName.isEmpty();
    }

    public boolean hasMultipleErrors() {
        return validationErrors != null && validationErrors.size() > 1;
    }

    public static CommentValidationException forInvalidContent(String content) {
        return new CommentValidationException(
            "El contenido del comentario no es válido",
            "content",
            content != null ? content.substring(0, Math.min(50, content.length())) + "..." : null
        );
    }

    public static CommentValidationException forInvalidRating(Integer rating) {
        return new CommentValidationException(
            "La calificación debe estar entre 1 y 5",
            "rating",
            rating
        );
    }

    public static CommentValidationException forContentTooShort(int actualLength, int minLength) {
        return new CommentValidationException(
            String.format("El contenido es muy corto: %d caracteres mínimo requerido, %d proporcionados", minLength, actualLength),
            "content",
            actualLength
        );
    }

    public static CommentValidationException forContentTooLong(int actualLength, int maxLength) {
        return new CommentValidationException(
            String.format("El contenido excede el límite permitido: %d caracteres máximo, %d proporcionados", maxLength, actualLength),
            "content",
            actualLength
        );
    }

    public static CommentValidationException forInappropriateContent() {
        return new CommentValidationException(
            "El contenido contiene lenguaje inapropiado y no puede ser publicado",
            "content",
            "[CONTENT_REJECTED]"
        );
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/domain/exceptions/DuplicateCommentException.java ===
package com.ecommerce.comments.domain.exceptions;


import com.ecommerce.comments.domain.model.Comment;
import java.time.Instant;
import java.util.UUID;

public class DuplicateCommentException extends RuntimeException {

    private final UUID productId;
    private final UUID userId;
    private final UUID existingCommentId;
    private final Instant existingCommentTimestamp;
    private final String idempotencyKey;

    public DuplicateCommentException(String message) {
        super(message);
        this.productId = null;
        this.userId = null;
        this.existingCommentId = null;
        this.existingCommentTimestamp = null;
        this.idempotencyKey = null;
    }

    public DuplicateCommentException(String message, Throwable cause) {
        super(message, cause);
        this.productId = null;
        this.userId = null;
        this.existingCommentId = null;
        this.existingCommentTimestamp = null;
        this.idempotencyKey = null;
    }

    public DuplicateCommentException(UUID productId, UUID userId, UUID existingCommentId) {
        super(String.format("El usuario %s ya ha comentado el producto %s", userId, productId));
        this.productId = productId;
        this.userId = userId;
        this.existingCommentId = existingCommentId;
        this.existingCommentTimestamp = null;
        this.idempotencyKey = null;
    }

    public DuplicateCommentException(UUID productId, UUID userId, UUID existingCommentId, 
                                     Instant existingCommentTimestamp) {
        super(String.format("El usuario %s ya ha comentado el producto %s el %s", 
            userId, productId, existingCommentTimestamp));
        this.productId = productId;
        this.userId = userId;
        this.existingCommentId = existingCommentId;
        this.existingCommentTimestamp = existingCommentTimestamp;
        this.idempotencyKey = null;
    }

    public DuplicateCommentException(UUID productId, UUID userId, String idempotencyKey) {
        super(String.format("Ya existe un comentario con la clave de idempotencia '%s' para el producto %s", 
            idempotencyKey, productId));
        this.productId = productId;
        this.userId = userId;
        this.existingCommentId = null;
        this.existingCommentTimestamp = null;
        this.idempotencyKey = idempotencyKey;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getExistingCommentId() {
        return existingCommentId;
    }

    public Instant getExistingCommentTimestamp() {
        return existingCommentTimestamp;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public boolean hasExistingComment() {
        return existingCommentId != null;
    }

    public boolean hasIdempotencyKey() {
        return idempotencyKey != null && !idempotencyKey.isEmpty();
    }

    public static DuplicateCommentException forExistingComment(UUID productId, UUID userId) {
        return new DuplicateCommentException(productId, userId, null);
    }

    public static DuplicateCommentException forIdempotencyKeyConflict(UUID productId, UUID userId, 
                                                                       String idempotencyKey) {
        return new DuplicateCommentException(productId, userId, idempotencyKey);
    }

    public static DuplicateCommentException forUserProductCombination(UUID productId, UUID userId, 
                                                                      UUID existingCommentId) {
        return new DuplicateCommentException(productId, userId, existingCommentId);
    }

    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("DuplicateCommentException: ").append(getMessage());
        
        if (productId != null) {
            sb.append(" | Product: ").append(productId);
        }
        if (userId != null) {
            sb.append(" | User: ").append(userId);
        }
        if (existingCommentId != null) {
            sb.append(" | Existing Comment: ").append(existingCommentId);
        }
        if (existingCommentTimestamp != null) {
            sb.append(" | Timestamp: ").append(existingCommentTimestamp);
        }
        if (idempotencyKey != null) {
            sb.append(" | Idempotency Key: ").append(idempotencyKey);
        }
        
        return sb.toString();
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/domain/exceptions/StorageException.java ===
package com.ecommerce.comments.domain.exceptions;


import com.ecommerce.comments.domain.model.Comment;
import java.util.UUID;

public class StorageException extends RuntimeException {

    private final UUID commentId;
    private final String operation;
    private final String storageContext;
    private final boolean retryable;

    public StorageException(String message) {
        super(message);
        this.commentId = null;
        this.operation = null;
        this.storageContext = null;
        this.retryable = false;
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
        this.commentId = null;
        this.operation = null;
        this.storageContext = null;
        this.retryable = determineRetryable(cause);
    }

    public StorageException(String message, UUID commentId, String operation) {
        super(message);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = null;
        this.retryable = false;
    }

    public StorageException(String message, UUID commentId, String operation, Throwable cause) {
        super(message, cause);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = null;
        this.retryable = determineRetryable(cause);
    }

    public StorageException(String message, UUID commentId, String operation, String storageContext) {
        super(message);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = storageContext;
        this.retryable = false;
    }

    public StorageException(String message, UUID commentId, String operation, String storageContext, 
                           Throwable cause) {
        super(message, cause);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = storageContext;
        this.retryable = determineRetryable(cause);
    }

    public StorageException(String message, UUID commentId, String operation, String storageContext, 
                           boolean retryable, Throwable cause) {
        super(message, cause);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = storageContext;
        this.retryable = retryable;
    }

    private static boolean determineRetryable(Throwable cause) {
        if (cause == null) {
            return false;
        }
        String exceptionClassName = cause.getClass().getSimpleName().toLowerCase();
        return exceptionClassName.contains("timeout") || 
               exceptionClassName.contains("connection") ||
               exceptionClassName.contains("transient");
    }

    public UUID getCommentId() {
        return commentId;
    }

    public String getOperation() {
        return operation;
    }

    public String getStorageContext() {
        return storageContext;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public boolean hasCommentId() {
        return commentId != null;
    }

    public boolean hasOperation() {
        return operation != null && !operation.isEmpty();
    }

    public boolean hasStorageContext() {
        return storageContext != null && !storageContext.isEmpty();
    }

    public static StorageException forSaveFailure(UUID commentId, Throwable cause) {
        return new StorageException(
            "Error al guardar el comentario en el almacenamiento",
            commentId,
            "SAVE",
            cause
        );
    }

    public static StorageException forDeleteFailure(UUID commentId, Throwable cause) {
        return new StorageException(
            "Error al eliminar el comentario del almacenamiento",
            commentId,
            "DELETE",
            cause
        );
    }

    public static StorageException forRetrieveFailure(UUID commentId, Throwable cause) {
        return new StorageException(
            "Error al recuperar el comentario del almacenamiento",
            commentId,
            "RETRIEVE",
            cause
        );
    }

    public static StorageException forConnectionFailure(String storageContext, Throwable cause) {
        return new StorageException(
            "Error de conexión con el almacenamiento",
            null,
            "CONNECT",
            storageContext,
            true,
            cause
        );
    }

    public static StorageException forDatabaseError(String operation, Throwable cause) {
        return new StorageException(
            "Error de base de datos durante la operación: " + operation,
            null,
            operation,
            "DATABASE",
            cause
        );
    }

    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("StorageException: ").append(getMessage());
        
        if (hasCommentId()) {
            sb.append(" | Comment ID: ").append(commentId);
        }
        if (hasOperation()) {
            sb.append(" | Operation: ").append(operation);
        }
        if (hasStorageContext()) {
            sb.append(" | Context: ").append(storageContext);
        }
        sb.append(" | Retryable: ").append(retryable);
        
        return sb.toString();
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java ===
package com.ecommerce.comments.application.usecases;

import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.domain.ports.CommentRepository;
import com.ecommerce.comments.domain.ports.CommentValidator;
import com.ecommerce.comments.infrastructure.api.dto.CreateCommentRequest;
import com.ecommerce.comments.infrastructure.api.dto.CommentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateCommentUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateCommentUseCase.class);

    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;

    public CreateCommentUseCase(CommentRepository commentRepository, CommentValidator commentValidator) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
    }

    @Transactional
    public CommentResponse execute(CreateCommentRequest request) {
        logger.info("Iniciando proceso de creacion de comentario para producto: {}", request.productId());

        UUID productId = UUID.fromString(request.productId());
        UUID userId = UUID.fromString(request.userId());

        validateInput(productId, userId, request.rating(), request.content());

        checkIdempotencyKey(productId, userId, request.idempotencyKey());

        Comment comment = Comment.create(productId, userId, request.content(), request.rating());

        if (!comment.isContentAppropriate()) {
            logger.warn("Contenido inappropriate detectado para producto: {}", productId);
            throw new CommentValidationException("El contenido del comentario contiene lenguaje inapropiado");
        }

        Comment savedComment = persistComment(comment);

        logger.info("Comentario creado exitosamente con ID: {}", savedComment.id());

        return mapToResponse(savedComment);
    }

    private void validateInput(UUID productId, UUID userId, Integer rating, String content) {
        commentValidator.validateCommentRating(rating);

        Comment tempComment = Comment.create(productId, userId, content, rating);
        commentValidator.validateCommentContent(tempComment);
    }

    private void checkIdempotencyKey(UUID productId, UUID userId, String idempotencyKey) {
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            var existingComment = commentRepository.findByProductIdAndUserId(productId, userId);
            if (existingComment.isPresent()) {
                logger.info("Comentario duplicado detectado para clave de idempotencia: {}", idempotencyKey);
                throw new DuplicateCommentException("Ya existe un comentario para este producto del mismo usuario");
            }
        }
    }

    private Comment persistComment(Comment comment) {
        try {
            return commentRepository.save(comment);
        } catch (Exception e) {
            logger.error("Error al almacenar comentario: {}", e.getMessage(), e);
            throw new StorageException("Error al guardar el comentario en el sistema");
        }
    }

    private CommentResponse mapToResponse(Comment comment) {
        return new CommentResponse(
            comment.id().toString(),
            comment.productId().toString(),
            comment.userId().toString(),
            comment.content(),
            comment.rating(),
            comment.isVisible(),
            comment.createdAt().toString()
        );
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java ===
package com.ecommerce.comments.infrastructure.api;

import com.ecommerce.comments.application.usecases.CreateCommentUseCase;
import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.infrastructure.api.dto.CommentResponse;
import com.ecommerce.comments.infrastructure.api.dto.CreateCommentRequest;
import com.ecommerce.comments.infrastructure.api.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comentarios", description = "API para gestion de comentarios de productos")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CreateCommentUseCase createCommentUseCase;

    public CommentController(CreateCommentUseCase createCommentUseCase) {
        this.createCommentUseCase = createCommentUseCase;
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo comentario",
        description = "Permite a un usuario crear un comentario sobre un producto. " +
                      "El sistema valida el contenido y almacena el comentario de manera idempotente."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Comentario creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos o contenido inapropiado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe un comentario para este producto del usuario",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<CommentResponse> createComment(
            @Parameter(description = "Datos del comentario a crear", required = true)
            @Valid @RequestBody CreateCommentRequest request) {

        logger.info("Received comment creation request for product: {}", request.productId());

        try {
            CommentResponse response = createCommentUseCase.execute(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (CommentValidationException e) {
            logger.warn("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);

        } catch (DuplicateCommentException e) {
            logger.warn("Duplicate comment error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(null);

        } catch (StorageException e) {
            logger.error("Storage error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java ===
package com.ecommerce.comments.infrastructure.persistence;

import com.ecommerce.comments.domain.model.Comment;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments", indexes = {
    @Index(name = "idx_product_id", columnList = "product_id"),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_product_user", columnList = "product_id, user_id", unique = true)
})
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "idempotency_key", length = 255)
    private String idempotencyKey;

    public CommentEntity() {
    }

    public CommentEntity(UUID productId, UUID userId, String content, Integer rating, Boolean isVisible) {
        this.productId = productId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.isVisible = isVisible;
    }

    public static CommentEntity fromDomain(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.id = comment.id();
        entity.productId = comment.productId();
        entity.userId = comment.userId();
        entity.content = comment.content();
        entity.rating = comment.rating();
        entity.isVisible = comment.isVisible();
        entity.createdAt = comment.createdAt();
        return entity;
    }

    public Comment toDomain() {
        return Comment.fromPersistence(
            this.id,
            this.productId,
            this.userId,
            this.content,
            this.rating,
            this.isVisible,
            this.createdAt
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java ===
package com.ecommerce.comments.infrastructure.validation;


import com.ecommerce.comments.domain.ports.ValidationResult;
import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.domain.ports.CommentRepository;
import com.ecommerce.comments.domain.ports.CommentValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class CommentValidatorImpl implements CommentValidator {

    private static final int MIN_CONTENT_LENGTH = 10;
    private static final int MAX_CONTENT_LENGTH = 2000;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private static final Pattern INAPPROPRIATE_CONTENT_PATTERN = Pattern.compile(
        "(?i)(spam|scam|fraud|malicious|offensive|explicit|hate|threat)"
    );
    private static final List<String> FORBIDDEN_WORDS = Arrays.asList(
        "spam", "scam", "fraud", "malicious", "offensive"
    );

    private final CommentRepository commentRepository;

    public CommentValidatorImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void validateCommentContent(Comment comment) {
        String content = comment.content();
        
        if (content == null || content.isBlank()) {
            throw new CommentValidationException("El contenido del comentario no puede estar vacío");
        }
        
        if (content.length() < MIN_CONTENT_LENGTH) {
            throw new CommentValidationException(
                String.format("El contenido debe tener al menos %d caracteres", MIN_CONTENT_LENGTH)
            );
        }
        
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new CommentValidationException(
                String.format("El contenido no puede exceder %d caracteres", MAX_CONTENT_LENGTH)
            );
        }
        
        if (containsInappropriateContent(content)) {
            throw new CommentValidationException("El contenido contiene lenguaje inapropiado");
        }
        
        if (containsForbiddenWords(content)) {
            throw new CommentValidationException("El contenido contiene palabras prohibidas");
        }
        
        if (!hasValidStructure(content)) {
            throw new CommentValidationException("El contenido tiene una estructura inválida");
        }
    }

    @Override
    public void validateCommentUniqueness(UUID productId, UUID userId) {
        if (productId == null) {
            throw new CommentValidationException("El ID del producto es obligatorio");
        }
        
        if (userId == null) {
            throw new CommentValidationException("El ID del usuario es obligatorio");
        }
        
        var existingComment = commentRepository.findByProductIdAndUserId(productId, userId);
        
        if (existingComment.isPresent()) {
            throw new DuplicateCommentException(
                "Ya existe un comentario para este producto del mismo usuario"
            );
        }
    }

    @Override
    public void validateCommentRating(Integer rating) {
        if (rating == null) {
            throw new CommentValidationException("La calificación es obligatoria");
        }
        
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new CommentValidationException(
                String.format(
                    "La calificación debe estar entre %d y %d", 
                    MIN_RATING, 
                    MAX_RATING
                )
            );
        }
    }

    @Override
    public ValidationResult validateComment(Comment comment) {
        try {
            validateCommentContent(comment);
            validateCommentUniqueness(comment.productId(), comment.userId());
            validateCommentRating(comment.rating());
            return ValidationResult.success();
        } catch (CommentValidationException | DuplicateCommentException e) {
            return ValidationResult.failure(e.getMessage());
        }
    }

    private boolean containsInappropriateContent(String content) {
        return INAPPROPRIATE_CONTENT_PATTERN.matcher(content).find();
    }

    private boolean containsForbiddenWords(String content) {
        String lowerContent = content.toLowerCase();
        return FORBIDDEN_WORDS.stream()
            .anyMatch(word -> lowerContent.contains(word.toLowerCase()));
    }

    private boolean hasValidStructure(String content) {
        String trimmed = content.trim();
        
        if (!Character.isLetterOrDigit(trimmed.charAt(0))) {
            return false;
        }
        
        if (!Character.isLetterOrDigit(trimmed.charAt(trimmed.length() - 1))) {
            return false;
        }
        
        long letterCount = content.chars()
            .filter(Character::isLetter)
            .count();
        
        return letterCount >= content.length() * 0.5;
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/infrastructure/config/GlobalExceptionHandler.java ===
package com.ecommerce.comments.infrastructure.config;

import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.infrastructure.api.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String DEFAULT_ERROR_MESSAGE = "Ha ocurrido un error interno en el sistema";

    @ExceptionHandler(CommentValidationException.class)
    public ResponseEntity<ErrorResponse> handleCommentValidationException(
            CommentValidationException ex, 
            WebRequest request) {
        
        logger.warn("Error de validación de comentario: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Error")
            .message(ex.getMessage())
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateCommentException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCommentException(
            DuplicateCommentException ex, 
            WebRequest request) {
        
        logger.warn("Intento de comentario duplicado: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.CONFLICT.value())
            .error("Conflict")
            .message(ex.getMessage())
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorResponse> handleStorageException(
            StorageException ex, 
            WebRequest request) {
        
        logger.error("Error de almacenamiento: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Storage Error")
            .message("Error al procesar el comentario en el almacenamiento")
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, 
            WebRequest request) {
        
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                error -> error.getDefaultMessage() != null 
                    ? error.getDefaultMessage() 
                    : "Valor inválido",
                (existing, replacement) -> existing
            ));
        
        logger.warn("Errores de validación en request: {}", errors);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Los datos proporcionados no son válidos")
            .path(getPathFromRequest(request))
            .validationErrors(errors)
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, 
            WebRequest request) {
        
        logger.warn("Argumento ilegal: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Bad Request")
            .message(ex.getMessage())
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, 
            WebRequest request) {
        
        logger.error("Error no manejado: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Internal Server Error")
            .message(DEFAULT_ERROR_MESSAGE)
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private String getPathFromRequest(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}

// === ARCHIVO: src/main/java/com/ecommerce/comments/infrastructure/config/OpenApiConfig.java ===
package com.ecommerce.comments.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${app.api.version:v1}")
    private String apiVersion;

    @Value("${app.api.title:Comments API}")
    private String apiTitle;

    @Value("${app.api.description:Sistema de gestión de comentarios para productos de e-commerce}")
    private String apiDescription;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(apiTitle)
                .version(apiVersion)
                .description(apiDescription)
                .contact(new Contact()
                    .name("Equipo de Desarrollo")
                    .email("dev@ecommerce.com")
                    .url("https://ecommerce.com")
                )
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                )
            )
            .servers(List.of(
                new Server()
                    .url("http://localhost:" + serverPort)
                    .description("Servidor de desarrollo local"),
                new Server()
                    .url("/api")
                    .description("Servidor de producción")
            ))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Token JWT para autenticación")
                )
            );
    }
}

// === ARCHIVO: src/test/java/com/ecommerce/comments/application/usecases/CreateCommentUseCaseTest.java ===
package com.ecommerce.comments.application.usecases;

import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.domain.ports.CommentRepository;
import com.ecommerce.comments.domain.ports.CommentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCommentUseCaseTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentValidator commentValidator;

    private CreateCommentUseCase createCommentUseCase;

    @BeforeEach
    void setUp() {
        createCommentUseCase = new CreateCommentUseCase(commentRepository, commentValidator);
    }

    @Test
    void createComment_WithValidData_ShouldReturnSavedComment() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Excelente producto, muy recomendado.";
        Integer rating = 5;

        Comment savedComment = Comment.create(productId, userId, content, rating);
        when(commentRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = createCommentUseCase.execute(productId, userId, content, rating);

        assertNotNull(result);
        verify(commentValidator).validateCommentContent(any(Comment.class));
        verify(commentValidator).validateCommentUniqueness(productId, userId);
        verify(commentValidator).validateCommentRating(rating);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void createComment_WithExistingComment_ShouldThrowDuplicateCommentException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Muy buen producto";
        Integer rating = 4;

        Comment existingComment = Comment.create(productId, userId, content, rating);
        when(commentRepository.findByProductIdAndUserId(productId, userId))
            .thenReturn(Optional.of(existingComment));

        assertThrows(DuplicateCommentException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );

        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void createComment_WithInvalidContent_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Contenido inapropiado";
        Integer rating = 3;

        doThrow(new CommentValidationException("El contenido no es apropiado"))
            .when(commentValidator).validateCommentContent(any(Comment.class));

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );

        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void createComment_WithInvalidRating_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Buen producto";
        Integer rating = 6;

        doThrow(new CommentValidationException("La calificación debe estar entre 1 y 5"))
            .when(commentValidator).validateCommentRating(rating);

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );

        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void createComment_WhenStorageFails_ShouldThrowStorageException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Muy satisfecho con la compra";
        Integer rating = 5;

        when(commentRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());
        when(commentRepository.save(any(Comment.class)))
            .thenThrow(new StorageException("Error al conectar con la base de datos"));

        assertThrows(StorageException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );
    }

    @Test
    void createComment_WithNullContent_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = null;
        Integer rating = 4;

        doThrow(new CommentValidationException("El contenido no puede estar vacío"))
            .when(commentValidator).validateCommentContent(any(Comment.class));

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );
    }

    @Test
    void createComment_WithEmptyContent_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "";
        Integer rating = 3;

        doThrow(new CommentValidationException("El contenido no puede estar vacío"))
            .when(commentValidator).validateCommentContent(any(Comment.class));

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );
    }

    @Test
    void createComment_WithNullRating_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Contenido válido";
        Integer rating = null;

        doThrow(new CommentValidationException("La calificación es obligatoria"))
            .when(commentValidator).validateCommentRating(rating);

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );
    }

    @Test
    void createComment_WithMinimumRating_ShouldSucceed() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "No me gustó nada";
        Integer rating = 1;

        Comment savedComment = Comment.create(productId, userId, content, rating);
        when(commentRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = createCommentUseCase.execute(productId, userId, content, rating);

        assertNotNull(result);
        verify(commentValidator).validateCommentRating(1);
    }

    @Test
    void createComment_WithMaximumRating_ShouldSucceed() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "El mejor producto que he comprado";
        Integer rating = 5;

        Comment savedComment = Comment.create(productId, userId, content, rating);
        when(commentRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = createCommentUseCase.execute(productId, userId, content, rating);

        assertNotNull(result);
        verify(commentValidator).validateCommentRating(5);
    }
}

// === ARCHIVO: src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java ===
package com.ecommerce.comments.infrastructure.api;


import com.ecommerce.comments.application.usecases.CreateCommentUseCase;
import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.infrastructure.api.dto.CreateCommentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private com.ecommerce.comments.application.usecases.CreateCommentUseCase createCommentUseCase;

    private CreateCommentRequest validRequest;
    private CreateCommentRequest invalidRequest;

    @BeforeEach
    void setUp() {
        validRequest = new CreateCommentRequest(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Este es un comentario válido sobre el producto",
            5
        );

        invalidRequest = new CreateCommentRequest(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "",
            6
        );
    }

    @Test
    void createComment_WithValidRequest_ShouldReturn201AndComment() throws Exception {
        UUID commentId = UUID.randomUUID();
        Comment savedComment = Comment.create(
            validRequest.productId(),
            validRequest.userId(),
            validRequest.content(),
            validRequest.rating()
        );

        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenReturn(savedComment);

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.productId").value(validRequest.productId().toString()))
            .andExpect(jsonPath("$.userId").value(validRequest.userId().toString()))
            .andExpect(jsonPath("$.content").value(validRequest.content()))
            .andExpect(jsonPath("$.rating").value(validRequest.rating()));

        verify(createCommentUseCase, times(1)).execute(
            eq(validRequest.productId()),
            eq(validRequest.userId()),
            eq(validRequest.content()),
            eq(validRequest.rating())
        );
    }

    @Test
    void createComment_WithDuplicateComment_ShouldReturn409Conflict() throws Exception {
        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenThrow(new DuplicateCommentException("El usuario ya ha comentado este producto"));

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error").value("CONFLICTO"))
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createComment_WithInvalidContent_ShouldReturn400BadRequest() throws Exception {
        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenThrow(new CommentValidationException("El contenido del comentario no puede estar vacío"));

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"))
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createComment_WithInvalidRating_ShouldReturn400BadRequest() throws Exception {
        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenThrow(new CommentValidationException("La calificación debe estar entre 1 y 5"));

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WhenStorageFails_ShouldReturn500InternalServerError() throws Exception {
        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenThrow(new StorageException("Error de conexión con la base de datos"));

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error").value("ERROR_INTERNO"))
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createComment_WithMissingProductId_ShouldReturn400BadRequest() throws Exception {
        CreateCommentRequest requestWithoutProductId = new CreateCommentRequest(
            null,
            UUID.randomUUID(),
            "Contenido válido",
            4
        );

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithoutProductId)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WithMissingUserId_ShouldReturn400BadRequest() throws Exception {
        CreateCommentRequest requestWithoutUserId = new CreateCommentRequest(
            UUID.randomUUID(),
            null,
            "Contenido válido",
            4
        );

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithoutUserId)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WithMissingContent_ShouldReturn400BadRequest() throws Exception {
        CreateCommentRequest requestWithoutContent = new CreateCommentRequest(
            UUID.randomUUID(),
            UUID.randomUUID(),
            null,
            4
        );

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithoutContent)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WithMissingRating_ShouldReturn400BadRequest() throws Exception {
        CreateCommentRequest requestWithoutRating = new CreateCommentRequest(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Contenido válido",
            null
        );

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithoutRating)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WithEmptyBody_ShouldReturn400BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createComment_WithInvalidJson_ShouldReturn400BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("not valid json"))
            .andExpect(status().isBadRequest());
    }
}
```
