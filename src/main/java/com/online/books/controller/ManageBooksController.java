package com.online.books.controller;

import com.online.books.entity.Book;
import com.online.books.entity.BookChapter;
import com.online.books.service.BookChapterService;
import com.online.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ManageBooksController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookChapterService chapterService;

    @GetMapping("/bookInfos")
    public Map<String, Object> getBookList(@RequestParam(defaultValue = "0") int start,
                                           @RequestParam(defaultValue = "5") int length,
                                           @RequestParam(defaultValue = "DESC") Sort.Direction direction){
        int page = start / length; // 计算当前页码
        Page<Book> bookPage = bookService.findAll(page,length,"id",direction);
        Map<String, Object> response = Map.of(
                "data", bookPage.getContent(),
                "recordsTotal", bookPage.getTotalElements(),
                "recordsFiltered", bookPage.getTotalElements()
        );
        return response;
    }

    @GetMapping("/bookInfo/{id}")
    public Book  bookInfo(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return  book;
    }

    @GetMapping("/chapterInfos/{bookNumber}")
    public Map<String,Object> chapterInfos(@PathVariable String bookNumber){
        Map<String,Object> response = Map.of(
                "data",chapterService.getBookChapterByBookNumber(bookNumber)
        );
        return response;
    }

    @GetMapping("/chapterInfo/{id}")
    public BookChapter chapterInfo(@PathVariable Long id){
        return chapterService.getChapterById(id);
    }

}
