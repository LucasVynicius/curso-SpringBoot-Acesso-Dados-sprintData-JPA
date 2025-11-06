package com.cursoudemyspring.libraryapi.service;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Livro;
import com.cursoudemyspring.libraryapi.model.Usuario;
import com.cursoudemyspring.libraryapi.repository.LivroRepository;
import com.cursoudemyspring.libraryapi.repository.specs.LivroSpecs;
import com.cursoudemyspring.libraryapi.security.SecurityService;
import com.cursoudemyspring.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cursoudemyspring.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {


    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro){
        livroValidator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorIr(UUID id){
        return livroRepository.findById(id);
    }

    public void deletarLivro(Livro livro) {
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro generoLivro,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina
    ){

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

        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return livroRepository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() != null){
            throw  new IllegalArgumentException("Para atualizar o livro precisa está salvo no banco de dados");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
