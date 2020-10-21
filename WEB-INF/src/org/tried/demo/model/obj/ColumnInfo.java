package org.tried.demo.model.obj;


public class ColumnInfo {
private String columnName;  // 字段名字
private String columnTitle; //字段标题
private Long columnLength;	//字段长度
private String columnType;	//字段类型
private boolean isPk=false; //是否是主键
private boolean isSort=false; //是否排序
private Integer columnWidth=100;  // 字段名字
private String columnSearch;
public boolean isSort() {
	return isSort;
} 
public String getColumnSearch() {
	return columnSearch;
}
public void setColumnSearch(String columnSearch) {
	this.columnSearch = columnSearch;
}
public void setSort(boolean isSort) {
	this.isSort = isSort;
}
 
public Integer getColumnWidth() {
	return columnWidth;
}
public void setColumnWidth(Integer columnWidth) {
	this.columnWidth = columnWidth;
}
public String getColumnName() {
	return columnName;
}
public void setColumnName(String columnName) {
	this.columnName = columnName;
}
public String getColumnTitle() {
	return columnTitle;
}
public void setColumnTitle(String columnTitle) {
	this.columnTitle = columnTitle;
}
public Long getColumnLength() {
	return columnLength;
}
public void setColumnLength(Long columnLength) {
	this.columnLength = columnLength;
}
public String getColumnType() {
	return columnType;
}
public void setColumnType(String columnType) {
	this.columnType = columnType;
}
public boolean isPk() {
	return isPk;
}
public void setPk(boolean isPk) {
	this.isPk = isPk;
}
}
