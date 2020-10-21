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
import com.tried.zjsys.testDataSrc.model.ViewHandInput;
import com.tried.zjsys.testDataSrc.service.ViewHandInputService;

/**
 * @Description - 管理
 * @author liuxd
 * @date 2020-07-10 11:14:03
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ViewHandInputAction extends BaseAction<ViewHandInput> {
	private static Logger logger = Logger.getLogger(ViewHandInputAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ViewHandInputService viewHandInputService;

	/**
	 * @Description 分页显示-
	 * @author liuxd
	 * @date 2020-07-10 11:14:03
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getId())) {
				this.condition = " and id like '%" + model.getId() + "%'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(viewHandInputService.findPage(new Page<ViewHandInput>(page, rows), "from ViewHandInput where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author liuxd
	 * @date 2020-07-10 11:14:03
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			viewHandInputService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author liuxd
	 * @date 2020-07-10 11:14:03
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			viewHandInputService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author liuxd
	 * @date 2020-07-10 11:14:03
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			viewHandInputService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
