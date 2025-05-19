package com.cursoudemyspring.libraryapi.dto;

import com.cursoudemyspring.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID uuid,
        @NotBlank(message = "Campo obrigatorio")
        String nome,
        @NotNull(message = "Campo obrigatorio")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio")
        String nacionalidade
) {
    public Autor dadosAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}
