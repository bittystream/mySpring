package cn.edu.cqu.ioc.bean.xml;

import java.util.HashMap;
import java.util.Map;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;
import cn.edu.cqu.ioc.bean.io.ResourceLoader;

public abstract class MyAbstractBeanDefinitionReader implements MyBeanDefinitionReader{

	// ע��bean����
	private Map<String,MyBeanDefinition> registry;
	
	// ��Դ������
	private ResourceLoader resourceLoader;
	
	MyAbstractBeanDefinitionReader(ResourceLoader loader){
		this.registry = new HashMap<String,MyBeanDefinition>();
		this.resourceLoader = loader;
	}
	
	// ��ȡע������
	public Map<String,MyBeanDefinition> getRegistry(){
		return registry;
	}
	
	// ��ȡ��Դ������
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
