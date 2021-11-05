package com.kh.spring.board;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.member.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BoardService boardService;
	
	@GetMapping("board-form")
	public void boardForm() {};
	
	@PostMapping("upload")
	public String uploadBoard(
				@RequestParam List<MultipartFile> fileList
				,@SessionAttribute("authentication") Member member
				, Board board
			) {
		
		board.setMember(member);
		boardService.persistBoard(fileList, board);
		return "redirect:/";
	}
	
	@GetMapping("board-detail")
	public void boardDetail(Model model, Long bdIdx) {
		Board board = boardService.findBoardByIdx(bdIdx);
		model.addAttribute("board", board);
	}
	
	@GetMapping("board-list")
	public void boardList(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
		model.addAllAttributes(boardService.findBoardByPage(page));
	}
	

	
	
	
	
	
	
	
	
	
	

}
