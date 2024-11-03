package com.online.books.repository;

import com.online.books.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


//

public interface BooksRepository extends JpaRepository<Book,Long> {

    Page<Book> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM books WHERE book_number = :bookNumber", nativeQuery = true)
    Book findByBookNumber(String bookNumber);
}