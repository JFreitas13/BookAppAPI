package com.svalero.books.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "writers")
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar en blanco")
    @NotNull(message = "El campo no puede estar vacío")
    @Size(min = 5, max = 30, message = "El campo nombre debe tener un minimo de 5 caracteres y un máximo de 30")
    private String name;

    @Column
    @NotBlank(message = "El campo no puede estar en blanco")
    @NotNull(message = "El campo no puede estar vacío")
    private String description;

    @Column
    @NotBlank(message = "El campo no puede estar en blanco")
    @NotNull(message = "El campo no puede estar vacío")
    private String age;

    @Column(name = "social_networks")
    @URL //para que indique la url de su bolg, cuenta de twitter, etc
    private String socialNetworks;

    @Column
    @Min(value = 0)
    @Max(value = 5)
    private float reviews;

//    @OneToMany(mappedBy = "bookWriter")
//    @JsonBackReference(value = "book_writer")
//    private List<Book> books;
}
