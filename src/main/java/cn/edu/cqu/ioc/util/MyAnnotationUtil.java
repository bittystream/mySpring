package cn.edu.cqu.ioc.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.cqu.ioc.entity.Computer;
import cn.edu.cqu.ioc.entity.Student;
import cn.edu.cqu.ioc.util.*;
import cn.edu.cqu.ioc.annotation.*;
 
public class MyAnnotationUtil {
	public static Map<String,Method> reqMap = new HashMap<String,Method>();
	public static Map<String,Object> iocMap = new HashMap<String,Object>();
	
	public static void doReqMapScanner(List<Class<?>> clsList) {
		if (clsList != null && clsList.size() > 0) {
			for (Class<?> cls : clsList) {
				// ���@MyRequestMapping��ǵ���
				if (cls.isAnnotationPresent(MyController.class)) {
					Method [] methods = cls.getDeclaredMethods();
					for (Method method : methods) {
						MyRequestMapping myRequestMapping = method.getAnnotation(MyRequestMapping.class);
						if (myRequestMapping != null) {
							// TODO: ��ȡӳ���ϵ������reqMap
						}
					}
				}
			}
		}
	}
	
	
	public static void doAutoWireScanner(){
		if (iocMap == null) return;
		for (Map.Entry<String,Object> entry : iocMap.entrySet()) {
			Field [] fields = entry.getValue().getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(MyAutoWired.class)) {
					String beanName = field.getType().getName();
					field.setAccessible(true);
					try {
						field.set(entry.getValue(),iocMap.get(beanName));
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
	}
	
	
	public static void doBeanScanner(List<Class<?>> clsList){
		if (clsList != null && clsList.size() > 0) {
			for (Class<?> cls : clsList) {
				// ���@MyConfiguration��ǵ���
				if (cls.isAnnotationPresent(MyConfiguration.class)) {
//					System.out.println(cls.getName());
					Method [] methods = cls.getDeclaredMethods();
					for (Method method : methods) {
//						System.out.println(method.getName());
						// ��ȡ���б�ע��@MyBean�ĳ�Ա
						if (method.isAnnotationPresent(MyBean.class)) {
							String beanName = method.getName();
							MyBean myBean = method.getAnnotation(MyBean.class);
							if (!myBean.value().equals("")) beanName = myBean.value();
							try {
								// Ĭ�ϱ�@MyBean��ǵķ����޲���!!!
								Object bean = method.invoke(Class.forName(cls.getName()).getDeclaredConstructor().newInstance());
								iocMap.put(beanName, bean);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				// ���@MyController��ǵ���, ��Ϊ��һ����ĸ��Сд
				else if (cls.isAnnotationPresent(MyController.class)) {
					String beanName = firstLetter2LowerCase(cls.getSimpleName());
					Object bean;
					try {
						bean = Class.forName(cls.getSimpleName()).getDeclaredConstructor().newInstance();
						iocMap.put(beanName,bean);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// ���@MyService��ǵ���(DAO), ���ܺ����Զ����value
				else if (cls.isAnnotationPresent(MyService.class)) {
					MyService myService = cls.getAnnotation(MyService.class);
					String beanName = firstLetter2LowerCase(cls.getSimpleName());
					if (!myService.value().equals("")) beanName = myService.value();
					Object bean;
					try {
						bean = Class.forName(cls.getSimpleName()).getDeclaredConstructor().newInstance();
						iocMap.put(beanName, bean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	}
	
	static String firstLetter2LowerCase(String name) {
		char [] charArr = name.toCharArray();
		charArr[0] += 32;
		return String.valueOf(charArr);
	}
	
	
	public static void main(String [] args) {
		List<Class<?>> clsList = MyClassUtil.getAllClassByPackageName("cn.edu.cqu");
		MyAnnotationUtil.doBeanScanner(clsList);
		Student student101 = (Student) MyAnnotationUtil.iocMap.get("student101");
		student101.sayHi();
		Computer computer = (Computer) MyAnnotationUtil.iocMap.get("computer");
		computer.sayHiForOwner();
	}
 
}
