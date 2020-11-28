package cn.edu.cqu.ioc.bean.io;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ResourceLoader {
	
	// ����һ��λ�ã�ʹ���ۼ�������Դ����url��������һ����Դurl���󣬱��ڻ�ȡ������
	public ResourceUrl getResource(String location){
		URL url = this.getClass().getClassLoader().getResource(location);
		return new ResourceUrl(url);
	}

}
