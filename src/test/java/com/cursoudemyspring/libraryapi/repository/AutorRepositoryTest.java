package com.cursoudemyspring.libraryapi.repository;

import com.cursoudemyspring.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

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
        var id = UUID.fromString("29988e85-4b6f-4759-a03d-cff4bcf6d0c3");
        repository.deleteById(id);
    }

    @Test
    public void delete(){
        var id = UUID.fromString("6d5aa18d-c785-4108-8c36-0e5dbeec5674");
        var lucas = repository.findById(id).get();
        repository.delete(lucas);
    }
}
