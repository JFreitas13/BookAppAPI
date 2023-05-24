package com.svalero.books.repository;

import com.svalero.books.domain.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    List<Publisher> findAll(); //listado de todos las editoriales
    List<Publisher> findByZipCode(String zipCode);
    List<Publisher> findByName(String name);
    List<Publisher> findByCity(String city);
    List<Publisher> findByNameAndCityAndZipCode(String name, String city, String zipCode);
}
