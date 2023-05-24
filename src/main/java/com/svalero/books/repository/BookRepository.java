package com.svalero.books.repository;

import com.svalero.books.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll(); //listado de todos los libros
    List<Book> findByName(String name);
    List<Book> findByYearEdition(int yearEdition);
    List<Book> findByPagesNumber(int pagesNumber);
    List<Book> findByNameAndYearEditionAndPagesNumber(String name, int yearEdition, int pagesNumber);
    //List<Book> findBookByUserId(Long userId);
   // Book findById(long id); // buscar por id
}
