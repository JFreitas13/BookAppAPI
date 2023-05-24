package com.svalero.books.exception;

public class BookNotFoundException extends Exception{

    public BookNotFoundException() {
        super("Book not found");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
