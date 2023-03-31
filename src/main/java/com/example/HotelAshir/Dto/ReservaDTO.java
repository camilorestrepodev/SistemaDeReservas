package com.example.HotelAshir.Dto;

import com.example.HotelAshir.Model.Cliente;
import com.example.HotelAshir.Model.Habitacion;

import java.time.LocalDate;

public class ReservaDTO {
    private LocalDate fechaReserva;
    private Habitacion habitacion;
    private Cliente cliente;
    private Integer codigoReserva;
    private Integer totalPago;

    public ReservaDTO(LocalDate fechaReserva, Habitacion habitacion, Cliente cliente, Integer codigoReserva, Integer totalPago) {
        this.fechaReserva = fechaReserva;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.codigoReserva = codigoReserva;
        this.totalPago = totalPago;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Integer getCodigoReserva() {
        return codigoReserva;
    }

    public Integer getTotalPago() {
        return totalPago;
    }
}
