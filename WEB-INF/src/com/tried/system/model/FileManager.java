package com.tried.system.model;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
* @Description 文件存储表
* @author liuxd
* @date 2016-05-30 09:36:51
* @version V1.0
*/
public class FileManager implements Serializable {
//;
private java.lang.String id;
//文件名;
private java.lang.String filename;
//文件存储名;
private java.lang.String fileSaveName;
//文件存储路径;
private java.lang.String fileSavePath;
//文件大小;
private java.lang.Double fileSize;
//文件说明;
private java.lang.String desciption;
//外键id;
private java.lang.String pkId;
//外键字段名;
private java.lang.String pkTable;
//外键字段名;
private java.lang.String pkColumn;
//操作人;
private java.lang.String recordUser;
private String recordUserName;
//上传时间;
private java.util.Date recordTime;
//excel 标题
private String excelTitle;
private String appPkId;

 public String getAppPkId() {
	return appPkId;
}

public void setAppPkId(String appPkId) {
	this.appPkId = appPkId;
}

public String getExcelTitle() {
	return excelTitle;
}

public void setExcelTitle(String excelTitle) {
	this.excelTitle = excelTitle;
}

public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String getPkTable() {
	return pkTable;
}

public void setPkTable(java.lang.String pkTable) {
	this.pkTable = pkTable;
}

public java.lang.String  getId(){
 return this.id;  
}

 public void setFilename(java.lang.String  filename){
 this.filename=filename;
}

 public java.lang.String  getFilename(){
 return this.filename;  
}

  

 public java.lang.String getFileSaveName() {
	return fileSaveName;
}

public void setFileSaveName(java.lang.String fileSaveName) {
	this.fileSaveName = fileSaveName;
}

public java.lang.String getFileSavePath() {
	return fileSavePath;
}

public void setFileSavePath(java.lang.String fileSavePath) {
	this.fileSavePath = fileSavePath;
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

 public void setPkId(java.lang.String  pkId){
 this.pkId=pkId;
}

 public java.lang.String  getPkId(){
 return this.pkId;  
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

public String getRecordUserName() {
	return recordUserName;
}

public void setRecordUserName(String recordUserName) {
	this.recordUserName = recordUserName;
}

/**
 * 对象深拷贝
 * @title: deepClone
 * @author: lyw
 * @date : 2018-12-27 上午11:44:21
 * @version: v1.0
 */
public Object deepClone() throws Exception{
	ByteArrayOutputStream bo = new ByteArrayOutputStream();
	ObjectOutputStream oo = new ObjectOutputStream(bo);
	oo.writeObject(this);
	ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
	ObjectInputStream oi = new ObjectInputStream(bi);
	return (oi.readObject());
}
 
}
