# Sistema de Reservas
Este proyecto es un sistema de reservas desarrollado en Java utilizando el framework Spring y la herramienta de construcción Gradle. 
El sistema permite a los clientes realizar reservas en línea para habitaciones de hotel (Hotel Ashir).

## Requisitos
- Java versión: 11
- Gradle:
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
La documentación de la API se genera automáticamente con Swagger. Para acceder a la documentación, abra un navegador web y vaya a http://localhost:8080/swagger-ui.html.

## Patrón de Diseño
Este proyecto utiliza el patrón de diseño DTO (Data Transfer Object) para transferir datos entre las diferentes capas de la aplicación. Los DTO son objetos simples que contienen campos y métodos de acceso, y se utilizan para transferir datos entre los controladores y los servicios.

## Pruebas Unitarias
Este proyecto cuenta con pruebas unitarias implementadas con Mockito y JUnit. Las pruebas se encuentran en la carpeta src/test/java y se pueden ejecutar con el siguiente comando:

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
- **GET /reservas/{cedula}** - Obtener lista de reservas por cedula
- **GET /disponibles/{fecha}** - Obtener lista de habitaciones disponibles por fecha
- **GET /disponibles/habitacion** - Obtener lista de habitaciones por tipo y fecha
