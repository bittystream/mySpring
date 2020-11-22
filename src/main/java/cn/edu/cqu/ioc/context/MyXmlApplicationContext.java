package cn.edu.cqu.ioc.context;

import java.util.Map;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;
import cn.edu.cqu.ioc.bean.factory.MyAutowireBeanFactory;
import cn.edu.cqu.ioc.bean.factory.MyBeanFactory;
import cn.edu.cqu.ioc.bean.io.ResourceLoader;
import cn.edu.cqu.ioc.bean.xml.MyXmlBeanDefinitionReader;

public class MyXmlApplicationContext implements MyApplicationContext{

	private String location;
	private MyBeanFactory beanFactory;
	
	public MyXmlApplicationContext(String location) throws Exception {
		this(location, new MyAutowireBeanFactory());
	}
	
	public MyXmlApplicationContext(String location, MyBeanFactory beanFactory) throws Exception {
		this.location = location;
		this.beanFactory = beanFactory;
		refresh();
	}
	
	private void refresh() throws Exception {
		MyXmlBeanDefinitionReader xml = new MyXmlBeanDefinitionReader(new ResourceLoader());
		xml.readXml(location);
	    // ѭ��xml�е�����bean
	    for (Map.Entry<String, MyBeanDefinition> beanDefinitionEntry : xml.getRegistry().entrySet()) {
	      // ��XML�����е�beanע�ᵽbean����
	      beanFactory
	          .registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
	    }
	}
	
	
	public Object getBean(String name) throws Exception {
		return beanFactory.getBean(name);
	}

}
