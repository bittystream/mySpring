package cn.edu.cqu.ioc.bean.io;

import java.io.InputStream;

public interface Resource{
	InputStream getInputStream() throws Exception;
}
