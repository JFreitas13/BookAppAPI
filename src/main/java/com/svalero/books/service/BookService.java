package com.svalero.books.service;

import com.svalero.books.domain.Book;
import com.svalero.books.exception.BookNotFoundException;
import com.svalero.books.exception.PublisherNotFoundException;
import com.svalero.books.exception.WriterNotFoundException;

import java.util.List;

//logica de la api. Llaman a la BBDD
public interface BookService {

    Book addBook(Book book);
    void deleteBook(long id) throws BookNotFoundException;
    Book modifyBook(long id, Book newBook) throws BookNotFoundException;
    List<Book> findAll();
    List<Book> findByName(String name) throws BookNotFoundException;
    List<Book> findByYearEdition(int yearEdition) throws BookNotFoundException;
    List<Book> findByPagesNumber(int pagesNumber) throws BookNotFoundException;
    List<Book> findByNameAndYearEditionAndPagesNumber(String name, int yearEdition, int pagesNumber) throws BookNotFoundException;
    Book findById(long id) throws BookNotFoundException;

}
