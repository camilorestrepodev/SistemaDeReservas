package com.example.HotelAshir.Service;

import com.example.HotelAshir.Dto.ReservaDto;
import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Model.Reserva;
import com.example.HotelAshir.Repository.ClienteRepository;
import com.example.HotelAshir.Repository.HabitacionRepository;
import com.example.HotelAshir.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private ReservaRepository reservaRepository;
    private ClienteRepository clienteRepository;
    private HabitacionRepository habitacionRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, ClienteRepository clienteRepository, HabitacionRepository habitacionRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.habitacionRepository = habitacionRepository;
    }

    public ReservaDto crearReserva(Integer numHabitacion, Integer cedula, String fecha){
        if(numHabitacion == null || cedula == null ||fecha == null){
            throw new ApiRequestException("Los datos están incorrectos");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(fecha,formatter);
        if (date.isBefore(LocalDate.now())){
            throw new ApiRequestException("La fecha es anterior a la actual");
        }
        Optional<Cliente> cliente = this.clienteRepository.findById(cedula);
        Optional<Habitacion> habitacion = this.habitacionRepository.findById(numHabitacion);
        if(cliente.isPresent()){
            List<Habitacion> disponibles = validarDisponibilidadFecha(date);
            if(habitacion.isPresent()){
                Integer base = habitacion.get().getPrecioBase();
                Integer total = calcularTotalPago(habitacion.get());
                if (!habitacion.get().getTipoHabitacion().equals("PREMIUM"))
                    total = base;
                if(disponibles.contains(habitacion.get())){
                    Reserva reserva =new Reserva(date,habitacion.get(),cliente.get(),total);
                    this.reservaRepository.save(reserva);
                    return new ReservaDto(reserva.getFechaReserva(),
                            reserva.getHabitacion().getNumero(),
                            reserva.getCodigoReserva(),
                            reserva.getTotalPago());
                } else{
                    throw new ApiRequestException("Esta habitación no esta disponible");
                }
            } else{
                throw new ApiRequestException("Esta habitación no se encuentra registrada");
            }
        } else{
            throw new ApiRequestException("Este cliente no esta registrado");
        }
    }

    public List<Reserva> obtenerReservasCliente(Integer cedula){
        Optional<Cliente> cliente = this.clienteRepository.findById(cedula);
        if(cliente.isEmpty()){
            throw new ApiRequestException("Esta cédula no esta registrada");
        }
        return this.reservaRepository.findAll().stream()
                .filter(x->x.getCliente().getCedula().equals(cedula))
                .collect(Collectors.toList());
    }

    public List<Habitacion> validarDisponibilidadFecha(LocalDate fecha){
        List<Habitacion> habitacionesDisponibles;
        List<Habitacion> habitaciones = this.habitacionRepository.findAll();
        List<Reserva> habitacionesReservadas = this.reservaRepository.findAll();
        List<Habitacion> habitacionesReservadasEnFecha = habitacionesReservadas.stream()
                .filter(reserva -> reserva.getFechaReserva().equals(fecha))
                .map(Reserva::getHabitacion)
                .collect(Collectors.toList());
        habitacionesDisponibles = habitaciones.stream()
                .filter(habitacion -> !habitacionesReservadasEnFecha.contains(habitacion))
                .collect(Collectors.toList());
        return habitacionesDisponibles;
    }

    public List<Habitacion> obtenerHabitacionesDisponiblesFecha(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(fecha,formatter);
        if(date.isBefore(LocalDate.now())){
            throw new ApiRequestException("La fecha no esta disponible");
        }
        return validarDisponibilidadFecha(date);
    }
    public List<Habitacion> obtenerHabitacionesTipoYFecha(String tipo, String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(fecha,formatter);
        List<Habitacion> habitacionesDisponibles = validarDisponibilidadFecha(date);
        habitacionesDisponibles = habitacionesDisponibles.stream()
                .filter(habitacion -> habitacion.getTipoHabitacion().equals(tipo))
                .collect(Collectors.toList());
        return habitacionesDisponibles;
    }

    public Integer calcularTotalPago(Habitacion habitacion) {
        Integer base = habitacion.getPrecioBase();
        if (habitacion.getTipoHabitacion().equals("PREMIUM")) {
            return base * 5 / 100;
        }
        return base;
    }
}

