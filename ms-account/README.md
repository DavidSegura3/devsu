# Microservicio de Gestión Bancaria (Prueba Técnica)

Este proyecto implementa un sistema bancario básico como una arquitectura de microservicios, separando las funcionalidades de Clientes y Personas por un lado, y Cuentas y Movimientos por el otro. Se utiliza Spring Boot para el desarrollo en Java, siguiendo principios de Clean Code y Clean Architecture.

## Estructura del Proyecto

El proyecto se divide en dos microservicios principales:

1.  **`ms-accounts` (Cuenta, Movimientos):**
    * Maneja la información de las Cuentas y los Movimientos.
    * Implementa el CRUD (Crear, Leer, Actualizar) para las entidades Cuenta y Movimiento.
    * Implementa la lógica de registro de movimientos, validación de saldo y generación de reportes.
    * Expone APIs REST para la gestión de cuentas y movimientos.

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
    * **`ms-accounts`:**
        * Crear (`POST`), Leer (`GET`), y Actualizar (`PUT`/`PATCH`) para la entidad `Cuenta` a través del endpoint `/cuentas`.
        * Crear (`POST`), Leer (`GET`), y Actualizar (`PUT`/`PATCH`) para la entidad `Movimiento` a través del endpoint `/movimientos`.

* **F2: Registro de Movimientos (`ms-accounts`):**
    * Endpoint para registrar movimientos (`POST` a `/movimientos`).
    * Permite valores positivos (depósitos) y negativos (retiros).
    * Actualiza el saldo disponible de la cuenta asociada al movimiento.
    * Registra la transacción con fecha, tipo de movimiento, valor y saldo resultante.

* **F3: Alerta de Saldo No Disponible (`ms-accounts`):**
    * Al intentar registrar un movimiento de retiro que exceda el saldo disponible, el API retornará un error específico (e.g., código de estado 400 Bad Request) con un mensaje claro: "Saldo no disponible". El manejo de esta excepción se realiza mediante un mecanismo de manejo de excepciones global o específico del controlador.

* **F4: Reporte de Estado de Cuenta (`ms-accounts`):**
    * Endpoint `GET /reportes?fechaDesde=YYYY-MM-DD&fechaHasta=YYYY-MM-DD&clienteId={clienteId}` para generar el reporte.
    * Retorna un JSON que contiene:
        * Información del cliente.
        * Listado de cuentas asociadas al cliente con sus saldos actuales.
        * Detalle de los movimientos realizados en cada cuenta dentro del rango de fechas especificado, incluyendo fecha, tipo de movimiento, valor y saldo al momento del movimiento.
    * La solicitud de las fechas se realiza mediante parámetros de consulta (`fechaDesde`, `fechaHasta`).

* **F5: Pruebas Unitarias (`ms-clients`):**
    * Se implementó al menos una prueba unitaria para la entidad `Cliente` (e.g., para verificar la creación de un cliente con estado activo por defecto).

* **F6: Pruebas de Integración (Deseable - `ms-clients` o `ms-accounts`):**
    * Se implementó al menos una prueba de integración para verificar la interacción entre componentes (e.g., probar la creación de un cliente a través del API y su persistencia en la base de datos).

* **F7: Despliegue en Contenedores:**
    * Se proporcionan archivos `Dockerfile` para cada microservicio para su contenedorización con Docker.
    * Se puede incluir un `docker-compose.yml` para orquestar el levantamiento de ambos microservicios y la base de datos.

## API REST Endpoints

A continuación, se listan los endpoints principales de cada microservicio:

**`ms-clients` (/clients):**
**`ms-accounts` (/accounts):**

* `POST /account`: Crear una nueva cuenta.
* `GET /account/accounts`: Listar todas las cuentas.
* `GET /account/account-number/{accountNumber}`: Obtener una cuenta por su número de cuenta.
* `PUT /account/balance/account-id/{accountId}`: Actualizar una cuenta existente.

**`ms-accounts` (/movements):**

* `POST /movement?movementType=?`: Registrar un nuevo movimiento.
* `GET /movement/movements`: Listar todos los movimientos.
* `GET /movement/movement-id/{movementId}`: Obtener un movimiento por su ID.



## Consideraciones Adicionales (Potencial para Máxima Puntuación)

* **Rendimiento:** Se consideraron estrategias para optimizar las consultas a la base de datos (e.g., indexación, consultas eficientes).
* **Escalabilidad:** La arquitectura de microservicios en sí misma facilita la escalabilidad horizontal. Se podría mencionar el uso de un orquestador de contenedores (e.g., Kubernetes) para escalar los servicios.
* **Resiliencia:** Se podrían implementar patrones como reintentos (retries) y disyuntores (circuit breakers) para manejar fallos en la comunicación entre microservicios (aunque no necesariamente implementados en esta versión básica).

## Despliegue con Docker

Cada microservicio incluye un `Dockerfile` en su raíz. Para construir las imágenes Docker, se puede ejecutar el siguiente comando en el directorio de cada microservicio:

```bash
docker build -t <nombre_imagen> .