package com.book;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.book.dto.BookDto;
import com.book.exceptions.BookDoesNotExist;
import com.book.exceptions.BookNotFound;
import com.book.exceptions.BookNotSaved;
import com.book.exceptions.BookUploadException;
import com.book.service.BookService;
import com.book.transformer.BookTransformer;
import com.book.utility.ExcelHelper;

/**
 * REST Controller class
 * @author Rakesh
 *
 */
@RestController
public class LibraryController {
	
	 	private BookService bookService;
	 	private ExcelHelper excelHelper;
	 	private BookTransformer bookTransformer;
	 	
	 	public LibraryController(BookService bookService,ExcelHelper excelHelper,BookTransformer bookTransformer)
	 	{
	 		this.bookService = bookService;
	 		this.excelHelper= excelHelper;
	 		this.bookTransformer = bookTransformer;
	 	}
	

	    @PostMapping(value = "/addBook", consumes = {MediaType.APPLICATION_JSON_VALUE})
	    public ResponseEntity<?> addBook(@RequestBody BookDto bookDto) {
	    	try {
	    		return ResponseEntity.ok(bookService.addBook(bookTransformer.tranformToBook(bookDto)));
	    	}
	        catch (BookNotSaved e) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
	    
	    @GetMapping(value = "/findBook")
	    public ResponseEntity<?> findBook(@RequestParam int isbn) {
	    	try{
	    		return ResponseEntity.ok(bookService.findBook(isbn));
	    	}
	    	catch (BookNotFound e) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
	    
	    @PutMapping(value = "/updateBook", consumes = {MediaType.APPLICATION_JSON_VALUE})
	    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) {
	        try {
				return ResponseEntity.ok(bookService.updateBook(bookTransformer.tranformToBook(bookDto)));
			} catch (BookNotSaved e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
	    }
	    
	    @DeleteMapping(value = "/deleteBook")
	    public ResponseEntity<?> deleteBook(@RequestParam int isbn){
	        try {
				return ResponseEntity.ok(bookService.deleteBook(isbn));
			} catch (BookDoesNotExist e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
	    }

	    @GetMapping(value = "/searchBooks")
	    public ResponseEntity<?> searchBooks(
	    		@RequestParam(required = false, name="isbn") Integer isbn, 
	    		@RequestParam(required = false, name ="title") String title,
	    		@RequestParam(required = false, name ="author") String author, 
	    		@RequestParam(required = false, name ="tags") List<String> tags) {
	    	return ResponseEntity.ok(bookService.searchBooks(isbn,title,author,tags));
	    }
	    

	    @PostMapping(value = "/uploadBooks", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	    public ResponseEntity<?> uploadBooks(@RequestParam("file") MultipartFile file) {
	    	String message = "";

	        if (excelHelper.hasExcelFormat(file)) {
	          try {
	        	  bookService.uploadBooks(file);

	            message = "Uploaded the file successfully: " + file.getOriginalFilename();
	            return ResponseEntity.status(HttpStatus.OK).body(message);
	          } catch (BookUploadException e) {
	            message = "Could not upload file: " + file.getOriginalFilename() + "!";
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
	          }
	        }
	        message = "Please upload a Excel file!";
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	    }

}