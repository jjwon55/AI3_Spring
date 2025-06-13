package com.aloha.string.dto;

public class Comment {

	private String writer;
	private String comment;
	
	
	public Comment() {
		this.writer = "작성자없음";
		this.comment = "댓글없음";
	}
	public Comment(String writer, String comment) {
		this.writer = writer;
		this.comment = comment;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "Comment [writer=" + writer + ", comment=" + comment + "]";
	}
	
	
}
