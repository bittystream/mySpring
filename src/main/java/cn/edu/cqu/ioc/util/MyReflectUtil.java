package cn.edu.cqu.ioc.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import cn.edu.cqu.annotation.MyAutoWired;

public class MyReflectUtil {
    //�������
	public static boolean setObjByFieldAnno(Object o){
		// ��ȡClass
		Class<?> c = o.getClass();
		// ��ȡ��
		Field [] fields = c.getDeclaredFields();
		// ����ֵ�����ж��Ƿ�����������
		boolean b = false;
		// ������
		for (Field f : fields) {
			// ��ȡ���е�ע�⣬����ע��
			Annotation [] anns = f.getAnnotations();
			for (Annotation ann : anns) {
				// ����ʹ��instanceof�ؼ��֣��ж�ע�����Ƿ����MyAutowired
				if (ann instanceof MyAutoWired){
					// ת��Class
					Class<?> c2 = (Class<?>) f.getGenericType();
					try {// ����ʵ��
						Object o2 = c2.getDeclaredConstructor().newInstance();
						// ��ʵ��ע�뵽�������
						f.set(o,o2);
						// �ɹ��򷵻�true
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
