package com.online.books.service;

import com.online.books.entity.BookChapter;
import com.online.books.repository.BookChapterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookChapterService {

    @Autowired
    private BookChapterRepository bookChapterRepository;


    public List<BookChapter> getBookChapterByBookNumber(String bookNumber) {
         return  bookChapterRepository.getBookChapterByBookNumber(bookNumber);
    }

    public BookChapter saveBookChapter(BookChapter bookChapter) {
        return bookChapterRepository.save(bookChapter);
    }

    public BookChapter getChapterById(Long id) {
        return bookChapterRepository.findById(id).orElse(null);
    }

    public BookChapter updateChapter(BookChapter chapter) {
        BookChapter bookChapter = bookChapterRepository.findById(Long.valueOf(chapter.getId())).orElseThrow(() -> new RuntimeException("BookChapter not found"));
        BeanUtils.copyProperties(chapter,bookChapter);
        return bookChapterRepository.save(bookChapter);
    }

    public void deleteChapterById(Long id) {
        bookChapterRepository.deleteById(id);
    }
}
