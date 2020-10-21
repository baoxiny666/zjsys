package org.tried.demo.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.tried.demo.model.crud.Crud;
import org.tried.demo.model.crud.CrudModel;
import org.tried.demo.model.crud.CrudPoJoJava;
import org.tried.demo.model.obj.ColumnInfo;
import org.tried.demo.model.tree.Tree;
import org.tried.demo.model.tree.TreeModel;
import org.tried.demo.model.tree.TreePojoJava;

import com.tried.system.model.SystemTableModel;

public class ReverseTool {
	
	
	public static boolean checkColumn(List<SystemTableModel> columns,String checkColumnName){
		boolean flag=false;
		for(SystemTableModel model :columns){
			if(model.getColumnName().equals(checkColumnName)){
				flag=true;
			}
		}
		
		return flag;
	}
	
	/**
	 * 
	  * @Description 逆向生成
	  * @author liuxd
	  * @date 2016-2-1 下午3:55:23
	  * @version V1.0
	 */
	public static void createReverse(SystemTableModel tableInfo,List<SystemTableModel> columns,String pagetType) throws Exception{
		
		String  columnStr=JSONArray.fromObject(columns).toString();
		if(!columnStr.contains("id")){
			SystemTableModel systemTableModel=new SystemTableModel();
			systemTableModel.setColumnName("id");
			systemTableModel.setColumnLength("32");
			systemTableModel.setColumnPk("是");
			systemTableModel.setColumnType("varchar");
			if("TABLE".equals(tableInfo.getObjectType())){
			Util.addColumn(tableInfo.getObjectName(),systemTableModel);
			}
			columns.add(systemTableModel);
		}
		if("TREE".equals(pagetType)){
			if(!checkColumn(columns,"parentId")){
				SystemTableModel systemTableModel = new SystemTableModel();
				systemTableModel.setColumnName("parentId");
				systemTableModel.setColumnLength("32");
				systemTableModel.setColumnPk("否");
				systemTableModel.setColumnType("varchar");
				if("TABLE".equals(tableInfo.getObjectType())){
					Util.addColumn(tableInfo.getObjectTable(),systemTableModel);
				}
				columns.add(systemTableModel);
			}
			if (!checkColumn(columns,"name")) {
				SystemTableModel systemTableModel = new SystemTableModel();
				systemTableModel.setColumnName("name");
				systemTableModel.setColumnTitle("节点名称");
				systemTableModel.setColumnLength("50");
				systemTableModel.setColumnPk("否");
				systemTableModel.setColumnType("varchar");
				if("TABLE".equals(tableInfo.getObjectType())){
					Util.addColumn(tableInfo.getObjectTable(),systemTableModel);
				}
				columns.add(systemTableModel);
			}
			if (!checkColumn(columns,"sequence")) {
				SystemTableModel systemTableModel=new SystemTableModel();
				systemTableModel.setColumnName("sequence");
				systemTableModel.setColumnLength("10");
				systemTableModel.setColumnPk("否");
				systemTableModel.setColumnType("int");
				if("TABLE".equals(tableInfo.getObjectType())){
					Util.addColumn(tableInfo.getObjectTable(),systemTableModel);
				}
				columns.add(systemTableModel);
			}
		}
		createPage(tableInfo,columns,pagetType);
		Util.configStatus(tableInfo);
		Util.createUrl(tableInfo);
	}
	/**
	  * @Description 生产对象页面
	  * @author liuxd
	  * @date 2015-12-25 上午11:33:51
	  * @version V1.0
	 */
	public static void createPage(SystemTableModel tableModel,List<SystemTableModel> columns,String pagetType) throws Exception{
		String packName=Util.getParam().get("packageName").toString();
		 if(null!=tableModel.getPackName()){
			 packName=tableModel.getPackName();
		}
		String tableName =tableModel.getObjectTable();
		List<ColumnInfo> columnS = new ArrayList<ColumnInfo>();
		Map<String, Object> tableInfo=new HashMap<String, Object>();
		tableInfo.put(tableName, tableModel.getObjectName());
		tableInfo.put("packName", packName);
		
		for(SystemTableModel column:columns){
			ColumnInfo columnInfo = new ColumnInfo();
			if("是".equals(column.getColumnPk())){
				tableInfo.put("PK", column.getColumnName());
			}
			columnInfo.setColumnName(column.getColumnName());
			// 字段标题
			columnInfo.setColumnTitle(column.getColumnTitle());
			columnInfo.setColumnType(column.getColumnType());
			columnInfo.setColumnLength((column.getColumnLength()!=null&&!column.getColumnLength().isEmpty())?Long.valueOf(column.getColumnLength()):1l);
			columnInfo.setColumnSearch(column.getColumnSearch());
			columnS.add(columnInfo);
		}
		tableInfo.put("column", columnS);
		/** crud 模式demo**/
		if(pagetType.equals("CRUD")){
			//生产pojo.hbm.xml文件
			PoJoHbmXml.createHbm(tableInfo, tableName);
			//生产pojo.java文件
			CrudPoJoJava.createPoJo(tableInfo, tableName);
			Map<String,String> elMap=createPoJo(tableInfo, tableName);
			elMap.put("headTitle",Crud.createListHead((List<ColumnInfo>)tableInfo.get("column")));//待完善
			elMap.put("managerInput",Crud.createManager((List<ColumnInfo>)tableInfo.get("column")));//待完善
			CrudModel.createModel(elMap,"crud");
		}
		if(pagetType.equals("TREE")){
			//生产pojo.hbm.xml文件
			PoJoHbmXml.createHbm(tableInfo, tableName);
			//生产pojo.java文件
			TreePojoJava.createPoJo(tableInfo, tableName);
			Map<String,String> elMap=createPoJo(tableInfo, tableName);
			elMap.put("headTitle",Tree.createListHead((List<ColumnInfo>)tableInfo.get("column")));//待完善
			elMap.put("managerInput",Tree.createManager((List<ColumnInfo>)tableInfo.get("column")));//待完善
			TreeModel.createModel(elMap);
			}
		/** tree 模式demo**/
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
			if("ID".equals(columnInfo.getColumnName().toUpperCase())){
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
