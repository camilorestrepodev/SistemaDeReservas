package com.example.HotelAshir.Controller;

import com.example.HotelAshir.Dto.ClienteDto;
import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@Api(value="cliente", description = "Crud de clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente creado con éxito"),
            @ApiResponse(code = 404, message = "El cliente no ha sido creado"),
            @ApiResponse(code = 500, message = "Error de conexión")
    })
    @ApiOperation(value = "Crear cliente", notes = "Crear cliente en la base de datos con la información obtenida", response = Cliente.class)
    @PostMapping("/cliente")
    public ClienteDto registrarCliente(@RequestBody ClienteDto clienteDto) {
        return this.clienteService.registrarCliente(clienteDto);
    }
}