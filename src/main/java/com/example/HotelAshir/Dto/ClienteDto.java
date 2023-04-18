package com.example.HotelAshir.Dto;

import java.io.Serializable;

public class ClienteDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String apellido;
    private Integer cedula;

    private String direccion;
    private Integer edad;
    private String correo;
    public ClienteDto() {
    }

    public ClienteDto(String nombre, String apellido, Integer cedula, String direccion, Integer edad, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.direccion = direccion;
        this.edad = edad;
        this.correo = correo;
    }

    public ClienteDto(String nombre, String apellido, Integer cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }
}
