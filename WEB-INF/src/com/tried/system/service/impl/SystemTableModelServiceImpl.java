package com.tried.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemTableModel;
import com.tried.system.service.SystemTableModelService;
/**
 * @Description 表/视图结构 服务接口实现
 * @author liuxd
 * @date 2015-09-21 19:47:17
 * @version V1.0
 */
@Service
public class SystemTableModelServiceImpl extends BaseServiceImpl<SystemTableModel> implements SystemTableModelService {

	@Override
	public void createTable(List<String> recordId) throws Exception {
		List<String> sqls=new ArrayList<String>();
		for(String id:recordId)
		{

			StringBuilder sql=new StringBuilder();
			SystemTableModel tableModel=this.getById(id);
			sql.append("CREATE TABLE "+tableModel.getObjectTable()+"(");
			List<SystemTableModel>	tableInfoList=this.findAll("from SystemTableModel where parentId='"+recordId+"'");
			String pkColumn="";
			for(int i=0;i<tableInfoList.size();i++){
				SystemTableModel tableInfo=tableInfoList.get(i);
					if(null==tableInfo.getColumnPk()){
						continue;
					}
					if("是".equals(tableInfo.getColumnIsNull())){
						sql.append(tableInfo.getColumnName()+" "+tableInfo.getColumnType()+"  NULL");
					}else{
						sql.append(tableInfo.getColumnName()+" "+tableInfo.getColumnType()+" NOT NULL");
					}
					
					if("是".equals(tableInfo.getColumnPk())){
						pkColumn=tableInfo.getColumnName();
					}
			
				
			
				sql.append("");
			}
		}
		this.dbBeatchSql(sqls);
	}

	@Override
	public void createPage(List<String> recordId) throws Exception{
		// TODO Auto-generated method stub
		
	}

}
