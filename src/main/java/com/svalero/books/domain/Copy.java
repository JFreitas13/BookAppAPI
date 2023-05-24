package com.svalero.books.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "copies")
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar en blanco") //obligatorio
    @NotNull(message = "El campo no puede estar vacío")
    private String barCode;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book copyBook;

    @ManyToOne
    @JoinColumn(name = "bookstore_id")
    private Bookstore copyBookstore;
}
