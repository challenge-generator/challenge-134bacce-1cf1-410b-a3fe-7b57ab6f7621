# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Manejo de Comentarios de Producción en un Sistema de E-commerce**.

| | |
|---|---|
| Tema | E2E prod comments 1790905766 |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web 3.5.6
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.postgresql:postgresql 42.7.3
- org.springframework.boot:spring-boot-starter-test n/a
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.6.0
- org.projectlombok:lombok 1.18.34

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Implementación del Canal de Entrada**: Canal de entrada operativo que valida y acepta comentarios de los usuarios.
- **Fase 2 — Almacenamiento Idempotente de Comentarios**: Sistema que almacena comentarios de manera idempotente.
- **Fase 3 — Manejo de Errores y Respuesta al Usuario**: Sistema que maneja errores y proporciona respuestas coherentes al usuario.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Referencias colgando (56)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `com.ecommerce.comments.infrastructure.api.dto.CommentResponse`
      El import com.ecommerce.comments.infrastructure.api.dto.CommentResponse usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `com.ecommerce.comments.infrastructure.api.dto.CommentResponse`
      El import com.ecommerce.comments.infrastructure.api.dto.CommentResponse usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/api/dto/ErrorResponse.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.annotation.JsonInclude pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `org.hibernate.annotations`
      El import org.hibernate.annotations.CreationTimestamp pertenece a org.hibernate.annotations, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/config/OpenApiConfig.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.models.Components pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/ecommerce/comments/CommentsApplication.java` — `ApplicationProperties.getAllowedOrigins`
      Se invoca `getAllowedOrigins` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/CommentsApplication.java` — `ApplicationProperties.getStorage`
      Se invoca `getStorage` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/domain/ports/CommentValidator.java` — `Comment.rating`
      Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/domain/ports/CommentValidator.java` — `Comment.productId`
      Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/domain/ports/CommentValidator.java` — `Comment.userId`
      Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.id`
      Se invoca `id` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.productId`
      Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.userId`
      Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.content`
      Se invoca `content` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.rating`
      Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `CommentEntity.setVisible`
      Se invoca `setVisible` sobre `CommentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.isVisible`
      Se invoca `isVisible` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.idempotencyKey`
      Se invoca `idempotencyKey` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.createdAt`
      Se invoca `createdAt` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `Comment.updatedAt`
      Se invoca `updatedAt` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java` — `CommentEntity.isVisible`
      Se invoca `isVisible` sobre `CommentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.productId`
      Se invoca `productId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.userId`
      Se invoca `userId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.rating`
      Se invoca `rating` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.content`
      Se invoca `content` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `CreateCommentRequest.idempotencyKey`
      Se invoca `idempotencyKey` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.id`
      Se invoca `id` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.productId`
      Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.userId`
      Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.content`
      Se invoca `content` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.rating`
      Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.isVisible`
      Se invoca `isVisible` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java` — `Comment.createdAt`
      Se invoca `createdAt` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `CreateCommentRequest.productId`
      Se invoca `productId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java` — `StorageException.getMessage`
      Se invoca `getMessage` sobre `StorageException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.id`
      Se invoca `id` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.productId`
      Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.userId`
      Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.content`
      Se invoca `content` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.rating`
      Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.isVisible`
      Se invoca `isVisible` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java` — `Comment.createdAt`
      Se invoca `createdAt` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `Comment.content`
      Se invoca `content` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `Comment.productId`
      Se invoca `productId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `Comment.userId`
      Se invoca `userId` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `Comment.rating`
      Se invoca `rating` sobre `Comment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java` — `DuplicateCommentException.getMessage`
      Se invoca `getMessage` sobre `DuplicateCommentException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `CreateCommentRequest.productId`
      Se invoca `productId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `CreateCommentRequest.userId`
      Se invoca `userId` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `CreateCommentRequest.content`
      Se invoca `content` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java` — `CreateCommentRequest.rating`
      Se invoca `rating` sobre `CreateCommentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (20)

- `pom.xml`
- `src/main/java/com/ecommerce/comments/CommentsApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/ecommerce/comments/domain/model/Comment.java`
- `src/main/java/com/ecommerce/comments/domain/ports/CommentRepository.java`
- `src/main/java/com/ecommerce/comments/domain/ports/CommentValidator.java`
- `src/main/java/com/ecommerce/comments/infrastructure/api/dto/CreateCommentRequest.java`
- `src/main/java/com/ecommerce/comments/infrastructure/api/dto/ErrorResponse.java`
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/JpaCommentRepository.java`
- `src/main/java/com/ecommerce/comments/domain/exceptions/CommentValidationException.java`
- `src/main/java/com/ecommerce/comments/domain/exceptions/DuplicateCommentException.java`
- `src/main/java/com/ecommerce/comments/domain/exceptions/StorageException.java`
- `src/main/java/com/ecommerce/comments/application/usecases/CreateCommentUseCase.java`
- `src/main/java/com/ecommerce/comments/infrastructure/api/CommentController.java`
- `src/main/java/com/ecommerce/comments/infrastructure/persistence/CommentEntity.java`
- `src/main/java/com/ecommerce/comments/infrastructure/validation/CommentValidatorImpl.java`
- `src/main/java/com/ecommerce/comments/infrastructure/config/GlobalExceptionHandler.java`
- `src/main/java/com/ecommerce/comments/infrastructure/config/OpenApiConfig.java`
- `src/test/java/com/ecommerce/comments/application/usecases/CreateCommentUseCaseTest.java`
- `src/test/java/com/ecommerce/comments/infrastructure/api/CommentControllerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/ecommerce/comments/application`
- `src/main/java/com/ecommerce/comments/domain`
- `src/main/java/com/ecommerce/comments/infrastructure`
- `src/main/java/com/ecommerce/comments/infrastructure/api`
- `src/main/java/com/ecommerce/comments/infrastructure/persistence`
- `src/main/java/com/ecommerce/comments/infrastructure/config`
- `src/main/resources`
- `src/test/java/com/ecommerce/comments`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Spring, Tecnología Java, Senior L2
- Brecha que el reto ataca: Comprueba manejo de errores y validaciones

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
