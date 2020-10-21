package com.tried.system.model;

import java.io.Serializable;

/**
 * @Description 消息管理
 * @author liuxd
 * @date 2018-07-20 15:11:45
 * @version V1.0
 */
public class SystemMessage implements Serializable {
	// ;
	private java.lang.String id;
	// ;
	private java.lang.String message_title;
	// ;
	private java.lang.String message_content;
	// ;
	private java.lang.String message_file;
	// ;
	private java.lang.String recordUser;
	// ;
	private java.util.Date recordTime;

	private java.lang.String message_receiveUser;

	private java.lang.String message_receiveUserName;

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getMessage_receiveUserName() {
		return message_receiveUserName;
	}

	public void setMessage_receiveUserName(java.lang.String message_receiveUserName) {
		this.message_receiveUserName = message_receiveUserName;
	}

	public java.lang.String getMessage_receiveUser() {
		return message_receiveUser;
	}

	public void setMessage_receiveUser(java.lang.String message_receiveUser) {
		this.message_receiveUser = message_receiveUser;
	}

	public java.lang.String getId() {
		return this.id;
	}

	public void setMessage_title(java.lang.String message_title) {
		this.message_title = message_title;
	}

	public java.lang.String getMessage_title() {
		return this.message_title;
	}

	public void setMessage_content(java.lang.String message_content) {
		this.message_content = message_content;
	}

	public java.lang.String getMessage_content() {
		return this.message_content;
	}

	public void setMessage_file(java.lang.String message_file) {
		this.message_file = message_file;
	}

	public java.lang.String getMessage_file() {
		return this.message_file;
	}

	public void setRecordUser(java.lang.String recordUser) {
		this.recordUser = recordUser;
	}

	public java.lang.String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordTime(java.util.Date recordTime) {
		this.recordTime = recordTime;
	}

	public java.util.Date getRecordTime() {
		return this.recordTime;
	}
}
