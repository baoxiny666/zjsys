package com.tried.system.model;
import java.io.Serializable;
/**
* @Description -
* @author liuxd
* @date 2018-10-22 16:09:40
* @version V1.0
*/
public class SystemDelHistory implements Serializable {
//;
private java.lang.String id;
//删除内容;
private java.lang.String delContext;

private java.lang.String cname;
//;
private java.util.Date recordTime;

private java.lang.String recordUser;
private java.lang.String recordUserName;

public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setDelContext(java.lang.String  delContext){
 this.delContext=delContext;
}

 public java.lang.String  getDelContext(){
 return this.delContext;  
}

 public void setRecordTime(java.util.Date  recordTime){
 this.recordTime=recordTime;
}

 public java.util.Date  getRecordTime(){
 return this.recordTime;  
}

public java.lang.String getCname() {
	return cname;
}

public void setCname(java.lang.String cname) {
	this.cname = cname;
}

public java.lang.String getRecordUser() {
	return recordUser;
}

public void setRecordUser(java.lang.String recordUser) {
	this.recordUser = recordUser;
}

public java.lang.String getRecordUserName() {
	return recordUserName;
}

public void setRecordUserName(java.lang.String recordUserName) {
	this.recordUserName = recordUserName;
}
  
}
