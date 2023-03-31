package com.example.HotelAshir.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Column(name = "fecha")
    private LocalDate fechaReserva;
    @OneToOne
    @JoinColumn(name = "numero")
    private Habitacion habitacion;
    @ManyToOne
    @JoinColumn(name = "cedula")
    private Cliente cliente;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigoReserva;
    @Column(name = "total")
    private Integer totalPago;

    public Reserva() {
    }

    public Reserva(LocalDate fechaReserva, Habitacion habitacion, Cliente cliente, Integer totalPago) {
        this.fechaReserva = fechaReserva;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.totalPago = totalPago;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(Integer codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public Integer getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(Integer totalPago) {
        this.totalPago = totalPago;
    }
}
