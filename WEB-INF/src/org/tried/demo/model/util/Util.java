package org.tried.demo.model.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.tried.demo.model.db.ConnectionPool;
import org.tried.demo.model.db.SqlCommond;

import com.tried.system.model.SystemTableModel;
public class Util {
	
	/**
	  * @Description 获取应用跟路径
	  * @author liuxd
	  * @date 2015-12-25 上午11:05:12
	  * @version V1.0
	 */
	public static String rootPath(){
		String resourcesPath=	(String)Util.getParam().get("resourcesPath");
		resourcesPath=resourcesPath.substring(0,resourcesPath.indexOf("WEB-INF"));
		return resourcesPath;
	} 
	 
	/**
	 * 获取param.properties参数
	 * @author liuxd
	 * @date 2013-12-10 下午01:59:16
	 * @version V1.0
	 */
	
	
	public static Map<String,Object> getParam() {
	Map<String,Object>  result=new HashMap<String,Object>();
	try {
		//	打包时用	
		InputStream   pInStream = Util.class.getResourceAsStream("/param.properties"); 
		Properties p = new Properties();
		p.load(pInStream );      
		Set<Object> keyValue = p.keySet();
		for (Iterator<Object> it = keyValue.iterator(); it.hasNext();)
		{
		String key = (String) it.next();
		result.put(key, p.getProperty(key));
		}

	} catch (FileNotFoundException e1) {
		e1.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return result;
}
	/**
	 * 获取目录下所有文件
	 * @author liuxd
	 * @date 2013-12-10 下午01:59:02
	 * @version V1.0
	 */
	public static List<File> getModelFiles(String dir){
	
		File dirPath=new File(rootPath()+"/model/"+dir+"/");
		
		List<File> files=new ArrayList<File>();
		for(File file:dirPath.listFiles()){
			if(!file.getName().contains(".svn")){
				files.add(file);
			}
		}
		return files;
	}

	/**
	  * @Description 根据表明转换成POJO名字
	  * @author liuxd
	  * @date 2015-9-5 下午9:02:29
	  * @version V1.0
	 */
	public static String ModelName(String tableName){
		String[] tableSplit =tableName.split("_");
		String ModelName="";
		if(tableSplit[0].toUpperCase().equals("TRIED")){
			for(int i=1;i<tableSplit.length;i++){
				ModelName=ModelName+tableSplit[i].substring(0, 1).toUpperCase()+tableSplit[i].substring(1);
			}
		}else{
			for(int i=0;i<tableSplit.length;i++){
				ModelName=ModelName+tableSplit[i].substring(0, 1).toUpperCase()+tableSplit[i].substring(1);
			}
		}
		return ModelName;
	}
	/**
	  * @Description 首字母小写
	  * @author liuxd
	  * @date 2015-9-5 下午5:23:34
	  * @version V1.0
	 */
	public static String firstLower(String str){
		str=str.substring(0, 1).toLowerCase()+str.substring(1);
		return str;
	}
	/**
	  * @Description 首字母大写
	  * @author liuxd
	  * @date 2015-9-5 下午5:23:21
	  * @version V1.0
	 */
	public static String firstUpper(String str){
		str=str.substring(0, 1).toUpperCase()+str.substring(1);
		return str;
	}
	/**
	  * @Description 当前时间
	  * @author liuxd
	  * @date 2015-9-5 下午9:02:14
	  * @version V1.0
	 */
	public static String currentTime(){
		SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formate.format(new Date());
	}
	
	
	
	
	
	
	
	/**
	  * @Description 创建功能地址
	  * @author liuxd
	  * @date 2015-12-25 下午2:41:05
	  * @version V1.0
	 */
	public static void createUrl(SystemTableModel tableInfo)throws Exception{
		String packageName=Util.getParam().get("packageName").toString();
		 if(null!=tableInfo.getPackName()){
			 packageName=tableInfo.getPackName();
		}
		String menuName=tableInfo.getObjectName();
		String ModelName= Util.ModelName(tableInfo.getObjectTable());
		String menuUrl="/page/"+packageName+"/"+ModelName+"/list.html";
		boolean exit = false;
		Connection conn = ConnectionPool.getInstance().getConnection();
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT id FROM tried_system_menu WHERE address ='" + menuUrl + "'");
		if (resultSet.next()) {
			if (null != resultSet.getString("id")) {
				exit = true;
			}
		}
		statement.close();
		ConnectionPool.getInstance().release(conn);
		if(!exit){
			String sql="insert into tried_system_menu(id,parentId,name,address,sequence,flag)values('"+UUID.randomUUID().toString().replaceAll("-", "")+"','0','"+menuName+"','"+menuUrl+"',0,'是')";
			SqlCommond.commondSql(sql);
		}
		
	}
	
	/**
	  * @Description 配置struts文件
	  * @author liuxd
	  * @date 2015-12-25 上午11:36:32
	  * @version V1.0
	 * @throws Exception 
	 */
	public static void configStatus(SystemTableModel tableInfo) throws Exception{
		
		String packageName=Util.getParam().get("packageName").toString();
		 if(null!=tableInfo.getPackName()){
			 packageName=tableInfo.getPackName();
		}
		File dir = new File((String)Util.getParam().get("resourcesPath") + "/resources/struts/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File strutsXml=new File(dir+File.separator+"struts-"+packageName+".xml");
		//配置文件存在
		if(strutsXml.exists()){
	            SAXReader saxReader=new SAXReader();
	            saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	            Document document=saxReader.read(strutsXml);
	            Element struts=document.getRootElement();//根节点
	            Element pageEle=struts.element("package");
	            Iterator<Element>  elementIts=  pageEle.elementIterator("action");
	            String className=firstLower(Util.ModelName(tableInfo.getObjectTable()))+"Action";
	            boolean flag=false;
	            while(elementIts.hasNext()){
	            	Element actionEle=elementIts.next();
	            	if(actionEle.attribute("class")!=null){
	            		if(className.equals(actionEle.attribute("class").getValue())){
	            			flag=true;//配置文件中存在action配置信息
	            		}
	            	}
	            }
	            if(!flag){
	            	Element packageEle = struts.element("package");
		            Element actionEle=packageEle.addElement("action");
		            actionEle.addAttribute("name", firstLower(Util.ModelName(tableInfo.getObjectTable()))+"Action_*");
		            actionEle.addAttribute("class",  firstLower(Util.ModelName(tableInfo.getObjectTable()))+"Action");
		            actionEle.addAttribute("method", "{1}");
		            OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
		            xmlFormat.setEncoding("UTF-8");
		            xmlFormat.setTrimText(false);
		            XMLWriter writer = new XMLWriter(new FileOutputStream(strutsXml),xmlFormat);
		            writer.write(document);
		       	 	writer.close();
	            }
		
		}else{
			String systemName=Util.getParam().get("systemName").toString();
			Document document = DocumentHelper.createDocument();
			document.setXMLEncoding("UTF-8");
			document.addDocType("struts", "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN", "http://struts.apache.org/dtds/struts-2.0.dtd");
			Element strutsElement = document.addElement("struts");
			Element pageElement=strutsElement.addElement("package");
			pageElement.addAttribute("name", "struts-"+packageName);
			pageElement.addAttribute("namespace","/"+systemName+"_"+packageName);
			pageElement.addAttribute("extends","struts-default,json-default");
			Element actionEle=pageElement.addElement("action");
		            actionEle.addAttribute("name", firstLower(Util.ModelName(tableInfo.getObjectTable()))+"Action_*");
		            actionEle.addAttribute("class",  firstLower(Util.ModelName(tableInfo.getObjectTable()))+"Action");
		            actionEle.addAttribute("method", "{1}");
		            OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
		            xmlFormat.setEncoding("UTF-8");
		            xmlFormat.setTrimText(false);
		            XMLWriter writer = new XMLWriter(new FileOutputStream(strutsXml),xmlFormat);
		            writer.write(document);
		       	 	writer.close();
			
			
		}
		
		  strutsXml = new File((String)Util.getParam().get("resourcesPath") + "/resources/struts.xml");
		if (strutsXml.exists()) {
			 	String newStrutsName="struts/struts-"+packageName+".xml";
			    SAXReader saxReader=new SAXReader();
			    saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	            Document document=saxReader.read(strutsXml);
	            Element struts=document.getRootElement();//根节点
	            Iterator<Element>  elementIts=  struts.elementIterator("include");
	            boolean flag=false;
	            while(elementIts.hasNext()){
	            	Element actionEle=elementIts.next();
	            	if(actionEle.attribute("file")!=null){
	            		if(newStrutsName.equals(actionEle.attribute("file").getValue())){
	            			flag=true;//配置文件中存在action配置信息
	            		}
	            	}
	            }
	            if(!flag){
	            	   	Element actionEle=struts.addElement("include");
			            actionEle.addAttribute("file", newStrutsName);
			            OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
			            xmlFormat.setEncoding("UTF-8");
			            xmlFormat.setTrimText(false);
			            XMLWriter writer = new XMLWriter(new FileOutputStream(strutsXml),xmlFormat);
			            writer.write(document);
			       	 	writer.close();
	            }
		}
	}
	
	
	
	/**
	  * @Description 表中添加字段
	  * @author liuxd
	  * @date 2016-2-1 下午4:26:32
	  * @version V1.0
	 */
	public static void addColumn(String tableName,SystemTableModel column)throws Exception{
		List<String> sqls=new LinkedList<String>();
		if("char".equals(column.getColumnType())||"varchar".equals(column.getColumnType())){
			sqls.add("alter table "+tableName+" add "+column.getColumnName()+" "+column.getColumnType()+"("+column.getColumnLength()+")  ");
		}else{
			sqls.add("alter table "+tableName+" add "+column.getColumnName()+"  "+column.getColumnType()+"  ");
		}
		SqlCommond.beachSql(sqls);
	}
	/**
	 * 生成数据库表  SEQ SERVER
	  * @Description
	  * @author liuxd
	  * @date 2015-11-24 下午1:48:30
	  * @version V1.0
	 * @throws Exception 
	 */
	public static void createTable(SystemTableModel tableInfo,List<SystemTableModel> columns) throws Exception{
		List<String> sqls=new LinkedList<String>();
		String tableName=tableInfo.getObjectTable();
		if(SqlCommond.exitTable(tableName)){
			if(SqlCommond.exitTable(tableName+"_bk")){
				sqls.add("drop table "+tableName+"_bk");//删除备份表
			}
			sqls.add("EXEC sp_rename '"+tableName+"','"+tableName+"_bk'");//以防误删
			sqls.add("drop table "+tableName);//删除老表
		}
		SqlCommond.beachSql(sqls);
		sqls.clear();
		String pk="";
		String pk_length="";
		for(SystemTableModel column:columns){
			if("是".equals(column.getColumnPk())){
				pk=column.getColumnName();
				pk_length=column.getColumnLength();
			}
		}
		//添加表
		sqls.add(" create table "+tableName+"( "+pk+" varchar ("+pk_length+") NOT NULL PRIMARY KEY  )");
		//添加表描述
		sqls.add(" EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'"+tableInfo.getObjectName()+"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'"+tableName+"'");
		
		for(SystemTableModel column:columns){
			if(!pk.equals(column.getColumnName())){
				if("char".equals(column.getColumnType())||"varchar".equals(column.getColumnType())){
					sqls.add("alter table "+tableName+" add "+column.getColumnName()+" "+column.getColumnType()+"("+column.getColumnLength()+")  ");
				}else{
					sqls.add("alter table "+tableName+" add "+column.getColumnName()+"  "+column.getColumnType()+"  ");
				}
				sqls.add(" EXEC   sp_addextendedproperty   'MS_Description','"+column.getColumnTitle()+"','user',dbo,'table','"+tableName+"','column',"+column.getColumnName());
			}
		}
		SqlCommond.beachSql(sqls);
	}
	
	
	
}
