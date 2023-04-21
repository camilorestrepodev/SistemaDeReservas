package com.example.HotelAshir.Controller;

import com.example.HotelAshir.Dto.ReservaDto;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Model.Reserva;
import com.example.HotelAshir.Service.ReservaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@Api(value = "Gestión de reservas", description = "API para gestionar reservas en el sistema")
public class ReservaController {

    private final ReservaService reservaService;
    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PreAuthorize("hasRole('READ')")
    @ApiOperation(value = "Obtiene todas las habitaciones disponibles en la fecha especificada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Las habitaciones fueron encontradas"),
            @ApiResponse(code = 404, message = "No se encontraron habitaciones disponibles")
    })
    @GetMapping("/disponibles/{fecha}")
    public List<Habitacion> habitacionesPorFecha(@PathVariable("fecha") String fecha) {
        return this.reservaService.obtenerHabitacionesDisponiblesFecha(fecha);
    }

    @GetMapping("/disponibles/habitacion")
    @ApiOperation(value = "Obtiene todas las habitaciones disponibles según su tipo (premium o estándar)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Las habitaciones fueron encontradas"),
            @ApiResponse(code = 404, message = "No se encontraron habitaciones disponibles del tipo especificado")
    })
    @PreAuthorize("hasRole('READ')")
    public List<Habitacion> habitacionesPorTipo(@RequestParam("tipo") String tipo, @RequestParam("fecha") String fecha) {
        return this.reservaService.obtenerHabitacionesTipoYFecha(tipo, fecha);
    }

    @PostMapping("/reservar")
    @ApiOperation(value = "Crea una nueva reserva")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La reserva fue creada exitosamente"),
            @ApiResponse(code = 401, message = "No se tiene autorización para realizar esta operación"),
            @ApiResponse(code = 500, message = "Ocurrió un error en el servidor")
    })
    @PreAuthorize("hasRole('WRITE')")
    public ReservaDto reservar(@RequestParam("numero") Integer numeroHabitacion, @RequestParam("fecha") String fecha, @RequestParam("cedula") Integer cedula) {
        return this.reservaService.crearReserva(numeroHabitacion, cedula, fecha);
    }

    @GetMapping("/reservas/{cedula}")
    @ApiOperation(value = "Obtiene una reserva por cédula")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La reserva fue encontrada"),
            @ApiResponse(code = 404, message = "La reserva no fue encontrada")
    })
    @PreAuthorize("hasRole('READ')")
    public List<Reserva> reservasCliente(@PathVariable("cedula") Integer cedula) {
        return this.reservaService.obtenerReservasCliente(cedula);
    }
}
