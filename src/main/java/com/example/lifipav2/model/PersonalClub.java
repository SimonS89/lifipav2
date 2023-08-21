package com.example.lifipav2.model;

import com.example.lifipav2.util.Rol;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String dni;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInscripcion;
    @Enumerated(EnumType.STRING)
    private List<Rol> rol;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id_domicilio", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Domicilio domicilio;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "contacto_id", referencedColumnName = "id_contacto", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DatosContacto datosContacto;
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
