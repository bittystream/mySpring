package cn.edu.cqu.ioc.bean;

public class MyProperty {

	private final String name;
	private final Object value;

	public MyProperty(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getname() {
		return this.name;
	}

	public Object getvalue() {
		return this.value;
	}

}
