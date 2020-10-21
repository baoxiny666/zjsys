package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
import java.util.Date;
/**
* @Description -
* @author sunlunan
* @date 2020-06-23 09:30:34
* @version V1.0
*/
public class YinguangSrc implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String circleId;
//采集数据库名称;
private java.lang.String fileName;
//样品编号;
private java.lang.String sampleNum;
private java.lang.String sample_code;
//样品名称;
private java.lang.String sampleName;
//归属;
private java.lang.String begroup;
//日期（数据中时间）;
private java.lang.String dataTime;
//结果（%）;
private java.lang.String test_TFe;
//分析物;
private java.lang.String test_SiO2;
//;
private java.lang.String test_CaO;
//;
private java.lang.String test_MgO;
//;
private java.lang.String test_Al2O3;
//;
private java.lang.String test_MnO;
//;
private java.lang.String test_TiO2;
//;
private java.lang.String test_V2O5;
//;
private java.lang.String test_P;
//;
private java.lang.String test_S;
//;
private java.lang.String test_K2O;
//;
private java.lang.String test_Na2O;
//;
private java.lang.String test_Co2O3;
//;
private java.lang.String test_Pb;
//;
private java.lang.String test_As;
//;
private java.lang.String test_Zn;
//;
private java.lang.String test_Cu;
//;
private java.lang.String test_Ni;
//;
private java.lang.String test_Cr2O3;
//;
private java.lang.String test_Au2O;
//;
private java.lang.String test_R2;
//;
private java.lang.String test_Fe2O3;

private java.lang.String test_R;
private java.lang.String test_P2O5;
private java.lang.String test_S2;
private java.lang.String test_Si;
private java.lang.String test_Mn;
private java.lang.String test_Ti;
private java.lang.String test_TMn;
private java.lang.String test_FeO;


//;
private Date recordTime;
//;
private java.lang.String recordUser;
private java.lang.String recordUserName;

private String dataStatus;
//设备编号
private String deviceNum;
//设备名称
private String deviceName;

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

 public void setCircleId(java.lang.String  circleId){
 this.circleId=circleId;
}

 public java.lang.String  getCircleId(){
 return this.circleId;  
}
 public java.lang.String getFileName() {
	return fileName;
}

public void setFileName(java.lang.String fileName) {
	this.fileName = fileName;
}

public Date getRecordTime() {
	return recordTime;
}

