package com.svalero.books.service;

import com.svalero.books.domain.Writer;
import com.svalero.books.exception.WriterNotFoundException;

import java.util.List;

public interface WriterService {

    Writer addWriter(Writer writer);
    void deleteWriter(long id) throws WriterNotFoundException;
    Writer modifyWriter(long id, Writer writer) throws WriterNotFoundException;
    List<Writer> findAll();
    List<Writer> findByName(String name) throws WriterNotFoundException;
    List<Writer> findByAge(String age) throws WriterNotFoundException;
    List<Writer> findByReviews(float reviews) throws WriterNotFoundException;
    List<Writer> findByNameAndAgeAndReviews(String name, String age, float reviews) throws WriterNotFoundException;
    Writer findById(long id) throws WriterNotFoundException;

}
