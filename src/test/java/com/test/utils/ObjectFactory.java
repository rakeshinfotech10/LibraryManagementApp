package com.test.utils;
import java.util.ArrayList;
import java.util.List;

import com.book.domain.Book;
import com.book.domain.Tags;
import com.book.dto.BookDto;

/**
 * Object factory class for Test purpose
 * @author rakes
 *
 */
public class ObjectFactory {
	
	private static Book book;
	private static BookDto bookDto;
	
	 static
	 {
		 List<Tags> tagsList = new ArrayList<Tags>();
		 Tags tags = new Tags();
		 book = new Book();
		 book.setIsbn(1);
		 book.setAuthor("Author1");
		 book.setTitle("Title1");
		 tags.setTags("Tag1");
		 tagsList.add(tags);
		 book.setTags(tagsList);

		 bookDto = new BookDto();
		 bookDto.setIsbn(1);
		 bookDto.setAuthor("Author1");
		 bookDto.setTitle("Title1");
		 bookDto.setTags(tagsList);
	 }

	public static Book getBook() {
		return book;
	}


	public static BookDto getBookDto() {
		return bookDto;
	}

}
