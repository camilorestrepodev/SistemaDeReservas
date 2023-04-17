package com.example.HotelAshir;

import com.example.HotelAshir.Dto.ReservaDto;
import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Model.Reserva;
import com.example.HotelAshir.Repository.ClienteRepository;
import com.example.HotelAshir.Repository.HabitacionRepository;
import com.example.HotelAshir.Repository.ReservaRepository;
import com.example.HotelAshir.Service.HabitacionService;
import com.example.HotelAshir.Service.ReservaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservaServiceTest {
    ClienteRepository clienteRepository;
    HabitacionRepository habitacionRepository;
    ReservaRepository reservaRepository;

    ReservaService reservaService;

    @BeforeEach
    public void SetUp(){
        this.clienteRepository = mock(ClienteRepository.class);
        this.habitacionRepository = mock(HabitacionRepository.class);
        this.reservaRepository = mock(ReservaRepository.class);
        this.reservaService = new ReservaService(reservaRepository,clienteRepository,habitacionRepository);
    }

    @InjectMocks
    private HabitacionService habitacionService;


   @Test
    void crearReservaValida() {
        Integer numHabitacion = 1;
        Integer cedula = 123456789;
        String fecha = "30-09-2023";
        String tipoHabitacion = "PREMIUM";
        Habitacion habitacion = new Habitacion(numHabitacion,tipoHabitacion,7500);
       List<Habitacion> disponibles = new ArrayList<>();
       disponibles.add(habitacion);
        when(habitacionRepository.findById(numHabitacion)).thenReturn(Optional.of(habitacion));
        when(habitacionRepository.save(habitacion)).thenReturn(habitacion);
        Cliente cliente = new Cliente("mateo","zapata",cedula,"calle 51",18,"mateoz@gmial.com");

        when(clienteRepository.findById(cedula)).thenReturn(Optional.of(cliente));

        Reserva reserva = new Reserva(LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy")), habitacion, cliente, 7500);
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        ReservaDto reservaDto = reservaService.crearReserva(numHabitacion,cedula,fecha);

        Assertions.assertEquals(numHabitacion, reservaDto.getNumero());
        Assertions.assertEquals(7500, reservaDto.getTotalPago());
    }

    @Test
    void crearReservaNoHayHabitacionesDisponiblesEnEsaFecha(){
        String fecha = "30-09-2023";
        Cliente cliente = new Cliente("Mateo","perez",123,"calle 56 6-56",20,"d.perez@gmail.com");
        Habitacion habitacion = new Habitacion(1,"PREMIUM",15000);
        when(clienteRepository.findById(cliente.getCedula())).thenReturn(Optional.of(cliente));
        when(habitacionRepository.findById(habitacion.getNumero())).thenReturn(Optional.of(habitacion));
        Reserva reserva = new Reserva(LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy")), habitacion, cliente, 7500);
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.crearReserva(habitacion.getNumero(),cliente.getCedula(),fecha),
                "Esta habitación no esta disponible"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("Esta habitación no esta disponible"));
    }
//
    @Test
    void crearReservaNoExisteHabitacion(){
        Cliente cliente = new Cliente("Mateo","perez",123,"calle 56 6-56",20,"d.perez@gmail.com");
        when(clienteRepository.findById(cliente.getCedula())).thenReturn(Optional.of(cliente));
        Habitacion habitacion = new Habitacion(1,"PREMIUM",15000);
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.crearReserva(123,123,"23-08-2023"),
                "Esta habitación no se encuentra registrada"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("Esta habitación no se encuentra registrada"));
    }
    @Test
    void crearReservaNoExisteCliente(){
    Cliente cliente = new Cliente("Mateo","perez",123,"calle 56 6-56",20,"d.perez@gmail.com");
    ApiRequestException thrown = assertThrows(
            ApiRequestException.class,
            () -> this.reservaService.crearReserva(123,123,"23-08-2023"),
            "Este cliente no esta registrado"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("Este cliente no esta registrado"));
    }

    @Test
    void crearReservasConDatosNull(){
    ApiRequestException thrown = assertThrows(
            ApiRequestException.class,
            () -> this.reservaService.crearReserva(0,0,null),
            "Los datos están incorrectos"
    );
        Assertions.assertTrue(thrown.getMessage().contentEquals("Los datos están incorrectos"));
    }

    @Test
    void crearReservaConFechaAnterior(){
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.crearReserva(10,123,"23-02-2021"),
                "La fecha es anterior a la actual"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("La fecha es anterior a la actual"));
    }

    @Test
    void obtenerClienteSinReserva(){
        Integer cedula =  1234;
        Cliente cliente = new Cliente("d","perez",1234,"calle 56 6-56",20,"d.perez@gmail.com");
        Cliente cliente1 = new Cliente("Enrique","Pabon", 2234,"av 6 #5-65",21,"enriquep@gmail.com");
        List<Reserva> reservas = new ArrayList<>();
        Habitacion habitacion = new Habitacion(2,"premium",15500);
        Reserva reserva1 = new Reserva(LocalDate.now(),habitacion,cliente1,20000);
        reservas.add(reserva1);
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        when(reservaRepository.findAll()).thenReturn(reservas);
        //Act
        List<Reserva> reserva = this.reservaService.obtenerReservasCliente(cedula);
        //Assert
        Assertions.assertTrue(reserva.isEmpty());
    }

    @Test
    void obtenerReservaClienteSinRegistrar(){
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.obtenerReservasCliente(123),
                "Esta cedula no esta registrada"
        );
        Assertions.assertFalse(thrown.getMessage().contentEquals("Esta cedula no esta registrada"));
    }


    @Test
    void obtenerReservarPorCliente(){
        Integer cedula =  1234;
        Cliente cliente = new Cliente("d","perez",1234,"calle 56 6-56",20,"d.perez@gmail.com");
        List<Reserva> reservas = new ArrayList<>();
        Habitacion habitacion = new Habitacion(2,"premium",15500);
        Reserva reserva1 = new Reserva(LocalDate.now(),habitacion,cliente,20000);
        reservas.add(reserva1);
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        when(reservaRepository.findAll()).thenReturn(reservas);
        //Act
        List<Reserva> reserva = this.reservaService.obtenerReservasCliente(cedula);
        //Assert
        Assertions.assertFalse(reserva.isEmpty());
    }

    @Test
    void validarDisponibilidadFecha_habitacionesDisponibles() {
        // Arrange
        LocalDate fecha = LocalDate.of(2023, 4, 1);
        Habitacion habitacion1 = new Habitacion(1, "premium", 20000);
        List<Reserva> reservas = new ArrayList<>();
        when(habitacionRepository.findById(any())).thenReturn(Optional.of(habitacion1));
        when(reservaRepository.findAll()).thenReturn(reservas);
        // Act
        List<Habitacion> actualHabitacionesDisponibles = this.reservaService.validarDisponibilidadFecha(fecha);
        // Assert
        Assertions.assertTrue(actualHabitacionesDisponibles.isEmpty());
    }

    @Test
    void validarDisponibilidadFecha_habitacionesNoDisponibles() {
        // Arrange
        LocalDate fecha = LocalDate.of(2023, 4, 1);
        Habitacion habitacion1 = new Habitacion(1, "premium", 20000);
        Habitacion habitacion2 = new Habitacion(2, "estandar", 15000);
        Cliente cliente1 = new Cliente("Enrique","Pabon", 2234,"av 6 #5-65",21,"enriquep@gmail.com");
        List<Habitacion> habitaciones = Arrays.asList(habitacion1, habitacion2);
        Reserva reserva1 = new Reserva(LocalDate.now(),habitacion1,cliente1,20000);
        List<Reserva> reservas = List.of(reserva1);
        when(habitacionRepository.findAll()).thenReturn(habitaciones);
        when(reservaRepository.findAll()).thenReturn(reservas);
        // Act
        List<Habitacion> actualHabitacionesNoDisponibles = this.reservaService.validarDisponibilidadFecha(fecha);
        // Assert
        Assertions.assertFalse(actualHabitacionesNoDisponibles.isEmpty());
    }

    @Test
    void obtenerHabitacionesDisponiblesFecha() {
        //Arrange
        String fecha = "28-03-2028";
        //Act
        List<Habitacion> habitacion = this.reservaService.obtenerHabitacionesDisponiblesFecha(fecha);
        //Assert
        Assertions.assertTrue(habitacion.isEmpty());
    }

    @Test
    void obtenerHabitacionesConFechaNoDisponible(){
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.obtenerHabitacionesDisponiblesFecha("05-02-2022"),
                "La fecha no esta disponible"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("La fecha no esta disponible"));
    }

    @Test
    void testObtenerHabitacionesTipoYFechaTipoHabitacionNoExiste() {
        // Arrange
        String fecha = "28-06-2023";
        // Act
        List<Habitacion> habitaciones = this.reservaService.obtenerHabitacionesTipoYFecha("premium", fecha);
        // Assert
        Assertions.assertTrue(habitaciones.isEmpty());
    }

    @Test
    void calcularTotalPagoTest() {
        // Arrange
        Habitacion habitacion = new Habitacion(1, "PREMIUM", 10000);
        when(habitacionRepository.findById(1)).thenReturn(Optional.of(habitacion));
        // Act
        ReservaService reservaService = new ReservaService(reservaRepository,clienteRepository,habitacionRepository);
        //Assert
        Assertions.assertEquals(500, reservaService.calcularTotalPago(habitacion));
    }

   /* @Test
    void obtenerHabitacionesTipoYFechaNoDisponible() {
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.obtenerHabitacionesTipoYFecha("ESTANDAR", "05-02-2022"),
                "La fecha no puede ser antes de la actual"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("La fecha no puede ser antes de la actual"));
    }*/
}





