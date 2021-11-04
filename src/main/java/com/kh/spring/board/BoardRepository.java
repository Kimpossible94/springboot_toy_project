package com.kh.spring.board;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.common.util.FileInfo;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
}
