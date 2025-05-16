package com.cursoudemyspring.libraryapi.controller;

import com.cursoudemyspring.libraryapi.dto.AutorDTO;
import com.cursoudemyspring.libraryapi.model.Autor;
import com.cursoudemyspring.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity salvar (@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.dadosAutor();
        autorService.salvar(autorEntidade);

        ServletUriComponentsBuilder;

        return new ResponseEntity("Autor Salvo com Sucesso! " + autor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity buscar(){}
}
