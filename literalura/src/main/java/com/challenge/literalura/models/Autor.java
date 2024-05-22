package com.challenge.literalura.models;

public class Autor {
    private Long id;
    private String nombre;
    private Integer AnioNacimiento;
    private Integer AnioMuerte;

    public Autor() {
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
}
