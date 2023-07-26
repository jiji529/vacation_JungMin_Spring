package com.exam.myapp.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoJdbc implements MemberDao {
	
	String url = "jdbc:oracle:thin:@localhost:1521:xe"; //데이터베이스 서버 주소
	String user = "web"; // 데이터베이스 접속 아이디
	String password = "web01"; // 데이터베이스 접속 비밀번호
	
	//memlistservlet 기능
	@Override
	public List<MemberVo> selectMemberList() {
		List<MemberVo> list = new ArrayList<MemberVo>();
		
		String sql = "select mem_id, mem_pass, mem_name, mem_point from member";
		
		
		try (	//1. 지정한 데이터베이스에 접속(로그인)
				Connection conn = DriverManager.getConnection(url, user, password);
				
				//2. 해당 연결을 통해 실행할 SQL문을 담은 명령문 객체 생성
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				//SQL문 실행(select문 실행은 executeQuery()메서드를 사용)
				ResultSet rs = pstmt.executeQuery(); //반환값은 조회결과 레코드(row) 수, 1회에 한 행씩만 읽음.
			)
			{		
			
			//처음 ResultSet 객체는 첫 레코드(row) 이전을 가리키고 있음. ex) mem_id, mem_pass, mem_name, mem_point
			// .next() 메서드를 실행하면 다음 레코드(실제 값들이 있는 곳)를 가리키게 된다.
			//rs.next()메서드는 다음 레코드가 있으면 true를 반환하고, 없으면 false를 반환한다.
			while (rs.next()) {
				MemberVo vo = new MemberVo();
				//컬럼값의 데이터타입에 따라서 get타입('컬럼명') 메서드를 사용하여 컬럼값 읽기
				vo.setMemId(rs.getString("mem_id")); //현재 가리키는 레코드(row)의 "mem-id"컬럼값 읽기.
				vo.setMemPass(rs.getString("mem_pass")); //현재 가리키는 레코드(row)의 "mem_pass"컬럼값 읽기.
				vo.setMemName(rs.getString("mem_name")); //현재 가리키는 레코드(row)의 "mem_name"컬럼값 읽기.
				vo.setMemPoint(rs.getInt("mem_point")); //현재 가리키는 레코드(row)의 "em_point"컬럼값 읽기.
				list.add(vo);
			}
			
			//try {}
			//conn.setAutoCommit(false); //JDBC 는 자동 커밋 기능이 있는데 이를 일부러 실행종료시키는 방법.
			//conn.getAutoCommit(); //자동커밋 여부를 확인하는 방법 -> Ctrl+1로 변수로 받아서 출력문에 넣어 실행하면 알 수 있음. 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} //finally 
		return list;
	}
	
	//memaddservlet 기능
	@Override
	public int insertMember(MemberVo vo) {
		String sql = "insert INTO member (mem_id, mem_pass, mem_name, mem_point)"
				+ "values ( ?, ?, ?, ? )";
		
		int n = 0;
		
		try (	//1. 지정한 데이터베이스에 접속(로그인)
				Connection conn = DriverManager.getConnection(url, user, password);
				
				//2. 해당 연결을 통해 실행할 SQL문을 담은 명령문 객체 생성
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
			{		
			//3. pstmt 명령문 객체에 담겨있는 SQL문의 ?에 값을 채워넣기
			//채워넣는 값의 타입에 따라서 명령문.set타입명() 메서드 사용
			pstmt.setString(1, vo.getMemId()); //1번째 ?에 memId 값을 넣기
			pstmt.setString(2, vo.getMemPass()); //2번째 ?에 memPass 값을 넣기
			pstmt.setString(3, vo.getMemName()); //3번째 ?에 memName 값을 넣기
			pstmt.setInt(4, vo.getMemPoint()); //4번째 ?에 memPoint 값을 넣기
			
			//4. SQL문 실행(INSERT, UPDATE, DELETE 문 실행은 executeUPDATE()메서드를 사용)
			n = pstmt.executeUpdate(); //반환값은 SQL문 실행으로 영향받은 레코드(row) 수, 실패하면(하나도 실행 안되면) 0 나옴.
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}
	
	@Override
	public int deleteMember(String mem_Id) {
		String sql = "DELETE FROM member WHERE mem_Id = ?";
		
		int n = 0;
		
		try (	//1. 지정한 데이터베이스에 접속(로그인)
				Connection conn = DriverManager.getConnection(url, user, password);
				
				//2. 해당 연결을 통해 실행할 SQL문을 담은 명령문 객체 생성
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
			{		
			//3. pstmt 명령문 객체에 담겨있는 SQL문의 ?에 값을 채워넣기
			//채워넣는 값의 타입에 따라서 명령문.set타입명() 메서드 사용
			pstmt.setString(1, mem_Id); //1번째 ?에 memId 값을 넣기

			
			//4. SQL문 실행(INSERT, UPDATE, DELETE 문 실행은 executeUPDATE()메서드를 사용)
			n = pstmt.executeUpdate(); //반환값은 SQL문 실행으로 영향받은 레코드(row) 수, 실패하면(하나도 실행 안되면) 0 나옴.
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return n;
	}


}
