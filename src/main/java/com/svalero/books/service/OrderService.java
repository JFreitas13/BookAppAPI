package com.svalero.books.service;


import com.svalero.books.domain.Order;
import com.svalero.books.exception.BookNotFoundException;
import com.svalero.books.exception.OrderNotFoundException;
import com.svalero.books.exception.UserNotFoundException;

import java.util.List;

public interface OrderService {

    Order addOrder(Order order, long bookId, long userId) throws BookNotFoundException, UserNotFoundException;
    void deleteOrder(long id) throws OrderNotFoundException;
    Order modifyOrder(long id, long bookId, long userId, Order order) throws OrderNotFoundException, BookNotFoundException, UserNotFoundException;
    List<Order> findAll();
    Order findById(long id) throws OrderNotFoundException;


}
