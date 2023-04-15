package com.example.HotelAshir.Dto;


import java.io.Serializable;
import java.time.LocalDate;

public class ReservaDto implements Serializable {
    private LocalDate fechaReserva;
    private Integer numero;
    private Integer codigoReserva;
    private Integer totalPago;

    public ReservaDto() {
    }

    public ReservaDto(LocalDate fechaReserva, Integer numero, Integer codigoReserva, Integer totalPago) {
        this.fechaReserva = fechaReserva;
        this.numero = numero;
        this.codigoReserva = codigoReserva;
        this.totalPago = totalPago;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getCodigoReserva() {
        return codigoReserva;
    }

    public Integer getTotalPago() {
        return totalPago;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setCodigoReserva(Integer codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public void setTotalPago(Integer totalPago) {
        this.totalPago = totalPago;
    }
}
