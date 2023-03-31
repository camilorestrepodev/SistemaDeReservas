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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservaServiceTest {
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
    public void obtenerHabitacionesDisponiblesFecha() {
        //Arrange
        String fecha = "28-03-2028";
        //Act
        List<Habitacion> habitacion = this.reservaService.obtenerHabitacionesDisponiblesFecha(fecha);
        //Assert
        Assertions.assertTrue(habitacion.isEmpty());
    }

    @Test
    public void obtenerReservaClienteSinRegistrar(){
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.reservaService.obtenerReservasCliente(123),
                "Esta cedula no esta registrada"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("Esta cedula no esta registrada"));
    }

    @Test
    public void obtenerReservarPorCliente(){
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
    public void obtenerClienteSinReserva(){
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
    public void obtenerClientesSinTipo(){
        String tipoHabitacion = "estandar";
        String fecha = "28-05-2023";
        Habitacion habitacion = new Habitacion(2,"premium",15000);
        List<Habitacion> habitacion1 = new ArrayList<>();
        habitacion1.add(habitacion);
        //Act
        List<Habitacion> habitacion2 = this.reservaService.ObtenerHabitacionesTipoYFecha(tipoHabitacion, fecha);
        //Assert
        Assertions.assertTrue(habitacion.equals(habitacion2));
    }

    @Test
    public void testObtenerHabitacionesTipoYFechaFechaInvalida() {
        // Arrange
        ReservaService reservaService = new ReservaService(null, null, null);
        String fecha = "2022-13-50";

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> {
            reservaService.ObtenerHabitacionesTipoYFecha("Habitacion sencilla", fecha);
        });
    }



    @Test
    public void testObtenerHabitacionesTipoYFechaTipoHabitacionNoExiste() {
        // Arrange
        ReservaService reservaService = new ReservaService(null, null, null);
        String fecha = "28-06-2023";

        // Act
        List<Habitacion> habitaciones = this.reservaService.ObtenerHabitacionesTipoYFecha("Habitacion de lujo", fecha);

        // Assert
        Assertions.assertTrue(habitaciones.isEmpty());
    }


}
