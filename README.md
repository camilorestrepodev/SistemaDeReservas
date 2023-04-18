# Sistema de Reservas <img alt="Java" height="40" width="40" src="https://media.giphy.com/media/fRaFhz7PtRH5vdfCGL/giphy.gif">
Este es un proyecto de sistema de reservas en Java y Spring que utiliza una base de datos MySQL, siguiendo un modelo entidad-relación. Este proyecto tiene documentación en Swagger y utiliza el patrón de diseño DTO. También se han creado pruebas unitarias con Mockito y JUnit para garantizar la calidad del código. Se han creado microservicios para manejar las entidades Cliente, Habitación y Reserva. 
El sistema permite a los clientes realizar reservas en línea para habitaciones de hotel (Hotel Ashir).
<div align="center"><img align="center" alt="HTML" height="150" width="200" src="https://user-images.githubusercontent.com/115324147/232672040-8daaa2cf-615e-46ea-8833-2524ab0e9b73.png">
</div>

## Requisitos
- Java versión: 11
- Gestor de dependencias: Gradle
- Spring Boot: 2.7.11 (SNAPSHOT)
- Base de datos: MySQL 

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
La documentación de la API se genera automáticamente con Swagger. Para acceder a la documentación, abra un navegador web y vaya a [Documentación Swagger](http://localhost:8080/swagger-ui.html).

## Patrón de Diseño
Este proyecto utiliza el patrón de diseño DTO (Data Transfer Object) para transferir datos entre las diferentes capas de la aplicación. Los DTO son objetos simples que contienen campos y métodos de acceso, y se utilizan para transferir datos entre los controladores y los servicios.

## Diagrama del Modelo Entidad-Relación
Este es el diagrama del modelo entidad-relación para la base de datos MySQL del proyecto de sistema de reservas:

![Diagrama](https://user-images.githubusercontent.com/119947851/232180155-0b7b4fa5-03e4-4316-a5cc-e0fa675722d2.png)

- La tabla **Cliente** contiene información sobre los clientes, como su cedula, nombre, apellido, direccion, edad y correo electronico.
- La tabla **Habitación** contiene información sobre las habitaciones, como el numero, precio y tipo.
- La tabla **Reserva** contiene información sobre las reservas, como el codigo de reserva, la cedula del cliente que la realizó, el numero de la habitación reservada y la fecha de la reserva y el total a pagar.

## Diagrama de Clases

#### Diagrama del proyecto por paquetes:

```java
com.example.HotelAshir
├── Configurer
│   └── SwaggerConfig.java
├── Controller
│   ├── ClienteController.java
│   ├── HabitacionController.java
│   └── ReservaController.java
├── Dto
│   ├── ClienteDTO.java
│   ├── HabitacionDTO.java
│   └── ReservaDTO.java
├── Entity
│   ├── Cliente.java
│   ├── Habitacion.java
│   └── Reserva.java
├── Repository
│   ├── ClienteRepository.java
│   ├── HabitacionRepository.java
│   └── ReservaRepository.java
├── Service
│   ├── ClienteService.java
│   ├── HabitacionService.java
│   └── ReservaService.java
└── HotelAshirApplication.java
```
- El paquete **Configurer** contiene las clases de configuración para la base de datos y Swagger.
- El paquete **Controller** contiene las clases controladoras para los microservicios de Cliente, Habitación y Reserva.
- El paquete **Dto** contiene las clases DTO (Data Transfer Object) para los objetos Cliente, Habitación y Reserva, que se utilizan para transferir datos entre la capa de presentación y la capa de servicios.
- El paquete **Entity** contiene las clases de entidades JPA (Java Persistence API) para los objetos Cliente, Habitación y Reserva, que se utilizan para mapear las tablas de la base de datos.
- El paquete **Repository** contiene las interfaces de repositorios JPA para los objetos Cliente, Habitación y Reserva, que se utilizan para interactuar con la base de datos.
- El paquete **Service** contiene las clases de servicios para los microservicios de Cliente, Habitación y Reserva, que contienen la lógica de negocio.
- La clase **HotelAshirApplication** es la clase principal del proyecto que se utiliza para iniciar la aplicación.
## Microservicios
Este proyecto está dividido en tres microservicios diferentes: Cliente, Habitación y Reserva. Cada microservicio tiene su propia base de datos y API REST. La comunicación entre los microservicios se realiza a través de peticiones HTTP.

#### Cliente Microservicio
Endpoints:
- **POST /clientes** - Crea un nuevo cliente
```java
{
"cedula": 12345,
"nombre":"Mateo",
"apellido":"Zapata",
"correoElectronico" : "mateo@gmail.com",
"direccion" :"calle 46 # 69-90",
"edad" : 18,
"correo" : "mateo@gmail.com"
}
```

#### Habitación Microservicio
Endpoints:
- **POST /habitaciones** - Crea una nueva habitación
```java
{
    "numero": 1,
    "tipoHabitacion": "PREMIUM",
    "precioBase": 25000
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
    "numero": 99,
    "codigoReserva": 27,
    "totalPago": 750
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
            "numero": 99,
            "tipoHabitacion": "PREMIUM",
            "precioBase": 15000
        },
        "cliente": {
            "nombre": "Mateo",
            "apellido": "Zapata",
            "cedula": 123,
            "direccion": "mateo@gmail.com",
            "edad": 18,
            "email": "calle 46 # 69-90"
        },
        "codigoReserva": 27,
        "totalPago": 750
    }
]
```

- **GET /disponibles/{fecha}** - Obtener lista de habitaciones disponibles por fecha

Ejemplo de petición:

``(http://localhost:8084/api/v1/disponibles/25-08-2023)``

- **GET /disponibles/habitacion** - Obtener lista de habitaciones por tipo y fecha

Ejemplo de petición:

``(http://localhost:8084/api/v1/disponibles/habitacion?tipo=premium&fecha=28-03-2023)``

```java
[
    {
        "numero": 11,
        "tipoHabitacion": "premium",
        "precioBase": 20500
    },
    {
        "numero": 12,
        "tipoHabitacion": "premium",
        "precioBase": 20500
    }
]
```

## Pruebas Unitarias <img alt="Pruebas" height="40" width="40" src="https://media.giphy.com/media/1sMGC0XjA1Hk58wppo/giphy.gif">
Se han incluido pruebas unitarias utilizando Mockito y JUnit para asegurar que los microservicios de Cliente, Habitación y Reserva funcionan correctamente.
Las pruebas unitarias se encuentran en la carpeta src/test/java del proyecto.


## Integración continua <img alt="Integracion" height="50" width="50" src="https://media.giphy.com/media/Vnk8f29XU6GSZK8uGJ/giphy.gif">
Este proyecto cuenta con integración continua mediante Github Actions. Cada vez que se realiza un push al repositorio, se ejecutan las pruebas unitarias y se crea un archivo JAR ejecutable.

## Despliegue del microservicio (Railway)
Este proyecto cuenta con un despliegue del microservicio mediante Railway. Conecta directamente con Github y nuestra base de datos(MySQL).

