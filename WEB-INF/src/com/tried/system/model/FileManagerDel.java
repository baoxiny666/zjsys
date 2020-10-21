package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 作废文件存储区
* @author liuyw
* @date 2016-11-16 14:57:50
* @version V1.0
*/
public class FileManagerDel implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String filename;
//;
private java.lang.Double fileSize;
//;
private java.lang.String desciption;
//;
private java.lang.String fileSavePath;
//;
private java.lang.String fileSaveName;
//;
private java.lang.String pkId;
//;
private java.lang.String pkTable;
//;
private java.lang.String pkColumn;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//;
private java.util.Date recordTime;
//备份时间;
private java.util.Date backTime;
private String appPkId;


 public String getAppPkId() {
	return appPkId;
}

public void setAppPkId(String appPkId) {
	this.appPkId = appPkId;
}

public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public java.lang.String getRecordUserName() {
	return recordUserName;
}

public void setRecordUserName(java.lang.String recordUserName) {
	this.recordUserName = recordUserName;
}

public void setFilename(java.lang.String  filename){
 this.filename=filename;
}

 public java.lang.String  getFilename(){
 return this.filename;  
}

 public void setFileSize(java.lang.Double  fileSize){
 this.fileSize=fileSize;
}

 public java.lang.Double  getFileSize(){
 return this.fileSize;  
}

 public void setDesciption(java.lang.String  desciption){
 this.desciption=desciption;
}

 public java.lang.String  getDesciption(){
 return this.desciption;  
}

 public void setFileSavePath(java.lang.String  fileSavePath){
 this.fileSavePath=fileSavePath;
}

 public java.lang.String  getFileSavePath(){
 return this.fileSavePath;  
}

 public void setFileSaveName(java.lang.String  fileSaveName){
 this.fileSaveName=fileSaveName;
}

 public java.lang.String  getFileSaveName(){
 return this.fileSaveName;  
}

 public void setPkId(java.lang.String  pkId){
 this.pkId=pkId;
}

 public java.lang.String  getPkId(){
 return this.pkId;  
}

 public void setPkTable(java.lang.String  pkTable){
 this.pkTable=pkTable;
}

 public java.lang.String  getPkTable(){
 return this.pkTable;  
}

 public void setPkColumn(java.lang.String  pkColumn){
 this.pkColumn=pkColumn;
}

 public java.lang.String  getPkColumn(){
 return this.pkColumn;  
}

 public void setRecordUser(java.lang.String  recordUser){
 this.recordUser=recordUser;
}

 public java.lang.String  getRecordUser(){
 return this.recordUser;  
}

 public void setRecordTime(java.util.Date  recordTime){
 this.recordTime=recordTime;
}

 public java.util.Date  getRecordTime(){
 return this.recordTime;  
}

 public void setBackTime(java.util.Date  backTime){
 this.backTime=backTime;
}

 public java.util.Date  getBackTime(){
 return this.backTime;  
}
}
