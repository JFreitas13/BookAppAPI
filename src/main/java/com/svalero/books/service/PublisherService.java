package com.svalero.books.service;

import com.svalero.books.domain.Publisher;
import com.svalero.books.exception.PublisherNotFoundException;

import java.util.List;

public interface PublisherService {

    Publisher addPublisher (Publisher publisher);
    void deletePublisher(long id) throws PublisherNotFoundException;
    Publisher modifyPublisher(long id, Publisher publisher) throws PublisherNotFoundException;
    List<Publisher> findAll();
    List<Publisher> findByZipCode(String zipCode) throws PublisherNotFoundException;
    List<Publisher> findByName(String name) throws PublisherNotFoundException;
    List<Publisher> findByCity(String city) throws PublisherNotFoundException;
    List<Publisher> findByNameAndCityAndZipCode(String name, String city, String zipCode) throws PublisherNotFoundException;
    Publisher findById(long id) throws PublisherNotFoundException;
}

