package com.tried.system.action;

import java.util.Date;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemSwitchValue;
import com.tried.system.service.SystemSwitchValueService;

/**
 * @Description - 管理
 * @author sunlunan
 * @date 2020-05-15 15:06:35
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemSwitchValueAction extends BaseAction<SystemSwitchValue> {
	private static Logger logger = Logger.getLogger(SystemSwitchValueAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemSwitchValueService systemSwitchValueService;

	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-05-15 15:06:35
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getSwitchName())) {
				this.condition = " and switchName like '%" + model.getSwitchName() + "%'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(systemSwitchValueService.findPage(new Page<SystemSwitchValue>(page, rows), "from SystemSwitchValue where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author sunlunan
	 * @date 2020-05-15 15:06:35
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemSwitchValueService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author sunlunan
	 * @date 2020-05-15 15:06:35
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			systemSwitchValueService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author sunlunan
	 * @date 2020-05-15 15:06:35
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemSwitchValueService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
