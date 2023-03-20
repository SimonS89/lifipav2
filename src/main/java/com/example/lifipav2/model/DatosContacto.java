package com.example.lifipav2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="dato_contacto")
public class DatosContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacto_sequence")
    @SequenceGenerator(name = "contacto_sequence", sequenceName = "contacto_sequence", allocationSize = 1)
    @Column(name = "id_contacto")
    private Long id;
    @NotBlank(message = "Por favor ingrese un telefono")
    @Size(min = 3, message = "El telefono debe tener al menos 3 caracteres")
    private String telefono;
    @Email(message = "Por favor ingrese un mail valido")
    private String mail;

    public DatosContacto() {
    }

    public DatosContacto(String telefono, String mail) {
        this.telefono = telefono;
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
