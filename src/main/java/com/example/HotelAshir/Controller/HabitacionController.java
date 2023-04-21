package com.example.HotelAshir.Controller;

import com.example.HotelAshir.Dto.HabitacionDto;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Service.HabitacionService;
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
@Api(value = "Gestión de habitaciones", description = "API para gestionar habitaciones en el sistema")
public class HabitacionController {
    private final HabitacionService habitacionService;

    @Autowired
    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @PostMapping("/habitacion")
    @ApiOperation(value = "Crea una nueva habitación")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La habitación fue creada exitosamente"),
            @ApiResponse(code = 401, message = "No se tiene autorización para realizar esta operación"),
            @ApiResponse(code = 500, message = "Ocurrió un error en el servidor")
    })
    @PreAuthorize("hasRole('WRITE')")
    public HabitacionDto registrarHabitacion(@RequestBody HabitacionDto habitacionDto) {
        return this.habitacionService.registrarHabitacion(habitacionDto);
    }
}
