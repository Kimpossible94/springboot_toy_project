package com.kh.spring.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandlableException;
import com.kh.spring.common.util.file.FileInfo;
import com.kh.spring.common.util.file.FileUtil;
import com.kh.spring.common.util.pagination.Paging;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService{
	
	private final BoardRepository boardRepository;
	
	public void persistBoard(List<MultipartFile> multiparts, Board board) {
		
		List<FileInfo> fileInfos = new ArrayList<>();
		FileUtil fileUtil = new FileUtil();
		
		for (MultipartFile multipartFile : multiparts) {
			fileInfos.add(fileUtil.fileUpload(multipartFile));
		}
		
		board.setFiles(fileInfos);
		boardRepository.save(board);
	}

	public Board findBoardByIdx(Long bdIdx) {
		return boardRepository.findById(bdIdx)
				.orElseThrow(() -> new HandlableException(ErrorCode.UNAUTHORIZED_PAGE_ERROR));
	}

	public Map<String, Object> findBoardByPage(int pageNumber) {
		int cntPerPage = 5;
		Page<Board> page = boardRepository.findAll(PageRequest.of(pageNumber-1, cntPerPage, Direction.DESC, "bdIdx"));
		List<Board> boards = page.getContent();
		Paging pageUtil = Paging.builder()
				.url("/board/board-list")
				.total((int) boardRepository.count())
				.cntPerPage(cntPerPage)
				.blockCnt(5)
				.curPage(pageNumber)
				.build();
		
		return Map.of("boardList", boards, "paging", pageUtil);
	}
	
}
