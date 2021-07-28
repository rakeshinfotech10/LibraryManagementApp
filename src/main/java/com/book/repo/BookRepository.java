package com.book.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.book.domain.Book;

/**
 * JPA Repository for CRUD operation
 * @author Rakesh
 *
 */
public interface BookRepository extends CrudRepository<Book, Integer> {

	Optional<Book> findByIsbn(int productId);

	@Query("SELECT b FROM Book b, Tags t WHERE b.isbn = t.id AND  (b.isbn =:isbn OR b.title =:title OR b.author =:author OR t.tags IN :tags)")
    public List<Book> searchBooks(
    		@Param("isbn") Integer isbn, 
    		@Param("title") String title,
    		@Param("author") String author,
    		@Param("tags") List<String> tags);



}
