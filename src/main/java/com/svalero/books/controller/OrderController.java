package com.svalero.books.controller;

import com.svalero.books.domain.Order;
import com.svalero.books.exception.BookNotFoundException;
import com.svalero.books.exception.ErrorMessage;
import com.svalero.books.exception.OrderNotFoundException;
import com.svalero.books.exception.UserNotFoundException;
import com.svalero.books.service.OrderService;
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
public class OrderController {

    @Autowired
    OrderService orderService; //no me puedo conectar directamente con la BBDD. Me conecto al Service que llama al Repository que llama a la BBDD

    private final Logger logger = LoggerFactory.getLogger(BookController.class); //Creamos el objeto capaz de pintar las trazas en el log y lo asociamos a la clase que queremos controlar

    //añadir order
    @PostMapping("/books/{bookId}/users/{userId}/orders")
    public ResponseEntity<Order> addOrder(@Valid @PathVariable("bookId") long bookId, @PathVariable("userId") long userId, @RequestBody Order order) throws BookNotFoundException, UserNotFoundException {
        logger.debug("begin addOrder"); //Para indicar en el log que alguien ha llamado a esta parte
        Order newOrder = orderService.addOrder(order, bookId, userId);
        logger.debug("end addOrder"); //Para indicar en el log que termina la llamada anterior
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    //borrar order
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) throws OrderNotFoundException {
        logger.debug("begin deleteOrder");
        orderService.deleteOrder(id);
        logger.debug("end deleteOrder");
        return ResponseEntity.noContent().build();
    }

    //modificar order
    @PutMapping("/orders/{id}/{bookId}/{userId}")
    public ResponseEntity<Order> modifyOrder(@PathVariable long id, @PathVariable("bookId") long bookId, @PathVariable("userId") long userId, @RequestBody Order order) throws OrderNotFoundException, BookNotFoundException, UserNotFoundException {
        logger.debug("begin modifyOrder");
        Order modifiedOrder = orderService.modifyOrder(id, bookId, userId, order);
        logger.debug("end modifyOrder");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedOrder);
    }

    //buscar todas orders
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.findAll()); //me devuelve desde el service
    }

    //buscar order por id
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) throws OrderNotFoundException {
        logger.debug("begin getOrder"); //Para indicar en el log que alguien ha llamado a esta parte
        Order order = orderService.findById(id); //cojo el libro
        logger.debug("end getOrder"); //Para indicar en el log que termina la llamada anterior
        return ResponseEntity.ok(order); //devuelvo la respuesta con el book
    }

    //Excepción 404: Order not found
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleOrderNotFoundException(OrderNotFoundException onfe) {
        logger.error((onfe.getMessage()), onfe); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(404, onfe.getMessage());
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
