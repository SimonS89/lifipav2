package com.example.lifipav2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "domicilio")
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domicilio_sequence")
    @SequenceGenerator(name = "domicilio_sequence", sequenceName = "domicilio_sequence", allocationSize = 1)
    @Column(name = "id_domicilio")
    private Long id;
    @NotBlank(message = "Por favor ingrese una calle")
    private String calle;
    @NotBlank(message = "Por favor ingrese un numero")
    private String numero;
    @NotBlank(message = "Por favor ingrese una localidad")
    @Size(min = 3, message = "la localidad debe tener al menos 3 caracteres")
    private String localidad;
    @NotBlank(message = "Por favor ingrese una provincia")
    @Size(min = 3, message = "La provincia debe tener al menos 3 caracteres")
    private String provincia;

    public Domicilio() {
    }

    public Domicilio(String calle, String numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Long getId() {
        return id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
