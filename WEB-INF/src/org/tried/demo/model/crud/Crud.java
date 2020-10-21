package org.tried.demo.model.crud;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.tried.demo.model.obj.ColumnInfo;

import net.sf.json.JSONArray;

public class Crud {

	/**
	 * @Description 生产manager input输入框
	 * @author liuxd
	 * @date 2015-9-6 上午9:04:49
	 * @version V1.0
	 */
	public static String createManager(List<ColumnInfo> ColumnList) {
		StringBuffer trInput = new StringBuffer();
		for (int i = 0; i < ColumnList.size(); i++) {
			
			ColumnInfo model = ColumnList.get(i);
			String columnName = model.getColumnName();
			String columnType = model.getColumnType();
			String columnTitle = model.getColumnTitle();
			if("id".equals(columnName)){
				continue;
			}
			String inputType = "easyui-textbox";
			trInput.append("<tr>");
			trInput.append(" <td style=\"height: 27px\">" +( (columnTitle!=null&&!columnTitle.isEmpty())?columnTitle:columnName) + "：</td>");
			if ("datetime".equals(columnType)) {
				inputType = "easyui-datebox textbox";
			}
			trInput.append(" <td><input class=\"" + inputType + "\"  name=\"" + columnName
					+ "\"  style=\"width: 200px; height: 25px\" /></td>");
			trInput.append("</tr>\r\n");
		}
		return trInput.toString();
	}
	
	public static String createListHead(List<ColumnInfo> ColumnList) {
		List<Map<Object,Object>> resultList=new LinkedList<Map<Object,Object>>();
		Map<Object,Object> head=new LinkedHashMap<Object, Object>();
		head.put("field", "ck");
		head.put("checkbox", true);
		resultList.add(head);
		
		for (int i = 0; i < ColumnList.size(); i++) {
			
			ColumnInfo model = ColumnList.get(i);
			String columnName = model.getColumnName();
			if("id".equals(columnName)){
				continue;
			}
			String columnTitle = model.getColumnTitle();
			Integer columnWidth = model.getColumnWidth();
			String columnType = model.getColumnType();
			Boolean isSort = model.isSort();
			
			if("recordUser".equals(columnName)){
				head=new LinkedHashMap<Object, Object>();
				head.put("field", columnName+"Name");
				head.put("title", "操作人");
				head.put("width", columnWidth);
				head.put("align", "center");
				head.put("sortable", isSort);
				resultList.add(head);
				continue;
			}
		
			head=new LinkedHashMap<Object, Object>();
			head.put("field", columnName);
			head.put("title", (columnTitle!=null&&!columnTitle.isEmpty())?columnTitle:columnName);
			head.put("width", "140");
			head.put("align", "center");
			head.put("sortable", isSort);
			if("datetime".equals(columnType)){
				if("datetime".equals(columnType)){
					head.put("formatter", "formateTime");
				}
			}
			
			
			
			
			resultList.add(head);
		}
		return JSONArray.fromObject(resultList).toString();
	}

}
