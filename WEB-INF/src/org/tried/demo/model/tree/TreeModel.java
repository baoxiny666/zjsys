package org.tried.demo.model.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.tried.demo.model.util.Util;

public class TreeModel {
 public static void createModel(Map<String,String> elMap){
	 List<File> models=Util.getModelFiles("tree");
	 String rootPath=Util.rootPath();
	 String systemName =elMap.get("systemName");
	 String packName=elMap.get("packName");
	 for(File modelFile:models){
	    	String modelFileName=modelFile.getName();
	    	String createPath="";
	    	String modelPath="";
	    	if(modelFileName.contains(".svn")){continue;}
	    	//java文件存放路径
	    	if(modelFileName.contains("Model")){
	    		 modelFileName=modelFile.getName().replace("Model", elMap.get("ModelName").toString());
	    		 if(modelFileName.contains("Action.java")){
	    			  modelPath="com.tried." + systemName + "." + packName + ".action";
	    		 }
	    		 if(modelFileName.contains("Service.java")){
	    			  modelPath="com.tried." + systemName + "." + packName + ".service";
	    		 }
	    		 if(modelFileName.contains("ServiceImpl.java")){
	    			  modelPath="com.tried." + systemName + "." + packName + ".service.impl";
	    		 }
	    		 createPath=(String)Util.getParam().get("resourcesPath") + "/src/"+modelPath.replaceAll("\\.", "/")+"/";
	    	}
	    	if(modelFileName.contains(".js")){
	    		createPath=rootPath+"/page/"+elMap.get("packName")+"/"+elMap.get("ModelName")+"/js/";
	    	}
	    	if(modelFileName.contains(".html")){
	    		createPath=rootPath+"/page/"+elMap.get("packName")+"/"+elMap.get("ModelName")+"/";
	    	}
	    	File dir=new File(createPath);
	    	if(!dir.exists()){
	    		dir.mkdirs();
	    	}
	    	OutputStreamWriter writer = null;
			BufferedReader	reader=null;
			try {
				writer = new OutputStreamWriter(new FileOutputStream(createPath+modelFileName,false),"UTF-8");
				//reader = new BufferedReader(new FileReader(modelFile));
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(modelFile), "UTF-8"));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					Iterator it=elMap.keySet().iterator();
					while(it.hasNext()){
						String key=it.next().toString();
						
						tempString=tempString.replaceAll("\\$\\{"+key+"\\}", elMap.get(key).toString());
					}
					writer.write(tempString+"\r\n"); 
				}
				reader.close();
				writer.close();
				System.out.println("文件："+modelFileName+"生成完毕");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
	 }
 }
}
