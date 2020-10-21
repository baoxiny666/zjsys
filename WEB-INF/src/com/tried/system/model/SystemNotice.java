package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 公告管理
* @author liuxd
* @date 2016-09-06 16:30:37
* @version V1.0
*/
public class SystemNotice implements Serializable {
//标题;
private java.lang.String notice_title;
//公告内容;
private java.lang.String notice_content;
//相关附件;
private java.lang.String notice_file;
private java.lang.String id;

private java.lang.String notice_status;
//操作人;
private java.lang.String recordUser;
//操作时间;
private java.util.Date recordTime;
private java.lang.String recordUserName;
 
 public java.lang.String getRecordUserName() {
	return recordUserName;
}

public void setRecordUserName(java.lang.String recordUserName) {
	this.recordUserName = recordUserName;
}

public void setNotice_title(java.lang.String  notice_title){
 this.notice_title=notice_title;
}

 public java.lang.String getNotice_status() {
	return notice_status;
}

public void setNotice_status(java.lang.String notice_status) {
	this.notice_status = notice_status;
}

public java.lang.String  getNotice_title(){
 return this.notice_title;  
}

 public void setNotice_content(java.lang.String  notice_content){
 this.notice_content=notice_content;
}

 public java.lang.String  getNotice_content(){
 return this.notice_content;  
}

 public void setNotice_file(java.lang.String  notice_file){
 this.notice_file=notice_file;
}

 public java.lang.String  getNotice_file(){
 return this.notice_file;  
}

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
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
}
