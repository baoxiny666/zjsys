package org.tried.demo.model.tree;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.tried.demo.model.obj.ColumnInfo;
public class Tree {

	/**
	 * @Description 生产manager input输入框
	 * @author liuxd
	 * @date 2015-9-6 上午9:04:49
	 * @version V1.0
	 */
	public static String createManager(List<ColumnInfo> ColumnList) {
		StringBuffer trInput = new StringBuffer();
		trInput.append("<tr>");
		trInput.append(" <td  style=\"height: 27px\">上级：</td>");
		trInput.append(" <td><input class=\"easyui-textbox\" type=\"text\"  name=\"pName\"  style=\"width: 200px; height: 25px\" /></td>");
		trInput.append("</tr>\r\n");
		for (int i = 0; i < ColumnList.size(); i++) {
			ColumnInfo model = ColumnList.get(i);
			String columnName = model.getColumnName();
			if("id".equals(columnName)||"parentId".equals(columnName)){
				continue;
			}
			String columnType = model.getColumnType();
			String columnTitle = model.getColumnTitle();
			String inputType = "easyui-textbox";
			trInput.append("<tr>");
			trInput.append(" <td  style=\"height: 27px\">" + ( (columnTitle!=null&&!columnTitle.isEmpty())?columnTitle:columnName)  + "：</td>");
			if ("datetime".equals(columnType)) {
				inputType = "easyui-datebox textbox";
			}
			trInput.append(" <td><input class=\"" + inputType + "\" type=\"text\"  name=\"" + columnName
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
			
			if("id".equals(columnName)||"parentId".equals(columnName)){
				continue;
			}
			String columnTitle = model.getColumnTitle();
			Integer columnWidth = model.getColumnWidth();
			String columnType = model.getColumnType();
			Boolean isSort = model.isSort();
			head=new LinkedHashMap<Object, Object>();
			head.put("field", columnName);
			head.put("title", (columnTitle!=null&&!columnTitle.isEmpty())?columnTitle:columnName);
			head.put("width", columnWidth);
			head.put("align", "center");
			head.put("sortable", isSort);
			if("datetime".equals(columnType)){
				String _func="function(value,row,index){" //格式化函数添加一个操作列
						+" if (value == undefined) {"
							 +" return \"\";"
					         +" }else{"
					    	 +" var unixTimestamp = new Date(value.time); "
					     	+" return unixTimestamp.toLocaleString();"
						+"}}";
				head.put("formatter", _func);
			}
			resultList.add(head);
		} 
		
		String _func="function(value,row,index){"   //格式化函数添加一个操作列
				+"   var btn = '<input type=\"button\" value=\"置顶\" style=\"border:0\" onclick=\"func_toFirst('+index+')\">';"
		 		+"   btn=btn+' <input type=\"button\" value=\"上移\"  style=\"border:0\" onclick=\"func_toPre('+index+')\">';"
		        +"   btn=btn+' <input type=\"button\" value=\"下移\"  style=\"border:0\" onclick=\"func_toNext('+index+')\">';"
		        +"   btn=btn+' <input type=\"button\" value=\"置底\"  style=\"border:0\" onclick=\"func_toBottom('+index+')\">' ;"
		        +"  return btn;}";
		        
		head=new LinkedHashMap<Object, Object>();
		head.put("field", "_opt");
		head.put("title",  "操作");
		head.put("width", 200);
		head.put("align", "center");
		head.put("formatter", _func);
		resultList.add(head);
		return JSONArray.fromObject(resultList).toString();
	}

}
