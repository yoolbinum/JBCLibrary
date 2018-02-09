package com.example.demo.Repositories;

import com.example.demo.Models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findAllByIsBorrowed(Boolean isBorrowed);
    Iterable<Book> findAllByOrderByNumBorrowedDesc();
}
