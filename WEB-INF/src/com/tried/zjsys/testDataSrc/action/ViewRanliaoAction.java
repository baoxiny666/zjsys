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
import com.tried.zjsys.testDataSrc.model.ViewRanliao;
import com.tried.zjsys.testDataSrc.service.ViewRanliaoService;

/**
 * @Description - 管理
 * @author liuxd
 * @date 2020-07-10 11:14:02
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ViewRanliaoAction extends BaseAction<ViewRanliao> {
	private static Logger logger = Logger.getLogger(ViewRanliaoAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ViewRanliaoService viewRanliaoService;

	/**
	 * @Description 分页显示-
	 * @author liuxd
	 * @date 2020-07-10 11:14:02
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getId())) {
				this.condition = " and id like '%" + model.getId() + "%'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(viewRanliaoService.findPage(new Page<ViewRanliao>(page, rows), "from ViewRanliao where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author liuxd
	 * @date 2020-07-10 11:14:02
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			viewRanliaoService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author liuxd
	 * @date 2020-07-10 11:14:02
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			viewRanliaoService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author liuxd
	 * @date 2020-07-10 11:14:02
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			viewRanliaoService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
