package cn.edu.cqu.ioc.context;

public interface MyApplicationContext {
	public Object getBean(String name) throws Exception;
}
