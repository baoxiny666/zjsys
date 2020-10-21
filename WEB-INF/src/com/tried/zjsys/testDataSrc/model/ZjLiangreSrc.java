package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
import java.util.Date;
/**
* @Description -
* @author lyw
* @date 2020-06-28 21:31:05
* @version V1.0
*/
public class ZjLiangreSrc implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String circleId;
//数据库表名;
private java.lang.String fileName;
//设备编号
private String deviceNum;
//仪器编号
private String test_yqNum;
//测试日期;
private java.lang.String testDate;
//开始时间;
private java.lang.String startTime;
//试样编号;
private java.lang.String sampleNum;
private java.lang.String sample_code;
//试样名称;
private java.lang.String sampleName;
//试样重量;
private java.lang.String test_weight;
//测试苯甲酸;
private java.lang.String test_jbs;
//主期温升;
private java.lang.String test_zqws;
//冷却矫正;
private java.lang.String test_lrjz;
//弹筒发热量;
private java.lang.String test_ttfrValue;
//空干机高位;
private java.lang.String test_kgjgw;
//空干机低微;
private java.lang.String test_sdjdw;
//点火温度;
private java.lang.String test_dhwd;
//终点温度;
private java.lang.String test_zdwd;
//仪器热容量;
private java.lang.String test_devicerol;
//点火丝热值;
private java.lang.String test_dhsrz;
//剩余点火丝;
private java.lang.String test_sydhs;
//添加物重量;
private java.lang.String test_tjwzl;
//添加物热值;
private java.lang.String test_tjwrz;
//控干基水分;
private java.lang.String test_kgjsf;
//控干基灰分;
private java.lang.String test_kgjhfen;
//控干基挥发;
private java.lang.String test_kgjhfa;
//空干机氢含;
private java.lang.String test_kgjqh;
//空干机硫含;
private java.lang.String test_kgjlh;
//送样单位;
private java.lang.String test_sampleDept;
//器皿编号;
private java.lang.String test_qmnum;
//化验员;
private java.lang.String huayanPerson;
//上传时间;
private Date recordTime;
//上传人;
private java.lang.String recordUser;
private java.lang.String recordUserName;
//数据类型;
private java.lang.String dataStatus;

 public void setId(java.lang.String  id){
 this.id=id;
}
 public java.lang.String getSample_code() {
	 if(this.sampleNum.length()>4){
		 return sampleNum.substring(0,4);
	 }
	return sample_code;
}

public void setSample_code(java.lang.String sample_code) {
	this.sample_code = sample_code;
}
 public java.lang.String  getId(){
 return this.id;  
}

 public String getTest_yqNum() {
	return test_yqNum;
}

public void setTest_yqNum(String test_yqNum) {
	this.test_yqNum = test_yqNum;
}

public String getDeviceNum() {
	return deviceNum;
}

