package com.aloha.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.spring.dto.Board;
import com.aloha.spring.service.BoardService;

@Controller					// 컨트롤러로 빈 등록
@RequestMapping("/board")	// 클래스 레벨 경로 /board로 지정
public class BoardController {

	private static final Logger logger
					= LoggerFactory.getLogger(BoardController.class);
	
	//의존성 자동 주입
	@Autowired
	private BoardService boardService;
	
	
	//[GET]		: @GetMapping("/list")
	//[POST] 	: @PostMapping("/insert")
	
	/**
	 * 게시글 목록			-[GET] 	/list
	 * @param model
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@GetMapping("/list")
	public String list(Model model) throws Exception {
		List<Board> list = boardService.list();
		model.addAttribute("list", list);
		return "board/list";
	}
	
	// 게시글 조회			-[GET] 	/read
	@GetMapping("/read")
	public String read(Model model, int no) throws Exception {
		Board board = boardService.select(no);
		model.addAttribute("board", board);
		return "board/read";
	}
	
	/**
	 * 게시글 등록			-[GET] 	/insert
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/insert")
	public String insert() throws Exception {
		
		return "board/insert";
	}
	
	// 게시글 등록 처리		-[POST] /insert
	@PostMapping("/insert")
	public String insertPost(Board board) throws Exception {
		
		boolean result = boardService.insert(board);
		
		if(result)
			return "redirect:/board/list";
		else
			return "redirect:/board/insert?error";
	}
	
	/**
	 * 게시글 수정			-[GET] 	/update
	 * @param model
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/update")
	public String update(Model model, int no) throws Exception {
		Board board = boardService.select(no);
		model.addAttribute("board", board);
		return "board/update";
	}
	
	
	/**
	 * 게시글 수정 처리		-[POST] /update
	 * @param board
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public String updatePost(Board board) throws Exception {
		
		boolean result = boardService.update(board);
		
		if(result)
			return "redirect:/board/list";
		else
			return "redirect:/board/update?error";
	}
	
	/**
	 * 게시글 삭제 처리		-[POST] /delete
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete")
	public String deletePost(int no) throws Exception {
		
		boolean result = boardService.delete(no);
		
		if(result)
			return "redirect:/board/list";
		else
			return "redirect:/board/update?error";
	}
	
}
