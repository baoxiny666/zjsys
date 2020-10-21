package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
import java.util.Date;
/**
* @Description -
* @author sunlunan
* @date 2020-07-01 09:19:04
* @version V1.0
*/
public class ZjDaiganglixueFly implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String circleId;
//;
private java.lang.String srcId;
//设备编号;
private java.lang.String deviceNum;

//班组;
private java.lang.String classGroup;

private java.lang.String filename;
//日期testTime;
private java.lang.String testTime;
//炉号;
private java.lang.String lunum;
//钢种;
private java.lang.String steeltype;
//规格;
private java.lang.String steelGuige;
//分厂;
private java.lang.String branchFactory;
//下屈服强度;
private java.lang.String yieldDown_streng;
//抗拉强度;
private java.lang.String kangla_streng;
//断后伸长率;
private java.lang.String duanhouLong_rate;
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

 public void setFilename(java.lang.String  filename){
 this.filename=filename;
}

 public java.lang.String  getFilename(){
 return this.filename;  
}

 public void setTestTime(java.lang.String  testTime){
 this.testTime=testTime;
}

 public java.lang.String  getTestTime(){
 return this.testTime;  
}

 public void setLunum(java.lang.String  lunum){
 this.lunum=lunum;
}

 public java.lang.String  getLunum(){
 return this.lunum;  
}

 public void setSteeltype(java.lang.String  steeltype){
 this.steeltype=steeltype;
}

 public java.lang.String  getSteeltype(){
 return this.steeltype;  
}

 public void setSteelGuige(java.lang.String  steelGuige){
 this.steelGuige=steelGuige;
}

 public java.lang.String  getSteelGuige(){
 return this.steelGuige;  
}

 public void setBranchFactory(java.lang.String  branchFactory){
 this.branchFactory=branchFactory;
}

 public java.lang.String  getBranchFactory(){
 return this.branchFactory;  
}

 public void setYieldDown_streng(java.lang.String  yieldDown_streng){
 this.yieldDown_streng=yieldDown_streng;
}

 public java.lang.String  getYieldDown_streng(){
 return this.yieldDown_streng;  
}

 public void setKangla_streng(java.lang.String  kangla_streng){
 this.kangla_streng=kangla_streng;
}

 public java.lang.String  getKangla_streng(){
 return this.kangla_streng;  
}

 public void setDuanhouLong_rate(java.lang.String  duanhouLong_rate){
 this.duanhouLong_rate=duanhouLong_rate;
}

 public java.lang.String  getDuanhouLong_rate(){
 return this.duanhouLong_rate;  
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

public java.lang.String getClassGroup() {
	return classGroup;
}

public void setClassGroup(java.lang.String classGroup) {
	this.classGroup = classGroup;
}

}
