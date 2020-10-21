package com.tried.zjsys.basics.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemUser;
import com.tried.zjsys.basics.model.DataCircle;
import com.tried.zjsys.basics.service.DataCircleService;
import com.tried.zjsys.basics.thread.ThreadStaticVariable;

/**
 * @Description 同步周期管理 管理
 * @author liuxd
 * @date 2020-06-19 22:35:52
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class DataCircleAction extends BaseAction<DataCircle> {
	private static Logger logger = Logger.getLogger(DataCircleAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	DataCircleService dataCircleService;

	/**
	 * @Description 分页显示同步周期管理
	 * @author liuxd
	 * @date 2020-06-19 22:35:52
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getDeviceName())) {
				this.condition = " and deviceName like '%" + model.getDeviceName() + "%'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(dataCircleService.findPage(new Page<DataCircle>(page, rows), "from DataCircle where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加同步周期管理
	 * @author liuxd
	 * @date 2020-06-19 22:35:52
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			ThreadStaticVariable.DataCircleMap.put(model.getDeviceNum(), model);
			dataCircleService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除同步周期管理
	 * @author liuxd
	 * @date 2020-06-19 22:35:52
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			dataCircleService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改同步周期管理
	 * @author liuxd
	 * @date 2020-06-19 22:35:52
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			ThreadStaticVariable.DataCircleMap.put(model.getDeviceNum(), model);
			dataCircleService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	 * 获取erp地址
	 */
	public void findErp(){
		try{
			DataCircle dataCircle =dataCircleService.getFirstRecordByField("from DataCircle WHERE deviceNum='ERP'");
			outJsonData(dataCircle);
		}catch(Exception e){
			outErrorJson("获取当前用户失败!");
		}
	}
	 
}
