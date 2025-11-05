package com.cursoudemyspring.libraryapi.controller;

import com.cursoudemyspring.libraryapi.dto.UsuarioDTO;
import com.cursoudemyspring.libraryapi.mappers.UsuarioMapper;
import com.cursoudemyspring.libraryapi.model.Usuario;
import com.cursoudemyspring.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    public void salvar(@RequestBody UsuarioDTO dto){
        Usuario usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
