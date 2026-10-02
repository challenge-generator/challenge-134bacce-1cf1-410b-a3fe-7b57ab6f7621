# Manejo de Comentarios de Producción en un Sistema de E-commerce

El sistema de e-commerce permite a los usuarios dejar comentarios sobre productos. Estos comentarios deben ser validados y almacenados de manera idempotente para evitar duplicados. El sistema debe manejar adecuadamente los errores que surjan durante el proceso de almacenamiento y proporcionar una respuesta coherente al usuario. Los actores involucrados son el usuario, el sistema de comentarios y el almacén de datos. El sistema debe asegurar que los comentarios no contengan lenguaje inapropiado y que cumplan con ciertas restricciones de longitud y formato.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | E2E prod comments 1790905766 |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Implementación del Canal de Entrada

**Objetivo:** Crear un canal de entrada que acepte comentarios de los usuarios y los valide según las reglas definidas.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- El canal de entrada debe recibir comentarios de los usuarios y validarlos para asegurar que cumplan con las restricciones de longitud y formato.
- El sistema debe manejar los errores de validación y proporcionar una respuesta adecuada al usuario.

**Entregable:** Canal de entrada operativo que valida y acepta comentarios de los usuarios.

<details>
<summary>Pistas de conocimiento</summary>

- Considera las posibles fuentes de errores y cómo manejarlos de manera efectiva.
- Piensa en cómo puedes asegurar la idempotencia en el almacenamiento de comentarios.

</details>

### Fase 2: Almacenamiento Idempotente de Comentarios

**Objetivo:** Implementar el almacenamiento idempotente de comentarios en el sistema.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- El sistema debe almacenar los comentarios de manera idempotente para evitar duplicados.
- Debes asegurar que el almacenamiento sea resiliente a fallos temporales y que los comentarios se almacenen correctamente.

**Entregable:** Sistema que almacena comentarios de manera idempotente.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo puedes usar una clave única para asegurar la idempotencia.
- Piensa en cómo manejar los fallos temporales durante el almacenamiento.

</details>

### Fase 3: Manejo de Errores y Respuesta al Usuario

**Objetivo:** Implementar el manejo de errores y proporcionar una respuesta coherente al usuario.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- El sistema debe manejar los errores que surjan durante el proceso de almacenamiento y proporcionar una respuesta coherente al usuario.
- Debes asegurarte de que el usuario reciba una respuesta clara y útil en caso de error.

**Entregable:** Sistema que maneja errores y proporciona respuestas coherentes al usuario.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los diferentes tipos de errores que pueden ocurrir y cómo manejarlos de manera efectiva.
- Piensa en cómo puedes proporcionar una respuesta útil al usuario en caso de error.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es el manejo de errores en el contexto de este reto?
- **paraQueSirve**: ¿Para qué sirve el almacenamiento idempotente de comentarios?
- **comoSeUsa**: ¿Cómo se usa una clave única para asegurar la idempotencia en el almacenamiento de comentarios?
- **erroresComunes**: ¿Cuáles son los errores comunes que pueden ocurrir durante el proceso de almacenamiento de comentarios?
- **queDecisionesImplica**: ¿Qué decisiones implica el manejo de errores y la idempotencia en el almacenamiento de comentarios?

## Criterios de Evaluacion

- Implementación de un canal de entrada que valida y acepta comentarios de los usuarios.
- Almacenamiento idempotente de comentarios en el sistema.
- Manejo adecuado de errores y provisión de respuestas coherentes al usuario.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
