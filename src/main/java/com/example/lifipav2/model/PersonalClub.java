package com.example.lifipav2.model;

import com.example.lifipav2.util.Rol;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personal_club")
public class PersonalClub {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_sequence")
    @SequenceGenerator(name = "personal_sequence", sequenceName = "personal_sequence", allocationSize = 1)
    @Column(name = "id_personal")
    private Long id;
    @NotBlank(message = "Por favor ingrese el nombre del personal")
    @Size(min = 3, message = "El nombre del personal debe tener al menos 3 caracteres")
    private String nombre;
    @NotBlank(message = "Por favor ingrese el apellido del personal")
    @Size(min = 3, message = "El apellido del personal debe tener al menos 3 caracteres")
    private String apellido;
    @NotBlank(message = "Por favor ingrese el dni del personal")
    @Size(min = 7, message = "El dni del personal debe tener al menos 7 caracteres")
    @Column(unique = true)
    private String dni;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Debe ingresar la fecha de nacimiento del personal")
    private LocalDate fechaNacimiento;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInscripcion;
    @NotNull(message = "Por favor ingrese el/los roles")
    @Enumerated(EnumType.STRING)
    private List<Rol> rol;
    @NotNull(message = "El domicilio de la persona no puede ser nulo")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id_domicilio", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Domicilio domicilio;
    @NotNull(message = "Debe ingresar los datos de contacto del personal")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "contacto_id", referencedColumnName = "id_contacto", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DatosContacto datosContacto;
    @NotNull(message = "Debe ingresar el club del personal")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", referencedColumnName = "id_club")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Club club;

    public PersonalClub() {
    }

    public PersonalClub(String nombre, String apellido, String dni, LocalDate fechaNacimiento, LocalDate fechaInscripcion, List<Rol> rol, Domicilio domicilio, DatosContacto datosContacto, Club club) {
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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
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
