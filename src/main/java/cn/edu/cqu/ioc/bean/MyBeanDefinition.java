package cn.edu.cqu.ioc.bean;

public class MyBeanDefinition {

	private Class<?> beanClass; 	//那个类
	private Object bean;		//那个类实例化对象，只放一份在ioC容器中
	private String className;
	private MyProperties properties = new MyProperties();

	public MyBeanDefinition() {
		this(null, null);
	}

	MyBeanDefinition(Class<?> beanClass, Object bean) {
		this.beanClass = beanClass;
		this.bean = bean;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public String getClassName() {
		return className;
	}
	
	public void setClassName(String name) {
		this.className = name;
		try {
			this.beanClass = Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public MyProperties getProperties() {
		return properties;
	}
	
	public void setProperties(MyProperties properties) {
		this.properties = properties;
	}
	
}
