package com.cursoudemyspring.libraryapi.repository.specs;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo){
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" +  titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro generoLivro){
        return (root, query, cb) -> cb.equal(root.get("generoLivro"), generoLivro);
    }

//    public static Specification<Livro> nomeAutorEqual(String nomeAutor){
//        return (root, query, cb) -> cb.equal(root.get("autor"), nomeAutor);
//    }
}
