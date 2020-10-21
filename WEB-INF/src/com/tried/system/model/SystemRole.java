package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 角色信息
* @author liuxd
* @date 2015-09-07 10:09:02
* @version V1.0
*/
public class SystemRole implements Serializable {
private java.lang.String id;
//角色名称;
private java.lang.String roleName;
//角色描述;
private java.lang.String roleContent;
//是否启用：是/否;
private java.lang.String flag;

private java.lang.String roleCode;
 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String getRoleCode() {
	return roleCode;
}

public void setRoleCode(java.lang.String roleCode) {
	this.roleCode = roleCode;
}

public java.lang.String  getId(){
 return this.id;  
}

 public void setRoleName(java.lang.String  roleName){
 this.roleName=roleName;
}

 public java.lang.String  getRoleName(){
 return this.roleName;  
}

 public void setRoleContent(java.lang.String  roleContent){
 this.roleContent=roleContent;
}

 public java.lang.String  getRoleContent(){
 return this.roleContent;  
}

 public void setFlag(java.lang.String  flag){
 this.flag=flag;
}

 public java.lang.String  getFlag(){
 return this.flag;  
}
}
