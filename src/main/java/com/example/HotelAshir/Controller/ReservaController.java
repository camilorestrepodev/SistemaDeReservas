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
@Api(value = "reserva", description = "Crud de reserva")
public class ReservaController {

    private final ReservaService reservaService;
    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Habitación obtenida por fecha con éxito"),
            @ApiResponse(code = 404, message = "No se ha encontrado la habitación por fecha"),
            @ApiResponse(code = 500, message = "Error de conexión")
    })
    @ApiOperation(value = "Obtener habitación por fecha", notes = "Obtener habitación disponibles por el método de la fecha", response = Habitacion.class)
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/disponibles/{fecha}")
    public List<Habitacion> habitacionesPorFecha(@PathVariable("fecha") String fecha) {
        return this.reservaService.obtenerHabitacionesDisponiblesFecha(fecha);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Habitación obtenida por tipo"),
            @ApiResponse(code = 404, message = "No se ha encontrado la habitación por tipo"),
            @ApiResponse(code = 500, message = "Error de conexión")
    })


    @ApiOperation(value = "Obtener habitación por tipo", notes = "Obtener habitaciones por tipo PREMIUM/ESTÁNDAR", response = Habitacion.class)
    @GetMapping("/disponibles/habitacion")
    @PreAuthorize("hasRole('READ')")
    public List<Habitacion> habitacionesPorTipo(@RequestParam("tipo") String tipo, @RequestParam("fecha") String fecha) {
        return this.reservaService.obtenerHabitacionesTipoYFecha(tipo, fecha);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reserva creada con éxito"),
            @ApiResponse(code = 404, message = "La reserva no se ha creado"),
            @ApiResponse(code = 500, message = "Error de conexión")
    })


    @ApiOperation(value = "Crear reserva", notes = "Crear reserva en la base de datos con la información obtenida", response = Habitacion.class)
    @PostMapping("/reservar")
    @PreAuthorize("hasRole('WRITE')")
    public ReservaDto reservar(@RequestParam("numero") Integer numeroHabitacion, @RequestParam("fecha") String fecha, @RequestParam("cedula") Integer cedula) {
        return this.reservaService.crearReserva(numeroHabitacion, cedula, fecha);
    }

   @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reserva obtenida por cédula exitosamente"),
            @ApiResponse(code = 404, message = "No se ha encontrado la reserva por cédula"),
            @ApiResponse(code = 500, message = "Error de conexión")
    })


    @ApiOperation(value = "Obtener reserva por cédula", notes = "Obtener una reserva por la cédula del cliente", response = Habitacion.class)
    @GetMapping("/reservas/{cedula}")
    @PreAuthorize("hasRole('READ')")
    public List<Reserva> reservasCliente(@PathVariable("cedula") Integer cedula) {
        return this.reservaService.obtenerReservasCliente(cedula);
    }
}
