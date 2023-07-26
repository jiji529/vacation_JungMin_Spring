package com.exam.myapp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller //컨트롤러(요청을 받았을 때 실행되는 객체)로서 스프링(Dispatcher)에 등록
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// "/"경로로 GET방식 요청이 오면 실행
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Map map, ModelMap modelmap) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		//문자열로 일시 변환하는 부분.
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		//모델 : 응답/화면(JSP) 출력시 포함할 데이터
		//모델에 데이터를 추가(저장)하기 위해서는,
		//인자로 받은 Model,Map,ModelMap 객체에 모델이름-값 형식으로 데이터를 저장 -> 요청객체에 자동으로 저장
		//JSP에서는 ${모델이름}으로 값을 꺼내서 사용 가능
		
		model.addAttribute("a", formattedDate );
		map.put("b", formattedDate );
		modelmap.addAttribute("c", formattedDate );
		
		return "home"; //컨트롤러가 문자열을 반환하면, 스프링은 문자열을 뷰이름으로 인식
		// servlet-context.xml의 설정대로
		// 문자열 앞에 "/WEB-INF/views/"를 추가하고
		// 문자열 뒤에 ".jsp"를 추가한 주소(경로)로 이동(forward)
		// 즉, "/WEB-INF/views/home.jsp"로 포워드 시키고 스프링은 이를 실행한다는 의미.
	}
	
	//브라우저에서 "http://localhost:8000/myapp/test"로 접속하면,
	//test()메소드와 test.jsp가 순서대로 실행되어
	//test.jsp의 h1 태그 내용에 변수 s 값("JSP에서 출력할 문자열")이 출력되도록 구현
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	//@GetMapping("/test"), @PostMapping("/test") 등 요청방식에 따라서 단축 어노테이션 있음 -> 버전 확인하고 쓸 것(스프링 4.3 이상), value값 하나면 'value=' 생략가능
	public String test(@RequestParam(name = "x") String xv //이름이 x인 요청파라미터 값을 변수 값에 저장, 파라미터 이름과 속성 이름이 다를 때 쓰기. 같을 때는 생략 가능.
						, int y  // 파라미터 이름과 변수 값이 같으면 @RequestParam 생략가능
						//사용자가 정의한 객체를 인자로 받는 경우
						//객체의 속성명과 동일한 이름의 파라미터 값을 객체의 속성에 자동 저장
						, @ModelAttribute("mv")  MyVo vo //@ModelAttribute("모델명")을 적용하여 매개변수 값을 지정한 이름으로 모뎅에 저장(추가) 가능
						, MyVo v //파라미터를 받기 위해서 배치한 매개변수는 자동으로 모델에 추가 -> 모델에 추가하는 메소드가 필요 없다는 의미.
								 // @ModelAttribute()처럼 모델명을 생략한 경우, 모델이름은 타입명(MyVo)의 첫글자를 소문자로 변환(m)하여 사용
						, Model model, Map map) {
		//변수값 로그로 출력하기
		logger.info("xv: {}, y: {}", xv, y);
		logger.info("vo.x: {}, vo.t: {}", vo.getX(), vo.getY());
		
		String s = "jsp에서 출력할 문자열";
		
		model.addAttribute("s", s);
		map.put("x", vo.getX());
		map.put("y", vo.getY());
		
		//map.put("vo", vo); -> 어노테이션으로 대체할 수 있음.
		
		return "test";
	}
	
}
