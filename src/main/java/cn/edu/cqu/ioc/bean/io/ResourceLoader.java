package cn.edu.cqu.ioc.bean.io;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ResourceLoader {
	
	// 给定一个位置，使用累加器的资源加载url，并创建一个资源url对象，便于获取输入流
	public ResourceUrl getResource(String location){
		URL url = this.getClass().getClassLoader().getResource(location);
		return new ResourceUrl(url);
	}

}
