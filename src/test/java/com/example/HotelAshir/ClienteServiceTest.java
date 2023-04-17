package com.example.HotelAshir;

import com.example.HotelAshir.Dto.ClienteDto;
import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Repository.ClienteRepository;
import com.example.HotelAshir.Service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ClienteServiceTest {
    ClienteService clienteService;
    ClienteRepository clienteRepository;

    @BeforeEach
    void SetUp(){
        this.clienteRepository = mock(ClienteRepository.class);
        this.clienteService = new ClienteService(clienteRepository);
    }

    @Test
    void validarRegistroCliente() {
        //Arrange
        ClienteDto cliente = new ClienteDto("camilo", "perez", 1234, "calle 6 #5", 18, "camilop@gmail.com");
        //Act
        ClienteDto cliente1 = this.clienteService.registrarCliente(cliente);
        //Assert
        Assertions.assertNotNull(cliente1.getNombre());
        Assertions.assertNotNull(cliente1.getApellido());
        Assertions.assertNotNull(cliente1.getCedula());
    }

    @Test
    void validarRegistroConClienteNull(){
        //Arrange
        ClienteDto  cliente = new ClienteDto(null,"perez", 1234, "calle 6 #5", 18, "camilop@gmail.com");
        //Act y Assert
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.clienteService.registrarCliente(cliente),
                "El campo de nombre no esta en el body");
        Assertions.assertTrue(thrown.getMessage().contentEquals("El campo de nombre no esta en el body"));
    }

    @Test
    void validarRegistroConApellidoNull(){
        //Arrange
        ClienteDto cliente = new ClienteDto("camilo",null, 1234, "calle 6 #5", 18, "camilop@gmail.com");
        //Act y Assert
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.clienteService.registrarCliente(cliente),
                "El campo de apellido no esta en el body");
        Assertions.assertTrue(thrown.getMessage().contentEquals("El campo de apellido no esta en el body"));
    }

    @Test
    void validarRegistroConCedulaNull(){
        //Arrange
        ClienteDto cliente = new ClienteDto("camilo","perez", null, "calle 6 #5", 18, "camilop@gmail.com");
        //Act y Assert
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.clienteService.registrarCliente(cliente),
                "El campo de cedula no esta en el body");
        Assertions.assertTrue(thrown.getMessage().contentEquals("El campo de cedula no esta en el body"));
    }
}
