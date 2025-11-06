package com.cursoudemyspring.libraryapi.controller;

import com.cursoudemyspring.libraryapi.dto.CadastroLivroDTO;
import com.cursoudemyspring.libraryapi.dto.ResultadoPesquisaDTO;
import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.mappers.LivroMapper;
import com.cursoudemyspring.libraryapi.model.Livro;
import com.cursoudemyspring.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {


    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
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
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<ResultadoPesquisaDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "generoLivro", required = false)
            GeneroLivro generoLivro,
            @RequestParam(value = "data-publicaco", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ){
        Page<Livro> paginaResultado = livroService.pesquisa(isbn, titulo, nomeAutor, generoLivro, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisaDTO> resultado = paginaResultado.map(livroMapper::toDTO);

        return ResponseEntity.ok(resultado);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO livroDTO) {
        return livroService.obterPorIr(UUID.fromString(id)).
                map(livro -> {
                    Livro entidade = livroMapper.toEntity(livroDTO);

                    livro.setDataPublicacao(entidade.getDataPublicacao());
                    livro.setIsbn(entidade.getIsbn());
                    livro.setPreco(entidade.getPreco());
                    livro.setGenero(entidade.getGenero());
                    livro.setTitulo(entidade.getTitulo());
                    livro.setAutor(entidade.getAutor());

                    livroService.atualizar(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
