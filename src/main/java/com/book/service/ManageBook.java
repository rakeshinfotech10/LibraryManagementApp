package com.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.book.domain.Book;
import com.book.dto.BookDto;
import com.book.exceptions.BookDoesNotExist;
import com.book.exceptions.BookNotFound;
import com.book.exceptions.BookNotSaved;
import com.book.exceptions.BookUploadException;

public interface ManageBook {
	
	public String addBook(Book book) throws BookNotSaved;
	public Book findBook(int isbn) throws BookNotFound;
	public BookDto updateBook(Book book) throws BookNotSaved;
	public String deleteBook(int isbn) throws BookDoesNotExist;
	public String uploadBooks(MultipartFile file) throws BookUploadException;
	public List<Book> searchBooks(Integer isbn, String title, String author, List<String> tags);

}
