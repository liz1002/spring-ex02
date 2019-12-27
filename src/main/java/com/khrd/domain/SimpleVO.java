package com.khrd.domain;

public class SimpleVO {
	private int num;
	private String name;
	private String password;
	
	public SimpleVO() {}
	
	public SimpleVO(int num, String name, String password) {
		super();
		this.num = num;
		this.name = name;
		this.password = password;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "\nSimpleVO [num=" + num + ", name=" + name + ", password=" + password + "]";
	}
}
