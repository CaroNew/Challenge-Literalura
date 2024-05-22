package com.challenge.literalura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer AnioNacimiento;
    private Integer AnioMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(String nombre, Integer anioNacimiento, Integer anioMuerte) {
        this.nombre = nombre;
        AnioNacimiento = anioNacimiento;
        AnioMuerte = anioMuerte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return AnioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        AnioNacimiento = anioNacimiento;
    }

    public Integer getAnioMuerte() {
        return AnioMuerte;
    }

    public void setAnioMuerte(Integer anioMuerte) {
        AnioMuerte = anioMuerte;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", AnioNacimiento=" + AnioNacimiento +
                ", AnioMuerte=" + AnioMuerte +
                '}';
    }
}
