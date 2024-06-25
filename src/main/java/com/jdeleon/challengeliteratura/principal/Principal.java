package com.jdeleon.challengeliteratura.principal;

import com.jdeleon.challengeliteratura.dto.AutorDto;
import com.jdeleon.challengeliteratura.dto.LibroDto;
import com.jdeleon.challengeliteratura.dto.Results;
import com.jdeleon.challengeliteratura.model.Autor;
import com.jdeleon.challengeliteratura.model.Libro;
import com.jdeleon.challengeliteratura.repository.AutorRepository;
import com.jdeleon.challengeliteratura.repository.LibroRepository;
import com.jdeleon.challengeliteratura.service.ConsumoAPI;
import com.jdeleon.challengeliteratura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> listaLibros;
    private List<Autor> listaAutores;

    public Principal(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepository = libroRepo;
        this.autorRepository = autorRepo;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Seleccione una de las siguientes opciones:
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnioDeterminado();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void listarLibrosPorIdioma() {

        System.out.println("Lista de libros por idioma \n---------------------");

        System.out.println("""
                \n\t---- Por favor, seleccione un idioma:  ----
                \ten - English
                \tes - Spanish
                \tfr - French
                \tpt - Portuguese
                """);
        String lenguaje = teclado.nextLine();

        List<Libro> librosEncontrados = libroRepository.findByIdiomasContaining(lenguaje);

        if (librosEncontrados.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma seleccionado.");
        } else {
            System.out.println("Libros encontrados en " + lenguaje + ":");
            for (Libro libro : librosEncontrados) {
                System.out.println(libro);
            }
        }

        System.out.println("------------------------------------------------");

    }

    private void listarAutoresVivosEnAnioDeterminado() {

        System.out.println("Lista de autores que se encuentran vivos durante el año ingresado\n---------------------");
        System.out.println("Por favor, ingrese el año de nacimiento: \n");
        Integer anio = teclado.nextInt();
        teclado.nextLine();

        listaAutores = autorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(anio,anio);

        if (listaAutores.isEmpty()){
            System.out.println("No se encontraron autores en este año");
        }else{
        listaAutores.stream()
                .sorted(Comparator.comparing(Autor::getName))
                .forEach(System.out::println);

        System.out.println("------------------------------------------------");
        }
    }

    private void listarAutoresRegistrados() {

        System.out.println("Lista de autores registrados\n---------------------");

        listaAutores = autorRepository.findAll();
        listaAutores.stream()
                .sorted(Comparator.comparing(Autor::getName))
                .forEach(System.out::println);

        System.out.println("------------------------------------------------");
    }

    private void listarLibrosRegistrados() {

        System.out.println("Lista de libros registrados\n---------------------");

        listaLibros = libroRepository.findAll();
        listaLibros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);

        System.out.println("------------------------------------------------");

    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que desea buscar: ");
        String nombreLibro = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        Results datos = conversor.obtenerDatos(json, Results.class);

        if (datos.results().isEmpty()){
            System.out.println("Libro no encontrado");
        }else{
            LibroDto libroEncontrado = datos.results().get(0);
            AutorDto autorEncontrado = libroEncontrado.autor().get(0);

            Libro libro = new Libro(libroEncontrado);
            Autor autor = new Autor(autorEncontrado);

            guardarLibroYAutor(libro, autor);

        }

    }


    private void guardarLibroYAutor(Libro book, Autor author) {
        System.out.println(book);


        Optional<Autor> authorFound = autorRepository.findByNameContains(author.getName());
        Autor savedAuthor;
        if (authorFound.isPresent()) {
            System.out.println("Este autor ya se encuentra registrado");
            savedAuthor = authorFound.get();
        } else {
            try {
                savedAuthor = autorRepository.save(author);
                System.out.println("Autor registrado");
            } catch (Exception e) {
                System.out.println("ERROR al guardar autor: " + e.getMessage());
                return;  // Si no podemos guardar el autor, no podemos continuar
            }
        }


        book.setAutor(savedAuthor);


        Optional<Libro> bookFound = libroRepository.findByTituloContains(book.getTitulo());
        if (bookFound.isPresent()) {
            System.out.println("Este libro ya se encuentra registrado");
        } else {
            try {
                libroRepository.save(book);
                System.out.println("Libro Registrado");
            } catch (Exception e) {
                System.out.println("ERROR al guardar libro: " + e.getMessage());
            }
        }
    }

}
