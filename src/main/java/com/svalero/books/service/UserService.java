package com.svalero.books.service;

import com.svalero.books.domain.User;
import com.svalero.books.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    User addUser(User user);
    void deleteUser(long id) throws UserNotFoundException;
    User modifyUser(long id, User newUser) throws UserNotFoundException;
    List<User> findAll();
    List<User> findByZipCode(String zipCode) throws UserNotFoundException;
    List<User> findByName(String name) throws UserNotFoundException;
    List<User> findByCity(String city) throws UserNotFoundException;
    List<User> findByNameAndCityAndZipCode(String name, String city, String zipCode) throws UserNotFoundException;
    User findById(long id) throws UserNotFoundException;

}
