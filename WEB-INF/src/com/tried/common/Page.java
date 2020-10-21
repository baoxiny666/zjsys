package com.tried.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.mapping.Array;

/**
 * 
 * @author WENFAN
 */

public class Page<T> {
	protected Integer pageNum = 1; //页码
	protected Integer rows = 1; 	//页行数
	protected Long total = 1l; 	//页行数
	protected List<T> data = new ArrayList<T>();
	protected Map<String,Object> result=new HashMap<String, Object>();
	public Page() {
	}
	public Page(Integer pageNum,Integer rows) {
		this.pageNum = pageNum;
		this.rows=rows;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Map<String, Object> getResult() {
		result.put("total", total);
		result.put("rows", data);
		return result;
	}
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}



}
