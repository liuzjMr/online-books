package com.online.books;

import com.online.books.entity.Book;
import com.online.books.service.BookService;
import com.online.books.util.DateUtils;
import com.online.books.util.SnowflakeIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineBooksApplicationTests {

	@Autowired
	private BookService bookService;

	@Test
	void contextLoads() {
	}

	@Test
	void insertBooks(){
		Book book = new Book();
		SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1,1);
		book.setBookNumber(generator.nextId() + "");
		book.setBookName("The Adventures of Reddy Fox");
		book.setBookAuthor("Thornton W. Burgess");
		book.setBookReleaseDate("1874-1965");
		book.setBookType("children");
		book.setBookIntroduction("These delightful stories created by the writer known famously as the Bedtime Story Man provide hours of endless enjoyment for readers both young and old. His daily newspaper column which he wrote without a break from 1912 through to 1960 featured a host of engaging characters and their lively pranks and doings.");

		book.setBookLanguage("English");
		book.setBookStar("5");
		book.setBookCoveImage("http://www.loyalbooks.com/image/detail/Adventures-of-Reddy-Fox.jpg");

		bookService.saveBook(book);
	}
}
