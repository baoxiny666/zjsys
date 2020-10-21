package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 视图工具
* @author liuxd
* @date 2016-01-21 20:59:00
* @version V1.0
*/
public class SystemViewModel implements Serializable {
//视图名称;
private java.lang.String view_name;
//视图描述;
private java.lang.String view_content;
//视图语句;
private java.lang.String view_sql;
//所属模块;
private java.lang.String view_package;
private java.lang.String id;

 public void setView_name(java.lang.String  view_name){
 this.view_name=view_name;
}

 public java.lang.String  getView_name(){
 return this.view_name;  
}

 public void setView_content(java.lang.String  view_content){
 this.view_content=view_content;
}

 public java.lang.String  getView_content(){
 return this.view_content;  
}

 public void setView_sql(java.lang.String  view_sql){
 this.view_sql=view_sql;
}

 public java.lang.String  getView_sql(){
 return this.view_sql;  
}

 public void setView_package(java.lang.String  view_package){
 this.view_package=view_package;
}

 public java.lang.String  getView_package(){
 return this.view_package;  
}

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}
}
