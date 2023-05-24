package com.svalero.books.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.List;

@Data //genera getters y setters automaticamente
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar en blanco") //obligatorio
    @NotNull(message = "El campo no puede estar vacío")
    private String name;

    @Column(name = "year_edition",length = 4)
//    @NotBlank(message = "El campo no puede estar en blanco")
    @NotNull(message = "El campo no puede estar vacío")
    @DateTimeFormat(pattern = "yyyy")
    private int yearEdition;

    @Column
    private String ageRecommended;

    @Column
    @Min(value = 0)
    private int pagesNumber;

    @Column
    private String description;

    @Column
    @NotNull(message = "El campo no puede estar vacío")
    private boolean ebook;

//   @ManyToMany
//    @JoinTable(name = "stocks",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "bookstore_id"))
//    private List<Bookstore> bookstores;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "orders",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> users;

//    @ManyToMany
//    @JoinTable(name = "collection",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "writer_id"))
//    private List<Writer> writers;

    @OneToMany(mappedBy = "copyBook")
    @JsonBackReference(value = "copy_book")
    private List<Copy> copies;

    @OneToMany(mappedBy = "orderBook")
    @JsonBackReference(value = "order_book")
    private List<Order> orders;
//
//    @ManyToOne
//    @JoinColumn(name = "publisher_id")
//    private Publisher bookPublisher;
//
//    @ManyToOne
//    @JoinColumn(name = "writer_id")
//    private Writer bookWriter;
}
