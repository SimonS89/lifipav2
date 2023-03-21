package com.example.lifipav2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CategoriaDTO {
    private Long id;
    @NotBlank(message = "Por favor ingrese el nombre de la categoria")
    @Size(min = 4, message = "El nombre de la categoria debe tener al menos 4 caracteres")
    @Column(unique = true)
    private String nombre;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha limite habilitacion para jugar no puede ser nula")
    @Column(unique = true)
    private LocalDate fechaHabilitada;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaHabilitada() {
        return fechaHabilitada;
    }

    public void setFechaHabilitada(LocalDate fechaHabilitada) {
        this.fechaHabilitada = fechaHabilitada;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" + "id=" + id + ", nombre='" + nombre + '\'' + ", fechaHabilitada=" + fechaHabilitada + '}';
    }
}
