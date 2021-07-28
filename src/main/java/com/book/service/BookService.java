package com.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.book.domain.Book;
import com.book.dto.BookDto;
import com.book.exceptions.BookDoesNotExist;
import com.book.exceptions.BookNotFound;
import com.book.exceptions.BookNotSaved;
import com.book.exceptions.BookUploadException;

public interface BookService {
	
	String addBook(Book book) throws BookNotSaved;
	Book findBook(int isbn) throws BookNotFound;
	BookDto updateBook(Book book) throws BookNotSaved;
	String deleteBook(int isbn) throws BookDoesNotExist;
	String uploadBooks(MultipartFile file) throws BookUploadException;
	List<Book> searchBooks(Integer isbn, String title, String author, List<String> tags);

}
