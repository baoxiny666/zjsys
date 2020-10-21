package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
/**
* @Description -
* @author liuxd
* @date 2020-06-24 22:35:40
* @version V1.0
*/
public class ZjTanliuFly implements Serializable {
//;
private java.lang.String id;
//数据源ID;
private java.lang.String srcId;

private java.lang.String sampleId;
//配置文件ID;
private java.lang.String circleId;
//设备编号;
private java.lang.String deviceNum;
//样品编码;
private java.lang.String sampleNum;
//日期;
private java.lang.String time;
//Result_C;
private java.lang.String resultc;
//Result_S;
private java.lang.String results;
//;
private java.util.Date recordTime;
//;
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

 public java.lang.String getSampleId() {
	return sampleId;
}

public void setSampleId(java.lang.String sampleId) {
	this.sampleId = sampleId;
}

public void setSrcId(java.lang.String  srcId){
 this.srcId=srcId;
}

 public java.lang.String  getSrcId(){
 return this.srcId;  
}

 public void setCircleId(java.lang.String  circleId){
 this.circleId=circleId;
}

 public java.lang.String  getCircleId(){
 return this.circleId;  
}

 public void setDeviceNum(java.lang.String  deviceNum){
 this.deviceNum=deviceNum;
}

 public java.lang.String  getDeviceNum(){
 return this.deviceNum;  
}

 public void setSampleNum(java.lang.String  sampleNum){
 this.sampleNum=sampleNum;
}

 public java.lang.String  getSampleNum(){
 return this.sampleNum;  
}

 public void setTime(java.lang.String  time){
 this.time=time;
}

 public java.lang.String  getTime(){
 return this.time;  
}

 public void setResultc(java.lang.String  resultc){
 this.resultc=resultc;
}

 public java.lang.String  getResultc(){
 return this.resultc;  
}

 public void setResults(java.lang.String  results){
 this.results=results;
}

 public java.lang.String  getResults(){
 return this.results;  
}

 

 public java.util.Date getRecordTime() {
	return recordTime;
}

public void setRecordTime(java.util.Date recordTime) {
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
