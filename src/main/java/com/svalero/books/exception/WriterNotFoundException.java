package com.svalero.books.exception;

public class WriterNotFoundException extends Exception{

    public WriterNotFoundException () {
        super("Writer not found");
    }

    public WriterNotFoundException(String message) {
        super(message);
    }
}
