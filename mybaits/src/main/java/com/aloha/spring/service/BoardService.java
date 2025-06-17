package com.aloha.spring.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.spring.dto.Board;

@Mapper
public interface BoardService {

	
	// 목록
		public List<Board> list() throws Exception;
		// 조회
		public Board select(int no) throws Exception;
		// 등록
		public boolean insert(Board board) throws Exception;
		// 수정
		public boolean update(Board board) throws Exception;
		// 삭제
		public boolean delete(int no) throws Exception;
}
