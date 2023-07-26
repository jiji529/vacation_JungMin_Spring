package com.exam.myapp;

public class MyVo {
	//파라미터 받기 위한 변수 선언, 이때 파라미터 이름과 같아야함.
	private int x;
	private int y;
	
	public int getX() //<- 속성 이름 
	{
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	

}
