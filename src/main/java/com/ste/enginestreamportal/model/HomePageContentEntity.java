package com.ste.enginestreamportal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Table(name = "HomePageContent")
@Entity
public class HomePageContentEntity extends BaseEntity{
	
	@Column(name = "title")
	private String title;
	
	//@Lob
	@Column(name = "content", columnDefinition="TEXT", length = 65535)
	private String content;
	
	@Column(name = "image")
	private String image;
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
