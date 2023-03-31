package com.example.HotelAshir.Service;

import com.example.HotelAshir.Exception.ApiRequestException;
import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Model.Habitacion;
import com.example.HotelAshir.Model.Reserva;
import com.example.HotelAshir.Repository.ClienteRepository;
import com.example.HotelAshir.Repository.HabitacionRepository;
import com.example.HotelAshir.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void fechaValida(LocalDate fecha){
        if(fecha.isBefore(LocalDate.now())){
            throw new ApiRequestException("La fecha es erronea");
        }
    }
    public List<Habitacion> obtenerHabitacionesDisponiblesFecha(String fecha){
        LocalDate date = stringToDate(fecha);
        fechaValida(date);
        return validarDisponibilidadFecha(date);
    }
    public List<Habitacion> ObtenerHabitacionesTipoYFecha(String tipo, String fecha){
        LocalDate date = stringToDate(fecha);
        fechaValida(date);
        List<Habitacion> habitacionesDisponibles = validarDisponibilidadFecha(date);
        habitacionesDisponibles = habitacionesDisponibles.stream()
                .filter(habitacion -> habitacion.getTipoHabitacion().equals(tipo))
                .collect(Collectors.toList());
        return habitacionesDisponibles;
    }
    public Reserva crearReserva(Integer numHabitacion, Integer cedula, String fecha){
        LocalDate localDate = stringToDate(fecha);
        fechaValida(localDate);
        Optional<Cliente> cliente = this.clienteRepository.findById(cedula);
        if(cliente.isPresent()){
            Optional<Habitacion> habitacion = this.habitacionRepository.findById(numHabitacion);
            if(habitacion.isPresent()){
                List<Habitacion> disponibles = validarDisponibilidadFecha(localDate);
                if(disponibles.contains(habitacion.get())){
                    return this.reservaRepository.save(new Reserva(localDate,habitacion.get(),cliente.get(),habitacion.get().getPrecioBase()));
                } else{
                    throw new ApiRequestException("Esta habitacion no esta disponible");
                }
            } else{
                throw new ApiRequestException("Esta habitacion no se encuentra registrada");
            }
        } else{
            throw new ApiRequestException("Este cliente no esta registrado");
        }
    }
    public List<Reserva> obtenerReservasCliente(Integer cedula){
        Optional<Cliente> cliente = this.clienteRepository.findById(cedula);
        if(cliente.isEmpty()){
            throw new ApiRequestException("Esta cedula no esta registrada");
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
    public LocalDate stringToDate(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(fecha,formatter);
        return date;
    }

}