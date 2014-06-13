package com.example.citeit;

public class Citation {

	private int id;
	private String author;
	private String citation;
	private byte[] image;

	public Citation(String citation, String author, byte[] image) {
		setAuthor(author);
		setCitation(citation);
		setImage(image);
	}
	
	public int getId() {
		return id;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
