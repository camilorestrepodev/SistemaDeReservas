package com.example.HotelAshir;

import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Repository.ClienteRepository;
import com.example.HotelAshir.Repository.HabitacionRepository;
import com.example.HotelAshir.Service.ClienteService;
import com.example.HotelAshir.Service.HabitacionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class HabitacionServiceTest {
    HabitacionService habitacionService;
    HabitacionRepository habitacionRepository;

    @BeforeEach
    void SetUp(){
        this.habitacionRepository = mock(HabitacionRepository.class);
        this.habitacionService = new HabitacionService(habitacionRepository);
    }

    @Test
    void validarRegistroCliente() {
        //Arrange
        Habitacion habitacion = new Habitacion(1,"ESTANDAR",35000);
        //Act
        Habitacion habitacion1 = this.habitacionService.registrarHabitacion(habitacion);
        //Assert
        Assertions.assertNotNull(habitacion1.getNumero());
        Assertions.assertNotNull(habitacion1.getTipoHabitacion());
        Assertions.assertNotNull(habitacion1.getPrecioBase());
    }

   @Test
    void validarRegistroTipoHabitacionNull(){
        //Arrange
       Habitacion habitacion = new Habitacion(2,null,4000);
        //Act y Assert
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.habitacionService.registrarHabitacion(habitacion),
                "El campo de estandar/premium no esta en el body");
        Assertions.assertTrue(thrown.getMessage().contentEquals("El campo de estandar/premium no esta en el body"));
    }

    @Test
    void validarRegistroConPrecioNull(){
        //Arrange
        Habitacion habitacion = new Habitacion(3,"ESTANDAR",null);
        //Act y Assert
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.habitacionService.registrarHabitacion(habitacion),
                "El campo de precio no esta en el body");
        Assertions.assertTrue(thrown.getMessage().contentEquals("El campo de precio no esta en el body"));
    }
}
