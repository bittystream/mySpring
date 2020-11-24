package cn.edu.cqu.ioc.config;

import cn.edu.cqu.ioc.annotation.*;
import cn.edu.cqu.ioc.entity.*;

@MyConfiguration
public class MyConfig {
	
	@MyBean("student101")
	public Student student() {
		return new Student("20188888","orz");
	}
	
	@MyBean
	public Computer computer() {
		return new Computer("ok");
	}
	
}
