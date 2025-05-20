package com.cursoudemyspring.libraryapi.mappers;

import com.cursoudemyspring.libraryapi.dto.AutorDTO;
import com.cursoudemyspring.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
