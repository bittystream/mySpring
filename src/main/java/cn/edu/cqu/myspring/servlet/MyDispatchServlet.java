package cn.edu.cqu.myspring.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
		if (request.getContentType()!=null) {
			System.out.println("there is file");
			try {
				doUpload(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("no file");
			try {
				doDispatch(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private void doDispatch(HttpServletRequest request, HttpServletResponse response)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		url = url.replaceAll(contextPath, "");
		System.out.println("doDispatch: " + url);

		if (!reqMap.containsKey(url)) {
			return;
		}
		String templateRoot = contextConfig.getProperty("template-root");
		String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
		
		Method method = reqMap.get(url);
		String beanName = MyAnnotationUtil.firstLetter2LowerCase(method.getDeclaringClass().getSimpleName());
		System.out.println("doDispatch0: Controller name " + beanName);
		String viewName = (String) method.invoke(iocMap.get(beanName), request, response);
		System.out.println(templateRootPath+viewName);
		MyViewResolver mvr = new MyViewResolver(templateRootPath,viewName);
		MyView mv = mvr.resolveView();
		mv.render(request, response);
	}

	// ��ʼ��
	public void init(ServletConfig servletConfig) {
		System.out.println("start init");
		String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
		doLoadConfig(contextConfigLocation);
		// ��ȡ�����ļ��д�ɨ��İ���
		String packageName = contextConfig.getProperty("scan-package");
		// ��ȡ�������е���
		clsList = MyClassUtil.getAllClassByPackageName(packageName);
		// ��ʼע�룡
		reqMap = MyAnnotationUtil.doReqMapScanner(clsList);
		iocMap = MyAnnotationUtil.doAutoWireScanner(MyAnnotationUtil.doBeanScanner(clsList));
		System.out.println("done init");
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

	private void doUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		List<FileItem> lstForms=upload.parseRequest(request);
		//�������б�Ԫ��
		for (FileItem fileItem : lstForms) {
		//�ж�ÿһ����Ԫ���Ƿ�����ͨ��
			if(fileItem.isFormField()){
				System.out.println(fileItem.getString("UTF-8"));
			}else{
				//�ϴ�ǰ׼������
				//a���ϴ����·��this.getServletContext().getRealPath("/")���ȥ��ǰ��Ŀ�� ����ķ������ϵľ���·��
				String path = "E:/javaee/000file/";
				//b���ҳ�Ҫ�ϴ����ļ�������
				String fileName = fileItem.getName();
				fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
				//c���ϴ�
				fileItem.write(new File(path+fileName));
			}
		}
	}
}
