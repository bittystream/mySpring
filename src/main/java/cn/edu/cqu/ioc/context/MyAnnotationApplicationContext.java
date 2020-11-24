package cn.edu.cqu.ioc.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.cqu.ioc.util.*;

// ͨ��ɨ����ȡע���ע�Ķ��� ע��ioc������
public class MyAnnotationApplicationContext implements MyApplicationContext {
	
	private String packageName;
	private Map<String,Object> iocMap;
//	private Map<String,Method> reqMap;
	
	public MyAnnotationApplicationContext(String packageName) {
		this.packageName = packageName;
		doContext();
	}
	
	private void doContext() {
		List<Class<?>> clsList = MyClassUtil.getAllClassByPackageName(packageName);
		iocMap = MyAnnotationUtil.doAutoWireScanner(MyAnnotationUtil.doBeanScanner(clsList));
	}
	

	public Object getBean(String name) throws Exception {
		return iocMap.get(name);
	}
}
