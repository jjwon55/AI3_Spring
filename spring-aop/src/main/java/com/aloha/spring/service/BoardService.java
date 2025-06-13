package com.aloha.spring.service;

import java.util.List;

import com.aloha.spring.dto.Board;

public interface BoardService {

	// 게시글 목록
	public List<Board> list() throws Exception;
	// 게시글 조회
	public Board select(int no ) throws Exception;
	
	// 
	public boolean insert(Board board) throws Exception;
	
	public boolean update(Board board) throws Exception;
	
	public boolean delete(int no) throws Exception;
	
}
