package com.cursoudemyspring.libraryapi.repository;

import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID>{

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(
            String nome,
            LocalDate dataNascimento,
            String nacionalidade
    );

    boolean existsByNomeAndDataNascimentoAndNacionalidade(
            String nome, LocalDate dataNascimento, String nacionalidade
    );

}
