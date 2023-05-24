package com.svalero.books.service;

import com.svalero.books.domain.Writer;
import com.svalero.books.exception.WriterNotFoundException;
import com.svalero.books.repository.WriterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    WriterRepository writerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Writer addWriter(Writer writer)  {
        return writerRepository.save(writer);
    }

    @Override
    public void deleteWriter(long id) throws WriterNotFoundException {
        Writer writer = writerRepository.findById(id)
                .orElseThrow(WriterNotFoundException::new);
        writerRepository.delete(writer);
    }

    @Override
    public Writer modifyWriter(long id, Writer newWriter) throws WriterNotFoundException {
        Writer existingWriter = writerRepository.findById(id)
                .orElseThrow(WriterNotFoundException::new);
        newWriter.setId(id);
        modelMapper.map(newWriter, existingWriter);
        return writerRepository.save(existingWriter);
    }

    @Override
    public List<Writer> findAll() {
        return writerRepository.findAll();
    }

    @Override
    public List<Writer> findByName(String name) {
        return writerRepository.findByName(name);
    }

    @Override
    public List<Writer> findByAge(String age) {
        return writerRepository.findByAge(age);
    }

    @Override
    public List<Writer> findByReviews(float reviews) {
        return writerRepository.findByReviews(reviews);
    }

    @Override
    public List<Writer> findByNameAndAgeAndReviews(String name, String age, float reviews) {
        return writerRepository.findByNameAndAgeAndReviews(name, age, reviews);
    }

    @Override
    public Writer findById(long id) throws WriterNotFoundException {
        return writerRepository.findById(id)
                .orElseThrow(WriterNotFoundException::new);
    }
}
