package cn.edu.cqu.myspring.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.cqu.ioc.util.*;
import cn.edu.cqu.myspring.domain.MyView;
import cn.edu.cqu.myspring.util.MyViewResolver;

/**
 * Servlet implementation class MyDispatchServlet
 */
public class MyDispatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// ioc containers
	private Map<String, Object> iocMap = new HashMap<String, Object>();
	private Map<String, Method> reqMap = new HashMap<String, Method>();
	private List<Class<?>> clsList = new ArrayList<Class<?>>();

	private Properties contextConfig = new Properties();

	public MyDispatchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doDispatch(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doDispatch(HttpServletRequest request, HttpServletResponse response)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		url = url.replaceAll(contextPath, "");
		System.out.println("doDispatch: " + url);
		String param = "";
		boolean hasParam = false;
		int index = url.lastIndexOf("/");
		if (index > 0) {// 有参数
			hasParam = true;
			param = url.substring(index + 1);
			url = url.substring(0, index);
		}

		if (!reqMap.containsKey(url)) {
			return;
		}
		String templateRoot = contextConfig.getProperty("template-root");
		String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

		Method method = reqMap.get(url);
		String beanName = MyAnnotationUtil.firstLetter2LowerCase(method.getDeclaringClass().getSimpleName());
		System.out.println("doDispatch0: Controller name " + beanName);
		System.out.println("method name = " + method.getName());

		if (!hasParam) {
			if (method.invoke(iocMap.get(beanName), request, response) == null)
				return;
			String viewName = (String) method.invoke(iocMap.get(beanName), request, response);
			System.out.println(templateRootPath + viewName);
			MyViewResolver mvr = new MyViewResolver(templateRootPath, viewName);
			MyView mv = mvr.resolveView();
			mv.render(request, response);
		} else {
			method.invoke(iocMap.get(beanName), request, response, param);
		}

	}

	// 初始化
	public void init(ServletConfig servletConfig) throws ServletException {
		// 很重要!!!!!
		super.init(servletConfig);
		
		System.out.println("start init");
		String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
		doLoadConfig(contextConfigLocation);
		// 获取配置文件中待扫描的包名
		String packageName = contextConfig.getProperty("scan-package");
		// 获取包下所有的类
		clsList = MyClassUtil.getAllClassByPackageName(packageName);
		// 开始注入！
		reqMap = MyAnnotationUtil.doReqMapScanner(clsList);
		iocMap = MyAnnotationUtil.doBeanScanner(clsList);
		iocMap.put("iocMap", iocMap);
		iocMap = MyAnnotationUtil.doAutoWireScanner(iocMap);
		iocMap.put("iocMap", iocMap);
		
		// 把上传文件的路径存入servletContext
		String fileSaveDir = contextConfig.getProperty("file-save-dir");
		getServletContext().setAttribute("fileSaveDir", fileSaveDir);
		System.out.println("in dispatcher: file save dir = "+fileSaveDir);
		System.out.println("done init");
//		
	}

	private void doLoadConfig(String contextConfigLocation) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
		try {
			contextConfig.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
