# Revisión del ejemplo original de AsyncTask

El ejemplo original era útil para 2017–2018 porque mostraba cuatro conceptos importantes de `AsyncTask`: preparación, trabajo en background, publicación de progreso y cancelación.

## Aspectos que estaban bien

- El trabajo lento se ejecutaba fuera del hilo principal.
- Existía progreso incremental con `publishProgress` / `onProgressUpdate`.
- Se contemplaba la cancelación.
- El ejemplo era pequeño y fácil de seguir en clase.

## Aspectos a corregir hoy

- `AsyncTask` está deprecado.
- `ProgressDialog` también es un patrón de UX antiguo y acopla la tarea a una ventana concreta.
- La tarea conserva un `Context` de la Activity.
- El máximo del `ProgressDialog` se configura desde `doInBackground()`, donde no debería tocarse estado de UI.
- El `InterruptedException` se limita a imprimir el stack trace y no restaura la interrupción.
- La lógica de suma, espera y UI están fuertemente acopladas.
- Los textos de la interfaz están hardcodeados.
- Las pruebas incluidas son únicamente las plantillas generadas por Android Studio y no prueban el comportamiento real.

## Modernización

La nueva versión mantiene el objetivo pedagógico original usando `ExecutorService`, `Future.cancel(true)` y `Handler` para regresar al hilo principal. `ProgressiveSummer` es Java puro, admite callbacks de progreso, respeta interrupciones y se prueba sin Android.
