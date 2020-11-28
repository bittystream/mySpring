package cn.edu.cqu.ioc.util;


import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.cqu.ioc.entity.Computer;
import cn.edu.cqu.ioc.entity.Student;
import cn.edu.cqu.ioc.util.*;
import cn.edu.cqu.ioc.annotation.*;
 
public class MyAnnotationUtil {
	public static Map<String,Method> doReqMapScanner(List<Class<?>> clsList) {
		Map<String,Method> reqMap = new HashMap<String, Method>();
		if (clsList != null && clsList.size() > 0) {
			for (Class<?> cls : clsList) {
				String baseUrl = "";
				// 获得@MyRequestMapping标记的类
				if (cls.isAnnotationPresent(MyRequestMapping.class)) {
					MyRequestMapping mrp = cls.getAnnotation(MyRequestMapping.class);
					baseUrl = mrp.value();
				}
				for (Method method: cls.getMethods()) {
					if (method.isAnnotationPresent(MyRequestMapping.class)) {
						MyRequestMapping mrp = method.getAnnotation(MyRequestMapping.class);
						String methodUrl = mrp.value();
						int index = methodUrl.lastIndexOf("/");
						if (index > 0) {
							methodUrl = methodUrl.substring(0, index);
						}
						String url = baseUrl + methodUrl;
						System.out.println(url+" method="+method.getName());
						reqMap.put(url, method);
					}
				}
			}
		}
		return reqMap;
	}
	
	// 处理@MyAutoWired
	public static Map<String,Object> doAutoWireScanner(Map<String,Object> iocMap){
		if (iocMap == null) return null;
		for (Map.Entry<String,Object> entry : iocMap.entrySet()) {
			Field [] fields = entry.getValue().getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(MyAutoWired.class)) {
					MyAutoWired myAutoWired = field.getAnnotation(MyAutoWired.class);
					String beanName = field.getType().getName();
					if (!myAutoWired.value().equals("")) beanName = myAutoWired.value();
					field.setAccessible(true);
					System.out.println("Autowire: "+entry.getValue()+" into "+iocMap.get(beanName));
					try {
						field.set(entry.getValue(),iocMap.get(beanName));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return iocMap;
	}
	
	
	public static Map<String,Object> doBeanScanner(List<Class<?>> clsList){
		Map<String,Object> iocMap = new HashMap<String,Object>();
		if (clsList != null && clsList.size() > 0) {
			for (Class<?> cls : clsList) {
				// 获得@MyConfiguration标记的类
				if (cls.isAnnotationPresent(MyConfiguration.class)) {
//					System.out.println(cls.getName());
					Method [] methods = cls.getDeclaredMethods();
					for (Method method : methods) {
//						System.out.println(method.getName());
						// 获取所有标注了@MyBean的成员
						if (method.isAnnotationPresent(MyBean.class)) {
							String beanName = method.getName();
							MyBean myBean = method.getAnnotation(MyBean.class);
							if (!myBean.value().equals("")) beanName = myBean.value();
							try {
								// 默认被@MyBean标记的方法无参数!!!
								Object bean = method.invoke(Class.forName(cls.getName()).getDeclaredConstructor().newInstance());
								iocMap.put(beanName, bean);
								System.out.println("Bean: "+beanName+"   "+bean.getClass().getName());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				// 获得@MyController标记的类, 名为第一个字母变小写
				else if (cls.isAnnotationPresent(MyController.class)) {
					String beanName = firstLetter2LowerCase(cls.getSimpleName());
//					System.out.println(beanName);
					Object bean;
					try {
						// 注意! 是getName(),需要完整的类名,而不能用getSimpleName()
						bean = Class.forName(cls.getName()).getConstructor().newInstance();
						iocMap.put(beanName,bean);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// 获得@MyService标记的类(DAO), 可能含有自定义的value
				else if (cls.isAnnotationPresent(MyService.class)) {
					MyService myService = cls.getAnnotation(MyService.class);
					String beanName = firstLetter2LowerCase(cls.getSimpleName());
					if (!myService.value().equals("")) beanName = myService.value();
					Object bean;
					try {
						bean = Class.forName(cls.getName()).getConstructor().newInstance();
						iocMap.put(beanName, bean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		return iocMap;
	}
	
	public static Map<String,List<String>> doParamScanner(List<Class<?>> clsList){
		Map<String,List<String>> paramMap = new HashMap<String, List<String>>();
		if (clsList != null && clsList.size() > 0) {
			for (Class<?> cls : clsList) {
				if (!cls.isAnnotationPresent(MyController.class)) {
					return null;
				}
				for (Method method : cls.getMethods()) {
					if (!method.isAnnotationPresent(MyRequestMapping.class)) {
						return null;
					}
					String methodName = method.getName();
					List<String> params = new ArrayList<String>();
					for (Parameter param : method.getParameters()) {
						if (param.getAnnotations().length > 0) {
							// is annotated with @MyRequestParam
							// 默认参数名跟value是一样的
							String paramName = param.getName();
							params.add(paramName);
							System.out.println(methodName + "   " + paramName);
						}
					}
					paramMap.put(methodName, params);
				}
			}
		}
		
		return paramMap;
	}
	
	public static String firstLetter2LowerCase(String name) {
		char [] charArr = name.toCharArray();
		charArr[0] += 32;
		return String.valueOf(charArr);
	}
	
	
	public static void main(String [] args) {
		List<Class<?>> clsList = MyClassUtil.getAllClassByPackageName("cn.edu.cqu");
		Map<String, Object> rm = MyAnnotationUtil.doBeanScanner(clsList);
	}
 
}
