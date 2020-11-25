package cn.edu.cqu.myspring.util;

import java.io.File;

import cn.edu.cqu.myspring.domain.MyView;

/*
 * SpringMVC���ڴ�����ͼ����Ҫ�������ӿ���ViewResolver��View
 * ViewResolver����Ҫ�����ǰ�һ���߼��ϵ���ͼ���ƽ���Ϊһ����������ͼ
 * SpringMVC�����ڰ�View������ָ��ͻ��˵���View������
 * ��ViewResolverֻ�ǰ��߼���ͼ���ƽ���Ϊ�����View����
 * View�ӿڵ���Ҫ���������ڴ�����ͼ��Ȼ�󷵻ظ��ͻ���
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
