package com.tried.zjsys.basics.model;
import java.io.Serializable;
import java.sql.Connection;
/**
* @Description 同步周期管理
* @author liuxd
* @date 2020-06-19 22:35:52
* @version V1.0
*/
public class DataCircle implements Serializable {
//;
private java.lang.String id;
//设备型号;
private java.lang.String deviceNum;
//设备名称;
private java.lang.String deviceName;
//设备型号;
private java.lang.String deviceType;
//存储类型，TEXT、数据库、EXCEL;
private java.lang.String dataType;
//存储路径;
private java.lang.String dataSavePath;
//jdbcUrl地址;
private java.lang.String jdbcUrl;
//驱动;
private java.lang.String driverClass;
//账户;
private java.lang.String username;
//密码;
private java.lang.String password;
//时间单位、秒、分、时;
private java.lang.String circleTimeType;
//循环周期;
private java.lang.Long circleTimeNum;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//;
private java.util.Date recordTime;
//ERP远程路径;
private java.lang.String remoteUrlPath;
 public void setId(java.lang.String  id){
 this.id=id;
}
 

public java.lang.String  getId(){
 return this.id;  
}

 public void setDeviceNum(java.lang.String  deviceNum){
 this.deviceNum=deviceNum;
}

 public java.lang.String  getDeviceNum(){
 return this.deviceNum;  
}

 public void setDeviceName(java.lang.String  deviceName){
 this.deviceName=deviceName;
}

 public java.lang.String  getDeviceName(){
 return this.deviceName;  
}

 public void setDeviceType(java.lang.String  deviceType){
 this.deviceType=deviceType;
}

 public java.lang.String  getDeviceType(){
 return this.deviceType;  
}

 public void setDataType(java.lang.String  dataType){
 this.dataType=dataType;
}

 public java.lang.String  getDataType(){
 return this.dataType;  
}

 public void setDataSavePath(java.lang.String  dataSavePath){
 this.dataSavePath=dataSavePath;
}

 public java.lang.String  getDataSavePath(){
 return this.dataSavePath;  
}

 public void setJdbcUrl(java.lang.String  jdbcUrl){
 this.jdbcUrl=jdbcUrl;
}

 public java.lang.String  getJdbcUrl(){
 return this.jdbcUrl;  
}

 public void setDriverClass(java.lang.String  driverClass){
 this.driverClass=driverClass;
}

 public java.lang.String  getDriverClass(){
 return this.driverClass;  
}

 public void setUsername(java.lang.String  username){
 this.username=username;
}

 public java.lang.String  getUsername(){
 return this.username;  
}

 public void setPassword(java.lang.String  password){
 this.password=password;
}

 public java.lang.String  getPassword(){
 return this.password;  
}

 public void setCircleTimeType(java.lang.String  circleTimeType){
 this.circleTimeType=circleTimeType;
}

 public java.lang.String  getCircleTimeType(){
 return this.circleTimeType;  
}

 public void setCircleTimeNum(java.lang.Long  circleTimeNum){
 this.circleTimeNum=circleTimeNum;
}

 public java.lang.Long  getCircleTimeNum(){
 return this.circleTimeNum;  
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

 public void setRecordTime(java.util.Date  recordTime){
 this.recordTime=recordTime;
}

 public java.util.Date  getRecordTime(){
 return this.recordTime;  
}

 public void setRemoteUrlPath(java.lang.String  remoteUrlPath){
 this.remoteUrlPath=remoteUrlPath;
}

 public java.lang.String  getRemoteUrlPath(){
 return this.remoteUrlPath;  
}
}
