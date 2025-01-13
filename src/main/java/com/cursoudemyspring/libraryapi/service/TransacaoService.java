package com.cursoudemyspring.libraryapi.service;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import com.cursoudemyspring.libraryapi.repository.AutorRepository;
import com.cursoudemyspring.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto(){

    }

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("b927e77d-b93b-465d-a569-7af2e3ee5992")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));

    }

    @Transactional
    public void executar(){

        //Salva o Autor
        Autor autor = new Autor();
        autor.setNome("Vania");
        autor.setNacionalidade("Brasileiro");
        autor.setData_nascimento(LocalDate.of(1995, 12, 11));

        autorRepository.save(autor);

        // Salva o Livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(100.0);
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("A mãe 2");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Vania")){
            throw new RuntimeException("Rollback!");
        }
    }
}
