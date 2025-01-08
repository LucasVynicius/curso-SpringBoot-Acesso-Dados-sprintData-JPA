package com.cursoudemyspring.libraryapi.repository;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(100.0);
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setData_publicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository.findById(UUID.fromString("2a5ef0b7-ec62-404d-af81-f13d1e3c82ad")).orElse(null);
        livro.setAutor(autor);



        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(100.0);
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro livro 2");
        livro.setData_publicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Miguel");
        autor.setNacionalidade("Brasileiro");
        autor.setData_nascimento(LocalDate.of(1995, 12, 11));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);

    }

    // Operação Cascade
    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(100.0);
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro livro");
        livro.setData_publicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Lucas");
        autor.setNacionalidade("Brasileiro");
        autor.setData_nascimento(LocalDate.of(1995, 12, 11));


        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("5c522eab-44e0-49bb-abab-7df377803db1");
        var livro = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("46068e00-0d7b-4efe-8fa0-e68ea5165271");
        Autor lucas = autorRepository.findById(idAutor).orElse(null);

        livro.setAutor(lucas);

        repository.save(livro);
        
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("5c522eab-44e0-49bb-abab-7df377803db1");

        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("9911ccd3-1526-4423-8644-c0f25b27e0fc");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Livro: ");
        System.out.println(livro.getAutor().getNome());


    }
}
