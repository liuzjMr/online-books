package com.online.books.repository;

import com.online.books.entity.BookChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookChapterRepository extends JpaRepository<BookChapter, Long> {


    @Query(value = "SELECT * FROM book_chapter WHERE book_number = :bookNumber ORDER BY id ASC", nativeQuery = true)
    List<BookChapter> getBookChapterByBookNumber(String bookNumber);
}
