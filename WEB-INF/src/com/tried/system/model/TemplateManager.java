package com.tried.system.model;
import java.io.Serializable;
/**
* @Description -
* @author lyw
* @date 2018-12-24 11:30:17
* @version V1.0
*/
public class TemplateManager implements Serializable {
//;
private java.lang.String id;
//模板名称;
private java.lang.String templateName;
//模板类型;
private java.lang.String templateType;

private java.lang.String templateFile;

private java.lang.String templateContent;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//;
private java.util.Date recordTime;
//模板附件名称
private String filename;


 public java.lang.String getTemplateContent() {
	return templateContent;
}

public void setTemplateContent(java.lang.String templateContent) {
	this.templateContent = templateContent;
}

public java.lang.String getTemplateFile() {
	return templateFile;
}

public void setTemplateFile(java.lang.String templateFile) {
	this.templateFile = templateFile;
}

public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setTemplateName(java.lang.String  templateName){
 this.templateName=templateName;
}

 public java.lang.String  getTemplateName(){
 return this.templateName;  
}

 public void setTemplateType(java.lang.String  templateType){
 this.templateType=templateType;
}

 public java.lang.String  getTemplateType(){
 return this.templateType;  
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

public String getFilename() {
	return filename;
}
public void setFilename(String filename) {
	this.filename = filename;
}
 
}
