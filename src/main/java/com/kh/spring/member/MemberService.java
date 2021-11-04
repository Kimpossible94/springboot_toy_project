package com.kh.spring.member;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.spring.common.code.Config;
import com.kh.spring.common.mail.EmailSender;
import com.kh.spring.member.validator.JoinForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService{
	
	private final RestTemplate template;
	private final EmailSender mailSender;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void insertMember(JoinForm form) {

	}

	public Member authenticateUser(Member member) {
		return null;
	}

	public Member selectMemberByUserId(String userId) {
		return null;
	}

	public void authenticateByEmail(JoinForm form, String token) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		
		body.add("mail-template", "join-auth-email");
		body.add("userId", form.getUserId());
		body.add("persistToken", token);
		
		//RestTEmplate의 기본 ContentType이 application/json이다.
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity
																.post(Config.DOMAIN.DESC+"/mail")
																.accept(MediaType.APPLICATION_FORM_URLENCODED)
																.body(body);
		
		String htmlText = template.exchange(request, String.class).getBody();
		mailSender.sendEmail(form.getEmail(), "회원가입을 축하합니다.", htmlText);
		
	};
	
}
