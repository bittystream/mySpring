package cn.edu.cqu.ioc;

import cn.edu.cqu.ioc.context.MyXmlApplicationContext;
import cn.edu.cqu.ioc.entity.*;

public class BeanTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		MyXmlApplicationContext c = new MyXmlApplicationContext("app.xml");
		Student student = (Student)c.getBean("student");
		student.sayHi();
//		Computer c = new Computer();
//		boolean bool = MyReflectUtil.setObjByFieldAnno(c);
//		if (bool) {
//			c.sayHiForOwner();
//		}
//		else {
//			System.out.println("failed!");
//		}
	}

}
