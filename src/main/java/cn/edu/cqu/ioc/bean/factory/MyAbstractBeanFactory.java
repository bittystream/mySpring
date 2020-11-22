package cn.edu.cqu.ioc.bean.factory;

import java.util.HashMap;
import java.util.Map;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;

public abstract class MyAbstractBeanFactory implements MyBeanFactory{
	
	// ����!!!
	private Map<String, MyBeanDefinition> myIOC = new HashMap<String,MyBeanDefinition>();
	
	// ����bean�����ƴ������л�ȡbean������Ҳ������׳��쳣���ҵ����򷵻�beanʵ��
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
