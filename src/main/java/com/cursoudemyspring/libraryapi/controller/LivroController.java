package com.cursoudemyspring.libraryapi.controller;

import com.cursoudemyspring.libraryapi.dto.CadastroLivroDTO;
import com.cursoudemyspring.libraryapi.dto.ErroResposta;
import com.cursoudemyspring.libraryapi.exceptions.RegistroDuplicadoException;
import com.cursoudemyspring.libraryapi.mappers.LivroMapper;
import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Livro;
import com.cursoudemyspring.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    @Autowired
    LivroService livroService;
    @Autowired
    LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvarLivro(@RequestBody @Valid CadastroLivroDTO livroDTO){
        try{
            Livro livro = livroMapper.toEntity(livroDTO);
            livroService.salvar(livro);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}").
                    buildAndExpand(livro.getId()).
                    toUri();
            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);

        }
    }
}
