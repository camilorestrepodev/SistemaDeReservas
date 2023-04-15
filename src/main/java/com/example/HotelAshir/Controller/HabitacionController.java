package com.example.HotelAshir.Controller;

import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Service.HabitacionService;
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
@Api(value="habitacion", description = "Crud de habitación")
public class HabitacionController {
    private final HabitacionService habitacionService;

    @Autowired
    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Habitación creado con exito"),
            @ApiResponse(code = 404, message = "La habitación no existe"),
            @ApiResponse(code = 500, message = "Error de conexion")
    })
    @ApiOperation(value = "Crear habitación", notes = "Crear habitación en la base de datos con la información obtenida", response = Habitacion.class)
    @PostMapping("/habitacion")
    public Habitacion registrarHabitacion(@RequestBody Habitacion habitacion) {
        return this.habitacionService.registrarHabitacion(habitacion);
    }
}
