package org.tried.demo.model.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.tried.demo.model.obj.ColumnInfo;

public class PoJoHbmXml {
	public static void createHbm(Map<String, Object> tableInfo, String tableName) throws Exception {
		File dir = new File((String) Util.getParam().get("resourcesPath") + "/resources/mappings/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String systemName = (String) Util.getParam().get("systemName");
		String packName = tableInfo.get("packName").toString();
		String ModelName = Util.ModelName(tableName);
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		document.addDocType("hibernate-mapping ", "-//Hibernate/Hibernate Mapping DTD 3.0//EN",
				"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
		Element mappElement = document.addElement("hibernate-mapping");
		mappElement.addAttribute("package", "com.tried." + systemName + "." + packName + ".model");
		Element classElement = mappElement.addElement("class");
		classElement.addAttribute("name", ModelName);
		classElement.addAttribute("table", tableName);
		if (tableInfo.get(tableName) != null && !tableInfo.get(tableName).toString().equals(tableName)) {
			classElement.addElement("comment").addText(tableInfo.get(tableName).toString());
		}
		Element idElement = classElement.addElement("id");
		idElement.addAttribute("name", tableInfo.get("PK").toString());
		Element generatorElement = idElement.addElement("generator");
		generatorElement.addAttribute("class", "uuid.hex");
		if (tableInfo.get("column") != null) {
			List<ColumnInfo> columnS = (List<ColumnInfo>) tableInfo.get("column");
			for (int i = 0; i < columnS.size(); i++) {

				String columnName = columnS.get(i).getColumnName();
				if (columnName.equals(tableInfo.get("PK").toString())) {
					continue;
				}
				Element propertyElement = classElement.addElement("property");
				propertyElement.addAttribute("name", Util.firstLower(columnName));
				Element columnElement = propertyElement.addElement("column");
				columnElement.addAttribute("name", columnName);
			}
		}

		OutputFormat outFormat = OutputFormat.createPrettyPrint();
		outFormat.setEncoding("UTF-8");
		outFormat.setTrimText(false);
		XMLWriter writer = new XMLWriter(new FileOutputStream(new File(dir + "/" + ModelName + ".hbm.xml"), false), outFormat);
		writer.write(document);
		writer.close();
		System.out.println("===============表" + tableName + " 生成文件:" + dir + File.separator + ModelName + ".hbm.xml  完毕===============");

	}
}
