package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 手写签名关联表
* @author liuxd
* @date 2019-01-15 14:53:49
* @version V1.0
*/
public class SystemAppSignRelation implements Serializable {
//;
private java.lang.String id;
//签发id;
private java.lang.String app_sign_id;
//关联id;
private java.lang.String relation_id;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setApp_sign_id(java.lang.String  app_sign_id){
 this.app_sign_id=app_sign_id;
}

 public java.lang.String  getApp_sign_id(){
 return this.app_sign_id;  
}

 public void setRelation_id(java.lang.String  relation_id){
 this.relation_id=relation_id;
}

 public java.lang.String  getRelation_id(){
 return this.relation_id;  
}
}
