package com.online.books.controller;

import ch.qos.logback.core.util.StringUtil;
import com.online.books.entity.Book;
import com.online.books.entity.BookChapter;
import com.online.books.service.BookChapterService;
import com.online.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookChapterService chapterService;

    @GetMapping("/")
    public String index(Model model) {
        List<Book> adBooks = bookService.getBookByIsAd();
        model.addAttribute("adBooks", adBooks);
        List<Book> newBooks = bookService.getBookByIsNew();
        model.addAttribute("newBooks", newBooks);
        List<Book> hotBooks = bookService.getBookByIsHot();
        model.addAttribute("hotBooks", hotBooks);
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Book> adBooks = bookService.getBookByIsAd();
        model.addAttribute("adBooks", adBooks);
        List<Book> newBooks = bookService.getBookByIsNew();
        model.addAttribute("newBooks", newBooks);
        List<Book> hotBooks = bookService.getBookByIsHot();
        model.addAttribute("hotBooks", hotBooks);
        return "index";
    }

    @GetMapping("/children")
    public String children(Model model) {
        List<Book> children = bookService.getBookByBookType("children");
        model.addAttribute("books", children);
        model.addAttribute("categories", "Children");
        List<Book> hotBooks = bookService.getBookByIsHot();
        model.addAttribute("hotBooks", hotBooks);
        return "categories";
    }

    @GetMapping("/fiction")
    public String fiction(Model model) {
        List<Book> fiction = bookService.getBookByBookType("fiction");
        model.addAttribute("books", fiction);
        model.addAttribute("categories", "Fiction");
        List<Book> hotBooks = bookService.getBookByIsHot();
        model.addAttribute("hotBooks", hotBooks);
        return "categories";
    }

    @GetMapping("/fantasy")
    public String fantasy(Model model) {
        List<Book> fantasy = bookService.getBookByBookType("fantasy");
        model.addAttribute("books", fantasy);
        model.addAttribute("categories", "Fantasy");
        List<Book> hotBooks = bookService.getBookByIsHot();
        model.addAttribute("hotBooks", hotBooks);
        return "categories";
    }

    @GetMapping("/mystery")
    public String mystery(Model model) {
        List<Book> mystery = bookService.getBookByBookType("mystery");
        model.addAttribute("books", mystery);
        model.addAttribute("categories", "Mystery");
        List<Book> hotBooks = bookService.getBookByIsHot();
        model.addAttribute("hotBooks", hotBooks);
        return "categories";
    }


    @GetMapping("/single")
    public String single(@RequestParam("id") Long id, Model model) {
        System.out.println(id);
        Book book = bookService.getBookById(id);
        List<BookChapter> chapters = chapterService.getBookChapterByBookNumber(book.getBookNumber());

        model.addAttribute("book", book);
        model.addAttribute("chapters", chapters);

        return "single";
    }




    @GetMapping("/manage")
    public String manageBooks( ) {
        return "manage";
    }


    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book) {
        if (StringUtil.isNullOrEmpty(book.getBookName())) {
            return "redirect:/manage";
        }
        if (StringUtil.isNullOrEmpty(book.getBookAuthor())) {
            return "redirect:/manage";
        }
        bookService.saveBook(book);
        return "manage";
    }

    @PutMapping("/editBook/{id}")
    public String editBook(@RequestBody Book book){
        bookService.updateBook(book);
        return "redirect:/manage";
    }




    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        Book book = bookService.getBookById(id);
        List<BookChapter> chapters = chapterService.getBookChapterByBookNumber(book.getBookNumber());
        if (chapters.size() > 0){
            return ResponseEntity.noContent().build();
        }
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }




    @DeleteMapping("/deleteChapter/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapterById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/chapter/{id}")
    public  String chapterInfo(@PathVariable Long id,Model model){
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "chapters";
    }

    @PostMapping("/saveChapter")
    public String saveBookChapter(@ModelAttribute BookChapter chapter) {
        Book book = bookService.findBookByBookNumber(chapter.getBookNumber());
        if (StringUtil.isNullOrEmpty(chapter.getChapterTitle())){
            return "redirect:/chapter/"+book.getId();
        }

        chapterService.saveBookChapter(chapter);
        return "redirect:/chapter/"+book.getId();
    }


    @PutMapping("/editChapter/{id}")
    public String editChapter(@RequestBody BookChapter chapter){
        BookChapter bookChapter = chapterService.updateChapter(chapter);
        Book book = bookService.findBookByBookNumber(bookChapter.getBookNumber());
        return "redirect:/chapter/"+book.getId();
    }





}
