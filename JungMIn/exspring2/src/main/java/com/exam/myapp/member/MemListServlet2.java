package com.exam.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet ("/student/list.do")
public class MemListServlet2 extends HttpServlet{
	// HttpServlet {{}} 안은 초최 1번 실행하는 곳.
	// 애플리케이션 JDBC 사용 전에 최초 1번은 JDBC 드라이버 클래스를 메모리에 로드 필요.
	

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; //데이터베이스 서버 주소
		String user = "web"; // 데이터베이스 접속 아이디
		String password = "web01"; // 데이터베이스 접속 비밀번호
		
		String sql = "select mem_no, mem_name, mem_score from student";
		
		resp.setContentType("text/html; Charset=UTF-8"); 
		req.setCharacterEncoding("UTF-8");
		
		 PrintWriter out =resp.getWriter();
		
		out.println("<!DOCTYPE html>                 ");
		out.println("<html>                          ");
		out.println("<head>                          ");
		out.println("<meta charset='UTF-8'>          ");
		out.println("<title>회원추가</title>            ");
		out.println("</head>                         ");
		out.println("<body>                          ");
		out.println("<h1>                            ");
		
		try (	//1. 지정한 데이터베이스에 접속(로그인)
				Connection conn = DriverManager.getConnection(url, user, password);
				
				//2. 해당 연결을 통해 실행할 SQL문을 담은 명령문 객체 생성
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
			{		
			
			//SQL문 실행(select문 실행은 executeQuery()메서드를 사용)
			ResultSet rs = pstmt.executeQuery(); //반환값은 조회결과 레코드(row) 수, 1회에 한 행씩만 읽음.
			
			//처음 ResultSet 객체는 첫 레코드(row) 이전을 가리키고 있음. ex) mem_id, mem_pass, mem_name, mem_point
			// .next() 메서드를 실행하면 다음 레코드(실제 값들이 있는 곳)를 가리키게 된다.
			//rs.next()메서드는 다음 레코드가 있으면 true를 반환하고, 없으면 false를 반환한다.
			while (rs.next()) {
				//컬럼값의 데이터타입에 따라서 get타입('컬럼명') 메서드를 사용하여 컬럼값 읽기
				String mem_No = rs.getString("mem_no");
				String mem_Name = rs.getString("mem_name"); 
				int mem_Score = rs.getInt("mem_score");
		out.println(mem_No +  ":" + mem_Name + ":" + mem_Score);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		out.println("</h1>                           ");
		out.println("</body>                         ");
		out.println("</html>                         ");
		}
	}

