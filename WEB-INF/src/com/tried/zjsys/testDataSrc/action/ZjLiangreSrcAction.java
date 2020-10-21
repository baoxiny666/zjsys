package com.tried.zjsys.testDataSrc.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.zjsys.testDataSrc.model.ZjLiangreSrc;
import com.tried.zjsys.testDataSrc.service.ZjLiangreSrcService;

/**
 * @Description - 管理
 * @author lyw
 * @date 2020-06-28 21:31:05
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjLiangreSrcAction extends BaseAction<ZjLiangreSrc> {
	private static Logger logger = Logger.getLogger(ZjLiangreSrcAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjLiangreSrcService zjLiangreSrcService;

	/**
	 * @Description 分页显示-
	 * @author lyw
	 * @date 2020-06-28 21:31:05
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getTestDate())) {
				this.condition += " and testDate = '" + model.getTestDate() + "'";
			}
			if(strIsNotNull(model.getSampleNum())){
				this.condition += " and sampleNum like '%"+model.getSampleNum()+"%'";
			}
			if(strIsNotNull(model.getDataStatus())){
				this.condition +=  " and dataStatus='"+model.getDataStatus()+"'";
			}
			this.condition +=  this.getOrderColumn();
			
			outRowsData(zjLiangreSrcService.findAll("from ZjLiangreSrc where 1=1 " + this.condition));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	/**
	 * 手动刷新
	 */
	public void refreshData(){
		try{
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());	
			zjLiangreSrcService.synCollectHand(model.getDeviceNum(),getObjStartTime());
			outSuccessJson("刷新成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("刷新失败");
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
			zjLiangreSrcService.add(model);
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
			zjLiangreSrcService.del(ids);
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
			zjLiangreSrcService.recovery(ids);
			outSuccessJson("恢复成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("恢复失败");
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
			ZjLiangreSrc zjLiangreSrc= zjLiangreSrcService.getById(model.getId());
			zjLiangreSrc.setRecordTime(new Date());
			zjLiangreSrc.setRecordUser(getCurrentUser().getId());
			zjLiangreSrc.setTest_kgjgw(model.getTest_kgjgw());
			zjLiangreSrc.setTest_sdjdw(model.getTest_sdjdw());
			zjLiangreSrcService.update(zjLiangreSrc);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	/**
	 * 手动刷新
	 */
	public void uploadLingReData(){
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());	
			zjLiangreSrcService.synCollect(model.getDeviceNum());
			outSuccessJson("数据已刷新");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("刷新失败");
		}
	}
	
	/**
	 * 发送数据
	 */
	public void fly(){
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			String[] ids = recordIdS.split(",");
			zjLiangreSrcService.flySrcData(ids,model);
			outSuccessJson("发送成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("发送失败");
		}
	
	}
	

	 
}
