package com.tried.system.model;
import java.io.Serializable;
/**
* @Description -
* @author liuxd
* @date 2019-07-19 09:20:17
* @version V1.0
*/
public class SystemStamp implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String name;
//公章、财务章、合同章、人名章;
private java.lang.String type;
//;
private java.lang.String belongId;
//;
private java.lang.String context;
//;
private java.util.Date recordTime;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//;
private java.lang.String system_stamp_file;

private String uploadFlag;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setName(java.lang.String  name){
 this.name=name;
}

 public java.lang.String  getName(){
 return this.name;  
}

 public void setType(java.lang.String  type){
 this.type=type;
}

 public java.lang.String  getType(){
 return this.type;  
}

 public void setBelongId(java.lang.String  belongId){
 this.belongId=belongId;
}

 public java.lang.String  getBelongId(){
 return this.belongId;  
}

 public void setContext(java.lang.String  context){
 this.context=context;
}

 public java.lang.String  getContext(){
 return this.context;  
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

 public void setSystem_stamp_file(java.lang.String  system_stamp_file){
 this.system_stamp_file=system_stamp_file;
}

 public java.lang.String  getSystem_stamp_file(){
 return this.system_stamp_file;  
}

public String getUploadFlag() {
	return uploadFlag;
}

public void setUploadFlag(String uploadFlag) {
	this.uploadFlag = uploadFlag;
}
}
