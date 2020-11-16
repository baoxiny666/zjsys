package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
/**
* @Description 设备属性阈值
* @author liuxd
* @date 2020-07-03 17:44:07
* @version V1.0
*/
public class DataKeyMaxMin implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String deviceName;
//;
private java.lang.String keyName;
//;
private java.lang.String keyMax;
//;
private java.lang.String keyMin;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//;
private java.lang.String fieldName;
private java.lang.Integer  viewpaiXu;
private java.lang.Integer  numberDecimal;
private java.util.Date recordTime;
public String jsField;
 public void setId(java.lang.String  id){
 this.id=id;
}

 
 public String getJsField() {
	 if(fieldName!=null&&!fieldName.isEmpty()){
		 return fieldName
				 .replace("handInput_", "")
				 .replace("yingguang_", "")
				 .replace("gongye_", "")
				 .replace("tanliu", "")
				 .replace("dingliu_", "")
				  .replace("liangre", "")
				 ;
	 }
	 return "";
	
}

public void setJsField(String jsField) {
	this.jsField = jsField;
}

 


public java.lang.String getFieldName() {
	return fieldName;
}

public void setFieldName(java.lang.String fieldName) {
	this.fieldName = fieldName;
}

public java.lang.String  getId(){
 return this.id;  
}

 

 public java.lang.String getDeviceName() {
	return deviceName;
}

public void setDeviceName(java.lang.String deviceName) {
	this.deviceName = deviceName;
}

public void setKeyName(java.lang.String  keyName){
 this.keyName=keyName;
}

 public java.lang.String  getKeyName(){
 return this.keyName;  
}
 

 public java.lang.String getKeyMax() {
	return keyMax;
}

public void setKeyMax(java.lang.String keyMax) {
	this.keyMax = keyMax;
}

public java.lang.String getKeyMin() {
	return keyMin;
}

public void setKeyMin(java.lang.String keyMin) {
	this.keyMin = keyMin;
}



public java.lang.Integer getViewpaiXu() {
	return viewpaiXu;
}


public void setViewpaiXu(java.lang.Integer viewpaiXu) {
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


public java.lang.Integer getNumberDecimal() {
	return numberDecimal;
}


public void setNumberDecimal(java.lang.Integer numberDecimal) {
	this.numberDecimal = numberDecimal;
}
 
 
}
