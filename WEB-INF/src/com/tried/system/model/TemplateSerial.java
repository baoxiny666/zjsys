package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 模板序列
* @author liuxd
* @date 2020-06-02 22:31:20
* @version V1.0
*/
public class TemplateSerial implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String templateId;
//序号;
private java.lang.Double serialNumber;
//标题;
private java.lang.String serialName;
private java.lang.String serialContent;
private java.lang.String serialBeizhu;
//关键字;
private java.lang.String serialKey;
//;
private java.util.Date recordTime;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
private java.lang.String  checkValue;
 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String getCheckValue() {
	return checkValue;
}

public void setCheckValue(java.lang.String checkValue) {
	this.checkValue = checkValue;
}

public java.lang.String getSerialContent() {
	return serialContent;
}

public void setSerialContent(java.lang.String serialContent) {
	this.serialContent = serialContent;
}

public java.lang.String getSerialBeizhu() {
	return serialBeizhu;
}

public void setSerialBeizhu(java.lang.String serialBeizhu) {
	this.serialBeizhu = serialBeizhu;
}

public java.lang.String  getId(){
 return this.id;  
}

 public void setTemplateId(java.lang.String  templateId){
 this.templateId=templateId;
}

 public java.lang.String  getTemplateId(){
 return this.templateId;  
}

 public void setSerialNumber(java.lang.Double  serialNumber){
 this.serialNumber=serialNumber;
}

 public java.lang.Double  getSerialNumber(){
 return this.serialNumber;  
}

 public void setSerialName(java.lang.String  serialName){
 this.serialName=serialName;
}

 public java.lang.String  getSerialName(){
 return this.serialName;  
}

 public void setSerialKey(java.lang.String  serialKey){
 this.serialKey=serialKey;
}

 public java.lang.String  getSerialKey(){
 return this.serialKey;  
}

 public void setRecordTime(java.util.Date  recordTime){
 this.recordTime=recordTime;
}

 public java.util.Date  getRecordTime(){
 return this.recordTime;  
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
}
