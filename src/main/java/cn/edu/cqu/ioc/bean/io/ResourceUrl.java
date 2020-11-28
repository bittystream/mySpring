package cn.edu.cqu.ioc.bean.io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ResourceUrl implements Resource{
	private final URL url;
	
	public ResourceUrl(URL u) {
		this.url = u;
	}

	public InputStream getInputStream() throws Exception {
		URLConnection urlConnection = url.openConnection();
		urlConnection.connect();
		return urlConnection.getInputStream();
	}
	
	
}
