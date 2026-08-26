# Enunciado / material de partida — Suma en segundo plano

Ejemplo docente del bloque de tareas asíncronas de Programación Multimedia y Dispositivos Móviles.

Ejecutar en segundo plano la suma progresiva de los valores `10, 7, 8, 2, 4, 1`, mostrando el progreso de la operación en la interfaz y permitiendo cancelarla antes de finalizar.

El objetivo es evitar trabajo bloqueante en el hilo principal, publicar actualizaciones parciales de forma segura y gestionar correctamente la cancelación y el ciclo de vida de la Activity.

La implementación histórica utilizaba `AsyncTask`; la versión actual mantiene el comportamiento pedagógico usando concurrencia estándar de Java.

No existe una base de datos asociada.
