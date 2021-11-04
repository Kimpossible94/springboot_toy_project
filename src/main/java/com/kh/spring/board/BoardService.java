package com.kh.spring.board;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService{
	private final BoardRepository boardRepository;
	
	public void insertBoard(List<MultipartFile> mfs, Board board) {
		
	}

	public Map<String, Object> selectBoardByBdIdx(String bdIdx) {
		return null;
	}

	
}
