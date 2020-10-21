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
import com.tried.zjsys.testDataSrc.model.YingguangSrcBxy;
import com.tried.zjsys.testDataSrc.service.YingguangSrcBxyService;

/**
 * @Description - 管理
 * @author sunlunan
 * @date 2020-06-23 09:30:34
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class YingguangSrcBxyAction extends BaseAction<YingguangSrcBxy> {
	private static Logger logger = Logger.getLogger(YingguangSrcBxyAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	YingguangSrcBxyService yingguangSrcBxyService;

	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-06-23 09:30:34
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
				this.condition += " and substring(y.dataTime,0,11) = '" +objStartTime+ "'";
			}
			if(strIsNotNull(model.getSampleNum())){
				this.condition += " and  y.sampleNum like '%"+model.getSampleNum()+"%'";
			}
			this.condition +=  this.getOrderColumn();
			
			outRowsData(yingguangSrcBxyService.findAll("from YingguangSrcBxy y ,DataCircle c where y.circleId=c.id " + this.condition));
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
			yingguangSrcBxyService.del(ids);
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
			yingguangSrcBxyService.recovery(ids);
			outSuccessJson("恢复成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("恢复失败");
		}
	}


	/**
	 * @Description 添加-
	 * @author sunlunan
	 * @date 2020-06-23 09:30:34
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			yingguangSrcBxyService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	 

	/**
	 * @Description 修改-
	 * @author sunlunan
	 * @date 2020-06-23 09:30:34
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			yingguangSrcBxyService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	 * 上传荧光仪检测数据
	 */
	public void uploadYinguangData(){
		try {
			
			yingguangSrcBxyService.synCollect(model.getDeviceNum(),objStartMonth);	
			 
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
			yingguangSrcBxyService.flySrcData(ids,model);
			outSuccessJson("发送成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("发送失败");
		}
	
	}
	
	
	 
}
