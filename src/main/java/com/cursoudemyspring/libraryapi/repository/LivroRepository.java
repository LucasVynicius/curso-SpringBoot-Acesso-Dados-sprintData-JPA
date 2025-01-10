package com.cursoudemyspring.libraryapi.repository;

import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
//    select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = isbn
    List<Livro> findByIsbn(String titulo);

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, Double preco);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    // select * from livro where data_publicaco between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);


    //JPQL -> referência as entidades e as propriedades
    // select l from Livro as l order by l.titulo
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadosPorTituloAndPreco();
}
