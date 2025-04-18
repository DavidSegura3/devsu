# Microservicio de Gestión Bancaria (Prueba Técnica)

Este proyecto implementa un sistema bancario básico como una arquitectura de microservicios, separando las funcionalidades de Clientes y Personas por un lado, y Cuentas y Movimientos por el otro. Se utiliza Spring Boot para el desarrollo en Java, siguiendo principios de Clean Code y Clean Architecture.

## Estructura del Proyecto

El proyecto se divide en dos microservicios principales:

1.  **`ms-clients` (Cliente, Persona):**
    * Maneja la información de las Personas y los Clientes.
    * Implementa el CRUD para la entidad Cliente.
    * Expone APIs REST para la gestión de clientes.

Se contempla una comunicación asincrónica entre estos dos microservicios para ciertas operaciones (aunque la implementación específica del mecanismo asíncrono dependerá de la complejidad deseada y no está detallada en este README base).

## Tecnologías Utilizadas

* **Java Spring Boot:** Framework para el desarrollo de los microservicios.
* **JPA/Hibernate:** Para la persistencia y el manejo de entidades en bases de datos relacionales.
* **Base de Datos Relacional:** (A elección del desarrollador, por ejemplo, H2 para desarrollo, PostgreSQL, MySQL).
* **Docker:** Para la contenedorización y despliegue de los microservicios.
* **JUnit:** Para la implementación de pruebas unitarias.
* **Spring Data JPA:** Para facilitar la interacción con la base de datos.
* **Lombok:** Para reducir el boilerplate code (opcional).
* **IDE de Preferencia:** (IntelliJ IDEA)
* **Postman/Karate DSL:** Para la validación de las APIs REST.

## Requisitos Funcionales Implementados

* **F1: Generación de CRUD en Entidades:**
    * **`ms-clients`:**
        * CRUD completo para la entidad `Cliente` a través del endpoint `/clientes`.

* **F5: Pruebas Unitarias (`ms-clients`):**
    * Se implementó al menos una prueba unitaria para la entidad `Cliente` (e.g., para verificar la creación de un cliente con estado activo por defecto).

* **F6: Pruebas de Integración (Deseable - `ms-clients` o `ms-accounts`):**
    * Se implementó al menos una prueba de integración para verificar la interacción entre componentes (e.g., probar la creación de un cliente a través del API y su persistencia en la base de datos).

* **F7: Despliegue en Contenedores:**
    * Se proporcionan archivos `Dockerfile` para cada microservicio para su contenedorización con Docker.
    * Se puede incluir un `docker-compose.yml` para orquestar el levantamiento de ambos microservicios y la base de datos.

## API REST Endpoints

A continuación, se listan los endpoints principales de cada microservicio:

**`ms-clients` (/clientes):**

* `POST /client`: Crear un nuevo cliente.
* `GET /client/clients`: Listar todos los clientes.
* `GET /client/client-id/{clientId}`: Obtener un cliente por su ID.
* `PUT /client/client-id/{clientId}`: Actualizar un cliente existente.
* `PATCH /client/password/client-id/{clientId}`: Actualizar parcialmente un cliente existente.
* `DELETE /client/client-id/{clienteId}`: Eliminar un cliente por su ID.

## Consideraciones Adicionales (Potencial para Máxima Puntuación)

* **Rendimiento:** Se consideraron estrategias para optimizar las consultas a la base de datos (e.g., indexación, consultas eficientes).
* **Escalabilidad:** La arquitectura de microservicios en sí misma facilita la escalabilidad horizontal. Se podría mencionar el uso de un orquestador de contenedores (e.g., Kubernetes) para escalar los servicios.
* **Resiliencia:** Se podrían implementar patrones como reintentos (retries) y disyuntores (circuit breakers) para manejar fallos en la comunicación entre microservicios (aunque no necesariamente implementados en esta versión básica).

## Despliegue con Docker

Cada microservicio incluye un `Dockerfile` en su raíz. Para construir las imágenes Docker, se puede ejecutar el siguiente comando en el directorio de cada microservicio:

```bash
docker build -t <nombre_imagen> .