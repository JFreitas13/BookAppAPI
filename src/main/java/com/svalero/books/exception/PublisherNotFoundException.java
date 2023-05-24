package com.svalero.books.exception;

public class PublisherNotFoundException extends Exception {

    public PublisherNotFoundException() {
        super("Publisher not found");
    }

    public PublisherNotFoundException(String message) {
        super(message);
    }
}
