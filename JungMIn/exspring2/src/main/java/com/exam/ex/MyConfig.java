package com.exam.ex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 스프링 설정파일 역할을 하는 클래스임을 표시
//value가 basepackage의 별명. 주소 하나면 value라는 문자를 생략 가능.
@ComponentScan(basePackages = "com.exam.ex") // 스프링 설정파일의 <context:component-scan base-package="com.exam.ex"/>와 같은 기능.
public class MyConfig {
	
	//@Bean//("ma") //이 메서드에서 반환하는 객체를 "ma"라는 이름으로 스프링에 등록
	public MyApp ma() {//이름을 생략하면 메서드명을 빈 이름으로 사용
		MyApp app = new MyApp();
		app.setMyService(msk());
		return app;
	}
	//@Bean//("msk") //이 메서드에서 반환하는 객체를 "msk"라는 이름으로 스프링에 등록
	public MyServiceKo msk() {
		return new MyServiceKo();
	}
	//@Bean//("mse") //이 메서드에서 반환하는 객체를 "mse"라는 이름으로 스프링에 등록
	public MyServiceEn mse() {
		return new MyServiceEn();
	}

}
