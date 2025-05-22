package com.cursoudemyspring.libraryapi.mappers;

import com.cursoudemyspring.libraryapi.dto.CadastroLivroDTO;
import com.cursoudemyspring.libraryapi.dto.ResultadoPesquisaDTO;
import com.cursoudemyspring.libraryapi.model.Livro;
import com.cursoudemyspring.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(livroDTO.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO livroDTO);

    public abstract ResultadoPesquisaDTO toDTO(Livro livro);
}
