package com.example.HotelAshir.Dto;

import java.io.Serializable;

public class HabitacionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer numero;
    private Integer precioBase;
    private String tipoHabitacion;

    public HabitacionDto() {
    }

    public HabitacionDto(Integer numero, Integer precioBase, String tipoHabitacion) {
        this.numero = numero;
        this.precioBase = precioBase;
        this.tipoHabitacion = tipoHabitacion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Integer precioBase) {
        this.precioBase = precioBase;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
}
