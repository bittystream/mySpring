package cn.edu.cqu.ioc.bean.factory;

import java.lang.reflect.Field;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;
import cn.edu.cqu.ioc.bean.MyBeanReference;
import cn.edu.cqu.ioc.bean.MyProperty;

public class MyAutowireBeanFactory extends MyAbstractBeanFactory{

	@Override
	Object doCreate(MyBeanDefinition definition) throws Exception {
//		�ѱ����õķ�����
//		Object bean = definition.getBeanClass().newInstance();
		Object bean = Class.forName(definition.getClassName()).getDeclaredConstructor().newInstance();
		addProperty(bean,definition);
		return bean;
	}

	// ����һ��Bean��һ��BeanDefinition��Ϊ������bean�е�����ע��ʵ��
	private void addProperty(Object bean, MyBeanDefinition definition) throws Exception {
		for (MyProperty property : definition.getProperties().getProperties()) {
			// ���ݸ����������ƻ�ȡ����bean�е����Զ���
			Field declaredField = bean.getClass().getDeclaredField(property.getname());
			// �������Է��ʵ�Ȩ��
			declaredField.setAccessible(true);
			// ��ȡ����������еĶ���
			Object value = property.getvalue();
			// �ж���������Ƿ���MyBeanReference
			if (value instanceof MyBeanReference) {
				MyBeanReference beanReference = (MyBeanReference) value;
				value = getBean(beanReference.getName());
			}
			
			// ��������bean������
			declaredField.set(bean, value);
		}
	}

}
