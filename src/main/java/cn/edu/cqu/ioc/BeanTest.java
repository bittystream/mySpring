package cn.edu.cqu.ioc;

import cn.edu.cqu.ioc.context.*;
import cn.edu.cqu.ioc.entity.*;
import cn.edu.cqu.ioc.util.*;

public class BeanTest {

	public static void main(String[] args) throws Exception {
		// �����ļ�ע��
		MyXmlApplicationContext c = new MyXmlApplicationContext("application.xml");
		Student student = (Student)c.getBean("student");
		student.sayHi();
		
//		 ע��ע��
//		MyAnnotationApplicationContext ac = new MyAnnotationApplicationContext("cn.edu.cqu.ioc");
//		Computer c = (Computer)ac.getBean("computer");
//		c.sayHiForOwner();
	}

}
