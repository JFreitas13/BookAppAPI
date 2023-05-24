package com.svalero.books.service;

import com.svalero.books.domain.User;
import com.svalero.books.exception.UserNotFoundException;
import com.svalero.books.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public User modifyUser(long id, User newUser) throws UserNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        newUser.setId(id);
        modelMapper.map(newUser, existingUser);
        return userRepository.save(existingUser);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByZipCode(String zipCode) {
        return userRepository.findByZipCode(zipCode);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> findByCity(String city) {
        return userRepository.findByCity(city);
    }

    @Override
    public List<User> findByNameAndCityAndZipCode(String name, String city, String zipCode) {
        return userRepository.findByNameAndCityAndZipCode(name, city, zipCode);
    }

    @Override
    public User findById(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
