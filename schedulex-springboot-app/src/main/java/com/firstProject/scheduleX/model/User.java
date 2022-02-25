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
    private Long id;
    private String nombreDeUsuario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public User() {
    }

    public User(Long id, String nombreDeUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", username='" + nombreDeUsuario + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
