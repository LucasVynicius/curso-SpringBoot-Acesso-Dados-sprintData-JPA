package com.cursoudemyspring.libraryapi.repository;

import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);
}
