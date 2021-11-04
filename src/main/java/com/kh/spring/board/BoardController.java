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
	public void BoardForm() {};
	
	@PostMapping("upload")
	public String uploadBoard(Board board, List<MultipartFile> files, @SessionAttribute("authentication") Member member) {
		
		board.setMember(member);
		boardService.insertBoard(files, board);
		
		return "redirect:/";
	}
	
	@GetMapping("board-detail")
	public void boardDetail(String bdIdx, Model model) {
		Map<String, Object> res = boardService.selectBoardByBdIdx(bdIdx);
		
		model.addAttribute("datas", res);
	}
}
