package com.firstProject.scheduleX.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="users")
public class User {

    @Id
    @SequenceGenerator(
            name="usuarios_sequence",
            sequenceName="usuarios_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usuarios_sequen0pce"
    )
    private Long idLong;
    private String nombreDeUsuario;
    public int id;
    public String username;
    public String accountNumber;
    public boolean enabled;
    public String color;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public User() {
    }

    public User(Long idLong, String nombreDeUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.nombreDeUsuario = nombreDeUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public User(String nombreDeUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public User(Long idLong, String nombreDeUsuario, int id, String username, String accountNumber, boolean enabled, String color, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idLong = idLong;
        this.nombreDeUsuario = nombreDeUsuario;
        this.id = id;
        this.username = username;
        this.accountNumber = accountNumber;
        this.enabled = enabled;
        this.color = color;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return idLong;
    }

    public void setId(Long id) {
        this.idLong = idLong;
    }

    public String getnombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setnombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "User{" +
                "idLong=" + idLong +
                ", nombreDeUsuario='" + nombreDeUsuario + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", enabled=" + enabled +
                ", color='" + color + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
