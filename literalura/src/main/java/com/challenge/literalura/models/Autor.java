package com.challenge.literalura.models;

public class Autor {
    private String nombre;
    private Integer birth_date;
    private Integer death_date;

    public Autor(String nombre, Integer birth_date, Integer death_date) {
        this.nombre = nombre;
        this.birth_date = birth_date;
        this.death_date = death_date;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Integer birth_date) {
        this.birth_date = birth_date;
    }

    public Integer getDeath_date() {
        return death_date;
    }

    public void setDeath_date(Integer death_date) {
        this.death_date = death_date;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", birth_date=" + birth_date +
                ", death_date=" + death_date +
                '}';
    }
}
