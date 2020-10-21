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
import com.tried.zjsys.testDataSrc.model.ZjLiangreFly;
import com.tried.zjsys.testDataSrc.service.ZjLiangreFlyService;

/**
 * @Description - 管理
 * @author lyw
 * @date 2020-06-28 21:31:05
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjLiangreFlyAction extends BaseAction<ZjLiangreFly> {
	private static Logger logger = Logger.getLogger(ZjLiangreFlyAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjLiangreFlyService zjLiangreFlyService;

	/**
	 * @Description 分页显示-
	 * @author lyw
	 * @date 2020-06-28 21:31:05
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(getObjStartTime())) {
				this.condition += " and testDate like '" + getObjStartTime() + "%'";
			}
			if(strIsNotNull(model.getSampleNum())){
				this.condition += " and sampleNum like '%"+model.getSampleNum()+"%'"; 
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(zjLiangreFlyService.findPage(new Page<ZjLiangreFly>(page, rows), "from ZjLiangreFly where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author lyw
	 * @date 2020-06-28 21:31:05
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			zjLiangreFlyService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author lyw
	 * @date 2020-06-28 21:31:05
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			zjLiangreFlyService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author lyw
	 * @date 2020-06-28 21:31:05
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			zjLiangreFlyService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
