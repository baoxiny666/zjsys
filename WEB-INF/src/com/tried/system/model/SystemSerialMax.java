package com.tried.system.model;
import java.io.Serializable;
/**
* @Description -
* @author liuxd
* @date 2019-03-01 10:59:02
* @version V1.0
*/
public class SystemSerialMax implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String serialId;
//对应value;
private java.lang.String sysStandardValue;
//;
private java.lang.Integer sysSerialNum;
//;
private java.util.Date recordTime;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setSerialId(java.lang.String  serialId){
 this.serialId=serialId;
}

 public java.lang.String  getSerialId(){
 return this.serialId;  
}

 public void setSysStandardValue(java.lang.String  sysStandardValue){
 this.sysStandardValue=sysStandardValue;
}

 public java.lang.String  getSysStandardValue(){
 return this.sysStandardValue;  
}

 public void setSysSerialNum(java.lang.Integer  sysSerialNum){
 this.sysSerialNum=sysSerialNum;
}

 public java.lang.Integer  getSysSerialNum(){
 return this.sysSerialNum;  
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
