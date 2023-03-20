package com.example.lifipav2.model;

import com.example.lifipav2.util.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "jugador")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jugador_sequence")
    @SequenceGenerator(name = "jugador_sequence", sequenceName = "jugador_sequence", allocationSize = 1)
    @Column(name = "id_jugador")
    private Long id;
    @NotBlank(message = "Por favor ingrese el nombre del jugador")
    @Size(min = 3, message = "El nombre del jugador debe tener al menos 3 caracteres")
    private String nombre;
    @NotBlank(message = "Por favor ingrese el apellido del jugador")
    @Size(min = 3, message = "El apellido del jugador debe tener al menos 3 caracteres")
    private String apellido;
    @NotBlank(message = "Por favor ingrese el dni del jugador")
    @Size(min = 7, message = "El dni del jugador debe tener al menos 7 caracteres")
    @Column(unique = true)
    private String dni;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de nacimiento del jugador no puede ser nula")
    private LocalDate fechaNacimiento;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInscripcion;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Debe seleccionar el genero del jugador")
    private Genero genero;
    @NotNull(message = "Debe ingresar los datos de contacto del personal")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "contacto_id", referencedColumnName = "id_contacto", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DatosContacto datosContacto;
    @NotNull(message = "Debe seleccionar el club del jugador")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", referencedColumnName = "id_club")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Club club;
    @NotNull(message = "Debe seleccionar la categoria del jugador")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id_categoria")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria categoria;

    public Jugador() {
    }

    public DatosContacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(DatosContacto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public Jugador(String nombre, String apellido, String dni, LocalDate fechaNacimiento, LocalDate fechaInscripcion, Genero genero, DatosContacto datosContacto, Club club, Categoria categoria) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaInscripcion = fechaInscripcion;
        this.genero = genero;
        this.datosContacto = datosContacto;
        this.club = club;
        this.categoria = categoria;
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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
