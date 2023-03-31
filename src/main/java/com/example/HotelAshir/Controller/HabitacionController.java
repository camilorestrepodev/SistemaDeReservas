package com.example.HotelAshir.Controller;

import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Service.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HabitacionController {

    private HabitacionService habitacionService;

    @Autowired
    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @PostMapping("/habitacion")
    public Habitacion registrarHabitacion(@RequestBody Habitacion habitacion) {
        return this.habitacionService.registrarHabitacion(habitacion);
    }
}
