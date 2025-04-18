# Microservicio de Gestión Bancaria (Prueba Técnica)

Este proyecto implementa un sistema bancario básico como una arquitectura de microservicios, separando las funcionalidades de Clientes y Personas por un lado, y Cuentas y Movimientos por el otro. Se utiliza Spring Boot para el desarrollo en Java, siguiendo principios de Clean Code y Clean Architecture.

## Estructura del Proyecto

El proyecto se divide en dos microservicios principales:

1.  **`ms-clientes` (Cliente, Persona):**
    * Maneja la información de las Personas y los Clientes.
    * Implementa el CRUD para la entidad Cliente.
    * Expone APIs REST para la gestión de clientes.

2.  **`ms-cuentas` (Cuenta, Movimientos):**
    * Maneja la información de las Cuentas y los Movimientos.
    * Implementa el CRUD (Crear, Leer, Actualizar) para las entidades Cuenta y Movimiento.
    * Implementa la lógica de registro de movimientos, validación de saldo y generación de reportes.
    * Expone APIs REST para la gestión de cuentas y movimientos.

Se contempla una comunicación asincrónica entre estos dos microservicios para ciertas operaciones (aunque la implementación específica del mecanismo asíncrono dependerá de la complejidad deseada y no está detallada en este README base).

## Tecnologías Utilizadas

* **Java Spring Boot:** Framework para el desarrollo de los microservicios.
* **JPA/Hibernate:** Para la persistencia y el manejo de entidades en bases de datos relacionales.
* **Base de Datos Relacional:** (H2 para desarrollo).
* **Docker:** Para la contenedorización y despliegue de los microservicios.
* **JUnit:** Para la implementación de pruebas unitarias.
* **Spring WebFlux (Opcional):** Para implementar comunicación asincrónica (WebClient).
* **Spring Data JPA:** Para facilitar la interacción con la base de datos.
* **Lombok:** Para reducir el boilerplate code (opcional).
* **IDE de Preferencia:** IntelliJ IDEA
* **Postman:** Para la validación de las APIs REST.

## Requisitos Funcionales Implementados

* **F1: Generación de CRUD en Entidades:**
    * **`ms-clientes`:**
        * CRUD completo para la entidad `Cliente` a través del endpoint `/clientes`.
    * **`ms-cuentas`:**
        * Crear (`POST`), Leer (`GET`), y Actualizar (`PUT`/`PATCH`) para la entidad `Cuenta` a través del endpoint `/cuentas`.
        * Crear (`POST`), Leer (`GET`), y Actualizar (`PUT`/`PATCH`) para la entidad `Movimiento` a través del endpoint `/movimientos`.

* **F2: Registro de Movimientos (`ms-cuentas`):**
    * Endpoint para registrar movimientos (`POST` a `/movimientos`).
    * Permite valores positivos (depósitos) y negativos (retiros).
    * Actualiza el saldo disponible de la cuenta asociada al movimiento.
    * Registra la transacción con fecha, tipo de movimiento, valor y saldo resultante.

* **F3: Alerta de Saldo No Disponible (`ms-cuentas`):**
    * Al intentar registrar un movimiento de retiro que exceda el saldo disponible, el API retornará un error específico (e.g., código de estado 400 Bad Request) con un mensaje claro: "Saldo no disponible". El manejo de esta excepción se realiza mediante un mecanismo de manejo de excepciones global o específico del controlador.

* **F4: Reporte de Estado de Cuenta (`ms-cuentas`):**
    * Endpoint `GET /reportes?fechaDesde=YYYY-MM-DD&fechaHasta=YYYY-MM-DD&clienteId={clienteId}` para generar el reporte.
    * Retorna un JSON que contiene:
        * Información del cliente.
        * Listado de cuentas asociadas al cliente con sus saldos actuales.
        * Detalle de los movimientos realizados en cada cuenta dentro del rango de fechas especificado, incluyendo fecha, tipo de movimiento, valor y saldo al momento del movimiento.
    * La solicitud de las fechas se realiza mediante parámetros de consulta (`fechaDesde`, `fechaHasta`).

* **F5: Pruebas Unitarias (`ms-clientes`):**
    * Se implementó al menos una prueba unitaria para la entidad `Cliente` (e.g., para verificar la creación de un cliente con estado activo por defecto).

* **F6: Pruebas de Integración (Deseable - `ms-clientes` o `ms-cuentas`):**
    * Se implementó al menos una prueba de integración para verificar la interacción entre componentes (e.g., probar la creación de un cliente a través del API y su persistencia en la base de datos).

* **F7: Despliegue en Contenedores:**
    * Se proporcionan archivos `Dockerfile` para cada microservicio para su contenedorización con Docker.
    * Se puede incluir un `docker-compose.yml` para orquestar el levantamiento de ambos microservicios y la base de datos.

## API REST Endpoints

* `Devsu.postman_collection.json`: Collección Postman [Devsu.postman_collection.json](Devsu.postman_collection.json)

A continuación, se listan los endpoints principales de cada microservicio:


