package com.example.HotelAshir.Controller;

import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Model.Reserva;
import com.example.HotelAshir.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ReservaController {

    private ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/disponibles/{fecha}")
    public List<Habitacion> habitacionesPorFecha(@PathVariable("fecha") String fecha) {
        return this.reservaService.obtenerHabitacionesDisponiblesFecha(fecha);
    }

    @GetMapping("/disponibles/habitacion")
    public List<Habitacion> habitacionesPorTipo(@RequestParam("tipo") String tipo, @RequestParam("fecha") String fecha) {
        return this.reservaService.ObtenerHabitacionesTipoYFecha(tipo, fecha);
    }

    @GetMapping("/reservas/{cedula}")
    public List<Reserva> reservasCliente(@PathVariable("cedula") Integer cedula) {
        return this.reservaService.obtenerReservasCliente(cedula);
    }

    @PostMapping("/reservar")
    public Reserva reservar(@RequestParam("numero") Integer numeroHabitacion, @RequestParam("fecha") String fecha, @RequestParam("cedula") Integer cedula) {
        return this.reservaService.crearReserva(numeroHabitacion, cedula, fecha);
    }
}
