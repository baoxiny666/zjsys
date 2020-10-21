package com.tried.zjsys.testDataSrc.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.zjsys.testDataSrc.model.YinguangFly;
import com.tried.zjsys.testDataSrc.service.YinguangFlyService;

/**
 * @Description - 管理
 * @author sunlunan
 * @date 2020-06-23 15:37:29
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class YinguangFlyAction extends BaseAction<YinguangFly> {
	private static Logger logger = Logger.getLogger(YinguangFlyAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	YinguangFlyService yinguangFlyService;

	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-06-23 15:37:29
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getDeviceNum())) {
				this.condition += " and  c.deviceNum like '%" + model.getDeviceNum() + "%'";
			}
			if(strIsNotNull(model.getDataStatus())){
				this.condition += " and  y.dataStatus='"+model.getDataStatus()+"'";
			}
			if (strIsNotNull(getObjStartTime())) {
				this.condition += " and y.dataTime like '" + getObjStartTime() + "%'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(yinguangFlyService.findPage(new Page<YinguangFly>(page, rows), "from YinguangFly y, DataCircle c where y.circleId=c.id  " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author sunlunan
	 * @date 2020-06-23 15:37:29
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			yinguangFlyService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author sunlunan
	 * @date 2020-06-23 15:37:29
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			yinguangFlyService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author sunlunan
	 * @date 2020-06-23 15:37:29
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			yinguangFlyService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
