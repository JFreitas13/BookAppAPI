package com.svalero.books.repository;

import com.svalero.books.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();
    List<User> findByZipCode(String zipCode);
    List<User> findByName(String name);
    List<User> findByCity(String city);
    List<User> findByNameAndCityAndZipCode(String name, String city, String zipCode);
    //List<User> findUsersByBookId(Long bookId);
}
