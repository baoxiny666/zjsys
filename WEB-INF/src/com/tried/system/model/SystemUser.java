package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 用户信息
* @author liuxd
* @date 2015-09-09 22:43:36
* @version V1.0
*/
public class SystemUser implements Serializable {
private java.lang.String id;
//登录名;
private java.lang.String loginName;
//登录密码;
private java.lang.String loginPass;
private java.lang.Long loginNum;
//真实姓名;
private java.lang.String userName;
//性别;
private java.lang.String userSex;
//生日;
private java.lang.String userBrithday;
//是否启用：是/否;
private java.lang.String flag;
private java.lang.String loginIP;
//操作时间;
private java.util.Date recordDate;
private java.lang.String recordUser;
private java.lang.String depId;
private java.lang.String depName;
private java.lang.String workId;
private java.lang.String workName;
private java.lang.String passKey;
private java.lang.String parentDepName;
private java.lang.String oldPassword;
private java.lang.String nowPassword;
//电子签名是否已上传
private Long uploadFlag;

private java.lang.String system_user_sign_name;



 

public java.lang.String getSystem_user_sign_name() {
	return system_user_sign_name;
}


public void setSystem_user_sign_name(java.lang.String system_user_sign_name) {
	this.system_user_sign_name = system_user_sign_name;
}


public Long getUploadFlag() {
	return uploadFlag;
}


public void setUploadFlag(Long uploadFlag) {
	this.uploadFlag = uploadFlag;
}


public java.lang.Long getLoginNum() {
	return loginNum;
}


public void setLoginNum(java.lang.Long loginNum) {
	this.loginNum = loginNum;
}


public void setId(java.lang.String  id){
 this.id=id;
}
 

public java.lang.String getParentDepName() {
	return parentDepName;
}


public void setParentDepName(java.lang.String parentDepName) {
	this.parentDepName = parentDepName;
}


public java.lang.String getLoginIP() {
	return loginIP;
}


public void setLoginIP(java.lang.String loginIP) {
	this.loginIP = loginIP;
}


public java.lang.String getPassKey() {
	return passKey;
}


public void setPassKey(java.lang.String passKey) {
	this.passKey = passKey;
}


public java.lang.String getDepId() {
	return depId;
}


public void setDepId(java.lang.String depId) {
	this.depId = depId;
}


public java.lang.String getOldPassword() {
	return oldPassword;
}


public void setOldPassword(java.lang.String oldPassword) {
	this.oldPassword = oldPassword;
}


public java.lang.String getNowPassword() {
	return nowPassword;
}


public void setNowPassword(java.lang.String nowPassword) {
	this.nowPassword = nowPassword;
}


public java.lang.String getDepName() {
	return depName;
}


public void setDepName(java.lang.String depName) {
	this.depName = depName;
}


public java.lang.String getWorkId() {
	return workId;
}

public void setWorkId(java.lang.String workId) {
	this.workId = workId;
}

public java.lang.String getWorkName() {
	return workName;
}

public void setWorkName(java.lang.String workName) {
	this.workName = workName;
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

public java.lang.String getLoginPass() {
	return loginPass;
}

public void setLoginPass(java.lang.String loginPass) {
	this.loginPass = loginPass;
}

public void setUserName(java.lang.String  userName){
 this.userName=userName;
}

 public java.lang.String  getUserName(){
 return this.userName;  
}

 public void setUserSex(java.lang.String  userSex){
 this.userSex=userSex;
}

 public java.lang.String  getUserSex(){
 return this.userSex;  
}

 public void setUserBrithday(java.lang.String  userBrithday){
 this.userBrithday=userBrithday;
}

 public java.lang.String  getUserBrithday(){
 return this.userBrithday;  
}

 public void setFlag(java.lang.String  flag){
 this.flag=flag;
}

 public java.lang.String  getFlag(){
 return this.flag;  
}

 public void setRecordDate(java.util.Date  recordDate){
 this.recordDate=recordDate;
}

 public java.util.Date  getRecordDate(){
 return this.recordDate;  
}

 public void setRecordUser(java.lang.String  recordUser){
 this.recordUser=recordUser;
}

 public java.lang.String  getRecordUser(){
 return this.recordUser;  
}
}
