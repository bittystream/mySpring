package cn.edu.cqu.ioc.bean.factory;

import java.lang.reflect.Field;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;
import cn.edu.cqu.ioc.bean.MyBeanReference;
import cn.edu.cqu.ioc.bean.MyProperty;

public class MyAutowireBeanFactory extends MyAbstractBeanFactory{

	@Override
	Object doCreate(MyBeanDefinition definition) throws Exception {
//		已被弃用的方法：
//		Object bean = definition.getBeanClass().newInstance();
		Object bean = Class.forName(definition.getClassName()).getDeclaredConstructor().newInstance();
		addProperty(bean,definition);
		return bean;
	}

	// 给定一个Bean和一个BeanDefinition，为给定的bean中的属性注入实例
	private void addProperty(Object bean, MyBeanDefinition definition) throws Exception {
		for (MyProperty property : definition.getProperties().getProperties()) {
			// 根据给定属性名称获取给定bean中的属性对象
			Field declaredField = bean.getClass().getDeclaredField(property.getname());
			// 设置属性访问的权限
			declaredField.setAccessible(true);
			// 获取定义的属性中的对象
			Object value = property.getvalue();
			// 判断这个对象是否是MyBeanReference
			if (value instanceof MyBeanReference) {
				MyBeanReference beanReference = (MyBeanReference) value;
				value = getBean(beanReference.getName());
			}
			
			// 反射诸如bean的属性
			declaredField.set(bean, value);
		}
	}

}
