package cn.edu.cqu.ioc.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import cn.edu.cqu.annotation.MyAutoWired;

public class MyReflectUtil {
    //传入对象
	public static boolean setObjByFieldAnno(Object o){
		// 获取Class
		Class<?> c = o.getClass();
		// 获取域
		Field [] fields = c.getDeclaredFields();
		// 返回值用于判断是否完成这个方法
		boolean b = false;
		// 遍历域
		for (Field f : fields) {
			// 获取域中的注解，遍历注解
			Annotation [] anns = f.getAnnotations();
			for (Annotation ann : anns) {
				// 这里使用instanceof关键字，判断注解中是否包含MyAutowired
				if (ann instanceof MyAutoWired){
					// 转成Class
					Class<?> c2 = (Class<?>) f.getGenericType();
					try {// 创建实例
						Object o2 = c2.getDeclaredConstructor().newInstance();
						// 将实例注入到这个域中
						f.set(o,o2);
						// 成功则返回true
						b = true;
						return b;
					} catch (Exception e) {
					e.printStackTrace();
					}
				}

			}
		}
		return b;
	}

}
