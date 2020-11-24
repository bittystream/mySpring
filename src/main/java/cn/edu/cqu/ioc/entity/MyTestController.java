package cn.edu.cqu.ioc.entity;

import cn.edu.cqu.ioc.annotation.*;

@MyController
@MyRequestMapping
public class MyTestController {
	
	@MyRequestMapping("/gogogo")
	public void test() {
		System.out.println("test : gogogo");
	}
}
