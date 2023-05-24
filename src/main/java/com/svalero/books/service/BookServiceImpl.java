package com.svalero.books.service;

import com.svalero.books.domain.Book;
import com.svalero.books.domain.Bookstore;
import com.svalero.books.domain.Publisher;
import com.svalero.books.domain.Writer;
import com.svalero.books.exception.BookNotFoundException;
import com.svalero.books.exception.BookstoreNotFoundException;
import com.svalero.books.exception.PublisherNotFoundException;
import com.svalero.books.exception.WriterNotFoundException;
import com.svalero.books.repository.BookRepository;
import com.svalero.books.repository.PublisherRepository;
import com.svalero.books.repository.WriterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//implemento la interface. Capa de la logica
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository; //conexion a BBDD y asi tiene acceso a todos los metodos

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    WriterRepository writerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Book addBook(Book book) {

        return bookRepository.save(book); //metodo save es "gratis" para guardar. Le pasas el objeto y el metodo save lo guarda en la BBDD
    }

    @Override
    public void deleteBook(long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    @Override
    public Book modifyBook(long id, Book newBook) throws BookNotFoundException {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        newBook.setId(id);

        modelMapper.map(newBook, existingBook);

        return bookRepository.save(existingBook);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public List<Book> findByYearEdition(int yearEdition) {
        return bookRepository.findByYearEdition(yearEdition);
    }

    @Override
    public List<Book> findByPagesNumber(int pagesNumber) {
        return bookRepository.findByPagesNumber(pagesNumber);
    }

    @Override
    public List<Book> findByNameAndYearEditionAndPagesNumber(String name, int yearEdition, int pagesNumber) {
        return bookRepository.findByNameAndYearEditionAndPagesNumber(name, yearEdition, pagesNumber);
    }

    @Override
    public Book findById(long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }
}
