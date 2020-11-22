package cn.edu.cqu.ioc.bean.factory;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;

public interface MyBeanFactory {
	// ����bean�����ƴ������л�ȡbean����
	Object getBean(String name) throws Exception;
	
	// ��beanע�ᵽ������
	void registerBeanDefinition(String name, MyBeanDefinition beanDefinition) throws Exception;
}
