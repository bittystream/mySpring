package cn.edu.cqu.ioc;

import cn.edu.cqu.ioc.context.*;
import cn.edu.cqu.ioc.entity.*;
import cn.edu.cqu.ioc.util.*;

public class BeanTest {

	public static void main(String[] args) throws Exception {
		// �����ļ�ע��
//		MyXmlApplicationContext c = new MyXmlApplicationContext("app.xml");
//		Student student = (Student)c.getBean("student000");
//		student.sayHi();
		
		// ע��ע��
//		MyAnnotationApplicationContext ac = new MyAnnotationApplicationContext("ioc");
//		Computer c = (Computer)ac.getBean("computer");
//		c.sayHiForOwner();
		String url = "/query/K001";
		int index = url.lastIndexOf("/");
		System.out.println(index);
		System.out.println(url.substring(0,index));
		System.out.println(url.substring(index+1));
	}

}