**`ms-client` (/clients):** `Documentación Endpoints Swagger` : http://localhost:8000/swagger-ui/index.html#


* `POST /client`: Creates a new client with the provided information.
* `GET /client/clients`: Retrieves a list of clients.
* `GET /client/client-id/{clientId}`: Retrieves a client based on the provided client ID.
* `PUT /client/client-id/{clientId}`: Retrieves a client based on the provided client ID.
* `PATCH /client/password/client-id/{clientId}`: Search for a client by clientId and change its password if found.
* `DELETE /client/client-id/{clientId}`: Deletes a client based on the provided client ID.

**`ms-account` (/accounts):** `Documentación Endpoints Swagger` : http://localhost:9000/swagger-ui/index.html#

* `POST /account`: Creates a new account with the provided information.
* `POST /account/client-id/{clientId}`: Creates a new account and associates it with a client.
* `GET /account/account-number/{accountNumber}`: Retrieves a account based on the provided account number.
* `GET /account/accounts`: Retrieves a list of accounts
* `DELETE /account/account-id/{accountId}`: Deletes a account based on the provided account ID.
* `PATCH /account/balance/account-id/{accountId}`: Search for a account by accountId and change its balance if found.

**`ms-account` (/movements):** `Documentación Endpoints Swagger` : http://localhost:9000/swagger-ui/index.html#

* `POST /movement`: Creates a new movement with the provided information.
* `GET /movement/movement-id/{movementId}`: Retrieves a movement based on the provided movement ID.
* `GET /movement/movements`: Retrieves a list of movements.
* `DELETE /movement/movement-id/{movementId}`: Deletes a movement based on the provided movement ID.


## Despliegue con Docker Compose

Este documento describe cómo desplegar la aplicación utilizando el archivo `docker-compose.yml` proporcionado.

### Requisitos

* Docker Engine instalado.
* Docker Compose instalado.
* El archivo `docker-compose.yml` [docker-compose.yml](docker-compose.yml).

### Pasos de Despliegue

1.  **Abrir la Terminal/Línea de Comandos**

    * Abre tu terminal o línea de comandos preferida (por ejemplo, `cmd.exe`, `PowerShell` en Windows, o `Terminal` en Linux/macOS).

2.  **Navegar al Directorio del Proyecto**

    * Utiliza el comando `cd` para navegar al directorio donde se encuentra el archivo `docker-compose.yml`. En este caso:

        ```bash
        cd "E:\Devsu\repo"
        ```

3.  **Levantar los Contenedores**

    * Ejecuta el siguiente comando para construir las imágenes (si es necesario) e iniciar los contenedores definidos en el archivo `docker-compose.yml`:

        ```bash
        docker compose up --build -d
        ```

    * `docker compose up`: Comando para iniciar los servicios definidos en `docker-compose.yml`.
    * `--build`: Construye las imágenes de Docker si no existen o si hay cambios en los `Dockerfile`.
    * `-d`: Ejecuta los contenedores en modo "detached" (en segundo plano).

4.  **Verificar el Estado de los Contenedores**

    * Para confirmar que los contenedores se están ejecutando correctamente, utiliza el comando:

        ```bash
        docker ps
        ```

    * Esto mostrará una lista de los contenedores en ejecución. Deberías ver `ms-client` y `ms-account` con un estado "Up" o similar.

5.  **Acceder a la Aplicación**

    * La aplicación estará accesible a través de los puertos definidos en el archivo `docker-compose.yml`:

        * `ms-client`: `http://localhost:8000`
        * `ms-account`: `http://localhost:9000`

    * Puedes acceder a estas URLs en tu navegador o utilizando una herramienta como Postman para interactuar con las APIs.

### Descripción de la Configuración de Docker Compose

El archivo `docker-compose.yml` define dos servicios:

* **`ms-client`**:

    * Construye la imagen desde el directorio `./ms-client` usando el `Dockerfile` ahí presente.
    * Mapea el puerto 8000 del contenedor al puerto 8000 del host.
    * Lo agrega a la red `devsu`.
    * Lo configura para reiniciarse automáticamente (`restart: always`).
    * Define variables de entorno para la configuración de la base de datos H2.

* **`ms-account`**:

    * Similar a `ms-client`, pero para el microservicio de cuentas.
    * Mapea el puerto 9000.
    * Depende de que `ms-client` esté en ejecución (`depends_on: - ms-client`).
    * También utiliza una base de datos H2.

* **`networks`**:

    * Define una red llamada `devsu` para que los microservicios puedan comunicarse entre sí.

## Repositorio en GitHub y Descarga del Proyecto

Puedes acceder al código fuente de este proyecto y descargarlo desde el siguiente repositorio en GitHub:

* **`Ruta GitHub:`** https://github.com/DavidSegura3/devsu?tab=readme-ov-file


Para clonar el repositorio y obtener una copia local del proyecto, puedes utilizar el siguiente comando:

git clone [https://github.com/DavidSegura3/devsu.git](https://github.com/DavidSegura3/devsu.git)

