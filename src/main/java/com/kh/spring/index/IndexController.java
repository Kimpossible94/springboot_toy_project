package com.kh.spring.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	//1. @Contoller : 해당 클래스를 applicationContext에 bean으로 등록
	//				Controller와 관련된 annotation을 사용할 수 있게 해준다.
	
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
	
	//10. @CookieVariable : 원하는 cookie의 값을 매개변수에 바인드해준다.
	
	//11. @PathVariable : url 템플릿에 담긴 파라미터값을 매개변수에 바인드
	
	//12. @ResponseBody : 메서드가 반환하는 값을 응답Body에 작성해준다.
	
	//13. Servlet 객체도 매개변수에 선언해 주입 받을 수 있다.
	//	(HttpServletRequest, HttpServletRequest, HttpSession 등)
	
	@GetMapping("/")
	public String index() {
		//Controller 메서드의 return 타입
		// void
		// String
		// ModelAndView (옛날에 쓰던 거)
		return "index";
	}
	
	
	
	
	
	
}
