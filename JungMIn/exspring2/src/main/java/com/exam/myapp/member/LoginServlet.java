package com.exam.myapp.member;

import java.io.IOException;

// 브라우저 주소창에 
// http://lacalhost:8000/exweb/member/login.do
//	를 입력하여 접속하면, LoginServlet 클래스와 login.jsp 파일이 순차적으로 실행되어
//	브라우저에 로그인 화면이 출력되도록 구현
//	2. 로그인 화면에서 submit 버튼을 클릭하면, 
//	LoginServlet 클래스의 doPost가 실행되도록 구현

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet ("/member/login.do")
public class LoginServlet extends HttpServlet{
	private MemberService memberService = MemberServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String memId = req.getParameter("memId");
//		MemberVo vo = memberDao.selectMember(memId);
//		req.setAttribute("mvo", vo);
		req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		MemberVo vo = new MemberVo();
		vo.setMemId(req.getParameter("memId"));
		vo.setMemPass(req.getParameter("memPass"));

		//id, pw 조회 기능. 
		MemberVo mvo = memberService.selectLogin(vo);
				
		if(mvo==null) { //로그인 실패시, 로그인 화면으로 이동
			resp.sendRedirect(req.getContextPath() + "/member/login.do");
			System.out.println("로그인 안됐습니다.");
		}else {//로그인 성공
			//요청객체(요청처리가 끝나면 삭제됨),세션객체(사용자가 일정 시간 동안 로그인하지 않았을 때 삭제),서블릿컨텍스트객체(딱 하나만 만들어져서 모든 사용자 기록 
																		//  -> 모든 사용자 로그인된 상태로 인식.)
			//-> 로그인 성공 시 회원정보(mvo)랑 로그인 여부를 세션 객체에 저장!
			HttpSession session = req.getSession();
			session.setAttribute("loginUser", mvo); //세션에 로그인한 사용자정보 저장
			resp.sendRedirect(req.getContextPath() + "/member/list.do"); //회원목록화면으로 이동
			
			System.out.println("로그인 됐습니다.");
		}
	}
}

