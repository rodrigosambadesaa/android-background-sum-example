# Android Background Sum Example

Versión moderna del antiguo ejemplo docente de `AsyncTask` que suma `10, 7, 8, 2, 4, 1`, publica progreso y permite cancelar la operación.

## Qué enseña

- Ejecutar trabajo bloqueante o costoso fuera del hilo principal.
- Publicar progreso en la UI de forma explícita.
- Cancelar una tarea mediante `Future.cancel(true)` e interrupciones.
- Separar el worker (`ProgressiveSummer`) de las clases Android.
- Limpiar tareas y callbacks al finalizar el ciclo de vida de la `Activity`.

La espera de un segundo por elemento es deliberada: simula una operación lenta para que el progreso y la cancelación sean visibles. No representa una técnica que deba usarse en lógica de producción.

## Stack

- Java 17
- Android Gradle Plugin 9.3.0
- Gradle 9.5.0
- compileSdk / targetSdk 37
- minSdk 23
- AndroidX AppCompat 1.8.0
- View Binding
- JUnit 4.13.2

## Validación

```bash
gradle testDebugUnitTest
gradle lintDebug
gradle assembleDebug
```

GitHub Actions instala explícitamente Gradle 9.5.0. Si se desea el wrapper estándar, puede generarse localmente con `gradle wrapper --gradle-version 9.5.0`.

Véase [`docs/ORIGINAL_REVIEW.md`](docs/ORIGINAL_REVIEW.md) para comparar el ejemplo académico con esta implementación.
