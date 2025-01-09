package com.cursoudemyspring.libraryapi.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;





@Entity
@Table(name = "autor", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"livros"})
@Data
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String  nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate data_nascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros;
}
