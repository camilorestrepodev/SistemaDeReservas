package com.example.HotelAshir.Controller;

import com.example.HotelAshir.Dto.ClienteDto;

import com.example.HotelAshir.Service.ClienteService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@Api(value = "Gestión de clientes", description = "API para gestionar los clientes en el sistema")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @PostMapping("/cliente")
    @ApiOperation(value = "Crea un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "El cliente fue creado exitosamente"),
            @ApiResponse(code = 401, message = "No se tiene autorización para realizar esta operación"),
            @ApiResponse(code = 500, message = "Ocurrió un error en el servidor")
    })
    @PreAuthorize("hasRole('WRITE')")
    public ClienteDto registrarCliente(@RequestBody ClienteDto clienteDto) {
        return this.clienteService.registrarCliente(clienteDto);
    }
}