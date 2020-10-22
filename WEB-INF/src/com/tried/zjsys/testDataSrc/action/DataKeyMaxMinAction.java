package com.tried.zjsys.testDataSrc.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.zjsys.testDataSrc.model.DataKeyMaxMin;
import com.tried.zjsys.testDataSrc.service.DataKeyMaxMinService;
import com.utils.JdbcUtils;

/**
 * @Description 设备属性阈值 管理
 * @author liuxd
 * @date 2020-07-03 17:44:07
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class DataKeyMaxMinAction extends BaseAction<DataKeyMaxMin> {
	private static Logger logger = Logger.getLogger(DataKeyMaxMinAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	DataKeyMaxMinService dataKeyMaxMinService;

	/**
	 * @Description 分页显示设备属性阈值
	 * @author liuxd
	 * @date 2020-07-03 17:44:07
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getDeviceName())) {
				this.condition = " and  deviceName='" + model.getDeviceName() + "' order by cast(viewpaiXu  as int) asc ";
				//this.condition +=  this.getOrderColumn();
				outJsonData(dataKeyMaxMinService.findPage(new Page<DataKeyMaxMin>(page, rows), "from DataKeyMaxMin where 1=1 " + this.condition).getResult());
			}
		 } catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加设备属性阈值
	 * @author liuxd
	 * @date 2020-07-03 17:44:07
	 * @version V1.0
	 */
	public void add() {
		try {
		
			/*
			 * String sql = "select NEXT VALUE FOR materialyuansuSeq as viewpaiXu";
			 * 
			 * Connection con = JdbcUtils.getConnection(); Statement statement =
			 * con.createStatement();
			 * 
			 * ResultSet resultSet = statement.executeQuery(sql); String viewpaiXu = null;
			 * while (resultSet.next()) { viewpaiXu = resultSet.getString("viewpaiXu");
			 * 
			 * }
			 */
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			
			//model.setViewpaiXu(viewpaiXu);
			dataKeyMaxMinService.add(model);
			//JdbcUtils.release(con, statement, resultSet);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除设备属性阈值
	 * @author liuxd
	 * @date 2020-07-03 17:44:07
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			dataKeyMaxMinService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改设备属性阈值
	 * @author liuxd
	 * @date 2020-07-03 17:44:07
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			dataKeyMaxMinService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	
	/**
	 * @Description 重新排序
	 * @author boxy tianglhtg
	 * @date 2020-10-22 09:56:06
	 * @version V2.0
	 */
	public void sort() {
		try {
			List<String> sqls=new ArrayList<String>();
			String[] ids=recordIdS.split(",");
			for(int i=0;i<ids.length;i++){
				sqls.add(" update  tried_data_key_max_min  set viewpaiXu="+i+" where id='"+ids[i]+"'");
			}
			dataKeyMaxMinService.dbBeatchSql(sqls);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	
	/**
	 * 获取最大最小阈值
	 */
	public void findKeyMaxMin(){
		try {
		    List<DataKeyMaxMin>   dataKeyMaxMinList=dataKeyMaxMinService.findAll("from DataKeyMaxMin where deviceName ='"+model.getDeviceName()+"'");
			Map<String,Map<String,Double>> resultMap=new HashMap<String,Map<String,Double>>();
			for(DataKeyMaxMin dataKeyMaxMin:dataKeyMaxMinList){
				Map<String,Double> m=new HashMap<String,Double>();
				m.put("max", (dataKeyMaxMin.getKeyMax()!=null&&!dataKeyMaxMin.getKeyMax().isEmpty())?Double.valueOf(dataKeyMaxMin.getKeyMax()):null);
				m.put("min", (dataKeyMaxMin.getKeyMin()!=null&&!dataKeyMaxMin.getKeyMin().isEmpty())?Double.valueOf(dataKeyMaxMin.getKeyMin()):null);
				resultMap.put(dataKeyMaxMin.getKeyName(),m);
			}
		    outJsonData(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}	
	}
	
	/**
	 * 获取最大最小阈值
	 */
	public void findAllKeyMaxMin(){
		try {
		    List<DataKeyMaxMin>   dataKeyMaxMinList=dataKeyMaxMinService.findAll("from DataKeyMaxMin");
			Map<String,Map<String,Double>> resultMap=new HashMap<String,Map<String,Double>>();
			for(DataKeyMaxMin dataKeyMaxMin:dataKeyMaxMinList){
				Map<String,Double> m=new HashMap<String,Double>();
				m.put("max", (dataKeyMaxMin.getKeyMax()!=null&&!dataKeyMaxMin.getKeyMax().isEmpty())?Double.valueOf(dataKeyMaxMin.getKeyMax()):null);
				m.put("min", (dataKeyMaxMin.getKeyMin()!=null&&!dataKeyMaxMin.getKeyMin().isEmpty())?Double.valueOf(dataKeyMaxMin.getKeyMin()):null);
				resultMap.put(dataKeyMaxMin.getDeviceName()+"_"+dataKeyMaxMin.getKeyName(),m);
			}
		    outJsonData(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}	
	}
}
