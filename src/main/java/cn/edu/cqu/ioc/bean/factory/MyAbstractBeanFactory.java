package cn.edu.cqu.ioc.bean.factory;

import java.util.HashMap;
import java.util.Map;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;

public abstract class MyAbstractBeanFactory implements MyBeanFactory{
	
	// 容器!!!
	private Map<String, MyBeanDefinition> myIOC = new HashMap<String,MyBeanDefinition>();
	
	// 根据bean的名称从容器中获取bean，如果找不到则抛出异常，找到了则返回bean实例
	public Object getBean(String name) throws Exception {
		MyBeanDefinition definition = myIOC.get(name);
		if (definition == null) {
			throw new IllegalArgumentException("Bean named "+name+" is not defined");
		}
		Object bean = definition.getBean();
		if (bean == null) {
			bean = doCreate(definition);
		}
		return bean;
	}

	public void registerBeanDefinition(String name, MyBeanDefinition beanDefinition) throws Exception {
		Object bean = doCreate(beanDefinition);
		beanDefinition.setBean(bean);
		myIOC.put(name, beanDefinition);
	}

	abstract Object doCreate(MyBeanDefinition definition) throws Exception;

}
