package com.kh.spring.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.Member;

@Service
public class AdminMemberService {
	
	@Autowired
	private AdminMemberRepository adminMemberRepository;
	
	public List<Member> selectAllMembers(){
		return null;
	}
	
	
	
	
	
	
	

}
