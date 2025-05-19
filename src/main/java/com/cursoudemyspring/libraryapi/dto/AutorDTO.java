package com.cursoudemyspring.libraryapi.dto;

import com.cursoudemyspring.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID uuid,
        String nome,
        LocalDate dataNascimento,
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
