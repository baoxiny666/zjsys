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
import com.tried.zjsys.testDataSrc.model.ZjDaiganglixueFly;
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiFly;
import com.tried.zjsys.testDataSrc.service.ZjDaiganglixueFlyService;

/**
 * @Description - 管理
 * @author sunlunan
 * @date 2020-07-01 09:19:04
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjDaiganglixueFlyAction extends BaseAction<ZjDaiganglixueFly> {
	private static Logger logger = Logger.getLogger(ZjDaiganglixueFlyAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjDaiganglixueFlyService zjDaiganglixueFlyService;

	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-07-01 09:19:04
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getDeviceNum())) {
				this.condition += " and  c.deviceNum like '%" + model.getDeviceNum() + "%'";
			}
			if(strIsNotNull(model.getDataStatus())){
				this.condition += " and  y.dataStatus='"+model.getDataStatus()+"'";
			}
			if (strIsNotNull(objStartTime)) {
				this.condition += " and y.testTime like '" +objStartTime+ "%'";
			}
			if (strIsNotNull(model.getClassGroup())) {
				this.condition += " and y.classGroup like '%" +model.getClassGroup()+ "%'";
			}
			if (strIsNotNull(model.getLunum())) {
				this.condition += " and y.lunum like '%" +model.getLunum()+ "%'";
			}
			if (strIsNotNull(model.getSteeltype())) {
				this.condition += " and y.steeltype like '%" +model.getSteeltype()+ "%'";
			}
			if (strIsNotNull(model.getSteelGuige())) {
				this.condition += " and y.steelGuige like '%" +model.getSteelGuige()+ "%'";
			}
			if (strIsNotNull(model.getBranchFactory())) {
				this.condition += " and y.branchFactory like '%" +model.getBranchFactory()+ "%'";
			}
			this.condition +=  this.getOrderColumn();
			
			outJsonData(zjDaiganglixueFlyService.findPage(new Page<ZjDaiganglixueFly>(page, rows), "from ZjDaiganglixueFly y,DataCircle c where y.circleId=c.id  " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author sunlunan
	 * @date 2020-07-01 09:19:04
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			zjDaiganglixueFlyService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author sunlunan
	 * @date 2020-07-01 09:19:04
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			zjDaiganglixueFlyService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author sunlunan
	 * @date 2020-07-01 09:19:04
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			zjDaiganglixueFlyService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	} 
}
