package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 逆向工具
* @author liuxd
* @date 2016-01-29 15:54:08
* @version V1.0
*/
public class SystemReverse implements Serializable {
//对象描述;
private java.lang.String tabel_content="";
private java.lang.String tabel_type="";
//对象名字;
private java.lang.String table_name="";
private java.lang.String id;
private String pageType;
private String pageName;

 public void setTabel_content(java.lang.String  tabel_content){
 this.tabel_content=tabel_content;
}

 public java.lang.String getTabel_type() {
	return tabel_type;
}

public void setTabel_type(java.lang.String tabel_type) {
	this.tabel_type = tabel_type;
}

public java.lang.String  getTabel_content(){
 return this.tabel_content;  
}

 public void setTable_name(java.lang.String  table_name){
 this.table_name=table_name;
}

 public java.lang.String  getTable_name(){
 return this.table_name;  
}

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

public String getPageType() {
	return pageType;
}

public void setPageType(String pageType) {
	this.pageType = pageType;
}

public String getPageName() {
	return pageName;
}

public void setPageName(String pageName) {
	this.pageName = pageName;
}
}
