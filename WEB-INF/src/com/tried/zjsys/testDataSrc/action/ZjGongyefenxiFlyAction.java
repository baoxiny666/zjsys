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
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiFly;
import com.tried.zjsys.testDataSrc.service.ZjGongyefenxiFlyService;

/**
 * @Description - 管理
 * @author sunlunan
 * @date 2020-07-05 21:49:13
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjGongyefenxiFlyAction extends BaseAction<ZjGongyefenxiFly> {
	private static Logger logger = Logger.getLogger(ZjGongyefenxiFlyAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjGongyefenxiFlyService zjGongyefenxiFlyService;

	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-07-05 21:49:13
	 * @version V1.0
	 */
	public void list() {
		try {

			if(strIsNotNull(model.getDataStatus())){
				this.condition += " and  dataStatus='"+model.getDataStatus()+"'";
			}
			if (strIsNotNull(getObjStartTime())) {
				this.condition += " and  test_syrq = '"+getObjStartTime()+ "'";
					}
			this.condition +=  this.getOrderColumn();
			outJsonData(zjGongyefenxiFlyService.findPage(new Page<ZjGongyefenxiFly>(page, rows), "from ZjGongyefenxiFly where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author sunlunan
	 * @date 2020-07-05 21:49:13
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			zjGongyefenxiFlyService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author sunlunan
	 * @date 2020-07-05 21:49:13
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			zjGongyefenxiFlyService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author sunlunan
	 * @date 2020-07-05 21:49:13
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			ZjGongyefenxiFly fly=zjGongyefenxiFlyService.getById(model.getId());
			if(model.getInput_M10()!=null){
				fly.setInput_M10(model.getInput_M10());
			}
			if(model.getInput_M25()!=null){
				fly.setInput_M25(model.getInput_M25());
			}
			if(model.getInput_Mt()!=null){
				fly.setInput_Mt(model.getInput_Mt());
			}	
			fly.setRecordTime(model.getRecordTime());
			fly.setRecordUser(model.getRecordUser());
			zjGongyefenxiFlyService.update(fly);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
