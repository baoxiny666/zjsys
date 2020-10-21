package com.tried.zjsys.testDataSrc.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.zjsys.testDataSrc.model.ZjDingliuFly;
import com.tried.zjsys.testDataSrc.model.ZjDingliuSrc;
import com.tried.zjsys.testDataSrc.service.ZjDingliuFlyService;
import com.tried.zjsys.testDataSrc.service.ZjDingliuSrcService;

/**
 * @Description - 管理
 * @author liuxd
 * @date 2020-06-28 21:07:24
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjDingliuSrcAction extends BaseAction<ZjDingliuSrc> {
	private static Logger logger = Logger.getLogger(ZjDingliuSrcAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjDingliuSrcService zjDingliuSrcService;
	@Resource
	ZjDingliuFlyService zjDingliuFlyService;

	/**
	 * @Description 分页显示-
	 * @author liuxd
	 * @date 2020-06-28 21:07:24
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(getObjStartTime())) {
				this.condition += " and tesDate like '" + getObjStartTime() + "%' ";
			}
			if (strIsNotNull(model.getDeviceNum())) {
				this.condition += " and deviceNum= '" + model.getDeviceNum() + "'";
			}
			if (strIsNotNull(model.getSample_no())) {
				this.condition += " and sample_no like '%" + model.getSample_no() + "%'";
			}
			if(strIsNotNull(model.getDataStatus())){
				this.condition +=  " and dataStatus='"+model.getDataStatus()+"'";
			}
			this.condition +=  this.getOrderColumn();
			outRowsData(zjDingliuSrcService.findAll( "from ZjDingliuSrc where 1=1 " + this.condition));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
 
	/**
	 * 手动刷新
	 */
	public void refreshData042(){
		try{
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());	
	  	     zjDingliuSrcService.synCollect042(model.getDeviceNum(),getObjStartMonth());
			outSuccessJson("刷新成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("刷新失败");
		}
	}
	/**
	 * 手动刷新
	 */
	public void refreshData8500(){
		try{
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());	
	  	     zjDingliuSrcService.synCollect8500(model.getDeviceNum(),getObjStartTime());
			outSuccessJson("刷新成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("刷新失败");
		}
	}
	 /**
	  * 均值发送
	  */
	public void flyJunZhi(){
		try{
				model.setRecordUser(getCurrentUser().getId());
				zjDingliuSrcService.flyJunZhi(model);
			outSuccessJson("发送成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("发送失败");
		}
	}
	/**
	 * 发送
	 */
	public void fly(){
		try{
	  	     zjDingliuSrcService.fly(recordIdS,getCurrentUser().getId());
			outSuccessJson("发送成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("发送失败");
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
			zjDingliuSrcService.del(ids);
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
			zjDingliuSrcService.recovery(ids);
			outSuccessJson("恢复成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("恢复失败");
		}
	}

}
