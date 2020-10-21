package com.tried.system.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemSerialMax;
import com.tried.system.service.SystemSerialMaxService;

/**
 * @Description - 管理
 * @author liuxd
 * @date 2019-03-01 10:59:02
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemSerialMaxAction extends BaseAction<SystemSerialMax> {
	private static Logger logger = Logger.getLogger(SystemSerialMaxAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemSerialMaxService systemSerialMaxService;

	/**
	 * @Description 分页显示-
	 * @author liuxd
	 * @date 2019-03-01 10:59:02
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getSerialId())) {
				this.condition += " and serialId = '" + model.getSerialId() + "'";
			}
			if (strIsNotNull(model.getSysStandardValue())) {
				this.condition += " and sysStandardValue like '%" + model.getSysStandardValue() + "%'";
			}
			if(this.getOrderColumn().isEmpty()){
				this.condition += " order by recordTime desc ";
			}else{
				this.condition += this.getOrderColumn();
			}
			outJsonData(systemSerialMaxService.findPage(new Page<SystemSerialMax>(page, rows), "from SystemSerialMax where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author liuxd
	 * @date 2019-03-01 10:59:02
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemSerialMaxService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author liuxd
	 * @date 2019-03-01 10:59:02
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			systemSerialMaxService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author liuxd
	 * @date 2019-03-01 10:59:02
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemSerialMaxService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
