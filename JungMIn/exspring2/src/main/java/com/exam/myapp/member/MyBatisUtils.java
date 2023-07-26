package com.exam.myapp.member;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtils {
	
	private static SqlSessionFactory sqlSessionFactory;
	// ↓ static 블록으로 만들어 주기
	static{
	
	try {
		//마이바티스 전체 설정파일 위치 (클래스패스 기준)
		String resource = "batis/mybatis-config.xml"; //마이바티스 전체 설정(DB연결)파일 위치
		
		InputStream inputStream = Resources.getResourceAsStream(resource);
		//설정파일의 내용대로 SqlSessionFactory(마이바티스본체)를 생성
		
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		} catch (IOException e) {

		e.printStackTrace();
		}
	}
	
	//다른 곳에서 불러서 쓸 수 있도록 만들기.
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

}
