package com.cursoudemyspring.libraryapi.dto;

import com.cursoudemyspring.libraryapi.enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "Campo Obrigatorio.")
        String isbn,
        @NotBlank(message = "Campo Obrigatorio.")
        String titulo,
        @NotNull(message = "Campo Obrigatorio.")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        Double preco,
        @NotNull(message = "Campo Obrigatorio.")
        UUID idAutor
) {
}
