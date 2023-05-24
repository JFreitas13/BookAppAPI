package com.svalero.books.service;

import com.svalero.books.domain.*;
import com.svalero.books.exception.*;
import com.svalero.books.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    CopyRepository copyRepository; //conexion a BBDD y asi tiene acceso a todos los metodos

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookstoreRepository bookstoreRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Copy addCopy(Copy copy, long bookId, long bookstoreId) throws BookNotFoundException, BookstoreNotFoundException {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        copy.setCopyBook(book);

        Bookstore bookstore = bookstoreRepository.findById(bookstoreId)
                .orElseThrow(BookstoreNotFoundException::new);
        copy.setCopyBookstore(bookstore);

        return copyRepository.save(copy);
    }

    @Override
    public void deleteCopy(long id) throws CopyNotFoundException {
        Copy copy = copyRepository.findById(id)
                .orElseThrow(CopyNotFoundException::new);
        copyRepository.delete(copy);
    }

    @Override
    public Copy modifyCopy(long id, long bookId, long bookstoreId, Copy newCopy) throws CopyNotFoundException, BookNotFoundException, BookstoreNotFoundException {

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        newCopy.setCopyBook(existingBook);

        Bookstore existingBookstore = bookstoreRepository.findById(bookstoreId)
                .orElseThrow(BookstoreNotFoundException::new);
        newCopy.setCopyBookstore(existingBookstore);

        Copy existingCopy = copyRepository.findById(id)
                .orElseThrow(CopyNotFoundException::new);
        newCopy.setId(id);

        modelMapper.map(newCopy, existingCopy);

        return copyRepository.save(existingCopy);
    }

    @Override
    public List<Copy> findAll() {
        return copyRepository.findAll();
    }

    @Override
    public Copy findById(long id) throws CopyNotFoundException {
        return copyRepository.findById(id)
                .orElseThrow(CopyNotFoundException::new);
    }
}
