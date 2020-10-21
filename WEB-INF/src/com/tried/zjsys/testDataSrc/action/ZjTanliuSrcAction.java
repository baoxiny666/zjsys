package com.tried.zjsys.testDataSrc.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.zjsys.testDataSrc.model.ZjTanliuSrc;
import com.tried.zjsys.testDataSrc.service.ZjTanliuSrcService;

/**
 * @Description - 管理
 * @author liuxd
 * @date 2020-06-24 22:35:41
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjTanliuSrcAction extends BaseAction<ZjTanliuSrc> {
	private static Logger logger = Logger.getLogger(ZjTanliuSrcAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjTanliuSrcService zjTanliuSrcService;

	/**
	 * @Description 分页显示-
	 * @author liuxd
	 * @date 2020-06-24 22:35:41
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
			if(strIsNotNull(model.getDataStatus())){
				this.condition +=  " and dataStatus='"+model.getDataStatus()+"'";
			}
			this.condition +=  this.getOrderColumn();
			outRowsData(zjTanliuSrcService.findAll("from ZjTanliuSrc where 1=1 " + this.condition));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
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
			zjTanliuSrcService.del(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}
	
	/**
	 * 恢复数据
	 */
	public void recovery() {
		try {
			String[] ids = recordIdS.split(",");
			zjTanliuSrcService.recovery(ids);
			outSuccessJson("恢复成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("恢复失败");
		}
	}

	/**
	 * 手动刷新
	 */
	public void refreshData(){
		try{
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());	
			zjTanliuSrcService.synCollect(model.getDeviceNum(),getObjStartTime());
			outSuccessJson("刷新成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("刷新失败");
		}
	}
	 
}
