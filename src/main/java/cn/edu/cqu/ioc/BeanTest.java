package cn.edu.cqu.ioc;

import cn.edu.cqu.ioc.context.*;
import cn.edu.cqu.ioc.entity.*;
import cn.edu.cqu.ioc.util.*;

public class BeanTest {

	public static void main(String[] args) throws Exception {
		// 配置文件注入
		MyXmlApplicationContext c = new MyXmlApplicationContext("app.xml");
		Student student = (Student)c.getBean("student000");
		student.sayHi();
		
		// 注解注入
//		MyAnnotationApplicationContext ac = new MyAnnotationApplicationContext("ioc");
//		Computer c = (Computer)ac.getBean("computer");
//		c.sayHiForOwner();
	
	}

}
