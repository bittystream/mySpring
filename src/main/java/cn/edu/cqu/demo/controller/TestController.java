package cn.edu.cqu.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.cqu.ioc.annotation.MyController;
import cn.edu.cqu.ioc.annotation.MyRequestMapping;

@MyController
@MyRequestMapping
public class TestController {

	// ∑µªÿ“≥√Ê
	@MyRequestMapping("/index")
	public String index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "index";
	}
	
	@MyRequestMapping("/upload")
	public String upload(HttpServletRequest req, HttpServletResponse resp) {
		return "upload";
	}
	
}
