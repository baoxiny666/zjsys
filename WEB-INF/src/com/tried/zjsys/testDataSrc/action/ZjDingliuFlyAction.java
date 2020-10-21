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
import com.tried.zjsys.testDataSrc.model.ZjDingliuFly;
import com.tried.zjsys.testDataSrc.service.ZjDingliuFlyService;

/**
 * @Description - 管理
 * @author liuxd
 * @date 2020-06-28 21:07:23
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjDingliuFlyAction extends BaseAction<ZjDingliuFly> {
	private static Logger logger = Logger.getLogger(ZjDingliuFlyAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjDingliuFlyService zjDingliuFlyService;

	/**
	 * @Description 分页显示-
	 * @author liuxd
	 * @date 2020-06-28 21:07:23
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(getObjStartTime())) {
				this.condition += " and tesDate like '" + getObjStartTime() + "%'";

			}
			if (strIsNotNull(model.getDeviceNum())) {
				this.condition += " and deviceNum= '" + model.getDeviceNum() + "'";
			}
			if (strIsNotNull(model.getSample_no())) {
				this.condition += " and sample_no like  '%" + model.getSample_no() + "%'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(zjDingliuFlyService.findPage(new Page<ZjDingliuFly>(page, rows), "from ZjDingliuFly where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	 
	 
}
