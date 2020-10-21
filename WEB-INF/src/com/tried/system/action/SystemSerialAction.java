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
import com.tried.system.model.SystemSerial;
import com.tried.system.service.SystemSerialService;

/**
 * @Description - 管理
 * @author liuxd
 * @date 2018-02-05 23:16:12
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemSerialAction extends BaseAction<SystemSerial> {
	private static Logger logger = Logger.getLogger(SystemSerialAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemSerialService systemSerialService;

	/**
	 * @Description 分页显示-
	 * @author liuxd
	 * @date 2018-02-05 23:16:12
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getSysType() && !model.getSysType().isEmpty()) {
				this.condition = " and sysType like '%" + model.getSysType() + "%'";
			}
			if (null != model.getSysStandard() && !model.getSysStandard().isEmpty()) {
				this.condition = " and sysStandard like '%" + model.getSysStandard()+ "%'";
			}
			this.condition = this.condition + "  " + this.getOrderColumn();
			outJsonData(systemSerialService.findPage(new Page<SystemSerial>(page, rows), "from SystemSerial where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author liuxd
	 * @date 2018-02-05 23:16:12
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemSerialService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author liuxd
	 * @date 2018-02-05 23:16:12
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_serial   where id='" + ids[i]+"'");
				}
			}
			systemSerialService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author liuxd
	 * @date 2018-02-05 23:16:12
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemSerialService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
