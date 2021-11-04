package com.kh.spring.board;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.spring.common.util.FileInfo;
import com.kh.spring.member.Member;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Board {

	@Id
	@GeneratedValue
	private Long bdIdx;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private Member member;
	
	@Column(columnDefinition = "date default sysdate")
	private LocalDate regDate;
	
	private String title;
	private String content;
	
	@Column(columnDefinition = "number default 0")
	private Boolean isDel;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<FileInfo> file = new ArrayList<FileInfo>();
	
	
}
