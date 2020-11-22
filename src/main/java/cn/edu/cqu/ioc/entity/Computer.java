package cn.edu.cqu.ioc.entity;

import cn.edu.cqu.ioc.annotation.MyAutoWired;

public class Computer {
	private String name;
	@MyAutoWired
	public Student owner;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setOwner(Student owner) {
		this.owner = owner;
	}
	
	public void sayHiForOwner() {
		owner.sayHi();
		System.out.println("who is owner of "+name);
	}
}
