package com.book.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Tags {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JoinColumn(name="isbn", nullable=false)
	@Column(name = "id")
	private Long id;
	
	
	@Column(name="tags")
	private String tags;

	

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	

}
