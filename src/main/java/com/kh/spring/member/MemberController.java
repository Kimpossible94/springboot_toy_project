package com.kh.spring.member;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.CookieGenerator;

import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandlableException;
import com.kh.spring.common.validator.ValidatorResult;
import com.kh.spring.member.validator.JoinForm;
//1. @Contoller : 해당 클래스를 applicationContext에 bean으로 등록
//				Controller와 관련된 annotation을 사용할 수 있게 해준다.
import com.kh.spring.member.validator.JoinFormValidator;

//2. @RequestMapping : 어떤 요청 URL과 Controller의 메서드를 매핑할 지 지정
//					클래스 위에 선언할 경우, 해당 클래스의 모든 메서드가 지정된 경로를 상위경로로 가진다.

//3. @GetMapping : 어떤 GET방식의 요청 URL과 Controller의 메서드를 매핑할 지 지정
//4. @PostMapping : 어떤 Post방식의 요청 URL과 Controller의 메서드를 매핑할 지 지정

//5. @RequestParam : 요청 파라미터를 컨트롤러 메서드의 매개변수에 바인드해준다.
//					단, Content-type이 application/x-www-form-urlEncoded인 경우에만 해준다.
//					왜냐하면 이 어노테이션의 경우에는 내부적으로 파라미터 매핑을 위해 form httpMessageConverter가 동작한다.
//					따라서, form태그로 보내는건 RequestParam으로 할 수 있다.
//		@RequestParam 속성
//			name : 요청파라미터명, 매개변수명과 요청파라미티명이 같은 경우 생략(같으면 스프링이 알아서 해줌)
//			required : 요청파라미터 필수 여부 지정, 기본값 : true
//			defaultValue : 요청 파라미터가 전달되지 않은 경우 지정할 기본 값

//6. @RequestBody : 요청 body를 읽어서 자바의 객체에 바인드
//					application/x-www-form-urlEncoded를 지원하지 않는다.
//					따라서 다른 Context-type으로 요청이 넘어온 경우에 작동된다.
//					즉, @RequestBody에게 form태그로 요청을하면 에러가난다.

//7. @ModelAttribute : 요청 파라미터를 setter를 사용해서 객체에 바인드 한다. (즉, 이걸 사용하려면 java bean 규약을 따라야한다. ex)DTO)
//					Model 객체에 바인드된 객체를 자동으로 저장해준다. 

//8. @RequestHeader : 요청의 헤더를 컨트롤러의 매개변수에 바인드

//9. @SessionAttribute : 원하는 session의 속성값을 매개변수에 바인드 할 수 있다.

//10. @CookieValue : 원하는 cookie의 값을 매개변수에 바인드해준다.

//11. @PathVariable : url 템플릿에 담긴 파라미터값을 매개변수에 바인드

//12. @ResponseBody : 메서드가 반환하는 값을 응답Body에 작성해준다.

//13. Servlet 객체도 매개변수에 선언해 주입 받을 수 있다.
//	(HttpServletRequest, HttpServletRequest, HttpSession 등)

@Controller
@RequestMapping("member")
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MemberService memberService;
	private JoinFormValidator joinFormValidator;
	
	public MemberController(MemberService memberService, JoinFormValidator joinFormValidator) {
		super();
		this.memberService = memberService;
		this.joinFormValidator = joinFormValidator;
	}

	//model의 속성중 속성명이 joinForm인 속성이 있는 경우에 WebDataBinder에 속성을 초기화해주는 역할을 수행
	//만약 value를 지정해주지 않으면 모든 validator가 이 메서드를 타게된다.
	@InitBinder(value = "joinForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(joinFormValidator);
	}
	
	@GetMapping("join")
	public void join(Model model) {}
	
	@PostMapping("join")
	public String join(@Validated JoinForm form
						, Errors errors //Errors 객체는 반드시 검증할 객체의 바로 뒤에 작성
						, Model model
						, HttpSession session
						, RedirectAttributes redirectAttr) {
		
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error", vr.getError());
		
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "member/login";
		}
		
		String token = UUID.randomUUID().toString();
		session.setAttribute("persistToken", token);
		session.setAttribute("persistUser", form);
		
		redirectAttr.addFlashAttribute("message", "회원가입완료를 위한 이메일이 발송되었습니다.");
		
		memberService.authenticateByEmail(form, token);
		
		return "redirect:/";
	}
	
	@GetMapping("join-impl/{token}")
	public String joinImpl(@PathVariable String token
						, @SessionAttribute(value = "persistToken", required = false) String persistToken
						, @SessionAttribute(value = "persistUser", required = false) JoinForm persistUser
						, HttpSession session
						, RedirectAttributes redirectAttr) {
		
		if(!token.equals(persistToken)) {
			throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		}
		
		session.removeAttribute("persistToken");
		session.removeAttribute("persistUser");
		redirectAttr.addFlashAttribute("message", "환영합니다. 고객님");
		memberService.insertMember(persistUser);
		return "redirect:/member/login";
	}
	
	@PostMapping("join-json")
	public String joinWithJson(@RequestBody Member member) {
		
		logger.debug("member : {}",member.toString());
		return "redirect:/";
	}
	
	@GetMapping("login")
	public void login() {}
	
	@PostMapping("login")
	public String loginImpl(Member member, HttpSession session, RedirectAttributes redirectAttr) {
		Member certifiedUser = memberService.authenticateUser(member);
		
		if(certifiedUser == null) {
			redirectAttr.addFlashAttribute("message", "아이디나 비밀번호가 틀렸습니다.");
			return "redirect:/member/login";
		}
		
		session.setAttribute("authentication", certifiedUser);
		logger.debug("member : {}", certifiedUser);
		
		return "redirect:/member/mypage";
	}
	
	@GetMapping("mypage")
	public String mypage(@SessionAttribute(name="authentication") Member certifiedUser
						, @CookieValue(name="JSESSIONID") String sessionId
						, HttpServletResponse response) {
		//JSESSIONID 쿠키 출력
		logger.debug("sessionId : {}", sessionId);
		
		//Session의 authentication 속성값 출력
		logger.debug(certifiedUser.toString());
		
		//쿠키 생성 및 응답헤더에 추가
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieName("testCookie");
		cookieGenerator.addCookie(response, "CookieTest_haha");
		
		return "member/mypage";
	}
	
	@GetMapping("id-check")
	@ResponseBody
	public String idCheck(String userId) {
		
		Member member = memberService.selectMemberByUserId(userId);
		
		if(member == null) {
			return "available";
		} else {
			return "disable";
		}
	}
	
	
}
