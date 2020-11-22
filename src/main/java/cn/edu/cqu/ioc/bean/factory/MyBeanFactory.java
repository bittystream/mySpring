package cn.edu.cqu.ioc.bean.factory;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;

public interface MyBeanFactory {
	// 根据bean的名称从容器中获取bean对象
	Object getBean(String name) throws Exception;
	
	// 将bean注册到容器中
	void registerBeanDefinition(String name, MyBeanDefinition beanDefinition) throws Exception;
}
