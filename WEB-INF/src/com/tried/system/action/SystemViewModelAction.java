package com.tried.system.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tried.demo.model.util.SqlStrlTool;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemViewModel;
import com.tried.system.service.SystemViewModelService;

/**
 * @Description 视图工具 管理
 * @author liuxd
 * @date 2016-01-21 20:59:00
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemViewModelAction extends BaseAction<SystemViewModel> {
	private static Logger logger = Logger.getLogger(SystemViewModelAction.class);
	private static final long serialVersionUID = 1L;
	@Resource
	SystemViewModelService systemViewModelService;

	/**
	 * @Description 分页显示视图工具
	 * @author liuxd
	 * @date 2016-01-21 20:59:00
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getView_name() && !model.getView_name().isEmpty()) {
				this.condition = " and view_name like '%" + model.getView_name() + "%'";
			}
			this.condition = this.condition + "  " + this.getOrderColumn();
			outJsonData(systemViewModelService.findPage(new Page<SystemViewModel>(page, rows), "from SystemViewModel where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
			}
	}

	/**
	 * @Description 添加视图工具
	 * @author liuxd
	 * @date 2016-01-21 20:59:00
	 * @version V1.0
	 */
	public void add() {
		try {
			systemViewModelService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除视图工具
	 * @author liuxd
	 * @date 2016-01-21 20:59:00
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_view_model   where id='" + ids[i]+"'");
				}
			}
			systemViewModelService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改视图工具
	 * @author liuxd
	 * @date 2016-01-21 20:59:00
	 * @version V1.0
	 */
	public void edit() {
		try {
			systemViewModelService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	/**
	  * @Description 视图生产工具
	  * @author liuxd
	  * @date 2016-1-21 下午9:11:47
	  * @version V1.0
	 */
	public void create() {
		try {
			String[] ids = recordIdS.split(",");
			for (String id : ids) {
				if (id.isEmpty()) {
					continue;
				} else {
					SystemViewModel viewModel=systemViewModelService.getById(id);
					SqlStrlTool.createPage(viewModel);
				}
			}
			outSuccessJson("执行成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("执行失败");
		}
	}
	 
}
