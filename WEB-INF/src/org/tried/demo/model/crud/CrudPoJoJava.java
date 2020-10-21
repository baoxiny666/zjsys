package org.tried.demo.model.crud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import org.tried.demo.model.obj.ColumnInfo;
import org.tried.demo.model.util.Util;

import com.tried.system.model.SystemTableModel;

public class CrudPoJoJava {
	
	public static void createPoJo(Map<String, Object> tableInfo, String tableName) throws Exception {
		
		
		String systemName = (String) Util.getParam().get("systemName");
		String packName=tableInfo.get("packName").toString();
		String ModelName = Util.ModelName(tableName);
		String modelPath="com.tried." + systemName + "." + packName + ".model";
		
		File dir = new File((String)Util.getParam().get("resourcesPath") + "/src/"+modelPath.replaceAll("\\.", "/")+"/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		 OutputStreamWriter pojoWriter = new OutputStreamWriter(new FileOutputStream(dir + "/" + ModelName + ".java",false),"UTF-8");
		
		
		pojoWriter.write("package com.tried." + systemName + "." + packName + ".model;\r\n");
		pojoWriter.write("import java.io.Serializable;\r\n");
		if (!tableInfo.get(tableName).equals(tableName)) {
			pojoWriter.write("/**\r\n");
			pojoWriter.write("* @Description " + tableInfo.get(tableName) + "\r\n");
			pojoWriter.write("* @author " + (String) Util.getParam().get("author") + "\r\n");
			pojoWriter.write("* @date " + Util.currentTime() + "\r\n");
			pojoWriter.write("* @version V1.0\r\n");
			pojoWriter.write("*/\r\n");
		}
		pojoWriter.write("public class " + ModelName + " implements Serializable {\r\n");

		List<ColumnInfo> columnS = (List<ColumnInfo>) tableInfo.get("column");
		for (int i = 0; i < columnS.size(); i++) {
			ColumnInfo model = columnS.get(i);
			String columnName = model.getColumnName();
			String columnType = model.getColumnType();
			if (model.getColumnTitle()!=null&&!model.getColumnTitle().equals(columnName)) {
				pojoWriter.write("//" + model.getColumnTitle() + ";\r\n");
			}
			if (columnType.equals("datetime")) {
				pojoWriter.write("private java.util.Date " + Util.firstLower(columnName) + ";\r\n");
			} else if (columnType.equals("float") || columnType.equals("numeric")) {
				pojoWriter.write("private java.lang.Double " + Util.firstLower(columnName) + ";\r\n");
			} else if (columnType.equals("int")) {
				pojoWriter.write("private java.lang.Integer " + Util.firstLower(columnName) + ";\r\n");
			} else if (columnType.equals("bigint")) {
				pojoWriter.write("private java.lang.Long " + Util.firstLower(columnName) + ";\r\n");
			} else {
				pojoWriter.write("private java.lang.String " + Util.firstLower(columnName) + ";\r\n");
			}

			if("recordUser".equals(columnName)){
				pojoWriter.write("private java.lang.String " + Util.firstLower(columnName)+"Name" + ";\r\n");
			}
		}
		for (int i = 0; i < columnS.size(); i++) {
			ColumnInfo model = columnS.get(i);
			String columnName = model.getColumnName();
			String columnType = model.getColumnType();
			if (columnType.equals("datetime")) {
				columnType = "java.util.Date ";
			} else if (columnType.equals("float") || columnType.equals("numeric")) {
				columnType = "java.lang.Double ";
			} else if (columnType.equals("int")) {
				columnType = "java.lang.Integer ";
			} else if (columnType.equals("bigint")) {
				columnType = "java.lang.Long ";
			} else {
				columnType = "java.lang.String ";
			}
			
			pojoWriter.write("\r\n public void set" + Util.firstUpper(columnName) + "("+columnType+ " " + Util.firstLower(columnName) + "){\r\n this."
					+ Util.firstLower(columnName) + "=" + Util.firstLower(columnName) + ";\r\n}\r\n");
			pojoWriter.write("\r\n public "+columnType+" get" + Util.firstUpper(columnName) + "(){\r\n return this." + Util.firstLower(columnName)
					+ ";  \r\n}\r\n");
			if("recordUser".equals(columnName)){
				pojoWriter.write("\r\n public void set" + Util.firstUpper(columnName)+"Name" + "("+columnType+ " " + Util.firstLower(columnName)+"Name" + "){\r\n this."
						+ Util.firstLower(columnName)+"Name" + "=" + Util.firstLower(columnName)+"Name" + ";\r\n}\r\n");
				pojoWriter.write("\r\n public "+columnType+" get" + Util.firstUpper(columnName)+"Name" + "(){\r\n return this." + Util.firstLower(columnName)+"Name"
						+ ";  \r\n}\r\n");
			}
			
			
		}
		pojoWriter.write("}\r\n");
		pojoWriter.close();
		
		System.out.println("===============表" + tableName + " 生成文件:" + dir + File.separator + ModelName + ".java  完毕===============");

	}
	
	/**
	  * @Description 视图pojo
	  * @author liuxd
	  * @date 2016-1-24 上午11:07:05
	  * @version V1.0
	 */
	public static void createViewPoJo(String packName,String modelNameEn,String modelNameCh,List<SystemTableModel> columns) throws Exception {
		String systemName = (String) Util.getParam().get("systemName");

		String ModelName = Util.ModelName(modelNameEn);
		String modelPath="com.tried." + systemName + "." + packName + ".model";
		
		File dir = new File((String)Util.getParam().get("resourcesPath") + "/src/"+modelPath.replaceAll("\\.", "/")+"/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		OutputStreamWriter pojoWriter = new OutputStreamWriter(new FileOutputStream(dir + "/" + ModelName + ".java",false),"UTF-8");
		pojoWriter.write("package com.tried." + systemName + "." + packName + ".model;\r\n");
		pojoWriter.write("import java.io.Serializable;\r\n");
		pojoWriter.write("/**\r\n");
		pojoWriter.write("* @Description " + modelNameCh + "\r\n");
		pojoWriter.write("* @author " + (String) Util.getParam().get("author") + "\r\n");
		pojoWriter.write("* @date " + Util.currentTime() + "\r\n");
		pojoWriter.write("* @version V1.0\r\n");
		pojoWriter.write("*/\r\n");
		pojoWriter.write("public class " + ModelName + " implements Serializable {\r\n");

		for (SystemTableModel column:columns) {
			String columnName = column.getColumnName();
			 pojoWriter.write("private java.lang.String " + Util.firstLower(columnName) + ";\r\n");

		}
		for (SystemTableModel column:columns) {
			String columnName = column.getColumnName();
			String columnType = "java.lang.String ";
			pojoWriter.write("\r\n public void set" + Util.firstUpper(columnName) + "("+columnType+ " " + Util.firstLower(columnName) + "){\r\n this."
					+ Util.firstLower(columnName) + "=" + Util.firstLower(columnName) + ";\r\n}\r\n");
			pojoWriter.write("\r\n public "+columnType+" get" + Util.firstUpper(columnName) + "(){\r\n return this." + Util.firstLower(columnName)
					+ ";  \r\n}\r\n");
		}
		pojoWriter.write("}\r\n");
		pojoWriter.close();
		
		System.out.println("===============sql" + ModelName + " 生成文件:" + dir + File.separator + ModelName + ".java  完毕===============");

	
	
	}
}
