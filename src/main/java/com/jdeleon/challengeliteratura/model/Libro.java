package com.jdeleon.challengeliteratura.model;

import com.jdeleon.challengeliteratura.dto.AutorDto;
import com.jdeleon.challengeliteratura.dto.LibroDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<String> idiomas;

    private Long numeroDeDescargas;

    public Libro() {
    }

    public Libro(String titulo, Autor autor, List<String> idiomas, Long numeroDeDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Libro(LibroDto libroDto) {
        this.titulo = libroDto.titulo();
        this.autor = conversorAutor(libroDto);
        this.idiomas = libroDto.idiomas();
        this.numeroDeDescargas = libroDto.numeroDeDescargas();
    }

    public Autor conversorAutor(LibroDto bookData) {
        AutorDto authorData = bookData.autor().get(0);
        return new Autor(authorData);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Long getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Long numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return String.format(
                "*************************%n" +
                        "Libro ID: %d%n" +
                        "Título: %s%n" +
                        "Autor: %s%n" +
                        "Idiomas: %s%n" +
                        "Número de Descargas: %d%n" +
                "*************************",
                Id,
                titulo,
                autor != null ? autor.getName() : "Desconocido",
                idiomas != null ? String.join(", ", idiomas) : "No especificado",
                numeroDeDescargas
        );
    }
}
