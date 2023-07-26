package com.exam.myapp.member;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

//@Repository
public class MemberDaoBatis implements MemberDao{
	//@Autowired
	//private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private SqlSession session;
	
	@Override
	public List<MemberVo> selectMemberList() {
		return session.selectList("com.exam.myapp.member.MemberDao.selectMemberList");
		//{
			//try (SqlSession session = sqlSessionFactory.openSession()) {
				////실행할 SQL문과 동일한 이름의 메서드를 사용하여 SQL문 실행
				////select 결과가 1행인 경우 selectOne, 2행 이상인 경우 selectList 메서드 이용
				////첫번째 인자로 실행할 SQL문의 고유한 이름을 전달
				////두번째 인자로 SQL문 실행 시 필요한 데이터(를 담은 객체)를 전달
				//list = session.selectList("com.exam.member.MemberDao.selectMemberList");
				//}
		//}
		
	}

	@Override
	public int insertMember(MemberVo vo) {
		return session.insert("com.exam.myapp.member.MemberDao.insertMember", vo);
		//try (SqlSession session = sqlSessionFactory.openSession()) {
			//num = session.insert("com.exam.member.MemberDao.insertMember", vo);
			//session.commit(); //jdbc와 달리 insert,update,delete 후에는 commit 필요
			//}
	}

//	삭제버튼을 클릭하면,
//	삭제가 되도록 memberDaoBatis 클래스와 MemberMApper.xml파일을 변경하세요.
	
	@Override
	public int deleteMember(String mem_Id) {
		return session.delete("com.exam.myapp.member.MemberDao.deleteMember", mem_Id);
		//try (SqlSession session = sqlSessionFactory.openSession()) {
			//num = session.delete("com.exam.member.MemberDao.deleteMember", mem_Id);
			//session.commit();
			//}
	}

	@Override
	public MemberVo selectMember(String memId) {
		return session.selectOne("com.exam.myapp.member.MemberDao.selectMember", memId);
		//{
			//try (SqlSession session = sqlSessionFactory.openSession()) {
				////실행할 SQL문과 동일한 이름의 메서드를 사용하여 SQL문 실행
				////select 결과가 1행인 경우 selectOne, 2행 이상인 경우 selectList 메서드 이용
				////첫번째 인자로 실행할 SQL문의 고유한 이름을 전달
				////두번째 인자로 SQL문 실행 시 필요한 데이터(를 담은 객체)를 전달
				//vo = session.selectOne("com.exam.member.MemberDao.selectMember", memId);
				//}
		//}
		
	}

	@Override
	public int updateMember(MemberVo vo) {
		return session.update("com.exam.myapp.member.MemberDao.updateMember", vo);
		//try (SqlSession session = sqlSessionFactory.openSession()) {
			//num = session.update("com.exam.member.MemberDao.updateMember", vo);
			//session.commit(); //jdbc와 달리 insert,update,delete 후에는 commit 필요
			//}
	}
	
	@Override
	public MemberVo selectLogin(MemberVo mvo) {
		// vo에 들어있는 아이디, 비밀번호가 일치하는 회원정보를
		// 데이터베이스에서 select 하여 반환하도록 구현
		return session.selectOne("com.exam.myapp.member.MemberDao.selectLogin", mvo);
		
		//try (SqlSession session = sqlSessionFactory.openSession()) {
			//vo = session.selectOne("com.exam.member.MemberDao.selectLogin", mvo);
		//}
	}


	
	
}
