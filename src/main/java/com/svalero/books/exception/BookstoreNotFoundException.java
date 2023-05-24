package com.svalero.books.exception;

public class BookstoreNotFoundException extends Exception {

    public BookstoreNotFoundException () {
        super("Bookstore not found");
    }

    public BookstoreNotFoundException(String message) {
        super(message);
    }

}
