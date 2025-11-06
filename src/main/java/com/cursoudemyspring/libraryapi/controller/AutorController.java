package com.cursoudemyspring.libraryapi.controller;

import com.cursoudemyspring.libraryapi.dto.AutorDTO;
import com.cursoudemyspring.libraryapi.mappers.AutorMapper;
import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.model.Usuario;
import com.cursoudemyspring.libraryapi.security.SecurityService;
import com.cursoudemyspring.libraryapi.service.AutorService;
import com.cursoudemyspring.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final SecurityService securityService;
    private final AutorMapper autorMapper;


    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        Autor autor = autorMapper.toEntity(autorDTO);
        autorService.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService.obterPorId(idAutor).map(autor -> {
            AutorDTO autorDTO = autorMapper.toDTO(autor);
            return ResponseEntity.ok(autorDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(autorMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO autorDTO) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setDataNascimento(autorDTO.dataNascimento());
        autor.setNacionalidade(autorDTO.nacionalidade());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }
}
