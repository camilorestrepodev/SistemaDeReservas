# Sistema de Reservas
Este proyecto es un sistema de reservas desarrollado en Java utilizando el framework Spring y la herramienta de construcción Gradle. 
El sistema permite a los clientes realizar reservas en línea para habitaciones de hotel (Hotel Ashir).

## Requisitos
- Java versión: 11
- Gradle:
- Spring Boot: 2.7.11 (SNAPSHOT)
- Base de datos: MySQL 
- JpaRepository

## Configuración
Antes de comenzar, asegúrese de tener una base de datos configurada y actualice las credenciales de la base de datos en el archivo **application.properties**.
```java
spring.datasource.url=jdbc:mysql://localhost:3306/sistemadereservas
spring.datasource.username=root
spring.datasource.password={password}
spring.jpa.hibernate.ddl-auto=update
server.port=8080
spring.mvc.pathmatch.matching-strategy=ant-path-matcher
```

## Documentación
La documentación de la API se genera automáticamente con Swagger. Para acceder a la documentación, abra un navegador web y vaya a http://localhost:8080/swagger-ui.html.

## Patrón de Diseño
Este proyecto utiliza el patrón de diseño DTO (Data Transfer Object) para transferir datos entre las diferentes capas de la aplicación. Los DTO son objetos simples que contienen campos y métodos de acceso, y se utilizan para transferir datos entre los controladores y los servicios.

## Diagrama del Modelo Entidad-Relación
Este es el diagrama del modelo entidad-relación para la base de datos MySQL del proyecto de sistema de reservas:
![Diagrama](https://user-images.githubusercontent.com/119947851/232180155-0b7b4fa5-03e4-4316-a5cc-e0fa675722d2.png)

- La tabla **Cliente** contiene información sobre los clientes, como su cedula, nombre, apellido, direccion, edad y correo electronico.
- La tabla **Habitación** contiene información sobre las habitaciones, como el numero, precio y tipo.
- La tabla **Reserva** contiene información sobre las reservas, como el codigo de reserva, la cedula del cliente que la realizó, el numero de la habitación reservada y la fecha de la reserva y el total a pagar.

## Diagrama de Clases
```java
com.example.reservas
├── config
│   ├── DatabaseConfig.java
│   └── SwaggerConfig.java
├── controller
│   ├── ClienteController.java
│   ├── HabitacionController.java
│   └── ReservaController.java
├── dto
│   ├── ClienteDTO.java
│   ├── HabitacionDTO.java
│   └── ReservaDTO.java
├── entity
│   ├── Cliente.java
│   ├── Habitacion.java
│   └── Reserva.java
├── repository
│   ├── ClienteRepository.java
│   ├── HabitacionRepository.java
│   └── ReservaRepository.java
├── service
│   ├── ClienteService.java
│   ├── HabitacionService.java
│   └── ReservaService.java
└── ReservasApplication.java
```
- El paquete config contiene las clases de configuración para la base de datos y Swagger.
- El paquete controller contiene las clases controladoras para los microservicios de Cliente, Habitación y Reserva.
- El paquete dto contiene las clases DTO (Data Transfer Object) para los objetos Cliente, Habitación y Reserva, que se utilizan para transferir datos entre la capa de presentación y la capa de servicios.
- El paquete entity contiene las clases de entidades JPA (Java Persistence API) para los objetos Cliente, Habitación y Reserva, que se utilizan para mapear las tablas de la base de datos.
- El paquete repository contiene las interfaces de repositorios JPA para los objetos Cliente, Habitación y Reserva, que se utilizan para interactuar con la base de datos.
- El paquete service contiene las clases de servicios para los microservicios de Cliente, Habitación y Reserva, que contienen la lógica de negocio.
- La clase ReservasApplication es la clase principal del proyecto que se utiliza para iniciar la aplicación.
## Microservicios
Este proyecto está dividido en tres microservicios diferentes: Cliente, Habitación y Reserva. Cada microservicio tiene su propia base de datos y API REST. La comunicación entre los microservicios se realiza a través de peticiones HTTP.

#### Cliente Microservicio
Endpoints:
- **POST /clientes** - Crea un nuevo cliente
```java
{
    "cedula": 12345,
    "nombre": "Mateo",
    "apellido": "Zapata",
    "celular": 30463,
    "correoElectronico" : "mateo@gmail.com",
    "direccion" : "calle 46 # 69-90",
    "ciudad" : "Medellin"
}
```

#### Habitación Microservicio
Endpoints:
- **POST /habitaciones** - Crea una nueva habitación
```java
{
    "numero": 10,
    "tipoHabitacion": "estandar",
    "precioBase": 21200
}
```

#### Reserva Microservicio
Endpoints:
- **POST /reservar** - Crea una nueva habitación
Ejemplo de petición:

``(http://localhost:8084/api/v1/reservar/?numero=10&fecha=25-08-2023&cedula=12345)``

Respuesta de petición:
```java
{
    "fechaReserva": "2023-08-25",
    "habitacion": {
        "numero": 10,
        "tipoHabitacion": "estandar",
        "precioBase": 21200
    },
    "cliente": {
        "nombre": "Mateo",
        "apellido": "Zapata",
        "cedula": 12345,
        "direccion": "calle 46 # 69-90",
        "edad": null,
        "email": null
    },
    "codigoReserva": 3,
    "totalPago": 21200
}
```

- **GET /reservas/{cedula}** - Obtener lista de reservas por cedula

Ejemplo de petición:

``(http://localhost:8084/api/v1/reservas/12345)``

```java
[
    {
        "fechaReserva": "2023-08-25",
        "habitacion": {
            "numero": 10,
            "tipoHabitacion": "estandar",
            "precioBase": 21200
        },
        "cliente": {
            "nombre": "Mateo",
            "apellido": "Zapata",
            "cedula": 12345,
            "direccion": "calle 46 # 69-90",
            "edad": null,
            "email": null
        },
        "codigoReserva": 3,
        "totalPago": 21200
    }
]
```

- **GET /disponibles/{fecha}** - Obtener lista de habitaciones disponibles por fecha

Ejemplo de petición:

``(http://localhost:8084/api/v1/disponibles/25-08-2023)``

- **GET /disponibles/habitacion** - Obtener lista de habitaciones por tipo y fecha

Ejemplo de petición:

``(http://localhost:8084/api/v1/disponibles/habitacion?tipo=premium&fecha=28-03-2023)``

## Pruebas Unitarias
Este proyecto cuenta con pruebas unitarias implementadas con Mockito y JUnit. Las pruebas se encuentran en la carpeta src/test/java y se pueden ejecutar con el siguiente comando:


## Integración continua
