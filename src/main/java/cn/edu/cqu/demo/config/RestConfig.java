package cn.edu.cqu.demo.config;

import cn.edu.cqu.demo.domain.Student;
import cn.edu.cqu.demo.service.FileUploadService;
import cn.edu.cqu.demo.service.StudentQueryService;
import cn.edu.cqu.ioc.annotation.MyBean;
import cn.edu.cqu.ioc.annotation.MyConfiguration;

@MyConfiguration
public class RestConfig {
	
	@MyBean
	public Student student0() {
		return new Student("Alice","K001",4.0);
	}
	
	@MyBean
	public Student student1() {
		return new Student("Bobby","K002",3.9);
	}
	
	@MyBean
	public Student student2() {
		return new Student("Cocky","K003",3.8);
	}
	
	@MyBean
	public Student student3() {
		return new Student("Dunky","K004",3.3);
	}
	
	@MyBean
	public StudentQueryService studentQueryService() {
		return new StudentQueryService();
	}
	
	@MyBean
	public FileUploadService fileUploadService() {
		return new FileUploadService();
	}
	
}
