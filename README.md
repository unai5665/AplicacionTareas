# Aplicación de Tareas

Este proyecto consiste en una aplicación para gestionar tareas, basada en el siguiente modelo de datos:

## Modelo de Datos

La aplicación utiliza dos entidades principales: **Tareas** y **TiposTareas**.

### Tareas
| Atributo       | Tipo     | Descripción                |
|----------------|----------|----------------------------|
| `id`           | `INT`    | Identificador único de la tarea |
| `título`       | `STRING` | Título de la tarea         |
| `descripción`  | `TEXT`   | Descripción detallada de la tarea |
| `id_tipostareas` | `INT`   | Relación con el tipo de tarea (clave foránea) |

### TiposTareas
| Atributo       | Tipo     | Descripción                |
|----------------|----------|----------------------------|
| `id`           | `INT`    | Identificador único del tipo de tarea |
| `título`       | `STRING` | Nombre del tipo de tarea   |

## Flujo de Trabajo

El flujo de trabajo de la aplicación será gestionado a través de la creación, actualización, eliminación y visualización de tareas. Cada tarea estará asociada a un tipo de tarea específico, lo que permitirá categorizar y organizar las tareas.

## Implementación

Partiremos de un borrador realizado en Figma para definir la interfaz de usuario (UI). A continuación se presentan algunas imágenes del boceto de la interfaz de usuario:

### Boceto de Figma

![Boceto de Figma](ruta/a/la/imagen/ficha_de_figma.png)

Este es el diseño inicial realizado en Figma, que nos servirá como base para la implementación de la UI en la aplicación.

### Interfaz de la Aplicación

Aquí puedes ver cómo se ve la interfaz de la aplicación:

![Interfaz de la Aplicación](ruta/a/la/imagen/interfaz_aplicacion.png)

Esta es la representación final de la aplicación después de ser implementada siguiendo el diseño original de Figma.

## Tecnologías Utilizadas

- **Android**: Desarrollo de la aplicación móvil.
- **Kotlin**: Lenguaje de programación principal.
- **Room Database**: Base de datos local para almacenar tareas y tipos de tarea.
- **Jetpack Compose**: Librería para crear interfaces de usuario declarativas en Android.

## Instrucciones para Ejecutar el Proyecto

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/unai5665/AplicacionTareas.git
