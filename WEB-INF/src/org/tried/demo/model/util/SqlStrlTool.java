package org.tried.demo.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.tried.demo.model.crud.Crud;
import org.tried.demo.model.crud.CrudModel;
import org.tried.demo.model.crud.CrudPoJoJava;
import org.tried.demo.model.obj.ColumnInfo;

import com.tried.system.model.SystemTableModel;
import com.tried.system.model.SystemViewModel;

public class SqlStrlTool {
	/**
	  * @Description 自定义sql模型创建方法
	  * @author liuxd
	  * @date 2016-1-24 下午12:06:10
	  * @version V1.0
	 */
	public static void createPage(SystemViewModel systemViewModel) throws Exception{
		String packName=systemViewModel.getView_package();if(packName==null){return ;}
		String modelNameEn=systemViewModel.getView_name();if(modelNameEn==null){return ;}
		String modelNameCh=(systemViewModel.getView_content()!=null)?systemViewModel.getView_content():"";
		String modelSql=systemViewModel.getView_sql();if(modelSql==null){return ;}
		modelSql=modelSql.trim().toUpperCase();
		int beginIndex=modelSql.indexOf("SELECT")+6;
		int endIndex=modelSql.indexOf("FROM");
		String columnStr=modelSql.substring(beginIndex, endIndex);
		String[] column=columnStr.split(",");
		List<SystemTableModel> columns=new LinkedList<SystemTableModel>();
		for(String c:column){
			SystemTableModel model=new SystemTableModel();
			String c_name=c.toLowerCase().trim();
			model.setColumnName(c_name);
			model.setColumnTitle(c_name);
			model.setColumnLength("50");
			model.setColumnPk("否");
			model.setColumnType("varchar");
			 
			columns.add(model);
		}
		//创建pojo
		CrudPoJoJava.createViewPoJo(packName,modelNameEn,modelNameCh,columns);
		SystemTableModel viewModel=new SystemTableModel();
		viewModel.setObjectTable(modelNameEn);
		viewModel.setObjectName(modelNameCh);
		viewModel.setPackName(packName);
		Map<String, Object> viewInfo=new HashMap<String, Object>();
		viewInfo.put(modelNameEn, modelNameCh);
		viewInfo.put("packName", packName);
		List<ColumnInfo> columnS = new ArrayList<ColumnInfo>();
	
		for(SystemTableModel c:columns){
			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setColumnName(c.getColumnName());
			// 字段标题
			columnInfo.setColumnTitle(c.getColumnTitle());
			columnInfo.setColumnType(c.getColumnType());
			columnInfo.setColumnLength((c.getColumnLength()!=null&&!c.getColumnLength().isEmpty())?Long.valueOf(c.getColumnLength()):1l);
			columnS.add(columnInfo);
		}
		viewInfo.put("column", columnS);
		Map<String,String> elMap=createPoJo(viewInfo, modelNameEn);
		elMap.put("jdbcSql", modelSql);
		elMap.put("headTitle",Crud.createListHead((List<ColumnInfo>)viewInfo.get("column")));//待完善
		CrudModel.createModel(elMap,"view");
		//struts配置文件
		Util.configStatus(viewModel); 
		//功能粒子数据
		Util.createUrl(viewModel);
	}
	public static Map<String,String>  createPoJo(Map<String, Object> tableInfo, String tableName) throws Exception {
		Map<String,String> elMap=new HashMap<String, String>();
		if (tableInfo.get("column") == null) {
			System.out.println("该模块没有属性字段");
			return elMap;
		}
		String packName=tableInfo.get("packName").toString();
		elMap.put("systemName", Util.getParam().get("systemName").toString());
		elMap.put("packName", packName);
		elMap.put("tableName", tableName);
		elMap.put("modelTitle", tableInfo.get(tableName).toString());
		elMap.put("ModelName", Util.ModelName(tableName));
		elMap.put("modelName", Util.firstLower(Util.ModelName(tableName)));
		elMap.put("author",Util.getParam().get("author").toString()); 
		elMap.put("date",Util.currentTime()); 
		if(tableInfo.get("PK")!=null){
			elMap.put("TablePk","<input name=\""+tableInfo.get("PK").toString()+"\" type=\"hidden\"/> "); 
		}
		if(tableInfo.get("column")!=null){
		//处理search，需要制定，现取默认的待完善
		List<ColumnInfo> ColumnList=(List<ColumnInfo>)tableInfo.get("column");
		StringBuilder zdySet=new StringBuilder();
		for(int i=0;i<ColumnList.size();i++){
			ColumnInfo columnInfo=ColumnList.get(i);
			if("是".equals(columnInfo.getColumnSearch())){
				String searchName=columnInfo.getColumnName();
				String searchTitle=(columnInfo.getColumnTitle()!=null&&!columnInfo.getColumnTitle().isEmpty())?columnInfo.getColumnTitle():columnInfo.getColumnName();
				elMap.put("searchName", searchName); 
				elMap.put("SearchName",Util.firstUpper(searchName)); 
				elMap.put("searchTitle",searchTitle); 
				elMap.put("searchInput", searchTitle+": <input id=\""+columnInfo.getColumnName()+"\" type=\"text\" style=\"width:110px\">");
			}
			//处理自定义sql模型的对象赋值问题，具体参照view下的${zdySet}
			zdySet.append(elMap.get("modelName")+".set"+Util.firstUpper(columnInfo.getColumnName())+"((obj["+i+"]!=null)?obj["+i+"].toString():\"\");\r\n");
		   }
		elMap.put("zdySet",zdySet.toString()); 
		}
		
	
	   return elMap;
	}
}
