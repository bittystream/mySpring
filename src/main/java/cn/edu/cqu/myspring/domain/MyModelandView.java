package cn.edu.cqu.myspring.domain;

import java.util.Map;

public class MyModelandView {
	
	private String viewName;
	private Map<String,?> model;
	
	public MyModelandView(String viewName) {
		this.viewName = viewName;
	}
	
	public MyModelandView(String viewName,Map<String,?> model) {
		this.viewName = viewName;
		this.model = model;
	}
	
	public String getViewName() {
		return viewName;
	}
	
	public Map<String,?> getModel(){
		return model;
	}
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	public void setModel(Map<String,?> model) {
		this.model = model;
	}
}
