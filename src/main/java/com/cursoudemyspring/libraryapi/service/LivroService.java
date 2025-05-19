package com.cursoudemyspring.libraryapi.service;

import com.cursoudemyspring.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

}
