package com.example.HotelAshir.Service;

import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HabitacionService {

    private HabitacionRepository habitacionRepository;

    @Autowired
    public HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public Habitacion registrarHabitacion(Habitacion habitacion){
        if(habitacion.getTipoHabitacion()==null){
            throw new ApiRequestException("El campo de estandar/premium no esta en el body");
        } else if(habitacion.getPrecioBase()==null){
            throw new ApiRequestException("El campo de precio no esta en el body");
        }
        this.habitacionRepository.save(habitacion);
        return habitacion;
    }
}
