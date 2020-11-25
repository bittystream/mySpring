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
		// ����һ����Դ������
		ResourceLoader loader = new ResourceLoader();
		// ����Դ�������л�ȡ������
		InputStream inputStream = loader.getResource(location).getInputStream();
		// ��ȡ�ĵ������߹���ʵ��
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// ���������ĵ�builder
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		// �ĵ������߽����� �����ĵ�����
		Document doc = docBuilder.parse(inputStream);
		// ���ݸ������ĵ�������н�������ע�ᵽbean������
		registerBeanDefinition(doc);
		inputStream.close();
	}
	
	private void registerBeanDefinition(Document doc) {
		// ��ȡ�ĵ���Ԫ��
		Element root = doc.getDocumentElement();
		// ����Ԫ�ظ��ڵ㼰���ڵ��µ������ӽڵ㲢��ӽ�ע������
		parseBeanDefinitions(root);
	}
	
	private void parseBeanDefinitions(Element root) {
		NodeList n = root.getChildNodes();
		for (int i = 0; i < n.getLength(); i++) {
			Node node = n.item(i);
			// �����ж�
			if (node instanceof Element) {
				Element element = (Element) node;
				// ����������node������name class property name value reference
				processBeanDefinition(element);
			}
		}
	}
	
	private void processBeanDefinition(Element element) {
		String name = element.getAttribute("name");
		String className = element.getAttribute("class");
		// ����һ��mybean����
		MyBeanDefinition definition = new MyBeanDefinition();
		// ��������
		definition.setClassName(className);
		// ע���Ա����
		addProperties(element,definition);
		// ��ע�����������bean���ƺ�bean����
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
