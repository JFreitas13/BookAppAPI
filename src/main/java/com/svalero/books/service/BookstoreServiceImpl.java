package com.svalero.books.service;

import com.svalero.books.domain.Bookstore;
import com.svalero.books.exception.BookstoreNotFoundException;
import com.svalero.books.repository.BookstoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookstoreServiceImpl implements BookstoreService {

    @Autowired
    BookstoreRepository bookstoreRepository; //conexion a BBDD

    @Autowired
    private ModelMapper modelMapper;

    //añadir libreira
    @Override
    public Bookstore addBookstore(Bookstore bookstore) {
        return bookstoreRepository.save(bookstore);
    }

    @Override
    public void deleteBookstore(long id) throws BookstoreNotFoundException {
        Bookstore bookstore = bookstoreRepository.findById(id)
                .orElseThrow(BookstoreNotFoundException::new);
        bookstoreRepository.delete(bookstore);
    }

    //modificar libreria
    @Override
    public Bookstore modifyBookstore(long id, Bookstore newBookstore) throws BookstoreNotFoundException {
        Bookstore existingBookstore = bookstoreRepository.findById(id)
                .orElseThrow(BookstoreNotFoundException::new);
        newBookstore.setId(id);
        modelMapper.map(newBookstore, existingBookstore);

        return bookstoreRepository.save(existingBookstore);
    }

    //buscar todas las librerias
    @Override
    public List<Bookstore> findAll() {
        return bookstoreRepository.findAll();
    }

    //buscar libreria por id
    @Override
    public Bookstore findById(long id) throws BookstoreNotFoundException {
        return bookstoreRepository.findById(id)
                .orElseThrow(BookstoreNotFoundException::new);
    }

    @Override
    public List<Bookstore> findByCityAndZipCode(String city, String zipCode) {
        return bookstoreRepository.findByCityAndZipCode(city, zipCode);
    }

    @Override
    public List<Bookstore> findByZipCode(String zipCode) {
        return bookstoreRepository.findByZipCode(zipCode);
    }

    @Override
    public List<Bookstore> findByName(String name) {
        return bookstoreRepository.findByName(name);
    }

    @Override
    public List<Bookstore> findByCity(String city) {
        return bookstoreRepository.findByCity(city);
    }

    @Override
    public List<Bookstore> findByNameAndCityAndZipCode(String name, String city, String zipCode) {
        return bookstoreRepository.findByNameAndCityAndZipCode(name, city, zipCode);
    }
}
