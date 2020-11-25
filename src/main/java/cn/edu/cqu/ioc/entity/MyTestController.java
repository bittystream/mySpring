package cn.edu.cqu.ioc.entity;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.cqu.ioc.annotation.*;

@MyController
@MyRequestMapping
public class MyTestController {
	
	@MyRequestMapping("/2")
	public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("test : gogogo");
		response.getWriter().write("hello! world!");
	}
}
