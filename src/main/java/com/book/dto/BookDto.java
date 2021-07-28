package com.book.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.book.domain.Tags;

@Component
public class BookDto {

	private int isbn;
	private String title;
	private String author;

	private List<Tags> tags;

	public int getIsbn() {
		return isbn;
	}



	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}


	public List<Tags> getTags() {
		return tags;
	}



	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}

}
