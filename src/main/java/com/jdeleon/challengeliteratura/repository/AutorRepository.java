package com.jdeleon.challengeliteratura.repository;

import com.jdeleon.challengeliteratura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findByNameContains(String name);
    List<Autor> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(Integer birthYear, Integer deathYear);

}
