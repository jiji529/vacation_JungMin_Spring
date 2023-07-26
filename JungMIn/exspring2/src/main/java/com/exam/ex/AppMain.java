package com.exam.ex;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {
	public static void main(String[] args) {

		//MyServiceKo msk = new MyServiceKo();
		//리턴타입 -> 출력함수 사용해서 출력.
		//System.out.println(msk.getMessage());
		
		//MyApp app = new MyApp();
		//app.setMyService(msk);
		//app.say();
		
		//---------영어로 출력하기----------//
		//MyServiceEn mse = new MyServiceEn();
		//app.setMyService(mse);
		
		//스프링 == (IoC/DI AOP 기능을 가진) 객체 컨테이너==BeanFactory==ApplicationContext
		//IoC : 제어의 역전 / DI : 의존성주입
		//라이브러리와 프레임 워크의 차이점.
		//라이브러리 : 자신이 프로그램의 전체 주된 흐름을 짜고, 필요한 곳에서 남이 만든 코드를 사용
		//프레임워크 : 이미 만들어진 프로그램의 전체 흐름에 필요한 부분 추가해서 사용 <- spring
		//클래스패스 상의 xml 파일로부터 설정을 읽어서, 스프링 객체 컨테이너를 생성
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("/com/exam/ex/context.xml");
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfig.class);
		//스프링에 ma라는 이름으로 등록되어 있는 객체를 가져오기
		MyApp app = (MyApp) ctx.getBean("ma");
		
		app.say();
	}
}
