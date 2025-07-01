# Aplicación de Rick y Morty

Este repositorio contiene los componentes tanto del frontend (Angular) como del backend (Spring Boot) de la aplicación de Rick y Morty.

## Descripción

Esta aplicación muestra una lista de personajes de la serie Rick y Morty. Permite a los usuarios ver los personajes, navegar a través de la lista y ver detalles de un personaje específico. El frontend está construido con Angular y el backend con Spring Boot, que obtiene los datos de la "API de Rick y Morty\" url=\"https://rickandmortyapi.com/\"

## Requisitos Previos

Asegúrate de tener instaladas las siguientes herramientas:

*   **Java**: Versión 17 o superior
*   **Maven**: Versión 3.6 o superior
*   **Node.js**: Versión 16 o superior
*   **Angular CLI**: Versión 14 o superior

## Estructura del Proyecto

El repositorio está organizado en dos directorios principales:

*   `RickAndMortyBackend/`: Contiene el proyecto de Spring Boot.
*   `rick-and-morty-frontend/`: Contiene el proyecto de Angular.

## Cómo Empezar

Para que la aplicación funcione, sigue estos pasos:

### Backend (Spring Boot)

1.  Navega al directorio `RickAndMortyBackend`:
    ```bash
    cd RickAndMortyBackend
    ```
2.  Compila la aplicación backend:
    ```bash
    mvn clean install
    ```
3.  Ejecuta la aplicación backend:
    ```bash
    mvn spring-boot:run
    ```
    El backend normalmente se ejecutará en `http://localhost:8080`.

### Frontend (Angular)

1.  Navega al directorio `rick-and-morty-frontend`:
    ```bash
    cd rick-and-morty-frontend
    ```
2.  Instala las dependencias del frontend:
    ```bash
    npm install
    ```
3.  Inicia el servidor de desarrollo del frontend:
    ```bash
    ng serve
    ```
    El frontend normalmente se ejecutará en `http://localhost:4200`.

## Endpoints de la API

El backend expone los siguientes endpoints:

*   `GET /api/characters`: Obtiene una lista paginada de personajes.
    *   Query Params: `page` (número de página, opcional).
*   `GET /api/characters/{id}`: Obtiene un personaje por su ID.

## Ejecución de Pruebas

### Pruebas de Backend

Para ejecutar las pruebas del backend, navega al directorio `RickAndMortyBackend` y ejecuta:

```bash
mvn test
```

### Pruebas de Frontend

Para ejecutar las pruebas del frontend, navega al directorio `rick-and-morty-frontend` y ejecuta:

```bash
ng test
```
