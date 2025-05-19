package com.cursoudemyspring.libraryapi.dto;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import com.cursoudemyspring.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        Double preco,
        AutorDTO autorDTO
) {
}
