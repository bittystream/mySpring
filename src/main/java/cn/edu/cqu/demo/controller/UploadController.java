package cn.edu.cqu.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.cqu.ioc.annotation.MyAutoWired;
import cn.edu.cqu.ioc.annotation.MyController;
import cn.edu.cqu.ioc.annotation.MyRequestMapping;

@MyController
@MyRequestMapping
public class UploadController {
	
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
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024 * 5); // 文件大小不超过5MB
        File tempDir = new File("E:/javaee/temp");
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 1024 * 5);
        try {
        	List<FileItem> items = upload.parseRequest(req);
        	for (FileItem item : items) {
        		if (item.isFormField()) {
        			String name = item.getFieldName();
        			String value = item.getString();
        			System.out.println(name+" : "+value);
        		}
        		else {
        			String fileName = item.getName();
        			InputStream in = item.getInputStream();
        			byte [] buffer = new byte[1024];
        			int len = 0;
        			System.out.println("fileName : "+fileName);
        			int tempIndex = fileName.lastIndexOf("\\");
        			String saveFileName = fileName.substring(tempIndex+1);
        			saveFileName = "E:\\javaee\\000file\\"+saveFileName;
        			System.out.println("saveFileName : "+saveFileName);
        			OutputStream out = new FileOutputStream(saveFileName);
        			while ((len = in.read(buffer)) != -1) {
        				out.write(buffer,0,len);
        			}
        			out.close();
        			in.close();
        		}
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        	return "failure";
        }
		return "success";
	}
}
