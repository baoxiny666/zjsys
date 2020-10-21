package com.tried.system.model;
import java.io.Serializable;
/**
* @Description 内容修改日志表
* @author liuxd
* @date 2018-07-20 15:11:32
* @version V1.0
*/
public class SystemDataLog implements Serializable {
//;
private java.lang.String id;
//;
private java.lang.String model_id;
//;
private java.lang.String model_name;
//修改通知标题;
private java.lang.String content_title;
//原修改内容;
private java.lang.String content_old;
//修改后内容;
private java.lang.String content_new;
//修改备注;
private java.lang.String content_beizhu;
//修改人;
private java.lang.String recordUser;
//修改时间;
private java.util.Date recordTime;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setModel_id(java.lang.String  model_id){
 this.model_id=model_id;
}

 public java.lang.String  getModel_id(){
 return this.model_id;  
}

 public void setModel_name(java.lang.String  model_name){
 this.model_name=model_name;
}

 public java.lang.String  getModel_name(){
 return this.model_name;  
}

 public void setContent_title(java.lang.String  content_title){
 this.content_title=content_title;
}

 public java.lang.String  getContent_title(){
 return this.content_title;  
}

 public void setContent_old(java.lang.String  content_old){
 this.content_old=content_old;
}

 public java.lang.String  getContent_old(){
 return this.content_old;  
}

 public void setContent_new(java.lang.String  content_new){
 this.content_new=content_new;
}

 public java.lang.String  getContent_new(){
 return this.content_new;  
}

 public void setContent_beizhu(java.lang.String  content_beizhu){
 this.content_beizhu=content_beizhu;
}

 public java.lang.String  getContent_beizhu(){
 return this.content_beizhu;  
}

 public void setRecordUser(java.lang.String  recordUser){
 this.recordUser=recordUser;
}

 public java.lang.String  getRecordUser(){
 return this.recordUser;  
}

 public void setRecordTime(java.util.Date  recordTime){
 this.recordTime=recordTime;
}

 public java.util.Date  getRecordTime(){
 return this.recordTime;  
}
}
