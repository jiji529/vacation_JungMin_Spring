package com.exam.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//웹브라우저에서
//http://localhost:8000/exweb/member/del.do?memId=삭제할 회원 아이디
//로 요청을 보내면, 지정한 회원 정보를 데이터베이스에서 삭제하고
//"몇 명의 회원 삭제 성공" 메시지와 "회원목록으로 이동" 링크를 화면에 출력

//@WebServlet ("/member/del.do")
public class MemDelServlet extends HttpServlet{
	// HttpServlet
	//애플리케이션 JDBC 사용 전에 최초 1번은 JDBC 드라이버 클래스를 메모리에 로드 필요.
	private MemberService memberService = MemberServiceImpl.getInstance();
		
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html; Charset=UTF-8"); 
		req.setCharacterEncoding("UTF-8");
		
		String mem_Id = req.getParameter("memId");
		
		int n = memberService.deleteMember(mem_Id);
		
			System.out.println(n + "명의 회원 삭제");
		
			resp.sendRedirect( req.getContextPath() + "/member/list.do");
		
//		    PrintWriter out =resp.getWriter();
//		   
//		    
//			out.println("<!DOCTYPE html>                 ");
//			out.println("<html>                          ");
//			out.println("<head>                          ");
//			out.println("<meta charset='UTF-8'>          ");
//			out.println("<title>회원추가</title>            ");
//			out.println("</head>                         ");
//			out.println("<body>                          ");
//			out.println("<h1>" + n + "개의 회원 삭제 성공" + "</h1>");
//			out.println("<a href='" + req.getContextPath() + "/member/list.do'>회원목록으로 이동</a>");
//			out.println("<h1>                            ");
//			out.println("</body>                         ");
//			out.println("</html>                         ");
//			
//			
		}

	
	}

