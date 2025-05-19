package com.cursoudemyspring.libraryapi.dto;

import com.cursoudemyspring.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID uuid,
        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 50, max = 130, message = "Campo fora do tamanho padrão")
        String nome,

        @NotNull(message = "Campo obrigatorio")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo obrigatorio")
        @Size(max = 50, min = 10, message = "Campo fora do tamanho padrão")
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
