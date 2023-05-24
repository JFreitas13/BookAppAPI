package com.svalero.books.repository;

import com.svalero.books.domain.Bookstore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookstoreRepository extends CrudRepository<Bookstore, Long> {

   List<Bookstore> findAll(); //Buscar todas las librerias
   List<Bookstore> findByCityAndZipCode(String city, String zipCode);
   List<Bookstore> findByZipCode(String zipCode);
   List<Bookstore> findByName(String name);
   List<Bookstore> findByCity(String city);
   List<Bookstore> findByNameAndCityAndZipCode(String name, String city, String zipCode);

}
