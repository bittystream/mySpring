package cn.edu.cqu.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.cqu.demo.service.FileUploadService;
import cn.edu.cqu.ioc.annotation.MyAutoWired;
import cn.edu.cqu.ioc.annotation.MyController;
import cn.edu.cqu.ioc.annotation.MyRequestMapping;

@MyController
@MyRequestMapping
public class UploadController {
	
	@MyAutoWired("fileUploadService")
	public FileUploadService fileUploadService;
	
	@MyRequestMapping("/uploadFile")
	public String uploadFile(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
		fileUploadService.doUpload(req,resp);
		return "success";
	}
}
