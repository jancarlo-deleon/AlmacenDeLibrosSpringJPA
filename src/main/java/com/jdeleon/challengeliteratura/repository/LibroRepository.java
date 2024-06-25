package com.jdeleon.challengeliteratura.repository;

import com.jdeleon.challengeliteratura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository  extends JpaRepository<Libro,Long> {

    Optional<Libro> findByTituloContains(String titulo);
    List<Libro> findByIdiomasContaining(String idioma);

}
