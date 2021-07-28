package com.book.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.book.domain.Book;
import com.book.dto.BookDto;
import com.book.exceptions.BookDoesNotExist;
import com.book.exceptions.BookNotSaved;
import com.book.exceptions.BookUploadException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksImplServiceTest {

	@MockBean
	private BookServiceImpl bookServiceImpl;
	public static final int ISBN_NUMBER = 1234;
	private BookDto bookDto;
	private Book book;
	

	@Before
	public void setUp() {
		bookDto = new BookDto();
		bookDto.setIsbn(ISBN_NUMBER);
		book = new Book();
		book.setIsbn(ISBN_NUMBER);
	}

     
    @Test
    public void testAddBook() throws BookNotSaved {
    	String expected = "Book with ISBN : "+book.getIsbn()+" Saved!!!";
    	doReturn(expected).when(bookServiceImpl).addBook(any());
    	String returnedBook= bookServiceImpl.addBook(book);
    	Assertions.assertEquals(expected, returnedBook);
    	
    }
    
   
    @Test
    public void testFindBook() {
    	Mockito.doReturn(book).when(bookServiceImpl).findBook(any(Integer.class));
    	Book returnedBook = bookServiceImpl.findBook(book.getIsbn());
    	Assertions.assertEquals(ISBN_NUMBER,returnedBook.getIsbn());
    	
    }
    
    @Test
    public void testDeleteBook() throws BookDoesNotExist {
    	String expected = "Book with ISBN : "+book.getIsbn()+" Deleted!!!";
    	Mockito.doReturn(expected).when(bookServiceImpl).deleteBook(any(Integer.class));
    	String response = bookServiceImpl.deleteBook(book.getIsbn());
    	Assertions.assertEquals(expected,response);
    	
    }
    
    @Test
    public void testUpdateBook() throws BookNotSaved  {
    	Mockito.doReturn(bookDto).when(bookServiceImpl).updateBook(any());
    	BookDto bookDtoReturned=bookServiceImpl.updateBook(book);
    	Assertions.assertEquals(ISBN_NUMBER,bookDtoReturned.getIsbn());
    	
    }
    
    @Test
    public void testSearchBooks() throws BookDoesNotExist{
    	Mockito.doReturn(new ArrayList<Book>()).when(bookServiceImpl).searchBooks(any(Integer.class), any(String.class), any(String.class), any(List.class));
    	List<Book> listOfBook = bookServiceImpl.searchBooks(book.getIsbn(), book.getTitle(), book.getAuthor(), new ArrayList<String>());
    	Assertions.assertTrue(listOfBook.isEmpty());
    	
    }
    
    @Test
    public void testUploadBooks() throws BookUploadException{ 
    	String expected= "Books imported from XLS file successfully";
    	Mockito.doReturn(expected).when(bookServiceImpl).uploadBooks(any());
    	String response = bookServiceImpl.uploadBooks(any());
    	Assertions.assertEquals(expected,response);
    	
    }
}
