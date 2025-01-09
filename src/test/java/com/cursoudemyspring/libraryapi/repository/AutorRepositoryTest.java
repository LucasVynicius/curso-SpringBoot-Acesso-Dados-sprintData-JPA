package com.cursoudemyspring.libraryapi.repository;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Lucas");
        autor.setNacionalidade("Brasileiro");
        autor.setData_nascimento(LocalDate.of(1950, 1, 31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("2a5ef0b7-ec62-404d-af81-f13d1e3c82ad");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(possivelAutor.get());

            autorEncontrado.setNome("Everaldo Albuquerque");
            autorEncontrado.setData_nascimento(LocalDate.of(1973, 8, 8));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void deletePorId(){
        var id = UUID.fromString("5baebe96-8f19-46ac-bdb2-a079da7c9210");
        repository.deleteById(id);
    }

    @Test
    public void delete(){
        var id = UUID.fromString("6d5aa18d-c785-4108-8c36-0e5dbeec5674");
        var lucas = repository.findById(id).get();
        repository.delete(lucas);
    }

    @Test
    void salvaAutorComLivroTest(){
        Autor autor = new Autor();
        autor.setNome("Lucas");
        autor.setNacionalidade("Brasileiro");
        autor.setData_nascimento(LocalDate.of(1950, 1, 31));

        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(250.0);
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Mansão do Terror");
        livro.setData_publicacao(LocalDate.of(1980, 1, 2));
        livro.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosAutor(){
        var id = UUID.fromString("e1d95f84-e1d8-4122-a0b2-472f1a8a705a");
        var autor = repository.findById(id).get();

        List<Livro> listaLivro = livroRepository.findByAutor(autor);
        autor.setLivros(listaLivro);

        autor.getLivros().forEach(System.out::println);

    }
}
