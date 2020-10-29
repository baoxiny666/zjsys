package com.tried.zjsys.basics.action;

import java.util.Date;
import java.util.List;
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
import com.tried.zjsys.basics.model.DataWlInfo;
import com.tried.zjsys.basics.service.DataWlInfoService;
import com.tried.zjsys.testDataSrc.model.DataKeyMaxMin;
import com.tried.zjsys.testDataSrc.service.DataKeyMaxMinService;
import com.utils.JdbcUtils;

/**
 * @Description 物料名称 管理
 * @author liuxd
 * @date 2020-07-06 16:20:50
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class DataWlInfoAction extends BaseAction<DataWlInfo> {
	private static Logger logger = Logger.getLogger(DataWlInfoAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	DataWlInfoService dataWlInfoService;
	@Resource
	DataKeyMaxMinService dataKeyMaxMinService;

	/**
	 * @Description 分页显示物料名称
	 * @author liuxd
	 * @date 2020-07-06 16:20:50
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getWlCode())) {
				this.condition = " and wlCode like '%" + model.getWlCode() + "%'";
			}
			if (strIsNotNull(model.getWlName())) {
				this.condition = " and wlName like '%" + model.getWlName() + "%'";
			}
			this.condition +=" order by cast(viewpaiXu  as int)  desc ";
			//this.condition +=  this.getOrderColumn();
			outJsonData(dataWlInfoService.findPage(new Page<DataWlInfo>(page, rows), "from DataWlInfo where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加物料名称
	 * @author liuxd
	 * @date 2020-07-06 16:20:50
	 * @version V1.0
	 */
	public void add() {
		try {
		
			 String sql = "select NEXT VALUE FOR wuliaoSeq as wuliaoSeq";
			  
			 Connection con = JdbcUtils.getConnection(); 
			 Statement statement =con.createStatement();
			 
			 ResultSet resultSet = statement.executeQuery(sql); 
			 String wuliaoSeq = null;
			 while (resultSet.next()) { 
				 wuliaoSeq = resultSet.getString("wuliaoSeq");
			 
			 }
			 
			
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			model.setViewpaiXu(wuliaoSeq);
			List<DataWlInfo> dataWlInfoList=dataWlInfoService.findAll("from DataWlInfo where wlCode='"+model.getWlCode()+"'");
		if(dataWlInfoList.size()==0){
			dataWlInfoService.add(model);
			outSuccessJson("添加成功");
		}else{
			outSuccessJson("物料代码重复！");
		}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除物料名称
	 * @author liuxd
	 * @date 2020-07-06 16:20:50
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			dataWlInfoService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}
	/**
	 * 复制物料数据
	 */
	public void copy(){
		try {
			if (strIsNotNull(getRecordId())) {
				dataWlInfoService.copy(getRecordId(), getCurrentUser().getId());
				outSuccessJson("复制成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("复制失败");
		}

	}

	/**
	 * @Description 修改物料名称
	 * @author liuxd
	 * @date 2020-07-06 16:20:50
	 * @version V1.0
	 */
	public void edit() {
		try {
			
			List<DataWlInfo> dataWlInfoList=dataWlInfoService.findAll("from DataWlInfo where wlCode='"+model.getWlCode()+"'");
			if(dataWlInfoList.size()>0){
				if(!dataWlInfoList.get(0).getId().equals(model.getId())){
					outSuccessJson("物料代码重复！");
				}else{
					DataWlInfo dataWlInfo=dataWlInfoList.get(0);
				
					//修改元素
					List<DataKeyMaxMin>  listKey= dataKeyMaxMinService.findAll("from DataKeyMaxMin where deviceName='"+dataWlInfo.getWlCode()+"'");
					for(DataKeyMaxMin data:listKey){
						data.setDeviceName(model.getWlCode());
						data.setRecordTime(new Date());
						data.setRecordUser(getCurrentUser().getId());
						dataKeyMaxMinService.update(data);
					}
					//修改样品
					dataWlInfo.setWlCode(model.getWlCode());
					dataWlInfo.setWlName(model.getWlName());
					dataWlInfo.setWlType(model.getWlType());
					dataWlInfo.setBelongcompany(model.getBelongcompany());
					dataWlInfo.setRecordTime(new Date());
					dataWlInfo.setRecordUser(getCurrentUser().getId());
					dataWlInfoService.update(dataWlInfo);
					outSuccessJson("修改成功");
				}
			}else{
				DataWlInfo dataWlInfo =dataWlInfoService.getById(model.getId());
				List<DataKeyMaxMin>  listKey= dataKeyMaxMinService.findAll("from DataKeyMaxMin where deviceName='"+dataWlInfo.getWlCode()+"'");
				for(DataKeyMaxMin data:listKey){
					data.setDeviceName(model.getWlCode());
					data.setRecordTime(new Date());
					data.setRecordUser(getCurrentUser().getId());
					dataKeyMaxMinService.update(data);
				}
				dataWlInfo.setWlCode(model.getWlCode());
				dataWlInfo.setWlName(model.getWlName());
				dataWlInfo.setWlType(model.getWlType());
				dataWlInfo.setBelongcompany(model.getBelongcompany());
				dataWlInfo.setRecordTime(new Date());
				dataWlInfo.setRecordUser(getCurrentUser().getId());
				
				dataWlInfoService.update(dataWlInfo);
				
				outSuccessJson("物料代码重复！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	
	
	public void comboboxWlNotDgAll(){
		try {
				outJsonList(dataWlInfoService.findAll("FROM DataWlInfo where wlType!='带钢'"));
		}catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	public void comboboxWlAll(){
		try {
				outJsonList(dataWlInfoService.findAll("FROM DataWlInfo"));
		}catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}


	 
}
