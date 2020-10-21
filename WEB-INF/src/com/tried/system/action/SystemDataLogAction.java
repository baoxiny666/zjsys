package com.tried.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemDataLog;
import com.tried.system.service.SystemDataLogService;

/**
 * @Description 内容修改日志表 管理
 * @author liuxd
 * @date 2018-07-20 15:11:32
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemDataLogAction extends BaseAction<SystemDataLog> {
	private static Logger logger = Logger.getLogger(SystemDataLogAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemDataLogService systemDataLogService;

	/**
	 * @Description 分页显示内容修改日志表
	 * @author liuxd
	 * @date 2018-07-20 15:11:32
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getId() && !model.getId().isEmpty()) {
				this.condition = " and id like '%" + model.getId() + "%'";
			}
			this.condition = this.condition + "  " + this.getOrderColumn();
			outJsonData(systemDataLogService.findPage(new Page<SystemDataLog>(page, rows), "from SystemDataLog where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加内容修改日志表
	 * @author liuxd
	 * @date 2018-07-20 15:11:32
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemDataLogService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除内容修改日志表
	 * @author liuxd
	 * @date 2018-07-20 15:11:32
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_data_log   where id='" + ids[i]+"'");
				}
			}
			systemDataLogService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改内容修改日志表
	 * @author liuxd
	 * @date 2018-07-20 15:11:32
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemDataLogService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
