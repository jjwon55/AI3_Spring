package com.aloha.string.dao;

import org.springframework.stereotype.Repository;

@Repository	//DAO 역할로 빈 등록
public class CommentDAO extends BoardDAO{

	public void test() {
		System.out.println("CommentDAO...");
	}
}
