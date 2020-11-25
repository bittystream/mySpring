package cn.edu.cqu.myspring.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyView {
	private File viewFile;
	public MyView(File file) {
		viewFile = file;
	}
	
	public void render(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		FileInputStream fis = new FileInputStream(viewFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		StringBuffer sb = new StringBuffer();
		boolean jFlag = false;
		// 读取jsp文件 在页面显示出来
		while ((line = br.readLine()) != null) {
			if (!jFlag && line.startsWith("<%")) {
				jFlag = true;
				continue;
			}
			if (jFlag && line.endsWith("%>")) {
				jFlag = false;
				continue;
			}
			sb.append(line);
		}
		br.close();
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(sb.toString());
	}
	
}
