package com.tried.zjsys.testDataSrc.action;

import java.util.Date;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiSrc;
import com.tried.zjsys.testDataSrc.service.ZjGongyefenxiSrcService;

/**
 * @Description - 管理
 * @author sunlunan
 * @date 2020-06-28 10:03:57
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjGongyefenxiSrcAction extends BaseAction<ZjGongyefenxiSrc> {
	private static Logger logger = Logger.getLogger(ZjGongyefenxiSrcAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjGongyefenxiSrcService zjGongyefenxiSrcService;

	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-06-28 10:03:57
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getDeviceNum())) {
				this.condition += "  and c.deviceNum  in(" + model.getDeviceNum()+ ")";
			}
			if (strIsNotNull(model.getTestType())) {
				this.condition += "  and g.testType ='" + model.getTestType()+ "'";
			}
			if (strIsNotNull(model.getDataStatus())) {
				this.condition += "  and g.dataStatus ='" + model.getDataStatus()+ "'";
			}
			if (strIsNotNull(model.getSampleNum())) {
				this.condition += " and g.sampleNum like '%" +model.getSampleNum()+ "%'";
			}
			if (strIsNotNull(objStartTime)) {
				this.condition += " and substring(g.test_syrq,0,11) = '" +objStartTime+ "'";
			}			
			//this.condition +=" order by g.sampleNum ,g.test_syrq ";
			this.condition +=  this.getOrderColumn();
			outRowsData(zjGongyefenxiSrcService.findAll("from ZjGongyefenxiSrc g,DataCircle c  where g.circleId=c.id " + this.condition));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author sunlunan
	 * @date 2020-06-28 10:03:57
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			zjGongyefenxiSrcService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author sunlunan
	 * @date 2020-06-28 10:03:57
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			zjGongyefenxiSrcService.del(ids);
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
			zjGongyefenxiSrcService.recovery(ids);
			outSuccessJson("恢复成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("恢复失败");
		}
	}
	/**
	 * @Description 修改-
	 * @author sunlunan
	 * @date 2020-06-28 10:03:57
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			zjGongyefenxiSrcService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	
	 /**
	  * 工业分析仪
	  * 上传刷新数据
	  */
	public void uploadGngyefenxiData(){
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());	
			if(model.getDeviceNum().contains("039")){
				zjGongyefenxiSrcService.synCollect039(model.getDeviceNum(),objStartMonth);
			}else if(model.getDeviceNum().contains("040")){
				zjGongyefenxiSrcService.synCollect040(model.getDeviceNum(),objStartMonth);
			}else if(model.getDeviceNum().contains("038")){
				zjGongyefenxiSrcService.synCollect038(model.getDeviceNum(),objStartMonth);
			}else if(model.getDeviceNum().contains("041")){
				zjGongyefenxiSrcService.synCollect041(model.getDeviceNum(),objStartMonth);
			}			
			outSuccessJson("数据已刷新");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("刷新失败");
		}
	
	}
	/**
	 * 发送
	 */
	public void fly(){
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			String[] ids = recordIdS.split(",");
			zjGongyefenxiSrcService.flySrcData(ids,model);
			outSuccessJson("发送成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("发送失败");
		}
	
	
	}
	 
}
