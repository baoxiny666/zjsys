package com.tried.system.model;
import java.io.Serializable;
/**
* @Description -
* @author liuxd
* @date 2018-02-05 23:16:12
* @version V1.0
*/
public class SystemSerial implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String sysType;
//;
private java.lang.String sysStandard;
//;
private java.lang.Integer sysSerialNum;
//;
private java.util.Date recordTime;
//;
private java.lang.String recordUser;
//;
private java.lang.String recordUserName;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String getRecordUserName() {
	return recordUserName;
}

public void setRecordUserName(java.lang.String recordUserName) {
	this.recordUserName = recordUserName;
}

public java.lang.String  getId(){
 return this.id;  
}

 public void setSysType(java.lang.String  sysType){
 this.sysType=sysType;
}

 public java.lang.String  getSysType(){
 return this.sysType;  
}

 public void setSysStandard(java.lang.String  sysStandard){
 this.sysStandard=sysStandard;
}

 public java.lang.String  getSysStandard(){
 return this.sysStandard;  
}

  
 public java.lang.Integer getSysSerialNum() {
	return sysSerialNum;
}

public void setSysSerialNum(java.lang.Integer sysSerialNum) {
	this.sysSerialNum = sysSerialNum;
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
}
