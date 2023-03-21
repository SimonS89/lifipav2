package com.example.lifipav2.dto;

import com.example.lifipav2.model.DatosContacto;
import com.example.lifipav2.model.Domicilio;
import com.example.lifipav2.util.Rol;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class PersonalClubDTO {
    private Long id;
    @NotBlank(message = "Por favor ingrese el nombre del personal")
    @Size(min = 3, message = "El nombre del personal debe tener al menos 3 caracteres")
    private String nombre;
    @NotBlank(message = "Por favor ingrese el apellido del personal")
    @Size(min = 3, message = "El apellido del personal debe tener al menos 3 caracteres")
    private String apellido;
    @NotBlank(message = "Por favor ingrese el dni del personal")
    @Size(min = 7, message = "El dni del personal debe tener al menos 7 caracteres")
    private String dni;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Debe ingresar la fecha de nacimiento del personal")
    private LocalDate fechaNacimiento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInscripcion;
    @NotNull(message = "Por favor ingrese el/los roles")
    @Enumerated(EnumType.STRING)
    private List<Rol> rol;
    @NotNull(message = "El domicilio de la persona no puede ser nulo")
    private Domicilio domicilio;
    @NotNull(message = "Debe ingresar los datos de contacto del personal")
    private DatosContacto datosContacto;
    @NotNull(message = "Debe ingresar el club del personal")
    private ClubPersonalDTO club;

    public PersonalClubDTO() {
    }

    public PersonalClubDTO(String nombre, String apellido, String dni, LocalDate fechaNacimiento, LocalDate fechaInscripcion, List<Rol> rol, Domicilio domicilio, DatosContacto datosContacto, ClubPersonalDTO club) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaInscripcion = fechaInscripcion;
        this.rol = rol;
        this.domicilio = domicilio;
        this.datosContacto = datosContacto;
        this.club = club;
    }

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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Rol> getRol() {
        return rol;
    }

    public void setRol(List<Rol> rol) {
        this.rol = rol;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public DatosContacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(DatosContacto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public ClubPersonalDTO getClub() {
        return club;
    }

    public void setClub(ClubPersonalDTO club) {
        this.club = club;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

}
