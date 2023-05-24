package com.svalero.books.repository;

import com.svalero.books.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {
    List<Copy> findAll();
}
