package com.svalero.books.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar en blanco")
    @NotNull(message = "El campo no puede estar vacío")
    private String name;

    @Column
    private String city;

    @Column(name = "zip_code", length = 5)
    @NotBlank(message = "El campo no puede estar en blanco")
    @NotNull(message = "El campo no puede estar vacío")
    private String zipCode;

    @Column(name = "phone_number")
    @Pattern(regexp = "[6][0-9]{8}") //para que el telefono movil empiece por 6 y tenga 9 digitos.
    private String phoneNumber;

    @Column
    private String description;

//    @OneToMany(mappedBy = "bookPublisher")
//    @JsonBackReference(value = "book_publisher")
//    private List<Book> books;

}
