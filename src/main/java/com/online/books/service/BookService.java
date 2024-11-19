package com.online.books.service;

import com.online.books.entity.Book;
import com.online.books.repository.BooksRepository;
import com.online.books.util.SnowflakeIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class BookService {


    @Autowired
    private BooksRepository booksRepository;

    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    public Book getBookById(Long id) {
        Book book = booksRepository.findById(id).get();
        return book;
    }

    public Book getBookByBookNumber(String bookNumber) {
       return booksRepository.findByBookNumber(bookNumber);
    }

    public Book saveBook(Book book) {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1,1);
        book.setBookNumber(generator.nextId() + "");
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        return booksRepository.save(book);
    }

    public Book updateBook(Book book) {
        Integer bookId = book.getId();
        Book bookResult =   booksRepository.findById(Long.valueOf(bookId)).orElseThrow(() -> new RuntimeException("Book not found"));

        BeanUtils.copyProperties(book,bookResult);

        bookResult.setUpdateTime(new Date());
        return booksRepository.save(bookResult);
    }

    public List<Book> getBookByBookType(String  bookType) {
        Book book = new Book();
        book.setBookType(bookType);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("bookType", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Book> example = Example.of(book, matcher);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return booksRepository.findAll(example,sort);
    }


    public List<Book>  getBookByIsAd(){
        Book book = new Book();
        book.setIsAd(1);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("isAd", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Book> example = Example.of(book, matcher);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Book> all = booksRepository.findAll(example,sort);
        return all;
    }

    public List<Book>  getBookByIsNew(){
        Book book = new Book();
        book.setIsNew(1);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("isNew", ExampleMatcher.GenericPropertyMatchers.exact());
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Example<Book> example = Example.of(book, matcher);
        return booksRepository.findAll(example,sort);
    }

    public List<Book>  getBookByIsHot(){
        Book book = new Book();
        book.setIsHot(1);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("isHot", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Book> example = Example.of(book, matcher);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return booksRepository.findAll(example,sort);
    }


    public Book insertBook(Book book) {
        return booksRepository.save(book);
    }


    public Page<Book> findAll(int page, int size) {
        return booksRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Book> findAll(int page, int size,String sortField, Sort.Direction direction ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return booksRepository.findAll(pageable);
    }



    public void deleteBookById(Long id) {
        booksRepository.deleteById(id);
    }

    public Book findBookByBookNumber(String bookNumber) {
        return booksRepository.findByBookNumber(bookNumber);
    }

}