public void setDeviceNum(String deviceNum) {
	this.deviceNum = deviceNum;
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

 public void setTestDate(java.lang.String  testDate){
 this.testDate=testDate;
}

 public java.lang.String  getTestDate(){
 return this.testDate;  
}

 public void setStartTime(java.lang.String  startTime){
 this.startTime=startTime;
}

 public java.lang.String  getStartTime(){
 return this.startTime;  
}

 public void setSampleNum(java.lang.String  sampleNum){
 this.sampleNum=sampleNum;
}

 public java.lang.String  getSampleNum(){
 return this.sampleNum;  
}

 public void setSampleName(java.lang.String  sampleName){
 this.sampleName=sampleName;
}

 public java.lang.String  getSampleName(){
 return this.sampleName;  
}

 public void setTest_weight(java.lang.String  test_weight){
 this.test_weight=test_weight;
}

 public java.lang.String  getTest_weight(){
 return this.test_weight;  
}

 public void setTest_jbs(java.lang.String  test_jbs){
 this.test_jbs=test_jbs;
}

 public java.lang.String  getTest_jbs(){
 return this.test_jbs;  
}

 public void setTest_zqws(java.lang.String  test_zqws){
 this.test_zqws=test_zqws;
}

 public java.lang.String  getTest_zqws(){
 return this.test_zqws;  
}

 public void setTest_lrjz(java.lang.String  test_lrjz){
 this.test_lrjz=test_lrjz;
}

 public java.lang.String  getTest_lrjz(){
 return this.test_lrjz;  
}

 public void setTest_ttfrValue(java.lang.String  test_ttfrValue){
 this.test_ttfrValue=test_ttfrValue;
}

 public java.lang.String  getTest_ttfrValue(){
 return this.test_ttfrValue;  
}

 public void setTest_kgjgw(java.lang.String  test_kgjgw){
 this.test_kgjgw=test_kgjgw;
}

 public java.lang.String  getTest_kgjgw(){
 return this.test_kgjgw;  
}

 public void setTest_sdjdw(java.lang.String  test_sdjdw){
 this.test_sdjdw=test_sdjdw;
}

 public java.lang.String  getTest_sdjdw(){
 return this.test_sdjdw;  
}

 public void setTest_dhwd(java.lang.String  test_dhwd){
 this.test_dhwd=test_dhwd;
}

 public java.lang.String  getTest_dhwd(){
 return this.test_dhwd;  
}

 public void setTest_zdwd(java.lang.String  test_zdwd){
 this.test_zdwd=test_zdwd;
}

 public java.lang.String  getTest_zdwd(){
 return this.test_zdwd;  
}

 public void setTest_devicerol(java.lang.String  test_devicerol){
 this.test_devicerol=test_devicerol;
}

 public java.lang.String  getTest_devicerol(){
 return this.test_devicerol;  
}

 public void setTest_dhsrz(java.lang.String  test_dhsrz){
 this.test_dhsrz=test_dhsrz;
}

 public java.lang.String  getTest_dhsrz(){
 return this.test_dhsrz;  
}

 public void setTest_sydhs(java.lang.String  test_sydhs){
 this.test_sydhs=test_sydhs;
}

 public java.lang.String  getTest_sydhs(){
 return this.test_sydhs;  
}

 public void setTest_tjwzl(java.lang.String  test_tjwzl){
 this.test_tjwzl=test_tjwzl;
}

 public java.lang.String  getTest_tjwzl(){
 return this.test_tjwzl;  
}

 public void setTest_tjwrz(java.lang.String  test_tjwrz){
 this.test_tjwrz=test_tjwrz;
}

 public java.lang.String  getTest_tjwrz(){
 return this.test_tjwrz;  
}

 public void setTest_kgjsf(java.lang.String  test_kgjsf){
 this.test_kgjsf=test_kgjsf;
}

 public java.lang.String  getTest_kgjsf(){
 return this.test_kgjsf;  
}

 public void setTest_kgjhfen(java.lang.String  test_kgjhfen){
 this.test_kgjhfen=test_kgjhfen;
}

 public java.lang.String  getTest_kgjhfen(){
 return this.test_kgjhfen;  
}

 public void setTest_kgjhfa(java.lang.String  test_kgjhfa){
 this.test_kgjhfa=test_kgjhfa;
}

 public java.lang.String  getTest_kgjhfa(){
 return this.test_kgjhfa;  
}

 public void setTest_kgjqh(java.lang.String  test_kgjqh){
 this.test_kgjqh=test_kgjqh;
}

 public java.lang.String  getTest_kgjqh(){
 return this.test_kgjqh;  
}

 public void setTest_kgjlh(java.lang.String  test_kgjlh){
 this.test_kgjlh=test_kgjlh;
}

 public java.lang.String  getTest_kgjlh(){
 return this.test_kgjlh;  
}

 public void setTest_sampleDept(java.lang.String  test_sampleDept){
 this.test_sampleDept=test_sampleDept;
}

 public java.lang.String  getTest_sampleDept(){
 return this.test_sampleDept;  
}

 public void setTest_qmnum(java.lang.String  test_qmnum){
 this.test_qmnum=test_qmnum;
}

 public java.lang.String  getTest_qmnum(){
 return this.test_qmnum;  
}

 public void setHuayanPerson(java.lang.String  huayanPerson){
 this.huayanPerson=huayanPerson;
}

 public java.lang.String  getHuayanPerson(){
 return this.huayanPerson;  
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
