package com.svalero.books.controller;

import com.svalero.books.domain.User;
import com.svalero.books.exception.ErrorMessage;
import com.svalero.books.exception.UserNotFoundException;
import com.svalero.books.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    //añadir usuario
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        logger.debug("begin addUser");
        User newUser = userService.addUser(user);
        logger.debug("end addUser");
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    //borrar usuario
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws UserNotFoundException {
        logger.debug("begin deleteUser");
        userService.deleteUser(id);
        logger.debug("end deleteUser");
        return ResponseEntity.noContent().build();
    }

    //modificar usuario
    @PutMapping("/users/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable long id, @RequestBody User user) throws UserNotFoundException {
        logger.debug("begin modifyUser");
        User modifiedUser = userService.modifyUser(id, user);
        logger.debug("end modifyUser");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUser);
    }

    //buscar todos los usuarios con filtros
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String zipCode) throws UserNotFoundException {

        if (name.equals("") && (city.equals("")) && zipCode.equals("")) {
            logger.debug("get user with filters");
            return ResponseEntity.ok(userService.findAll());
        } else if ((city.equals("")) && zipCode.equals("")) {
            logger.debug("get user with filters");
            return ResponseEntity.ok(userService.findByName(name));
        } else if (name.equals("") && (city.equals(""))) {
            logger.debug("get user with filters");
            return ResponseEntity.ok(userService.findByZipCode(zipCode));
        } else if (name.equals("") && zipCode.equals("")) {
            logger.debug("get user with filters");
            return ResponseEntity.ok((userService.findByCity(city)));
        }
        logger.debug("get publisher with filters");
        List<User> users = userService.findByNameAndCityAndZipCode(name, city, zipCode);
        return ResponseEntity.ok(users);
    }

    //buscar usuario por id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) throws UserNotFoundException {
        logger.debug("end getUser");
        User user =userService.findById(id);
        logger.debug("end getUser");
        return ResponseEntity.ok(user);
    }

    //Excepción 404: User not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException unfe) {
        logger.error((unfe.getMessage()), unfe); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(404, unfe.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    //Excetion 400: Bad request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve) {
        logger.error((manve.getMessage()), manve); //traza de log
        Map<String, String> errors = new HashMap<>(); //Montamos un Map de errores
        manve.getBindingResult().getAllErrors().forEach(error -> { //para la exception manve recorremos todos los campos
            String fieldName = ((FieldError) error).getField(); //Extraemos con getField el nombre del campo que no ha pasado la validación
            String message = error.getDefaultMessage(); // y el mensaje asociado
            errors.put(fieldName, message);
        });

        ErrorMessage errorMessage = new ErrorMessage(400, "Bad Request");
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    //cualquier exception. 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        logger.error((e.getMessage()), e); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
