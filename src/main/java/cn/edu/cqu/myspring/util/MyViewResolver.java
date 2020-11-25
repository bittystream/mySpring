package cn.edu.cqu.myspring.util;

import java.io.File;

import cn.edu.cqu.myspring.domain.MyView;

/*
 * SpringMVC用于处理视图最重要的两个接口是ViewResolver和View
 * ViewResolver的主要作用是把一个逻辑上的视图名称解析为一个真正的视图
 * SpringMVC中用于把View对象呈现给客户端的是View对象本身
 * 而ViewResolver只是把逻辑视图名称解析为对象的View对象
 * View接口的主要作用是用于处理视图，然后返回给客户端
 * 
 * */
public class MyViewResolver {
	public static final String SUFFIX = ".jsp";
	private String viewPath,templateRootPath;
	
	public MyViewResolver(String templateRootPath,String viewName) {
		this.templateRootPath = templateRootPath;
		this.viewPath = this.templateRootPath + viewName + SUFFIX;
	}
	
	public MyView resolveView() {
		if (viewPath == null || viewPath.equals("")) {
			return null;
		}
		File file = new File(viewPath);
		return new MyView(file);
	}

}
