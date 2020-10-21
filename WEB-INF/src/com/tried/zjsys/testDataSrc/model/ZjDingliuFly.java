package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
import java.util.Date;
/**
* @Description -
* @author liuxd
* @date 2020-06-28 21:07:23
* @version V1.0
*/
public class ZjDingliuFly implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String circleId;
//;
private java.lang.String srcId;
//;
private java.lang.String deviceNum;
//样品编号;
private java.lang.String sample_no;
//测试时间 测试日期+开始时间;
private java.lang.String tesDate;
//全硫stad 042判断含量;
private java.lang.String stad_value;
//操作时间;
private Date recordTime;
//操作人;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//数据状态;
private java.lang.String dataStatus;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setCircleId(java.lang.String  circleId){
 this.circleId=circleId;
}

 public java.lang.String  getCircleId(){
 return this.circleId;  
}

 public void setSrcId(java.lang.String  srcId){
 this.srcId=srcId;
}

 public java.lang.String  getSrcId(){
 return this.srcId;  
}

 public void setDeviceNum(java.lang.String  deviceNum){
 this.deviceNum=deviceNum;
}

 public java.lang.String  getDeviceNum(){
 return this.deviceNum;  
}

 public void setSample_no(java.lang.String  sample_no){
 this.sample_no=sample_no;
}

 public java.lang.String  getSample_no(){
 return this.sample_no;  
}

 public void setTesDate(java.lang.String  tesDate){
 this.tesDate=tesDate;
}

 public java.lang.String  getTesDate(){
 return this.tesDate;  
}

 public void setStad_value(java.lang.String  stad_value){
 this.stad_value=stad_value;
}

 public java.lang.String  getStad_value(){
 return this.stad_value;  
}

 

 public Date getRecordTime() {
	return recordTime;
}

public void setRecordTime(Date recordTime) {
	this.recordTime = recordTime;
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

 public void setDataStatus(java.lang.String  dataStatus){
 this.dataStatus=dataStatus;
}

 public java.lang.String  getDataStatus(){
 return this.dataStatus;  
}
}
