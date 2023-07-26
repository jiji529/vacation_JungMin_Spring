package com.exam.myapp.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.exam.myapp.member.MemberVo;

//필터: 서블릿의 실행 전후에 끼어들어가서 실행
//	다수의 서블릿들이 수행하는 공통작업을 실행할 때 사용
//	filter 인터페이스를 구현하여 필터 클래스 정의
//web.xml 에 <filter> 태그로 등록하거나, 클래스에 @WebFilter 적용

//다수의 컨트롤러 실행 전 후에 수행해야하는 공통작업들은
//핸들러 인터셉터를 사용하여 구현 가능
public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 컨트롤러 실행 전에 실행되는 메서드
		// handler: 현재 인터셉터 이후에 실행된 (인터셉터 여러 개일 때)인터셉터 또는 컨트롤러
		// 반환값 : 이후에 실행될 인터셉터 또는 컨트롤러의 실행 여부 -> 실행할 건지 말건지 결정.(T/F)
		
		//요청 보낸 사용자의 세션을 가져와서
		HttpSession session = request.getSession();
		//세션에 로그인 정보를 가져와서
		MemberVo vo = (MemberVo)session.getAttribute("loginUser");
		//로그인 정보가 없다면,
		if(vo==null) { 
		//로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/member/login.do");
			return false; //컨트롤러 실행하지 않음
		}	
 		return true; //로그인 정보가 있다면, 컨트롤러 실행.
	}
	
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		// 컨트롤러 실행 후, 뷰(JSP) 실행 전에 실행되는 메서드	
//		// 뷰 실행 전에 컨트롤러가 반환해준 정보 = modelAndView
//	}
//	
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		// // 뷰 렌더링(JSP실행 및 출력) 완료 후에 실행되는 메서드
//		
//	}

	
}
