package com.tried.system.model;
import java.io.Serializable;
import java.util.Date;
/**
* @Description -
* @author sunlunan
* @date 2020-05-15 15:06:34
* @version V1.0
*/
public class SystemSwitchValue implements Serializable {
//;
private java.lang.String id;

private String switchName;
//0:关；1：开；;
private Integer switchState;

private String  switchContext;

private Date recordTime;

private String recordUser;

private String recordUserName;

public java.lang.String getId() {
	return id;
}

public void setId(java.lang.String id) {
	this.id = id;
}

public String getSwitchName() {
	return switchName;
}

public void setSwitchName(String switchName) {
	this.switchName = switchName;
}

public Integer getSwitchState() {
	return switchState;
}

public void setSwitchState(Integer switchState) {
	this.switchState = switchState;
}

public String getSwitchContext() {
	return switchContext;
}

public void setSwitchContext(String switchContext) {
	this.switchContext = switchContext;
}

public Date getRecordTime() {
	return recordTime;
}

public void setRecordTime(Date recordTime) {
	this.recordTime = recordTime;
}

public String getRecordUser() {
	return recordUser;
}

public void setRecordUser(String recordUser) {
	this.recordUser = recordUser;
}

public String getRecordUserName() {
	return recordUserName;
}

public void setRecordUserName(String recordUserName) {
	this.recordUserName = recordUserName;
}

}
