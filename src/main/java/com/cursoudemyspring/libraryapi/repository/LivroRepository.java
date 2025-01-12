package com.cursoudemyspring.libraryapi.repository;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    /**select from livro l join autor a on a.id = l.id_autor;**/
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from Livro l
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileiro'
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    // Named Parameters = parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro,
                             @Param("paramOrdenacao") String nomePropriedade
    );

    // Positional Parameters = parametros nomeados
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionParameters(@Param("genero") GeneroLivro generoLivro,
                             @Param("paramOrdenacao") String nomePropriedade
    );

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate novaData);
}
