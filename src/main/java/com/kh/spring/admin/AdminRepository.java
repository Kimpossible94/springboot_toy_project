package com.kh.spring.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.member.Member;

public interface AdminRepository extends JpaRepository<Member, String> {


}
