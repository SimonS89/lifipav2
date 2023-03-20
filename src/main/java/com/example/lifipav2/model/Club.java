package com.example.lifipav2.model;

import com.example.lifipav2.util.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "club_sequence")
    @SequenceGenerator(name = "club_sequence", sequenceName = "club_sequence", allocationSize = 1)
    @Column(name = "id_club")
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Por favor ingrese el nombre de la institucion")
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String nombre;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de fundacion de la institucion no puede ser nula")
    private LocalDate fechaFundacion;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInscrpcion;
    @NotNull(message = "El domicilio de la institucion no puede ser nulo")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id_domicilio", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Domicilio domicilio;
    @JoinTable(name = "categoria_x_club", joinColumns = @JoinColumn(name = "club_id", referencedColumnName = "id_club"), inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id_categoria"))
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Categoria> categorias;
    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PersonalClub> personal;
    @NotNull(message = "El club debe tener al menos las categorias de un genero")
    @Enumerated(EnumType.STRING)
    private List<Genero> generos;
    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Jugador> jugadores;

    public Club() {
    }

    public Club(String nombre, LocalDate fechaFundacion, LocalDate fechaInscrpcion, Domicilio domicilio, Set<Categoria> categorias, Set<PersonalClub> personal, List<Genero> generos, Set<Jugador> jugadores) {
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.fechaInscrpcion = fechaInscrpcion;
        this.domicilio = domicilio;
        this.categorias = categorias;
        this.personal = personal;
        this.generos = generos;
        this.jugadores = jugadores;
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

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public LocalDate getFechaInscrpcion() {
        return fechaInscrpcion;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setFechaInscrpcion(LocalDate fechaInscrpcion) {
        this.fechaInscrpcion = fechaInscrpcion;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<PersonalClub> getPersonal() {
        return personal;
    }

    public void setPersonal(Set<PersonalClub> personal) {
        this.personal = personal;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public Set<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Set<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}
