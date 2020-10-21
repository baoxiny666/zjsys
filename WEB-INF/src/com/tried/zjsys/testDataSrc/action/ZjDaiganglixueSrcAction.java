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
import com.tried.zjsys.testDataSrc.model.ZjDaiganglixueSrc;
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiSrc;
import com.tried.zjsys.testDataSrc.service.ZjDaiganglixueSrcService;

/**
 * @Description - 管理
 * @author sunlunan
 * @date 2020-07-01 10:39:20
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjDaiganglixueSrcAction extends BaseAction<ZjDaiganglixueSrc> {
	private static Logger logger = Logger.getLogger(ZjDaiganglixueSrcAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	ZjDaiganglixueSrcService zjDaiganglixueSrcService;

	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-07-01 10:39:20
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getDeviceNum())) {
				this.condition += "  and g.deviceNum ='" + model.getDeviceNum()+ "'";
			}
			if (strIsNotNull(model.getFilename())) {
				this.condition += "  and g.filename like '%" + model.getFilename()+ "%'";
			}
			if (strIsNotNull(model.getDataStatus())) {
				this.condition += "  and g.dataStatus ='" + model.getDataStatus()+ "'";
			}
			if (strIsNotNull(objStartTime)) {
				this.condition += " and g.filename like '" +objStartTime+ "%'";
			}
			if(strIsNotNull(model.getClassGroup())){
				this.condition += "  and g.classGroup like '%" + model.getClassGroup()+ "%'";	
			}
			if(strIsNotNull(model.getLunum())){
				this.condition += "  and g.lunum like '%" + model.getLunum()+ "%'";	
			}
			if(strIsNotNull(model.getSteeltype())){
				this.condition += "  and g.steeltype  like '%" + model.getSteeltype()+ "%'";	
			}
			if(strIsNotNull(model.getSteelGuige())){
				this.condition += "  and g.steelGuige like '%" + model.getSteelGuige()+ "%'";	
			}
			if(strIsNotNull(model.getBranchFactory())){
				this.condition += "  and g.branchFactory like '%" + model.getBranchFactory()+ "%'";	
			}
			this.condition +=  this.getOrderColumn();			
			outRowsData( zjDaiganglixueSrcService.findAll("from ZjDaiganglixueSrc g  where 1=1 " + this.condition));
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
			zjDaiganglixueSrcService.del(ids);
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
			zjDaiganglixueSrcService.recovery(ids);
			outSuccessJson("恢复成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("恢复失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author sunlunan
	 * @date 2020-07-01 10:39:20
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			//model.setRecordUser(getCurrentUser().getId());
			zjDaiganglixueSrcService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	
	public void refreshData(){
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());	
			if(model.getDeviceNum().contains("031")){
				zjDaiganglixueSrcService.synCollect031(model.getDeviceNum(), objStartMonth);
			}else if(model.getDeviceNum().contains("032")){
				zjDaiganglixueSrcService.synCollect032(model.getDeviceNum(), objStartMonth);
			}else if(model.getDeviceNum().contains("033")){
				zjDaiganglixueSrcService.synCollect033(model.getDeviceNum(), objStartMonth);
			}else if(model.getDeviceNum().contains("034")){
				zjDaiganglixueSrcService.synCollect034(model.getDeviceNum(), objStartMonth);
			}			
			outSuccessJson("数据已刷新");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("刷新失败");
		}		
	}
	
	public void fly(){
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			String[] ids = recordIdS.split(",");			
			zjDaiganglixueSrcService.flySrcData(ids,model);				
			outSuccessJson("发送成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("发送失败");
		}

	}
	
	
	
	
	
	 
}
