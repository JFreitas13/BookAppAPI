package com.svalero.books.service;

import com.svalero.books.domain.Bookstore;
import com.svalero.books.exception.BookstoreNotFoundException;

import java.util.List;

public interface BookstoreService {

    Bookstore addBookstore(Bookstore bookstore);
    void deleteBookstore(long id) throws BookstoreNotFoundException;
    Bookstore modifyBookstore(long id, Bookstore bookstore) throws BookstoreNotFoundException;
    List<Bookstore> findAll();
    List<Bookstore> findByCityAndZipCode(String city, String zipCode) throws BookstoreNotFoundException;
    List<Bookstore> findByZipCode(String zipCode) throws BookstoreNotFoundException;
    List<Bookstore> findByName(String name) throws BookstoreNotFoundException;
    List<Bookstore> findByCity(String city) throws BookstoreNotFoundException;
    List<Bookstore> findByNameAndCityAndZipCode(String name, String city, String zipCode) throws BookstoreNotFoundException;
    Bookstore findById(long id) throws BookstoreNotFoundException;
}
