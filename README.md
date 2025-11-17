# <p align="center">Analizador de Arcos SITM-MIO (Avance de proyecto) 🚌</p>

### INTEGRANTES

- Angy María Hurtado Osorio &nbsp;&nbsp;&nbsp;&nbsp; [A00401755]
- Daniel Esteban Arcos Cerón &nbsp;&nbsp;&nbsp;&nbsp;[A00400760]
- Hideki Tamura Hernández &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[A00348618]

---

### CONTEXTO

<p align="justify">Este proyecto permite analizar y visualizar los arcos (conexiones) de las rutas del sistema SITM-MIO a partir de datos CSV. El programa genera gráficos en formato de imagen y presenta un menú interactivo en consola para explorar rutas individuales o todas las rutas de forma global.</p>

---

### REQUERIMIENTOS

* **Java 17 o superior**
* **PowerShell** (para la compilación con Java)
* Opcional: **Gradle** (el proyecto incluye `gradlew`)

---

### INSTRUCCIONES DE USO

## A) Java puro (PowerShell)

**1. Para compilar**

Ejecutar desde la raíz del proyecto:

```
javac -encoding UTF-8 -d Tracker/bin/main (Get-ChildItem -Recurse Tracker/src/main/java -Filter *.java | ForEach-Object { $_.FullName })
```

**2. Para ejecutar**

```
java -cp ".\Tracker\bin\main" app.Main
```

---

## B) Alternativa con Gradle

**1. Para compilar**

```
.\gradlew :Tracker:build
```

**2. Para ejecutar**

```
java -jar .\Tracker\build\libs\Tracker.jar
```

---

### ¿QUÉ HACE EL PROGRAMA?

<p align="justify">El sistema permite visualizar los arcos del SITM-MIO ya sea para <b>todas las rutas</b> o para <b>una ruta específica</b>. En ambos casos se genera un grafo que se exporta como imagen (.jpg). Todas las imágenes producidas por el programa se almacenan automáticamente en la carpeta:</p>

```
imgs/
```