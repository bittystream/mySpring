package cn.edu.cqu.ioc.entity;

import cn.edu.cqu.ioc.annotation.*;

public class Computer {
	private String name;
	@MyAutoWired
	public Student owner;

	public Computer(String name, Student owner) {
		this.name = name;
		this.owner = owner;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setOwner(Student owner) {
		this.owner = owner;
	}
	
	public String getName() {
		return name;
	}
	
	public Student getOwner() {
		return owner;
	}
	
	public void sayHiForOwner() {
		owner.sayHi();
		System.out.println("who is owner of "+name);
	}
}
