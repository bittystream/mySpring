package cn.edu.cqu.demo.service;

import java.util.Map;

import cn.edu.cqu.demo.domain.Student;
import cn.edu.cqu.ioc.annotation.MyAutoWired;
import cn.edu.cqu.ioc.annotation.MyService;


@MyService
public class StudentQueryService {
	
	@MyAutoWired("iocMap")
	public Map<String,Object> iocMap;

	public Student queryById(String id) {
		for (Object o : iocMap.values()) {
			if (o instanceof Student) {
				Student student = (Student)o;
				System.out.println(student.getId());
				if (student.getId().equals(id)) {
					return student;
				}
			}
		}
		return null;
	}
	
}
