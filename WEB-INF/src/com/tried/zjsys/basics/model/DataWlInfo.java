package com.tried.zjsys.basics.model;
import java.io.Serializable;
/**
* @Description 物料名称
* @author liuxd
* @date 2020-07-06 16:20:50
* @version V1.0
*/
public class DataWlInfo implements Serializable {
//;
private java.lang.String id;
//物料名称;
private java.lang.String wlName;
// 物料编码;
private java.lang.String wlCode;
private java.lang.String wlType;

//排序
private String viewpaiXu;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//;
private java.util.Date recordTime;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String getWlType() {
	return wlType;
}

public void setWlType(java.lang.String wlType) {
	this.wlType = wlType;
}

public java.lang.String  getId(){
 return this.id;  
}

 public void setWlName(java.lang.String  wlName){
 this.wlName=wlName;
}

 public java.lang.String  getWlName(){
 return this.wlName;  
}

 public void setWlCode(java.lang.String  wlCode){
 this.wlCode=wlCode;
}

 public java.lang.String  getWlCode(){
 return this.wlCode;  
}
 
 
 

 public String getViewpaiXu() {
	return viewpaiXu;
}

public void setViewpaiXu(String viewpaiXu) {
	this.viewpaiXu = viewpaiXu;
}

public void setRecordUser(java.lang.String  recordUser){
 this.recordUser=recordUser;
}

 public java.lang.String  getRecordUser(){
 return this.recordUser;  
}

 public void setRecordUserName(java.lang.String  recordUserName){
 this.recordUserName=recordUserName;
}

 public java.lang.String  getRecordUserName(){
 return this.recordUserName;  
}

 public void setRecordTime(java.util.Date  recordTime){
 this.recordTime=recordTime;
}

 public java.util.Date  getRecordTime(){
 return this.recordTime;  
}
}
