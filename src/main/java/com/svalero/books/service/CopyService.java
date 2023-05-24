package com.svalero.books.service;


import com.svalero.books.domain.Copy;
import com.svalero.books.exception.BookNotFoundException;
import com.svalero.books.exception.BookstoreNotFoundException;
import com.svalero.books.exception.CopyNotFoundException;


import java.util.List;

public interface CopyService {

    Copy addCopy(Copy copy, long bookId, long bookstoreId) throws BookNotFoundException, BookstoreNotFoundException;
    void deleteCopy(long id) throws CopyNotFoundException;
    Copy modifyCopy(long id, long bookId, long bookstoreId, Copy copy) throws CopyNotFoundException, BookNotFoundException, BookstoreNotFoundException;
    List<Copy> findAll();
    Copy findById(long id) throws CopyNotFoundException;


}
