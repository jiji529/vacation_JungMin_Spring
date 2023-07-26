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

//@WebServlet ("/member/logout.do")
public class LogoutServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		session.setAttribute("loginUser", null); //세션에 지정한 이름의 속성값을 null로 설정
//		session.removeAttribute("loginUser"); //세선에서 지정한 이름의 속성을 삭제
		session.invalidate(); //세선객체를 제거(후 다시 생성), 모든 속성들도 함께 삭제
		
		//회원목록화면으로 이동
		resp.sendRedirect(req.getContextPath() + "/member/login.do");
			
		}
		}
	

