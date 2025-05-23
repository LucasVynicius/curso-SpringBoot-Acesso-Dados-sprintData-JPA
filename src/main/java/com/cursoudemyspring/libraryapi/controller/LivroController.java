package com.cursoudemyspring.libraryapi.controller;

import com.cursoudemyspring.libraryapi.dto.CadastroLivroDTO;
import com.cursoudemyspring.libraryapi.dto.ResultadoPesquisaDTO;
import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.mappers.LivroMapper;
import com.cursoudemyspring.libraryapi.model.Livro;
import com.cursoudemyspring.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    @Autowired
    LivroService livroService;
    @Autowired
    LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Void> salvarLivro(@RequestBody @Valid CadastroLivroDTO livroDTO) {
        Livro livro = livroMapper.toEntity(livroDTO);
        livroService.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaDTO> obterDetalhesLivro(@PathVariable("id") String id){
        return  livroService.obterPorIr(UUID.fromString(id)).map(livro -> {
            var dto = livroMapper.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarLivro(@PathVariable("id") String id){
        return livroService.obterPorIr(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletarLivro(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "generoLivro", required = false)
            GeneroLivro generoLivro,
            @RequestParam(value = "data-publicaco", required = false)
            Integer anoPublicacao
    ){
        var resultado = livroService.pesquisa(isbn, titulo, nomeAutor, generoLivro, anoPublicacao);
        var lista = resultado
                .stream()
                .map(livroMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);

    }
}
