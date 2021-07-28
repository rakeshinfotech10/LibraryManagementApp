package com.book.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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
import com.test.utils.ObjectFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageBooksImplTest {
	
	@MockBean
	ManageBooksImpl manageBooksImpl;
	private BookDto bookDto;
	private Book book;
	

	@Before
	public void setUp() throws Exception {
		
		bookDto = ObjectFactory.getBookDto();
    	book = ObjectFactory.getBook();
		 
	}

	@After
	public void tearDown() throws Exception {
		
		bookDto = null;
    	book = null;
	}

     
    @Test
    public void testAddBook() throws BookNotSaved {
    	String expected = "Book with ISBN : "+book.getIsbn()+" Saved!!!";
    	doReturn(expected).when(manageBooksImpl).addBook(any());
    	String returnedBook= manageBooksImpl.addBook(book);
    	Assertions.assertEquals(expected, returnedBook);
    	
    }
    
   
    @Test
    public void testFindBook() {
    	Mockito.doReturn(book).when(manageBooksImpl).findBook(any(Integer.class));
    	Book returnedBook = manageBooksImpl.findBook(book.getIsbn());
    	Assertions.assertEquals(1,returnedBook.getIsbn());
    	
    }
    
    @Test
    public void testDeleteBook() throws BookDoesNotExist {
    	String expected = "Book with ISBN : "+book.getIsbn()+" Deleted!!!";
    	Mockito.doReturn(expected).when(manageBooksImpl).deleteBook(any(Integer.class));
    	String response = manageBooksImpl.deleteBook(book.getIsbn());
    	Assertions.assertEquals(expected,response);
    	
    }
    
    @Test
    public void testUpdateBook() throws BookNotSaved  {
    	Mockito.doReturn(bookDto).when(manageBooksImpl).updateBook(any());
    	BookDto bookDtoReturned=manageBooksImpl.updateBook(book);
    	Assertions.assertEquals(1,bookDtoReturned.getIsbn());
    	
    }
    
    @Test
    public void testSearchBooks() throws BookDoesNotExist{
    	Mockito.doReturn(new ArrayList<Book>()).when(manageBooksImpl).searchBooks(any(Integer.class), any(String.class), any(String.class), any(List.class));
    	List<Book> listOfBook = manageBooksImpl.searchBooks(book.getIsbn(), book.getTitle(), book.getAuthor(), new ArrayList<String>());
    	Assertions.assertTrue(listOfBook.isEmpty());
    	
    }
    
    @Test
    public void testUploadBooks() throws BookUploadException{ 
    	String expected= "Books imported from XLS file successfully";
    	Mockito.doReturn(expected).when(manageBooksImpl).uploadBooks(any());
    	String response = manageBooksImpl.uploadBooks(any());
    	Assertions.assertEquals(expected,response);
    	
    }
    
}
