package com.tried.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
 * @Description 文件工具
 * @author liuyw
 * @date 2016-04-02 10:27:53
 * @version V1.0
 */
public class LogFileUtil {
	/**
	 * @param fileName 文件名称
	 * @return  文件总长度
	 * @throws Exception
	 */
	public static int getTotalFileLines(String fileName){
		int lines = 0;
		FileReader in;
		LineNumberReader reader;
		try {
			in = new FileReader(new File(fileName));
			reader = new LineNumberReader(in);
			String str = reader.readLine();
			System.out.println(str);
			while(str!=null){
				lines++;
				str = reader.readLine();
			}
			reader.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 */
	public static List<Map<String,Object>> getTotalFileLineContent(String fileName){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		BufferedReader br = null;
		try{
			String logTime = "";
			String content = "";
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"gb2312"));
			String line = null;
			int i=1;
			while((line= br.readLine())!=null){
				if(line.contains("com.tried.ntcisys")){
					Map<String,Object> temp = new LinkedHashMap<String,Object>();
					logTime = line.indexOf('[')>0?line.substring(0,line.indexOf('[')-1):"";
					temp.put("logTime", logTime);
					content = line.indexOf("] - ")>0?line.substring(line.indexOf("] - ")+4,line.length()):line;
					temp.put("content", content);
					list.add(temp);
					i++;
				}
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取文件的最后修改事件
	 * @param fileName
	 * @return
	 */
	public static String getFileModifiedTime(String fileName){
		File f = new File(fileName);
		long time = f.lastModified();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	/**
	 * 获取文件的创建时间
	 * @param pathName
	 * @return
	 */
	public static String getFileCreateTime(String pathName){
		String strTime = null;
		String newFileName = pathName.replaceAll("/", "\\\\");
		String fileName = pathName.substring(pathName.lastIndexOf('/')+1, pathName.length());
		try{
			Process pro = Runtime.getRuntime().exec("cmd /C dir "+ newFileName + " /tc");
			InputStream is = pro.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = br.readLine())!=null){
				if(line.endsWith(fileName)){
					strTime = line.substring(0, 17);
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return strTime;
	}
	
	public static void main(String[] args)throws Exception{
		/*System.out.println(getTotalFileLines("D:/TGlog/log.txt.1"));
		List<Map<String,Object>> list = getTotalFileLineContent("D:/TGlog/log.txt.1");
		for(Map<String,Object> map : list){
			System.out.println(map);
		}*/
		//System.out.println(getFileCreateTime("D:/TGlog/log.txt"));
		
		
		String str = "2016-04-05 13:40:32 [com.tried.ntcisys.filter.CheckUserFilter]-[INFO] - 用户:超级管理员;操作路径：/marksys/marksys_system/systemUserAction_list.action";
		String str2 = "2016-04-05 13:35:49 [org.apache.struts2.config.AbstractBeanSelectionProvider]-[INFO] - Choosing bean (struts) for (org.apache.struts2.views.util.UrlHelper)";
		//System.out.println(str.replaceAll("/", "\\\\"));
		//System.out.println(str.substring(str.lastIndexOf('\\'), str.length()));
		/*System.out.println(getFileCreateTime(str));*/
		System.out.println(str2.contains("com.tried.ntcisys"));
	}
}
