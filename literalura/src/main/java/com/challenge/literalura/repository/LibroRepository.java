package com.challenge.literalura.repository;

import com.challenge.literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String title);

    List<Libro> findByIdiomaContainsIgnoreCase(String language);
}
