package com.exam.myapp.member;

import java.io.IOException;

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

//@WebServlet ("/member/add.do")
public class MemAddServlet extends HttpServlet{
	// HttpServlet
	//애플리케이션 JDBC 사용 전에 최초 1번은 JDBC 드라이버 클래스를 메모리에 로드 필요.
	private MemberService memberService = MemberServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/member/memAdd.jsp").forward(req, resp);
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		req.setCharacterEncoding("UTF-8");	
		
		//close문 따로 작성시 필요.
		//Connection conn;
		//PreparedStatement pstmt;
		
		//try () 내부에 선언된 변수의 값은
		//try-catch 블럭의 실행이 완료된 후 자동으로 close() 메서드 실행
		
		MemberVo vo = new MemberVo();
		vo.setMemId(req.getParameter("memId"));
		vo.setMemPass(req.getParameter("memPass"));
		vo.setMemName(req.getParameter("memName"));
		vo.setMemPoint(Integer.parseInt(req.getParameter("memPoint")));
		
		int n = memberService.insertMember(vo);
		
			System.out.println(n + "명의 회원 추가");
		
			resp.setContentType("text/html; Charset=UTF-8"); 
			
		
		    PrintWriter out =resp.getWriter();
		    
		    //회원목록 출력
		    //MemListEservlet 실행!
		    //forward  : 요청객체와 응답객체를 전달하면서 다른 서블릿을 실행
		    //		현재 서블릿에서든 더 이상 응답을 출력하지 않는다. 이후 새로운 프린트 작업을 하지 못함.
//		    req.getRequestDispatcher("/member/list.do").forward(req, resp);
		    //include : 요청객체와 응답객체를 전달하면서 다른 서블릿을 실행. 
		    //		현재 서블릿의 출력 내용 중간에 지정한 서블릿의 출력 내용을 포함. 아래 소스코드 수정한 내용 화면에 적용 가능함.(forward는 안됨)
//		    req.getRequestDispatcher("/member/list.do").include(req, resp);
		    //redirect : 지정한 주소로 이동하라는 명령을 담은 응답을 웹브라우저에게 전송
			resp.sendRedirect( req.getContextPath() + "/member/list.do");
		    
		    
//			out.println("<!DOCTYPE html>                 ");
//			out.println("<html>                          ");
//			out.println("<head>                          ");
//			out.println("<meta charset='UTF-8'>          ");
//			out.println("<title>회원추가</title>            ");
//			out.println("</head>                         ");
//			out.println("<body>                          ");
//			out.println("<h1>" + n + "개의 회원 추가 성공" + "</h1>");
//			out.println("<a href='" + req.getContextPath() + "/member/list.do'>회원목록으로 이동</a>");
//			out.println("<h1>                            ");
//			out.println("</body>                         ");
//			out.println("</html>                         ");
			
			
		}

	}

