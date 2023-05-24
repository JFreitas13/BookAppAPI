package com.svalero.books.service;

import com.svalero.books.domain.Book;
import com.svalero.books.domain.Order;
import com.svalero.books.domain.User;
import com.svalero.books.exception.BookNotFoundException;
import com.svalero.books.exception.OrderNotFoundException;
import com.svalero.books.exception.UserNotFoundException;
import com.svalero.books.repository.BookRepository;
import com.svalero.books.repository.OrderRepository;
import com.svalero.books.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository; //conexion a BBDD y asi tiene acceso a todos los metodos

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Order addOrder(Order order, long bookId, long userId) throws BookNotFoundException, UserNotFoundException {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        order.setOrderBook(book);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        order.setOrderUser(user);

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(long id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        orderRepository.delete(order);
    }

    @Override
    public Order modifyOrder(long id, long bookId, long userId, Order newOrder) throws OrderNotFoundException, BookNotFoundException, UserNotFoundException {

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        newOrder.setOrderBook(existingBook);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        newOrder.setOrderUser(existingUser);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        newOrder.setId(id);
        modelMapper.map(newOrder, existingOrder);

        return orderRepository.save(existingOrder);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(long id) throws OrderNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }
}
