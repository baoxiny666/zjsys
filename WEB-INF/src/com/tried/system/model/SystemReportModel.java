package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 报告模板
* @author liuxd
* @date 2019-07-30 10:02:06
* @version V1.0
*/
public class SystemReportModel implements Serializable {
//;
private java.lang.String id;
//样机模板编号;
private java.lang.String reportNum;
//样机模板名称;
private java.lang.String reportName;
//;
private java.lang.String jcyiName;
//模板类型
private String modelType;
//;
private java.lang.String system_report_model_file;
//;
private java.util.Date recordTime;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//附件名
private  java.lang.String filename;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}
 public java.lang.String getReportNum() {
	return reportNum;
}

public void setReportNum(java.lang.String reportNum) {
	this.reportNum = reportNum;
}

public void setReportName(java.lang.String  reportName){
 this.reportName=reportName;
}

 public java.lang.String  getReportName(){
 return this.reportName;  
}

 public void setJcyiName(java.lang.String  jcyiName){
 this.jcyiName=jcyiName;
}

 public java.lang.String  getJcyiName(){
 return this.jcyiName;  
}

 public void setSystem_report_model_file(java.lang.String  system_report_model_file){
 this.system_report_model_file=system_report_model_file;
}

 public java.lang.String  getSystem_report_model_file(){
 return this.system_report_model_file;  
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

public String getModelType() {
	return modelType;
}

public void setModelType(String modelType) {
	this.modelType = modelType;
}

public java.lang.String getFilename() {
	return filename;
}

public void setFilename(java.lang.String filename) {
	this.filename = filename;
}
 
}
