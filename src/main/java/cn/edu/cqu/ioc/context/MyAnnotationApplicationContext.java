package cn.edu.cqu.ioc.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.cqu.ioc.util.*;

// 通过扫包获取注解标注的对象 注入ioc容器中
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
