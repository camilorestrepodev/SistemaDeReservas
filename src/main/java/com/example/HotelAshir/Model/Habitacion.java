package com.example.HotelAshir.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Id
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "tipo", nullable = false)
    private String tipoHabitacion;

    @Column(name = "precio")
    private Integer precioBase;


    public Habitacion() {
    }

    public Habitacion(Integer numero, String tipoHabitacion, Integer precioBase) {
        this.numero = numero;
        this.tipoHabitacion = tipoHabitacion;
        this.precioBase = precioBase;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public Integer getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Integer precioBase) {
        this.precioBase = precioBase;
    }


}

