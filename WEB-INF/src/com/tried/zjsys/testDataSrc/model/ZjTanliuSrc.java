package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
/**
* @Description -
* @author liuxd
* @date 2020-06-24 22:35:41
* @version V1.0
*/
public class ZjTanliuSrc implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String deviceNum;
private java.lang.String circleId;
//采集数据库名称;
private java.lang.String fileName;
private java.lang.String sample_code;
//样品编号;
private java.lang.String sampleNum;
//分析号;
private java.lang.String analyseNo;
//样品Id;
private java.lang.String sampleId;
//样品名称;
private java.lang.String sampleName;
//用户编号;
private java.lang.String userNo;
//测试日期;
private java.lang.String time;
//取样重量;
private java.lang.String weight;
//测试C结果;
private java.lang.String resultc;
//测试S结果;
private java.lang.String results;
//;
private java.lang.String jzfs_c;
//;
private java.lang.String jzfs_s;
//;
private java.lang.String kbz_s;
//;
private java.lang.String kbz_c;
//;
private java.lang.String bybzz_c;
//;
private java.lang.String bybzz_s;
//;
private java.lang.String jzdp_c;
//;
private java.lang.String jzdp_s;
//;
private java.lang.String peakTime_c;
//;
private java.lang.String peakVal_c;
//;
private java.lang.String anaTime_c;
//;
private java.lang.String peakTime_s;
//;
private java.lang.String peakVal_s;
//;
private java.lang.String anaTime_s;
//;
private java.lang.String area_c;
//;
private java.lang.String area_s;
//;
private java.lang.String count;
//;
private java.lang.String over_c;
//;
private java.lang.String over_s;
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

 public void setCircleId(java.lang.String  circleId){
 this.circleId=circleId;
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
 public java.lang.String  getCircleId(){
 return this.circleId;  
}

 public void setFileName(java.lang.String  fileName){
 this.fileName=fileName;
}

 public java.lang.String  getFileName(){
 return this.fileName;  
}

 public void setSampleNum(java.lang.String  sampleNum){
 this.sampleNum=sampleNum;
}

 public java.lang.String  getSampleNum(){
 return this.sampleNum;  
}

 public void setAnalyseNo(java.lang.String  analyseNo){
 this.analyseNo=analyseNo;
}

 public java.lang.String  getAnalyseNo(){
 return this.analyseNo;  
}

 public void setSampleId(java.lang.String  sampleId){
 this.sampleId=sampleId;
}

 public java.lang.String  getSampleId(){
 return this.sampleId;  
}

 public void setSampleName(java.lang.String  sampleName){
 this.sampleName=sampleName;
}

 public java.lang.String  getSampleName(){
 return this.sampleName;  
}

 public void setUserNo(java.lang.String  userNo){
 this.userNo=userNo;
}

 public java.lang.String  getUserNo(){
 return this.userNo;  
}

 public void setTime(java.lang.String  time){
 this.time=time;
}

 public java.lang.String  getTime(){
 return this.time;  
}

 public void setWeight(java.lang.String  weight){
 this.weight=weight;
}

 public java.lang.String  getWeight(){
 return this.weight;  
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

 public void setJzfs_c(java.lang.String  jzfs_c){
 this.jzfs_c=jzfs_c;
}

 public java.lang.String  getJzfs_c(){
 return this.jzfs_c;  
}

 public void setJzfs_s(java.lang.String  jzfs_s){
 this.jzfs_s=jzfs_s;
}

 public java.lang.String getDeviceNum() {
	return deviceNum;
}

public void setDeviceNum(java.lang.String deviceNum) {
	this.deviceNum = deviceNum;
}

public java.lang.String  getJzfs_s(){
 return this.jzfs_s;  
}

 public void setKbz_s(java.lang.String  kbz_s){
 this.kbz_s=kbz_s;
}

 public java.lang.String  getKbz_s(){
 return this.kbz_s;  
}

 public void setKbz_c(java.lang.String  kbz_c){
 this.kbz_c=kbz_c;
}

 public java.lang.String  getKbz_c(){
 return this.kbz_c;  
}

 public void setBybzz_c(java.lang.String  bybzz_c){
 this.bybzz_c=bybzz_c;
}

 public java.lang.String  getBybzz_c(){
 return this.bybzz_c;  
}

 public void setBybzz_s(java.lang.String  bybzz_s){
 this.bybzz_s=bybzz_s;
}

 public java.lang.String  getBybzz_s(){
 return this.bybzz_s;  
}

 public void setJzdp_c(java.lang.String  jzdp_c){
 this.jzdp_c=jzdp_c;
}

 public java.lang.String  getJzdp_c(){
 return this.jzdp_c;  
}

 public void setJzdp_s(java.lang.String  jzdp_s){
 this.jzdp_s=jzdp_s;
}

 public java.lang.String  getJzdp_s(){
 return this.jzdp_s;  
}

 public void setPeakTime_c(java.lang.String  peakTime_c){
 this.peakTime_c=peakTime_c;
}

 public java.lang.String  getPeakTime_c(){
 return this.peakTime_c;  
}

 public void setPeakVal_c(java.lang.String  peakVal_c){
 this.peakVal_c=peakVal_c;
}

 public java.lang.String  getPeakVal_c(){
 return this.peakVal_c;  
}

 public void setAnaTime_c(java.lang.String  anaTime_c){
 this.anaTime_c=anaTime_c;
}

 public java.lang.String  getAnaTime_c(){
 return this.anaTime_c;  
}

 public void setPeakTime_s(java.lang.String  peakTime_s){
 this.peakTime_s=peakTime_s;
}

 public java.lang.String  getPeakTime_s(){
 return this.peakTime_s;  
}

 public void setPeakVal_s(java.lang.String  peakVal_s){
 this.peakVal_s=peakVal_s;
}

 public java.lang.String  getPeakVal_s(){
 return this.peakVal_s;  
}

 public void setAnaTime_s(java.lang.String  anaTime_s){
 this.anaTime_s=anaTime_s;
}

 public java.lang.String  getAnaTime_s(){
 return this.anaTime_s;  
}

 public void setArea_c(java.lang.String  area_c){
 this.area_c=area_c;
}

 public java.lang.String  getArea_c(){
 return this.area_c;  
}

 public void setArea_s(java.lang.String  area_s){
 this.area_s=area_s;
}

 public java.lang.String  getArea_s(){
 return this.area_s;  
}

 public void setCount(java.lang.String  count){
 this.count=count;
}

 public java.lang.String  getCount(){
 return this.count;  
}

 public void setOver_c(java.lang.String  over_c){
 this.over_c=over_c;
}

 public java.lang.String  getOver_c(){
 return this.over_c;  
}

 public void setOver_s(java.lang.String  over_s){
 this.over_s=over_s;
}

 public java.lang.String  getOver_s(){
 return this.over_s;  
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