public void setRecordTime(Date recordTime) {
	this.recordTime = recordTime;
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

 public void setBegroup(java.lang.String  begroup){
 this.begroup=begroup;
}

 public java.lang.String  getBegroup(){
 return this.begroup;  
}

 public void setDataTime(java.lang.String  dataTime){
 this.dataTime=dataTime;
}

 public java.lang.String  getDataTime(){
 return this.dataTime;  
}
public java.lang.String getTest_TFe() {
	return test_TFe;
}

public void setTest_TFe(java.lang.String test_TFe) {
	this.test_TFe = test_TFe;
}

public java.lang.String getTest_SiO2() {
	return test_SiO2;
}

public void setTest_SiO2(java.lang.String test_SiO2) {
	this.test_SiO2 = test_SiO2;
}

public java.lang.String getTest_CaO() {
	return test_CaO;
}

public void setTest_CaO(java.lang.String test_CaO) {
	this.test_CaO = test_CaO;
}

public java.lang.String getTest_MgO() {
	return test_MgO;
}

public void setTest_MgO(java.lang.String test_MgO) {
	this.test_MgO = test_MgO;
}

public java.lang.String getTest_Al2O3() {
	return test_Al2O3;
}

public void setTest_Al2O3(java.lang.String test_Al2O3) {
	this.test_Al2O3 = test_Al2O3;
}

public java.lang.String getTest_MnO() {
	return test_MnO;
}

public void setTest_MnO(java.lang.String test_MnO) {
	this.test_MnO = test_MnO;
}

public java.lang.String getTest_TiO2() {
	return test_TiO2;
}

public void setTest_TiO2(java.lang.String test_TiO2) {
	this.test_TiO2 = test_TiO2;
}

public java.lang.String getTest_V2O5() {
	return test_V2O5;
}

public void setTest_V2O5(java.lang.String test_V2O5) {
	this.test_V2O5 = test_V2O5;
}

public java.lang.String getTest_P() {
	return test_P;
}

public void setTest_P(java.lang.String test_P) {
	this.test_P = test_P;
}

public java.lang.String getTest_S() {
	return test_S;
}

public void setTest_S(java.lang.String test_S) {
	this.test_S = test_S;
}

public java.lang.String getTest_K2O() {
	return test_K2O;
}

public void setTest_K2O(java.lang.String test_K2O) {
	this.test_K2O = test_K2O;
}

public java.lang.String getTest_Na2O() {
	return test_Na2O;
}

public void setTest_Na2O(java.lang.String test_Na2O) {
	this.test_Na2O = test_Na2O;
}

public java.lang.String getTest_Co2O3() {
	return test_Co2O3;
}

public void setTest_Co2O3(java.lang.String test_Co2O3) {
	this.test_Co2O3 = test_Co2O3;
}

public java.lang.String getTest_Pb() {
	return test_Pb;
}

public void setTest_Pb(java.lang.String test_Pb) {
	this.test_Pb = test_Pb;
}

public java.lang.String getTest_As() {
	return test_As;
}

public void setTest_As(java.lang.String test_As) {
	this.test_As = test_As;
}

public java.lang.String getTest_Zn() {
	return test_Zn;
}

public void setTest_Zn(java.lang.String test_Zn) {
	this.test_Zn = test_Zn;
}

public java.lang.String getTest_Cu() {
	return test_Cu;
}

public void setTest_Cu(java.lang.String test_Cu) {
	this.test_Cu = test_Cu;
}

public java.lang.String getTest_Ni() {
	return test_Ni;
}

public void setTest_Ni(java.lang.String test_Ni) {
	this.test_Ni = test_Ni;
}

public java.lang.String getTest_Cr2O3() {
	return test_Cr2O3;
}

public void setTest_Cr2O3(java.lang.String test_Cr2O3) {
	this.test_Cr2O3 = test_Cr2O3;
}

public java.lang.String getTest_Au2O() {
	return test_Au2O;
}

public void setTest_Au2O(java.lang.String test_Au2O) {
	this.test_Au2O = test_Au2O;
}

public java.lang.String getTest_R2() {
	return test_R2;
}

public void setTest_R2(java.lang.String test_R2) {
	this.test_R2 = test_R2;
}

public java.lang.String getTest_Fe2O3() {
	return test_Fe2O3;
}

public void setTest_Fe2O3(java.lang.String test_Fe2O3) {
	this.test_Fe2O3 = test_Fe2O3;
}


public java.lang.String getTest_R() {
	return test_R;
}

public void setTest_R(java.lang.String test_R) {
	this.test_R = test_R;
}

public java.lang.String getTest_P2O5() {
	return test_P2O5;
}

public void setTest_P2O5(java.lang.String test_P2O5) {
	this.test_P2O5 = test_P2O5;
}

public java.lang.String getTest_S2() {
	return test_S2;
}

public void setTest_S2(java.lang.String test_S2) {
	this.test_S2 = test_S2;
}

public java.lang.String getTest_Si() {
	return test_Si;
}

public void setTest_Si(java.lang.String test_Si) {
	this.test_Si = test_Si;
}

public java.lang.String getTest_Mn() {
	return test_Mn;
}

public void setTest_Mn(java.lang.String test_Mn) {
	this.test_Mn = test_Mn;
}

public java.lang.String getTest_Ti() {
	return test_Ti;
}

public void setTest_Ti(java.lang.String test_Ti) {
	this.test_Ti = test_Ti;
}

public java.lang.String getTest_TMn() {
	return test_TMn;
}

public void setTest_TMn(java.lang.String test_TMn) {
	this.test_TMn = test_TMn;
}

public java.lang.String getTest_FeO() {
	return test_FeO;
}

public void setTest_FeO(java.lang.String test_FeO) {
	this.test_FeO = test_FeO;
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

public String getDeviceNum() {
	return deviceNum;
}

public void setDeviceNum(String deviceNum) {
	this.deviceNum = deviceNum;
}

public String getDeviceName() {
	return deviceName;
}

public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
}

public String getDataStatus() {
	return dataStatus;
}

public void setDataStatus(String dataStatus) {
	this.dataStatus = dataStatus;
}
 
}
