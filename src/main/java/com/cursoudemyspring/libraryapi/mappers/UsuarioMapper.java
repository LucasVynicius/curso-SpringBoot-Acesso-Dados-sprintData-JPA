package com.cursoudemyspring.libraryapi.mappers;

import com.cursoudemyspring.libraryapi.dto.UsuarioDTO;
import com.cursoudemyspring.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);
}
