package cn.edu.cqu.myspring.servlet;

import java.io.IOException;
import java.io.InputStream;
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

import cn.edu.cqu.ioc.util.*;

/**
 * Servlet implementation class MyDispatchServlet
 */
public class MyDispatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// ioc containers
	private Map<String, Object> iocMap = new HashMap<String, Object>();
	private Map<String,Method> reqMap = new HashMap<String,Method>();
	private List<Class<?>> clsList = new ArrayList<Class<?>>(); 
	
	private Properties contextConfig = new Properties();

	
    public MyDispatchServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	// ��ʼ��
	public void init(ServletConfig servletConfig) {		
		String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
		doLoadConfig(contextConfigLocation);
		// ��ȡ�����ļ��д�ɨ��İ���
		String packageName = contextConfig.getProperty("scan-package");
		// ��ȡ�������е���
		clsList = MyClassUtil.getAllClassByPackageName(packageName);
		// ��ʼע�룡
		reqMap = MyAnnotationUtil.doReqMapScanner(clsList);
		iocMap = MyAnnotationUtil.doAutoWireScanner(MyAnnotationUtil.doBeanScanner(clsList));
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
