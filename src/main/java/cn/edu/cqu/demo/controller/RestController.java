package cn.edu.cqu.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.cqu.demo.domain.Student;
import cn.edu.cqu.demo.service.StudentQueryService;
import cn.edu.cqu.ioc.annotation.MyAutoWired;
import cn.edu.cqu.ioc.annotation.MyController;
import cn.edu.cqu.ioc.annotation.MyRequestMapping;
import cn.edu.cqu.ioc.annotation.MyRequestParam;

@MyController
@MyRequestMapping("/rest")
public class RestController {
	
	@MyAutoWired("studentQueryService")
	public StudentQueryService sqservice;
	
	private static String JSON_LIKE = "{'id':'%s','name':'%s','gpa':%.2f}";
	
	
	@MyRequestMapping("/query/{id}")
	public String query(HttpServletRequest req, HttpServletResponse resp, @MyRequestParam("id") String id) {
		System.out.println(id);
		if (sqservice == null) {
			System.out.println("sqservice is not wired");
			return null;
		}
		Student stu = sqservice.queryById(id);
		return show(resp,stu);
	}
	
	public String show(HttpServletResponse resp, Student stu) {
		try {
			resp.getWriter().write(String.format(JSON_LIKE, stu.getId(),stu.getName(),stu.getGpa()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
