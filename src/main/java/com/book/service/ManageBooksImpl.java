package com.book.service;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.book.domain.Book;
import com.book.dto.BookDto;
import com.book.exceptions.BookDoesNotExist;
import com.book.exceptions.BookNotFound;
import com.book.exceptions.BookNotSaved;
import com.book.exceptions.BookUploadException;
import com.book.repo.BookRepository;
import com.book.transformer.BookTransformer;
import com.book.utility.ExcelHelper;

/**
 * Service Implementation Class
 * @author Rakesh
 *
 */
@Service
public class ManageBooksImpl implements ManageBook {

	private ExcelHelper excelhelper;
	private BookRepository booksRepo;
	private BookTransformer bookTransformer;
	
	public ManageBooksImpl(ExcelHelper excelhelper,BookRepository booksRepo,
			BookTransformer bookTransformer)
	{
		this.excelhelper = excelhelper;
		this.booksRepo = booksRepo;
		this.bookTransformer = bookTransformer;
		
	}
	
	

	@Override
	public String addBook(Book book) throws BookNotSaved {
		try{
			booksRepo.save(book);
		}
		catch(Exception e){
			
			throw new BookNotSaved("Book was not saved, Please try again!!");
		}
		return  "Book with ISBN : "+book.getIsbn()+" Saved!!!";
		
	}

	@Override
	public Book findBook(int isbn) throws BookNotFound {
			return booksRepo.findByIsbn(isbn).orElseThrow(()-> new BookNotFound("Book with ISBN : "+isbn+ " Does Not Exist!!!"));
	}


	@Override
	public BookDto updateBook(Book book) throws BookNotSaved {
		BookDto bookDtoResponse;
		try{
			Book  book1 = booksRepo.findById(book.getIsbn()).orElse(null);
			if(book1 != null && book1.getTags() != null){
				book.getTags().addAll(book1.getTags());
			}
			bookDtoResponse = bookTransformer.tranformToBookDto(booksRepo.save(book));
		}
		catch(Exception e){
			
			throw new BookNotSaved("Book was not saved, Please try again!!");
		}
		return bookDtoResponse;
	}
	
	@Override
	public String deleteBook(int isbn) throws BookDoesNotExist {
	
		try{
			
		booksRepo.deleteById(isbn);
		}
		catch(EmptyResultDataAccessException e){
			
			throw new BookDoesNotExist("Book does not exist with ISBN :"+ isbn);
		}
		return "Book with ISBN : "+isbn+" Deleted!!!";
	}
	

	@Override
	public List<Book> searchBooks(Integer isbn, String title, String author, List<String> tags) {
		List<Book> books = booksRepo.searchBooks(isbn, title, author, tags);
		return books;
	}

	
	@Override
	 public String uploadBooks(MultipartFile file) throws BookUploadException {
		    try {
		      List<Book> books = excelhelper.excelToBooks(file.getInputStream());
		      
		      for (Book book : books) {
		    	Book book1 =booksRepo.findById(book.getIsbn()).orElse(null);
		  		if(book1 != null && book1.getTags() != null){
		  		book.getTags().addAll(book1.getTags());
		  		}
		    	booksRepo.save(book);
			}
		    } catch (Exception e) {
		      throw new BookUploadException("Exception occurred during File Upload");
		    }
		    return "Books imported from XLS file successfully";
		  }

}
