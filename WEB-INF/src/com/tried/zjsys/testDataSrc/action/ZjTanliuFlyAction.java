package com.tried.zjsys.testDataSrc.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.zjsys.testDataSrc.model.ZjTanliuFly;
import com.tried.zjsys.testDataSrc.service.ZjTanliuFlyService;
import com.tried.zjsys.testDataSrc.service.ZjTanliuSrcService;

/**
 * @Description - 管理
 * @author liuxd
 * @date 2020-06-24 22:35:40
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjTanliuFlyAction extends BaseAction<ZjTanliuFly> {
	private static Logger logger = Logger.getLogger(ZjTanliuFlyAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjTanliuFlyService zjTanliuFlyService;

	/**
	 * @Description 分页显示-
	 * @author liuxd
	 * @date 2020-06-24 22:35:40
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(getObjStartTime())) {
				this.condition += " and time like '" + getObjStartTime() + "%'";
			}
			if (strIsNotNull(model.getDeviceNum())) {
				this.condition += " and deviceNum= '" + model.getDeviceNum() + "'";
			}
			if (strIsNotNull(model.getSampleNum())) {
				this.condition += " and sampleNum like '%" + model.getSampleNum() + "%'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(zjTanliuFlyService.findPage(new Page<ZjTanliuFly>(page, rows), "from ZjTanliuFly where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	/**
	 * 提交数据
	 */
	public void submitData() {
		try {
			zjTanliuFlyService.submitData(recordIdS,getCurrentUser().getId());
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
