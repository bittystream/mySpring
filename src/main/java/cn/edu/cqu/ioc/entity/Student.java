package cn.edu.cqu.ioc.entity;

public class Student {
	private String id;
	private String name;

	public Student() {}
	public Student(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void sayHi() {
		System.out.println("Hi from "+name+" numbered "+id);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
}
