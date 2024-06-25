package com.jdeleon.challengeliteratura.model;

import com.jdeleon.challengeliteratura.dto.AutorDto;
import com.jdeleon.challengeliteratura.dto.LibroDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Integer  birthYear;

    private Integer  deathYear;

    private String name;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(Integer birthYear, Integer deathYear, String name, List<Libro> libros) {
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.name = name;
        this.libros = libros;
    }

    public Autor(AutorDto authorData) {
        this.name = authorData.name();
        this.birthYear = Integer.valueOf(authorData.birthDate());
        this.deathYear = Integer.valueOf(authorData.deathYear());
    }

    public Autor conversorAutor(LibroDto bookData) {
        AutorDto authorData = bookData.autor().get(0);
        return new Autor(authorData);
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return String.format(
                "*************************%n" +
                        "Autor: %s%n" +
                        "Nacimiento: %d, Fallecimiento: %d%n" +
                        "*************************",
                name,
                birthYear,
                deathYear
        );
    }
}
