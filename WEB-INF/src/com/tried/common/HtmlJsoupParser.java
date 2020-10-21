package com.tried.common;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * html解析工具类
 * @title HtmlJsoupParser
 * @author lyw
 * @date 2019-1-11 上午10:11:50
 * @version v1.0
 */
public class HtmlJsoupParser {
	
	protected List<List<String>>data = new LinkedList<List<String>>();
	
	/**
	 * 获取value值
	 * @title: getValue
	 * @author: lyw
	 * @date : 2019-1-11 上午10:00:11
	 * @version: v1.0
	 */
	public static String getValue(Element e){
		return e.attr("value");
	}
	/**
	 * @title: getText
	 * @author: lyw
	 * @date : 2019-1-11 上午10:11:17
	 * @version: v1.0
	 */
	public static String getText(Element e){
		return e.text();
	}
	/**
	 * 根据元素id获取标签 html的id唯一
	 * @title: getById
	 * @author: lyw
	 * @date : 2019-1-11 上午10:10:49
	 * @version: v1.0
	 */
	public static Element getById(String body,String id){
		Document doc = Jsoup.parse(body);
		Elements elements  = doc.select("#"+id);
		return elements.first();
	}
	/**
	 * 获取属性class的标签组
	 * @title: getClassTag
	 * @author: lyw
	 * @date : 2019-1-11 上午10:10:08
	 * @version: v1.0
	 */
	public static Elements getClassTag(String body,String classTag){
		Document doc = Jsoup.parse(body);
		return doc.select("."+classTag);
	}
	/**
	 * 获取tr标签元素组
	 * @title: getTr
	 * @author: lyw
	 * @date : 2019-1-11 上午10:09:48
	 * @version: v1.0
	 */
	public static Elements getTr(Element e){
		return e.getElementsByTag("tr");
	}
	
	/**
	 * 获取td标签元素组
	 * @title: getTd
	 * @author: lyw
	 * @date : 2019-1-11 上午10:09:19
	 * @version: v1.0
	 */
	public static Elements getTd(Element e){
		return e.getElementsByTag("td");
	}
	
	/**
	 * 获取table元组 包括table嵌套
	 * @title: getTables
	 * @author: lyw
	 * @date : 2019-1-11 上午10:08:02
	 * @version: v1.0
	 */
	public static List<List<String>> getTables(Element table){
		List<List<String>> data = new ArrayList<List<String>>();
		
		for(Element etr : table.select("tr")){
			List<String> list = new ArrayList<String>();
			for(Element etd : etr.select("td")){
				String tdTxt = etd.text().replaceAll(Jsoup.parse("&nbsp;").text(),"");
				list.add(tdTxt);
			}
			data.add(list);
		}
		return data;
	}
	
	public static void main(String[] args) throws Exception{
		 /*Document doc = Jsoup.parse(new File("D:/project/dg-workspace/fileDir/email/0025-9440-8984-1100.html"),"gbk");
		 Element table = doc.select("table").get(2);//第3个table
		 List<List<String>> list = HtmlJsoupParser.getTables(table);
		 System.out.println(list.get(7).get(1).trim());*/
		String arr[] = {"aaa","bbb","ccc"};
		System.out.println(arr.length);
	}
	
}
