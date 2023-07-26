package com.exam.ex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//이 클래스의 객체를 생성하여 "ma"라는 이름으로 스프링에 등록
@Component("ma")
public class MyApp {
	//@Autowired(스프링 자체 제공), @Inject(자바제공), @Resource(자바제공) : 스프링에 등록된 객체를 이 변수(속성)에 주입(저장)
	@Autowired
	private MyService myService;
	
	public void say() {
		System.out.println(myService.getMessage());
	}

	public MyService getMyService() {
		return myService;
	}

	//인터페이스 -> 객체타입으로 파라미터 값 받음.
	public void setMyService(MyService myService) {
		this.myService = myService;
	}
}
