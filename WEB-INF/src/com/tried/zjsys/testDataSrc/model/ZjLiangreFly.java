package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
import java.util.Date;
/**
* @Description -
* @author lyw
* @date 2020-06-28 21:31:05
* @version V1.0
*/
public class ZjLiangreFly implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String srcId;
//;
private java.lang.String circleId;
//仪器编号
private java.lang.String yqNum;
//测试日期+开始日期;
private java.lang.String testDate;
//样品编号;
private java.lang.String sampleNum;
//弹筒发热量;
private java.lang.String test_ttfrValue;
//;
private Date recordTime;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//;
private java.lang.String test_kgjgw;
private java.lang.String test_sdjdw;
private java.lang.String dataStatus;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public java.lang.String getTest_kgjgw() {
	return test_kgjgw;
}

public void setTest_kgjgw(java.lang.String test_kgjgw) {
	this.test_kgjgw = test_kgjgw;
}

public java.lang.String getTest_sdjdw() {
	return test_sdjdw;
}

public void setTest_sdjdw(java.lang.String test_sdjdw) {
	this.test_sdjdw = test_sdjdw;
}

public void setSrcId(java.lang.String  srcId){
 this.srcId=srcId;
}

 public java.lang.String  getSrcId(){
 return this.srcId;  
}

 public java.lang.String getYqNum() {
	return yqNum;
}

public void setYqNum(java.lang.String yqNum) {
	this.yqNum = yqNum;
}

public void setCircleId(java.lang.String  circleId){
 this.circleId=circleId;
}

 public java.lang.String  getCircleId(){
 return this.circleId;  
}

 public void setTestDate(java.lang.String  testDate){
 this.testDate=testDate;
}

 public java.lang.String  getTestDate(){
 return this.testDate;  
}

 public void setSampleNum(java.lang.String  sampleNum){
 this.sampleNum=sampleNum;
}

 public java.lang.String  getSampleNum(){
 return this.sampleNum;  
}

 public void setTest_ttfrValue(java.lang.String  test_ttfrValue){
 this.test_ttfrValue=test_ttfrValue;
}

 public java.lang.String  getTest_ttfrValue(){
 return this.test_ttfrValue;  
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
