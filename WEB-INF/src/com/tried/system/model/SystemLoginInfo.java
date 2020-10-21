package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 用户登录记录表
* @author liuyw
* @date 2016-11-18 10:44:55
* @version V1.0
*/
public class SystemLoginInfo implements Serializable {
//;
private java.lang.String id;
//登录用户;
private java.lang.String recordUser;
private java.lang.String loginName;
//登录时间;
private java.util.Date recordTime;
//登录IP;
private java.lang.String loginIP;
//;
private java.lang.String loginAddress;
//描述：登录；注销;
private java.lang.String context;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}


public java.lang.String getLoginName() {
	return loginName;
}

public void setLoginName(java.lang.String loginName) {
	this.loginName = loginName;
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

 public void setLoginIP(java.lang.String  loginIP){
 this.loginIP=loginIP;
}

 public java.lang.String  getLoginIP(){
 return this.loginIP;  
}

 public void setLoginAddress(java.lang.String  loginAddress){
 this.loginAddress=loginAddress;
}

 public java.lang.String  getLoginAddress(){
 return this.loginAddress;  
}

 public void setContext(java.lang.String  context){
 this.context=context;
}

 public java.lang.String  getContext(){
 return this.context;  
}
}
