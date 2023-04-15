package com.example.HotelAshir;

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
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void fechaValidaTest() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 4, 30);
        // Act
        Boolean reserva = reservaService.fechaValida(fecha);
        // Assert
        Assertions.assertTrue(reserva);
    }
    @Test
    void fechaInvalidaTest() {
        LocalDate fecha = LocalDate.of(2022, 12, 31);
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.fechaValida(fecha),
                "La fecha es erronea");
        Assertions.assertTrue(thrown.getMessage().contentEquals("La fecha es erronea"));
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
    void obtenerReservaClienteSinRegistrar(){
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.obtenerReservasCliente(123),
                "Esta cedula no esta registrada"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("Esta cedula no esta registrada"));
    }

    @Test
    void crearReservaSinHabitacion(){
        Cliente cliente = new Cliente("d","perez",1234,"calle 56 6-56",20,"d.perez@gmail.com");
        Habitacion habitacion = new Habitacion(2,"premium",15500);
        clienteRepository.save(cliente);
        habitacionRepository.save(habitacion);
        String fecha = "2023-05-01";
        LocalDate localDate = LocalDate.parse(fecha);
        when(clienteRepository.findById(cliente.getCedula())).thenReturn(Optional.empty());
        when(habitacionRepository.findById(habitacion.getNumero())).thenReturn(Optional.empty());

        Reserva reserva = this.reservaService.crearReserva(cliente.getCedula(),habitacion.getNumero(),fecha);
        Assertions.assertTrue((BooleanSupplier) reserva);
    }

    @Test
    void crearReservaConHabitacionNoDisponible(){
        Habitacion habitacion = new Habitacion(2,"premium",15500);
        habitacionRepository.save(habitacion);
        Integer cedula = 123;
        String fecha = "2023-05-02";
        LocalDate localDate = LocalDate.parse(fecha);
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.crearReserva(4,cedula,fecha),
                "Esta habitacion no esta disponible"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("Esta habitacion no esta disponible"));
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
    void testObtenerHabitacionesTipoYFechaTipoHabitacionNoExiste() {
        // Arrange
        String fecha = "28-06-2023";

        // Act
        List<Habitacion> habitaciones = this.reservaService.ObtenerHabitacionesTipoYFecha("premium", fecha);

        // Assert
        Assertions.assertTrue(habitaciones.isEmpty());
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
    void stringToDate_fechaValida() {
        // Arrange
        String fecha = "31-03-2023";
        LocalDate expectedDate = LocalDate.of(2023, 3, 31);

        // Act
        LocalDate actualDate = this.reservaService.stringToDate(fecha);

        // Assert
        Assertions.assertEquals(expectedDate, actualDate);
    }

    @Test
    void stringToDate_fechaInvalida() {
        // Arrange
        String fecha = "31/03/2023";

        // Act + Assert
        Assertions.assertThrows(DateTimeParseException.class, () -> this.reservaService.stringToDate(fecha));
    }
}





