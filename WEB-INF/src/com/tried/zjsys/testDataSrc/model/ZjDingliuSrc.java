package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
/**
* @Description -
* @author liuxd
* @date 2020-06-28 21:07:24
* @version V1.0
*/
public class ZjDingliuSrc implements Serializable {
//;
private java.lang.String id;
private java.lang.String autoNo;
private java.lang.String deviceNum;
//;
private java.lang.String circleId;
//采集数据库名称;
private java.lang.String fileName;
//测试类型;
private java.lang.String testType;
//测试日期;
private java.lang.String tesDate;
//;
private java.lang.String startTime;
//;
private java.lang.String endTime;
//;
private java.lang.String costTime;
//样品编号;
private java.lang.String sample_no;
//样品代码
private java.lang.String sample_code;
//样重(mg);
private java.lang.String test_weight;
//全硫St,ad(%);
private java.lang.String test_value;
//分析水分Mad(%);
private java.lang.String test_mad;
//St,d(%);
private java.lang.String test_std;
//分析基硫;
private java.lang.String test_stad;
//;
private java.lang.String test_coef;
//;
private java.lang.String test_id;
//试验类型;
private java.lang.String test_type;
//;
private java.lang.String test_sydw;
//;
private java.lang.String test_yqid;
//;
private java.util.Date recordTime;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//数据类型;
private java.lang.String dataStatus;
private Integer testIndex;
 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String getSample_code() {
	 if(this.sample_no.length()>4){
		 return sample_no.substring(0,4);
	 }
	return sample_code;
}

public void setSample_code(java.lang.String sample_code) {
	this.sample_code = sample_code;
}

public java.lang.String getDeviceNum() {
	return deviceNum;
}

public void setDeviceNum(java.lang.String deviceNum) {
	this.deviceNum = deviceNum;
}

 

public Integer getTestIndex() {
	return testIndex;
}

public void setTestIndex(Integer testIndex) {
	this.testIndex = testIndex;
}

public java.lang.String getAutoNo() {
	return autoNo;
}

public void setAutoNo(java.lang.String autoNo) {
	this.autoNo = autoNo;
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

 public void setFileName(java.lang.String  fileName){
 this.fileName=fileName;
}

 public java.lang.String  getFileName(){
 return this.fileName;  
}

 public void setTestType(java.lang.String  testType){
 this.testType=testType;
}

 public java.lang.String  getTestType(){
 return this.testType;  
}

 public void setTesDate(java.lang.String  tesDate){
 this.tesDate=tesDate;
}

 public java.lang.String  getTesDate(){
 return this.tesDate;  
}

 public void setStartTime(java.lang.String  startTime){
 this.startTime=startTime;
}

 public java.lang.String  getStartTime(){
 return this.startTime;  
}

 public void setEndTime(java.lang.String  endTime){
 this.endTime=endTime;
}

 public java.lang.String  getEndTime(){
 return this.endTime;  
}

 public void setCostTime(java.lang.String  costTime){
 this.costTime=costTime;
}

 public java.lang.String  getCostTime(){
 return this.costTime;  
}

 public void setSample_no(java.lang.String  sample_no){
 this.sample_no=sample_no;
}

 public java.lang.String  getSample_no(){
 return this.sample_no;  
}

 public void setTest_weight(java.lang.String  test_weight){
 this.test_weight=test_weight;
}

 public java.lang.String  getTest_weight(){
 return this.test_weight;  
}

 public void setTest_value(java.lang.String  test_value){
 this.test_value=test_value;
}

 public java.lang.String  getTest_value(){
 return this.test_value;  
}

 public void setTest_mad(java.lang.String  test_mad){
 this.test_mad=test_mad;
}

 public java.lang.String  getTest_mad(){
 return this.test_mad;  
}

 public void setTest_std(java.lang.String  test_std){
 this.test_std=test_std;
}

 public java.lang.String  getTest_std(){
 return this.test_std;  
}

 public void setTest_stad(java.lang.String  test_stad){
 this.test_stad=test_stad;
}

 public java.lang.String  getTest_stad(){
 return this.test_stad;  
}

 public void setTest_coef(java.lang.String  test_coef){
 this.test_coef=test_coef;
}

 public java.lang.String  getTest_coef(){
 return this.test_coef;  
}

 public void setTest_id(java.lang.String  test_id){
 this.test_id=test_id;
}

 public java.lang.String  getTest_id(){
 return this.test_id;  
}

 public void setTest_type(java.lang.String  test_type){
 this.test_type=test_type;
}

 public java.lang.String  getTest_type(){
 return this.test_type;  
}

 public void setTest_sydw(java.lang.String  test_sydw){
 this.test_sydw=test_sydw;
}

 public java.lang.String  getTest_sydw(){
 return this.test_sydw;  
}

 public void setTest_yqid(java.lang.String  test_yqid){
 this.test_yqid=test_yqid;
}

 public java.lang.String  getTest_yqid(){
 return this.test_yqid;  
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
