package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 表/视图结构
* @author liuxd
* @date 2015-09-21 19:47:17
* @version V1.0
*/
public class SystemTableModel implements Serializable {
private java.lang.String id;
private java.lang.String parentId;
//对象名称;
private java.lang.String objectName;
//TABLE名字;
private java.lang.String objectTable;
//字段名称;
private java.lang.String columnName;
//字段标题;
private java.lang.String columnTitle;
//字段类型
private java.lang.String columnType;
//字段长度
private java.lang.String columnLength;
//是否主键;
private java.lang.String columnPk;
//是否可空
private java.lang.String columnIsNull;
//是否搜索条件;
private java.lang.String columnSearch;
//是否排序;
private java.lang.String columnSort;
//排序;
private java.lang.Float sequence;
private java.util.Date recordDate;
private java.lang.String recordUser;
private java.lang.String pId;
private java.lang.String name;
private java.lang.String packName;
private java.lang.String tableIsCreate;
private java.lang.String pageType;
private java.lang.String objectType;

 public java.lang.String getObjectType() {
	return objectType;
}

public void setObjectType(java.lang.String objectType) {
	this.objectType = objectType;
}

public java.lang.String getPageType() {
	return pageType;
}

public void setPageType(java.lang.String pageType) {
	this.pageType = pageType;
}

public java.lang.String getColumnLength() {
	return columnLength;
}

public void setColumnLength(java.lang.String columnLength) {
	this.columnLength = columnLength;
}

public java.lang.String getColumnType() {
	return columnType;
}

public void setColumnType(java.lang.String columnType) {
	this.columnType = columnType;
}

public java.lang.String getColumnIsNull() {
	return columnIsNull;
}

public void setColumnIsNull(java.lang.String columnIsNull) {
	this.columnIsNull = columnIsNull;
}

public java.lang.String getTableIsCreate() {
	return tableIsCreate;
}

public void setTableIsCreate(java.lang.String tableIsCreate) {
	this.tableIsCreate = tableIsCreate;
}

public java.lang.String getPackName() {
	return packName;
}

public void setPackName(java.lang.String packName) {
	this.packName = packName;
}

public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String getName() {
	return objectName;
}

public java.lang.String  getId(){
 return this.id;  
}

 public void setParentId(java.lang.String  parentId){
 this.parentId=parentId;
}

 public java.lang.String  getParentId(){
 return this.parentId;  
}

 public void setObjectName(java.lang.String  objectName){
 this.objectName=objectName;
}

 public java.lang.String  getObjectName(){
 return this.objectName;  
}

 public void setObjectTable(java.lang.String  objectTable){
 this.objectTable=objectTable;
}

 public java.lang.String  getObjectTable(){
 return this.objectTable;  
}

 public void setColumnName(java.lang.String  columnName){
 this.columnName=columnName;
}

 public java.lang.String  getColumnName(){
 return this.columnName;  
}

 public void setColumnTitle(java.lang.String  columnTitle){
 this.columnTitle=columnTitle;
}

 public java.lang.String  getColumnTitle(){
 return this.columnTitle;  
}

 public void setColumnPk(java.lang.String  columnPk){
 this.columnPk=columnPk;
}

 public java.lang.String  getColumnPk(){
 return this.columnPk;  
}

 public void setColumnSearch(java.lang.String  columnSearch){
 this.columnSearch=columnSearch;
}

 public java.lang.String  getColumnSearch(){
 return this.columnSearch;  
}

 public void setColumnSort(java.lang.String  columnSort){
 this.columnSort=columnSort;
}

 public java.lang.String  getColumnSort(){
 return this.columnSort;  
}

 public void setSequence(java.lang.Float  sequence){
 this.sequence=sequence;
}

 public java.lang.Float  getSequence(){
 return this.sequence;  
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

 public java.lang.String  getpId(){
 return this.parentId;  
}
}
