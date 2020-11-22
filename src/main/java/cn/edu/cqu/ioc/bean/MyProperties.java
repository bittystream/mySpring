package cn.edu.cqu.ioc.bean;

import java.util.ArrayList;
import java.util.List;

public class MyProperties {

	private final List<MyProperty> properties = new ArrayList<MyProperty>();
	
	public MyProperties() {}

	public void addProperty(MyProperty p) {
		properties.add(p);
	}

	public List<MyProperty> getProperties() {
		return properties;
	}

}