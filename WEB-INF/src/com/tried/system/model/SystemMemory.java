package com.tried.system.model;

public class SystemMemory {
	private java.lang.String id;
	private java.lang.String relationId;
	private java.lang.String relationColumn;
	private java.lang.String relationContext;
	private java.util.Date recordTime;
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getRelationId() {
		return relationId;
	}
	public java.lang.String getRelationColumn() {
		return relationColumn;
	}
	public void setRelationColumn(java.lang.String relationColumn) {
		this.relationColumn = relationColumn;
	}
	public void setRelationId(java.lang.String relationId) {
		this.relationId = relationId;
	}
	public java.lang.String getRelationContext() {
		return relationContext;
	}
	public void setRelationContext(java.lang.String relationContext) {
		this.relationContext = relationContext;
	}
 
	public java.util.Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(java.util.Date recordTime) {
		this.recordTime = recordTime;
	}
	
}
