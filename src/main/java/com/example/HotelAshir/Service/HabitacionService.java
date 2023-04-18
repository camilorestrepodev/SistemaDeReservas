package com.example.HotelAshir.Service;

import com.example.HotelAshir.Dto.HabitacionDto;
import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    @Autowired
    public HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public HabitacionDto registrarHabitacion(HabitacionDto habitacionDto){
        if(habitacionDto.getTipoHabitacion()==null){
            throw new ApiRequestException("El campo de estandar/premium no esta en el body");
        } else if(habitacionDto.getPrecioBase()==null){
            throw new ApiRequestException("El campo de precio no esta en el body");
        }
        Habitacion habitacion = new Habitacion(
                habitacionDto.getNumero(),
                habitacionDto.getTipoHabitacion(),
                habitacionDto.getPrecioBase());
        habitacionRepository.save(habitacion);
        return habitacionDto;
    }
}
