package com.ixinnuo.financial.knowledge.xml;

import java.io.File;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class XmlValidator {

	public static final String W3C_XML_SCHEMA_INSTANCE_NS_URI = "http://www.w3.org/2001/XMLSchema-instance";
	public static final String W3C_XML_SCHEMA_NS_URI = "http://www.w3.org/2001/XMLSchema";

	public static void main(String[] args) {
		String xsdPath = "src/main/java/com/ixinnuo/financial/knowledge/xml/my.xsd";
		String xmlPath = "src/main/java/com/ixinnuo/financial/knowledge/xml/my.xml";
		String result = validateXMLSchema(xsdPath, xmlPath);
		System.out.println(result.equals("") == true ? "通过" : "未通过");
	}

	/**
	 * 使用xsd验证xml文件
	 * 
	 * @param xsdPath
	 *            xsd文件路径
	 * @param xmlPath
	 *            xml文件路径
	 * @return 错误信息，空字符串表示通过
	 */
	public static String validateXMLSchema(String xsdPath, String xmlPath) {
		String result = "";
		try {
			SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
			File file = new File(xsdPath);
			Schema schema = factory.newSchema(file);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		}
		return result;
	}
}
