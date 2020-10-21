package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 部门信息
* @author liuxd
* @date 2015-09-07 23:12:52
* @version V1.0
*/
public class SystemDepartment implements Serializable {
private java.lang.String id;
//公司/部门名称;
private java.lang.String name;
//部门描述;
private java.lang.String departmentContent;
//父id;
private java.lang.String parentId;
//是否启用：是/否;
private java.lang.Float sequence;
private java.lang.String flag;
private java.lang.String pId;

private java.lang.String dataType;

 public java.lang.String getDataType() {
	return dataType;
}


public void setDataType(java.lang.String dataType) {
	this.dataType = dataType;
}


public java.lang.String getpId() {
	return parentId;
}


public java.lang.Float getSequence() {
	return sequence;
}


public void setSequence(java.lang.Float sequence) {
	this.sequence = sequence;
}


public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setName(java.lang.String  name){
 this.name=name;
}

 public java.lang.String  getName(){
 return this.name;  
}

 public void setDepartmentContent(java.lang.String  departmentContent){
 this.departmentContent=departmentContent;
}

 public java.lang.String  getDepartmentContent(){
 return this.departmentContent;  
}

 public void setParentId(java.lang.String  parentId){
 this.parentId=parentId;
}

 public java.lang.String  getParentId(){
 return this.parentId;  
}

 public void setFlag(java.lang.String  flag){
 this.flag=flag;
}

 public java.lang.String  getFlag(){
 return this.flag;  
}
}
