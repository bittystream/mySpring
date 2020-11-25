package cn.edu.cqu.ioc.bean.xml;

import java.io.InputStream;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.cqu.ioc.bean.MyBeanDefinition;
import cn.edu.cqu.ioc.bean.MyBeanReference;
import cn.edu.cqu.ioc.bean.MyProperty;
import cn.edu.cqu.ioc.bean.io.ResourceLoader;

public class MyXmlBeanDefinitionReader extends MyAbstractBeanDefinitionReader {

	public MyXmlBeanDefinitionReader(ResourceLoader loader) {
		super(loader);
	}

	public void readXml(String location) throws Exception {
		// 创建一个资源加载器
		ResourceLoader loader = new ResourceLoader();
		// 从资源加载器中获取输入流
		InputStream inputStream = loader.getResource(location).getInputStream();
		// 获取文档建造者工厂实例
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 工厂创建文档builder
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		// 文档建造者解析流 返回文档对象
		Document doc = docBuilder.parse(inputStream);
		// 根据给定的文档对象进行解析，并注册到bean容器中
		registerBeanDefinition(doc);
		inputStream.close();
	}
	
	private void registerBeanDefinition(Document doc) {
		// 读取文档根元素
		Element root = doc.getDocumentElement();
		// 解析元素根节点及根节点下的所有子节点并添加进注册容器
		parseBeanDefinitions(root);
	}
	
	private void parseBeanDefinitions(Element root) {
		NodeList n = root.getChildNodes();
		for (int i = 0; i < n.getLength(); i++) {
			Node node = n.item(i);
			// 类型判断
			if (node instanceof Element) {
				Element element = (Element) node;
				// 解析给定的node，包括name class property name value reference
				processBeanDefinition(element);
			}
		}
	}
	
	private void processBeanDefinition(Element element) {
		String name = element.getAttribute("name");
		String className = element.getAttribute("class");
		// 创建一个mybean对象
		MyBeanDefinition definition = new MyBeanDefinition();
		// 设置类名
		definition.setClassName(className);
		// 注入成员变量
		addProperties(element,definition);
		// 向注册容器中添加bean名称和bean定义
		getRegistry().put(name, definition);
	}
	
	private void addProperties(Element element, MyBeanDefinition definition) {
		NodeList propertyNode = element.getElementsByTagName("property");
		for (int i = 0; i < propertyNode.getLength(); i++) {
			Node node = propertyNode.item(i);
			if (node instanceof Element) {
				Element propertyElement = (Element) node;
				String name = propertyElement.getAttribute("name");
				String value = propertyElement.getAttribute("value");
				if (value != null && value.length() > 0) {
					definition.getProperties().addProperty(new MyProperty(name,value));
				}
				else {
					String ref = propertyElement.getAttribute("ref");
					if (ref == null || ref.length() == 0) {
						throw new IllegalArgumentException();
					}
					MyBeanReference beanRef = new MyBeanReference(name);
					definition.getProperties().addProperty(new MyProperty(name,beanRef));
				}
			}
		}
	}
	
}
