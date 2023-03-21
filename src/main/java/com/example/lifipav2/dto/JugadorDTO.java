package com.example.lifipav2.dto;

import com.example.lifipav2.model.Categoria;
import com.example.lifipav2.model.DatosContacto;
import com.example.lifipav2.util.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class JugadorDTO {
    private Long id;
    @NotBlank(message = "Por favor ingrese el nombre del jugador")
    @Size(min = 3, message = "El nombre del jugador debe tener al menos 3 caracteres")
    private String nombre;
    @NotBlank(message = "Por favor ingrese el apellido del jugador")
    @Size(min = 3, message = "El apellido del jugador debe tener al menos 3 caracteres")
    private String apellido;
    @NotBlank(message = "Por favor ingrese el dni del jugador")
    @Size(min = 7, message = "El dni del jugador debe tener al menos 7 caracteres")
    private String dni;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de nacimiento del jugador no puede ser nula")
    private LocalDate fechaNacimiento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInscripcion;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Debe seleccionar el genero del jugador")
    private Genero genero;
    @NotNull(message = "Debe ingresar los datos de contacto del personal")
    private DatosContacto datosContacto;
    @NotNull(message = "Debe seleccionar el club del jugador")
    private ClubPersonalDTO club;
    @NotNull(message = "Debe seleccionar la categoria del jugador")
    private Categoria categoria;

    public Long getId() {
        return id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public DatosContacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(DatosContacto datosContacto) {
        this.datosContacto = datosContacto;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public ClubPersonalDTO getClub() {
        return club;
    }

    public void setClub(ClubPersonalDTO club) {
        this.club = club;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "JugadorDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaInscripcion=" + fechaInscripcion +
                ", genero=" + genero +
                ", datosContacto=" + datosContacto +
                ", club=" + club +
                ", categoria=" + categoria +
                '}';
    }
}
