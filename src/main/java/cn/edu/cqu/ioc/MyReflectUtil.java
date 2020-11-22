package cn.edu.cqu.ioc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import cn.edu.cqu.ioc.annotation.MyAutoWired;

public class MyReflectUtil {
    //�������
	public static boolean setObjByFieldAnno(Object o){
		//��ȡClass
		Class<?> c=o.getClass();
		/*�����*/
		Field []fields=c.getDeclaredFields();
		//����ֵ�����ж��Ƿ�����������
		boolean b=false;
		//������
		for (Field f:fields) {
			//��ȡ���е�ע�⣬����ע��
			Annotation []anns=f.getAnnotations();
			for (Annotation ann:anns) {
				//����ʹ��instanceof�ؼ��֣��ж�ע�����Ƿ����MyAutowired
				if (ann instanceof MyAutoWired){
					System.out.println(f.getName()+"--�����ʹ�����ҵ�ע��");
					//f.getGenericType()����ȡ���������
					System.out.println(f.getGenericType().toString()+"--����������");
					//ת��Class
					Class<?> c2= (Class<?>) f.getGenericType();
					try {//����ʵ��
						Object o2= c2.getDeclaredConstructor().newInstance();
						//��������ǽ�ʵ��ע�뵽������У���ϸʹ�������JAVA API
						f.set(o,o2);
						System.out.println("�ɹ�ע��");
						//�ɹ��򷵻�true
						b=true;
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
