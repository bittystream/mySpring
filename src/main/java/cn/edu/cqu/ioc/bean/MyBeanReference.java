package cn.edu.cqu.ioc.bean;

public class MyBeanReference {

	private String name;
	private Object bean;

	public MyBeanReference(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Object getBean() {
		return this.bean;
	}
}