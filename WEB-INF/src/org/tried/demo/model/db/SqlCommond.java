package org.tried.demo.model.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tried.demo.model.obj.ColumnInfo;

public class SqlCommond {
	public static int num = 0;

	/**
	 * 执行sql语句
	 * 
	 * @author liuxd
	 * @date 2015-3-3 下午02:55:27
	 * @version V1.0
	 */
	public static void commondSql(String sql) throws Exception {
		Connection conn = ConnectionPool.getInstance().getConnection();
		Statement statement = conn.createStatement();
		statement.execute(sql);
		conn.commit();
		statement.close();
		ConnectionPool.getInstance().release(conn);
	}

	public static void beachSql(List<String> sqls) throws Exception {
		if (sqls.size() == 0) {
			return;
		}
		try {
			Connection conn = ConnectionPool.getInstance().getConnection();
			Statement statement = conn.createStatement();
			for (String sql : sqls) {
				System.out.println(sql);
				statement.addBatch(sql);
			}
			statement.executeBatch();
			conn.commit();
			statement.close();
			ConnectionPool.getInstance().release(conn);
		} catch (Exception e) {
			System.out.println("异常" + e.getMessage());
		}
	}
	public static boolean exitTable(String tableName){
		boolean exit=false;
		Connection conn=null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT  name,id FROM dbo.SysObjects WHERE ID = object_id(N'"+tableName+"')");
 			if(resultSet.next()){
			if(null!=resultSet.getString("name")){
				exit=true;
				}
 			}
			statement.close();
			return exit;
		} catch (Exception e) {
			System.out.println("异常" + e.getMessage());
			return exit;
		}finally{
			ConnectionPool.getInstance().release(conn);
			
		}
		
	}
	/**
	 * 
	 * @author liuxd
	 * @date 2015-3-3 下午02:55:43
	 * @version V1.0
	 */
	public static Map<String, Object> getTableInfo(String tableName) {
		Map<String, Object> result = new HashMap<String, Object>(); // 表信息
		result.put(tableName, tableName);
		StringBuffer sql = new StringBuffer();

		
		sql.append(" SELECT  cast(  (case when a.colorder=1 then d.name else '' end) as varchar(100)),");
		sql.append("   cast(  case when a.colorder=1 then isnull(f.value,'') else '' end as varchar(100)),");
		sql.append("  cast(  a.name as varchar(100)),");
		sql.append("   cast(  case when exists(SELECT 1 FROM sysobjects where xtype='PK' and parent_obj=a.id and name in (");
		sql.append("  SELECT name FROM sysindexes WHERE indid in( SELECT indid FROM sysindexkeys WHERE id = a.id AND colid=a.colid))) then '√' else '' end");
		sql.append("   as varchar(100)),");
		sql.append(" cast( b.name 	as varchar(100)), ");
		sql.append("  cast(  a.length 	as varchar(100)),");
		sql.append("  cast( case when a.isnullable=1 then '√'else '' end as varchar(100)), ");
		sql.append("  cast( isnull(g.[value],'') as varchar(100))  ");
		
		
		sql.append(" FROM   syscolumns a left join  systypes b  on  a.xusertype=b.xusertype ");
		sql.append(" inner join   sysobjects d on    a.id=d.id  and d.xtype='U' and  d.name<>'dtproperties' ");
		sql.append(" left join      syscomments e on   a.cdefault=e.id left join sys.extended_properties   g  ");
		sql.append(" on  a.id=G.major_id and a.colid=g.minor_id  left join sys.extended_properties f ");
		sql.append(" on  d.id=f.major_id and f.minor_id=0 where d.name='" + tableName + "'  order by  ");
		sql.append("  a.id,a.colorder ");
		Connection conn = ConnectionPool.getInstance().getConnection();
		Statement prestatem;
		List<ColumnInfo> columnS = new ArrayList<ColumnInfo>();
		try {
			prestatem = conn.createStatement();
			ResultSet resultSet = prestatem.executeQuery(sql.toString());
			while (resultSet.next()) {
				ColumnInfo columnInfo = new ColumnInfo();
				String columnName = resultSet.getString(3);
				// 设置表明
				if (null != resultSet.getString(4) && "√".equals(resultSet.getString(4))) {
					if (null != resultSet.getString(2) && !resultSet.getString(2).isEmpty()){
						result.put(tableName, resultSet.getString(2));}
					result.put("PK", columnName);
				}
				// 字段名
				columnInfo.setColumnName(columnName);
				// 字段标题
				columnInfo.setColumnTitle((resultSet.getString(8) != null&&!resultSet.getString(8).isEmpty()) ? resultSet.getString(8) : columnName);
				columnInfo.setColumnType(resultSet.getString(5));
				//columnInfo.setColumnLength((resultSet.getString(7)!=null)?Long.valueOf(resultSet.getString(7)):0l);
				columnS.add(columnInfo);
			}
			result.put("column", columnS);
			resultSet.close();
			prestatem.close();
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().release(conn);
		}
		return result;
	}

}
