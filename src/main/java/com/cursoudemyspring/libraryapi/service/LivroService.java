package com.cursoudemyspring.libraryapi.service;

import com.cursoudemyspring.libraryapi.model.Livro;
import com.cursoudemyspring.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

}
