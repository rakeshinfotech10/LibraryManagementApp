package com.book.transformer;

import org.springframework.stereotype.Component;

import com.book.domain.Book;
import com.book.dto.BookDto;


@Component
public class BookTransformer {
	
	    public Book tranformToBook(BookDto bookDto) {
	        Book book = new Book();
	        book.setIsbn(bookDto.getIsbn()); 
	        book.setAuthor(bookDto.getAuthor());
	        book.setTitle(bookDto.getTitle());
	    	book.setTags(bookDto.getTags());
	        return book;
	    }
	    
	    public BookDto tranformToBookDto(Book book) {
	    	BookDto bookDto = new BookDto();
	    	bookDto.setIsbn(book.getIsbn()); 
	    	bookDto.setAuthor(book.getAuthor());
	    	bookDto.setTitle(book.getTitle());
	    	bookDto.setTags(book.getTags());
	        return bookDto;
	    }


}
