package com.cursoudemyspring.libraryapi.repository;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

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
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

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
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

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


    @Test
    void pesquisarPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorIsbnTest(){
        List<Livro> lista = repository.findByIsbn("");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloEPreco(){
        var preco = Double.valueOf(100.0);
        List<Livro> lista = repository.findByTituloAndPreco("Outro livro 2", preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadosPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidos(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var resultado = repository.findByGenero(GeneroLivro.FICCAO, "dataPublicacao");
        resultado.forEach(System.out::println);

    }

    @Test
    void listarPorGeneroPositionalParamTest(){
        var resultado = repository.findByGeneroPositionParameters(GeneroLivro.FICCAO, "dataPublicacao");
        resultado.forEach(System.out::println);

    }

}
