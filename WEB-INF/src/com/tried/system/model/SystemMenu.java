package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 功能管理
* @author liuxd
* @date 2015-12-25 16:08:34
* @version V1.0
*/
public class SystemMenu implements Serializable {
private java.lang.String id;
//父id;
private java.lang.String parentId;
//功能名称;
private java.lang.String name;
//功能图片;
private java.lang.String address;
//功能路径;
private java.lang.String icon;
//功能描述;
private java.lang.String menuContent;
//功能位置;
private java.lang.Float sequence;
//是否启用：是/否;
private java.lang.String flag;
//树形结构父id
private java.lang.String pId;
//临时id
private java.lang.String tempId;
private boolean checked=false;
public boolean isChecked() {
	return checked;
}

public void setChecked(boolean checked) {
	this.checked = checked;
}

public java.lang.String getTempId() {
	return tempId;
}

public void setTempId(java.lang.String tempId) {
	this.tempId = tempId;
}

public void setId(java.lang.String  id){
 this.id=id;
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

 public void setName(java.lang.String  name){
 this.name=name;
}

 public java.lang.String  getName(){
 return this.name;  
}

 public void setAddress(java.lang.String  address){
 this.address=address;
}

 public java.lang.String  getAddress(){
 return this.address;  
}

 public void setIcon(java.lang.String  icon){
 this.icon=icon;
}

 public java.lang.String  getIcon(){
 return this.icon;  
}

 public void setMenuContent(java.lang.String  menuContent){
 this.menuContent=menuContent;
}

 public java.lang.String  getMenuContent(){
 return this.menuContent;  
}

 public void setSequence(java.lang.Float  sequence){
 this.sequence=sequence;
}

 public java.lang.Float  getSequence(){
 return this.sequence;  
}

 public void setFlag(java.lang.String  flag){
 this.flag=flag;
}

 public java.lang.String  getFlag(){
 return this.flag;  
}

 public java.lang.String  getpId(){
 return this.parentId;  
}
}
