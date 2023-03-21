package com.example.lifipav2.dto;

import com.example.lifipav2.model.Domicilio;

import java.time.LocalDate;

public class ClubPersonalDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaFundacion;
    private LocalDate fechaInscrpcion;
    private Domicilio domicilio;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public LocalDate getFechaInscrpcion() {
        return fechaInscrpcion;
    }

    public void setFechaInscrpcion(LocalDate fechaInscrpcion) {
        this.fechaInscrpcion = fechaInscrpcion;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
}
