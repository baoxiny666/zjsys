package com.tried.system.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.system.model.SystemReverse;
import com.tried.system.model.SystemTableModel;
import com.tried.system.service.SystemReverseService;
/**
 * @Description 逆向工具 服务接口实现
 * @author liuxd
 * @date 2016-01-28 10:26:06
 * @version V1.0
 */
@Service
public class SystemReverseServiceImpl extends BaseServiceImpl<SystemReverse> implements SystemReverseService {

	@Override
	public List<SystemReverse> findTableView(String modelName) throws Exception {
		List<SystemReverse> results=new LinkedList<SystemReverse>();
	 
		
		String sql="select   a.name AS tableName,  isnull(cast(g.value as varchar(500)) ,'-') AS tableContent from  (select object_id,name from  sys.tables where  name like '%"+modelName+"%') a    left join sys.extended_properties g on (   a.object_id = g.major_id AND g.minor_id = 0   ) ";
		List<Object[]>	tableLists=this.dbFindList(sql, null);
		for(Object[] obj:tableLists){
			SystemReverse reverse = new SystemReverse();
			reverse.setId(ConfigUtils.getUUID());
			reverse.setTable_name(((obj[0]!=null)?obj[0].toString():""));
			reverse.setTabel_content((obj[1]!=null)?obj[1].toString():"");
			reverse.setTabel_type("数据表");
			results.add(reverse);
		}
		sql="select a.name AS tableName,  isnull(cast(g.value as varchar(500)) ,'-') AS tableContent from (select object_id,name from  sys.views where  name like '%"+modelName+"%') a left join sys.extended_properties g on(a.object_id = g.major_id  and g.name='MS_Description')";
		tableLists=this.dbFindList(sql, null);
		for(Object[] obj:tableLists){
			SystemReverse reverse = new SystemReverse();
			reverse.setTable_name(((obj[0]!=null)?obj[0].toString():""));
			reverse.setTabel_content((obj[1]!=null)?obj[1].toString():"");
			reverse.setTabel_type("视图");
			results.add(reverse);
		}
		return results;
	}

	@Override
	public SystemTableModel findModelInfo(String modelName) throws Exception {
		SystemTableModel model=new SystemTableModel();
		String sql="select   a.name AS tableName,  isnull(cast(g.value as varchar(500)) ,'-') AS tableContent from  (select object_id,name from  sys.tables where  name = '"+modelName+"') a    left join sys.extended_properties g on (   a.object_id = g.major_id AND g.minor_id = 0   ) ";
		List<Object[]>	tableLists=this.dbFindList(sql, null);
		for(Object[] obj:tableLists){
			 model.setObjectTable(((obj[0]!=null)?obj[0].toString():""));
			 model.setObjectName(((obj[1]!=null)?obj[1].toString():""));
			 model.setObjectType("TABLE");
		}
    	sql="select a.name AS tableName,  isnull(cast(g.value as varchar(500)) ,'-') AS tableContent from (select object_id,name from  sys.views where  name = '"+modelName+"') a left join sys.extended_properties g on(a.object_id = g.major_id  and g.name='MS_Description')";
    		tableLists=this.dbFindList(sql, null);
    	for(Object[] obj:tableLists){
			 model.setObjectTable(((obj[0]!=null)?obj[0].toString():""));
			 model.setObjectName(((obj[1]!=null)?obj[1].toString():""));
			 model.setObjectType("VIEW");
		}
		return model;
	}

	@Override
	public List<SystemTableModel> findModelColumnInfo(String modelName,String modelType) throws Exception {
		List<SystemTableModel> result=new LinkedList<SystemTableModel>();
		if("TABLE".equals(modelType)){
		String sql = "SELECT  cast(  a.name as varchar(100)),     cast( isnull(g.[value],'') as varchar(100)) , "
				+ " cast(  case when exists(SELECT 1 FROM sysobjects where xtype='PK' and parent_obj=a.id and name in (  "
				+ "   SELECT name FROM sysindexes WHERE indid in( SELECT indid FROM sysindexkeys WHERE id = a.id AND colid=a.colid))) then '√' else '' end  "
				+ "   as varchar(100)),    cast( b.name 	as varchar(100)),      cast(  a.length 	as varchar(100)),  "
				+ "   cast( case when a.isnullable=1 then '√'else '' end as varchar(100))   "
				+ "  FROM   syscolumns a left join  systypes b  on  a.xusertype=b.xusertype   "
				+ "  inner join   sysobjects d on    a.id=d.id  and d.xtype='U' and  d.name<>'dtproperties'   "
				+ "  left join      syscomments e on   a.cdefault=e.id left join sys.extended_properties   g    "
				+ "  on  a.id=g.major_id and a.colid=g.minor_id  left join sys.extended_properties f   "
				+ " on  d.id=f.major_id and f.minor_id=0 where d.name='"+modelName+"'  order by    a.id,a.colorder  ";
	
		List<Object[]>	tableLists=this.dbFindList(sql, null);
		for(Object[] obj:tableLists){
			SystemTableModel model=new SystemTableModel();
			if(obj[2]!=null&&"√".equals(obj[2].toString())){
				model.setColumnPk("是");
				model.setColumnSearch(obj[2].toString());
			}else{
				model.setColumnPk("否");
			}
			model.setColumnName(((obj[0]!=null)?obj[0].toString():""));
			model.setColumnTitle(((obj[1]!=null)?obj[1].toString():""));
			model.setColumnType(((obj[3]!=null)?obj[3].toString():""));
			model.setColumnLength(((obj[4]!=null)?obj[4].toString():""));
			if(obj[5]!=null&&"√".equals(obj[5].toString())){
				model.setColumnIsNull("是");
			}else{
				model.setColumnIsNull("否");
			}
			result.add(model);
		}
		}else{
			String sql="select c.name,t.name from syscolumns c ,systypes t where c.xtype=t.xtype and c.id=object_id('"+modelName+"')";
			List<Object[]>	tableLists=this.dbFindList(sql, null);
			for(Object[] obj:tableLists){
				SystemTableModel model=new SystemTableModel();
				if(obj[0]!=null&&"id".equals(obj[0].toString().toLowerCase())){
					model.setColumnPk("是");
					model.setColumnSearch(obj[0].toString());
				}else{
					model.setColumnPk("否");
				}
				model.setColumnName(((obj[0]!=null)?obj[0].toString():""));
				model.setColumnTitle(((obj[0]!=null)?obj[0].toString():""));
				model.setColumnType(((obj[1]!=null)?obj[1].toString():""));
				model.setColumnLength("100");
				model.setColumnIsNull("是");
				result.add(model);
			}
		}
		return result;
	}

}
