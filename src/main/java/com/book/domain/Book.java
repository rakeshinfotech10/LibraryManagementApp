package com.book.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Book implements Serializable  {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "isbn")
	private int isbn;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "author")
	private String author;
	
	@ElementCollection
	@Column(name = "tags")
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Tags> tags;
	
	
	public String getTitle() {
		return title;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
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
