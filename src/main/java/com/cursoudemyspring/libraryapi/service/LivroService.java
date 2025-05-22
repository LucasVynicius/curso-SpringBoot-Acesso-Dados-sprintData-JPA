package com.cursoudemyspring.libraryapi.service;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Livro;
import com.cursoudemyspring.libraryapi.repository.LivroRepository;
import com.cursoudemyspring.libraryapi.repository.specs.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cursoudemyspring.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorIr(UUID id){
        return livroRepository.findById(id);
    }

    public void deletarLivro(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String titulo,String nomeAutor, GeneroLivro generoLivro, Integer dataPublicacao){

        Specification<Livro> specs = Specification.where(
                (root, query, cb) -> cb.conjunction());
        if(isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if(generoLivro != null){
            specs = specs.and(generoEqual(generoLivro));
        }



        return livroRepository.findAll(specs);
    }
}
