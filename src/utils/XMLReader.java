package utils;

import java.io.File;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import dbms.Structure;

public class XMLReader {
	public static Structure getStruct(String engine){
		Document xml = null;
		Structure struct = new Structure();
		engine = "src/dbms/xml/"+engine+".xml";
		SAXBuilder builder = new SAXBuilder();
		try {
			xml = builder.build(new File(engine));
		} catch (Exception e) {
			e.printStackTrace();
			return struct;
		}
		String name = xml.getRootElement().getChild("general").getChild("name").getText();
		String safeCaracter = xml.getRootElement().getChild("general").getChild("safe_caracter").getText();
		String driverPath = xml.getRootElement().getChild("general").getChild("driver_path").getText();
		String connection = xml.getRootElement().getChild("general").getChild("connection_string").getText();
		struct.setGeneral(name, safeCaracter, driverPath, connection);
		for(Object obj : xml.getRootElement().getChild("connection").getChildren()){
			Element element = (Element)obj;
			struct.connection.put(element.getQualifiedName(), element.getText());
		}
		
		for(Object obj : xml.getRootElement().getChild("datatypes").getChildren()){
			Element element = (Element)obj;
			if(element.getAttribute("condition")==null)
				struct.addDataType(element.getQualifiedName(), element.getText());
			else
				struct.addDataType(element.getQualifiedName(), element.getText(), element.getAttributeValue("condition"));
		}
		
		for(Object obj : xml.getRootElement().getChild("dml").getChildren()){
			Element element = (Element)obj;
			struct.dml.put(element.getQualifiedName(),element.getText());
		}
		
		for(Object obj : xml.getRootElement().getChild("query").getChildren()){
			Element element = (Element)obj;
			struct.query.put(element.getQualifiedName(),element.getText());
		}
		
		return struct;
	}
}
