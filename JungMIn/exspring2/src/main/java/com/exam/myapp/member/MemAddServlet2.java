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

@WebServlet ("/student/add.do")
public class MemAddServlet2 extends HttpServlet{
	// HttpServlet
	//애플리케이션 JDBC 사용 전에 최초 1번은 JDBC 드라이버 클래스를 메모리에 로드 필요.
	

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; //데이터베이스 서버 주소
		String user = "web"; // 데이터베이스 접속 아이디
		String password = "web01"; // 데이터베이스 접속 비밀번호
		
		String sql = "insert INTO student (mem_no, mem_name, mem_score)"
					+ "values ( ?, ?, ? )";
		
		resp.setContentType("text/html; Charset=UTF-8"); 
		req.setCharacterEncoding("UTF-8");
		
		String mem_No = req.getParameter("memNo");
		String mem_Name = req.getParameter("memName");
		String mem_Score = req.getParameter("memScore");
		int A = Integer.parseInt(mem_Score);
		
		int n = 0;
		
		try (	
				Connection conn = DriverManager.getConnection(url, user, password);
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
			{		

			pstmt.setString(1, mem_No); 
			pstmt.setString(2, mem_Name); 
			pstmt.setInt(3, A); 
			
			n = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		    PrintWriter out =resp.getWriter();
		    
			out.println("<!DOCTYPE html>                 ");
			out.println("<html>                          ");
			out.println("<head>                          ");
			out.println("<meta charset='UTF-8'>          ");
			out.println("<title>학생입력</title>            ");
			out.println("</head>                         ");
			out.println("<body>                          ");
			out.println("<h1>" + n + "개의 학생 추가" + "</h1>");
			out.println("<h1>                            ");
			out.println("</body>                         ");
			out.println("</html>                         ");
			
			
		}
	}

