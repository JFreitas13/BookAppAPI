package com.svalero.books.controller;

import com.svalero.books.domain.Copy;
import com.svalero.books.exception.*;
import com.svalero.books.service.CopyService;
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
public class CopyController {

    @Autowired
    CopyService copyService; //no me puedo conectar directamente con la BBDD. Me conecto al Service que llama al Repository que llama a la BBDD

    private final Logger logger = LoggerFactory.getLogger(BookController.class); //Creamos el objeto capaz de pintar las trazas en el log y lo asociamos a la clase que queremos controlar

    //añadir copy
    @PostMapping("/books/{bookId}/bookstores/{bookstoreId}/copies")
    public ResponseEntity<Copy> addCopy(@Valid @PathVariable("bookId") long bookId, @PathVariable("bookstoreId") long bookstoreId, @RequestBody Copy copy) throws BookNotFoundException, BookstoreNotFoundException {
        logger.debug("begin addCopy"); //Para indicar en el log que alguien ha llamado a esta parte
        Copy newCopy = copyService.addCopy(copy, bookId, bookstoreId);
        logger.debug("end addCopy"); //Para indicar en el log que termina la llamada anterior
        return new ResponseEntity<>(newCopy, HttpStatus.CREATED);
    }

    //borrar copy
    @DeleteMapping("/copies/{id}")
    public ResponseEntity<Void> deleteCopy(@PathVariable long id) throws CopyNotFoundException {
        logger.debug("begin deleteCopy");
        copyService.deleteCopy(id);
        logger.debug("end deleteCopy");
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/copies/{id}")
//    public ResponseEntity<Copy> modifyCopy(@PathVariable long id, @RequestBody Copy copy) throws CopyNotFoundException {
//        logger.debug("begin modifyCopy");
//        Copy modifiedCopy = copyService.modifyCopy(id, copy);
//        logger.debug("end modifyCopy");
//        return ResponseEntity.status(HttpStatus.OK).body(modifiedCopy);
//    }

    //modificar copy
    @PutMapping("/copies/{id}/{bookId}/{bookstoreId}")
    public ResponseEntity<Copy> modifyCopy(@PathVariable long id, @PathVariable("bookId") long bookId, @PathVariable("bookstoreId") long bookstoreId, @RequestBody Copy copy) throws CopyNotFoundException, BookNotFoundException, BookstoreNotFoundException {
        logger.debug("begin modifyCopy");
        Copy modifiedCopy = copyService.modifyCopy(id, bookId, bookstoreId, copy);
        logger.debug("end modifyCopy");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedCopy);
    }

    //buscar todas las copies
    @GetMapping("/copies")
    public ResponseEntity<List<Copy>> getCopies() {
        return ResponseEntity.ok(copyService.findAll()); //me devuelve desde el service
    }

    //buscar copy por id
    @GetMapping("/copies/{id}")
    public ResponseEntity<Copy> getCopy(@PathVariable long id) throws CopyNotFoundException {
        logger.debug("begin getCopy");
        Copy copy = copyService.findById(id);
        logger.debug("end getCopy");
        return ResponseEntity.ok(copy);
    }

    //Excepción 404: Order not found
    @ExceptionHandler(CopyNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCopyNotFoundException(CopyNotFoundException cnfe) {
        logger.error((cnfe.getMessage()), cnfe); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(404, cnfe.getMessage());
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
