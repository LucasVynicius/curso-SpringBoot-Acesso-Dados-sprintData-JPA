package com.cursoudemyspring.libraryapi.dto;

import com.cursoudemyspring.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(
        String nome,
        LocalDate data_nascimento,
        String nacionalidade
) {
    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setData_nascimento(this.data_nascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
