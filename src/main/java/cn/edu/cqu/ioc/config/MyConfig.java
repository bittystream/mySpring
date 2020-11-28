package cn.edu.cqu.ioc.config;

import cn.edu.cqu.ioc.annotation.*;
import cn.edu.cqu.ioc.entity.*;

@MyConfiguration
public class MyConfig {
	
	@MyBean("student101")
	public Student student() {
		return new Student("20200000","Bill");
	}
	
	@MyBean
	public Computer computer() {
		return new Computer("ok");
	}
	
}
