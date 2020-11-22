package cn.edu.cqu.ioc.bean.xml;

import java.util.HashMap;
import java.util.Map;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;
import cn.edu.cqu.ioc.bean.io.ResourceLoader;

public abstract class MyAbstractBeanDefinitionReader implements MyBeanDefinitionReader{

	// 注册bean容器
	private Map<String,MyBeanDefinition> registry;
	
	// 资源加载器
	private ResourceLoader resourceLoader;
	
	MyAbstractBeanDefinitionReader(ResourceLoader loader){
		this.registry = new HashMap<String,MyBeanDefinition>();
		this.resourceLoader = loader;
	}
	
	// 获取注册容器
	public Map<String,MyBeanDefinition> getRegistry(){
		return registry;
	}
	
	// 获取资源加载器
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
