package com.svalero.books.controller;

import com.svalero.books.domain.Bookstore;
import com.svalero.books.exception.BookstoreNotFoundException;
import com.svalero.books.exception.ErrorMessage;
import com.svalero.books.service.BookstoreService;
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
public class BookstoreController {

    @Autowired
    BookstoreService bookstoreService; //no me puedo conectar directamente con la BBDD. Me conecto al Service que llama al Repository que llama a la BBDD

    private final Logger logger = LoggerFactory.getLogger(BookstoreController.class); //Creamos el objeto capaz de pintar las trazas en el log y lo asociamos a la clase que queremos controlar

    //añadir libreria
    @PostMapping("/bookstores")
    public ResponseEntity<Bookstore> addBookstore(@Valid @RequestBody Bookstore bookstore) {
        logger.debug("begin addBookstore");
        Bookstore newBookstore = bookstoreService.addBookstore(bookstore);
        logger.debug("end addBookstore)");
        return ResponseEntity.status(HttpStatus.OK).body(newBookstore);
    }

    //borrar libreria
    @DeleteMapping("/bookstores/{id}")
    public ResponseEntity<Void> deleteBookstore(@PathVariable long id) throws BookstoreNotFoundException {
        logger.debug("begin deleteBookstore");
        bookstoreService.deleteBookstore(id);
        logger.debug("end deleteBookstore");
        return ResponseEntity.noContent().build();
    }

    //modificar libreria
    @PutMapping("/bookstores/{id}")
    public ResponseEntity<Bookstore> modifyBookstore(@PathVariable long id, @RequestBody Bookstore bookstore) throws BookstoreNotFoundException {
        logger.debug("begin modifyBookstore");
        Bookstore modifiedBookstore = bookstoreService.modifyBookstore(id, bookstore);
        logger.debug("end modifyBookstore");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedBookstore);
    }

    //buscar todas librerias con filtros
    @GetMapping("/bookstores")
    public ResponseEntity<List<Bookstore>> getBookstores(
                @RequestParam(defaultValue = "") String name,
                @RequestParam(defaultValue = "") String city,
                @RequestParam(defaultValue = "") String zipCode) throws BookstoreNotFoundException {

        if (name.equals("") && (city.equals("")) && zipCode.equals("")) {
            logger.debug("get with filters");
            return ResponseEntity.ok(bookstoreService.findAll());
        } else if ((city.equals("")) && zipCode.equals("")) {
            logger.debug("get with filters");
            return ResponseEntity.ok(bookstoreService.findByName(name));
        } else if (name.equals("") && (city.equals(""))) {
            logger.debug("get with filters");
            return ResponseEntity.ok(bookstoreService.findByZipCode(zipCode));
        } else if (name.equals("") && zipCode.equals("")) {
            logger.debug("get with filters");
            return ResponseEntity.ok((bookstoreService.findByCity(city)));
        }
        logger.debug("get with filters");
        List<Bookstore> bookstores = bookstoreService.findByNameAndCityAndZipCode(name, city, zipCode);
        return ResponseEntity.ok(bookstores);

    }

    //buscar libreria por id
    @GetMapping("/bookstores/{id}")
    public ResponseEntity<Bookstore> getBookstore(@PathVariable long id) throws BookstoreNotFoundException {
        logger.debug("begin getBookstore");
        Bookstore bookstore = bookstoreService.findById(id);
        logger.debug("end getBookstore");
        return ResponseEntity.ok(bookstore);
    }

    //Excepción 404: Bookstore not found
    @ExceptionHandler(BookstoreNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleBookstoreNotFoundException(BookstoreNotFoundException bsnfe) {
        logger.error((bsnfe.getMessage()), bsnfe); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(404, bsnfe.getMessage());
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
