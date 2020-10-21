package com.tried.system.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemReportModel;
import com.tried.system.service.SystemReportModelService;

/**
 * @Description 报告模板 管理
 * @author liuxd
 * @date 2019-07-30 10:02:06
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemReportModelAction extends BaseAction<SystemReportModel> {
	private static Logger logger = Logger.getLogger(SystemReportModelAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemReportModelService systemReportModelService;

	/**
	 * @Description 分页显示报告模板
	 * @author liuxd
	 * @date 2019-07-30 10:02:06
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getReportNum())) {
				this.condition += " and reportNum like '%" + model.getReportNum() + "%'";
			}
			if (strIsNotNull(model.getReportName())) {
				this.condition += " and reportName like '%" + model.getReportName() + "%'";
			}
			if (strIsNotNull(model.getModelType())) {
				this.condition += " and modelType = '" + model.getModelType() + "'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(systemReportModelService.findPage(new Page<SystemReportModel>(page, rows), "from SystemReportModel where 1=1  " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	public void qtList() {
		try {
			if (strIsNotNull(model.getReportNum())) {
				this.condition += " and reportNum like '%" + model.getReportNum() + "%'";
			}
			if (strIsNotNull(model.getReportName())) {
				this.condition += " and reportName like '%" + model.getReportName() + "%'";
			}
			if (strIsNotNull(model.getModelType())) {
				this.condition += " and modelType = '" + model.getModelType() + "'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(systemReportModelService.findPage(new Page<SystemReportModel>(page, rows), "from SystemReportModel where modelType<>'样机报告模板'  " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}	
	/**
	 * 
		 * @Description 获取模板下拉框
		 * @author liuxd
		 * @date 2019-7-30 上午11:21:29
		 * @version V1.0
	 */
	public void findModelCombox(){
		try{
			List<SystemReportModel>list=systemReportModelService.findAll();
			if(model.getModelType()!=null){
				if(model.getModelType().equals("样机报告模板")){
					list=systemReportModelService.findAll(" from SystemReportModel where modelType='样机报告模板'");	
				}else{
					list=systemReportModelService.findAll(" from SystemReportModel where modelType<>'样机报告模板'");	
				}
			}			
			outJsonList(list);
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	/**
	 * @Description 添加报告模板
	 * @author liuxd
	 * @date 2019-07-30 10:02:06
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemReportModelService.save(model, pkId);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除报告模板
	 * @author liuxd
	 * @date 2019-07-30 10:02:06
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			systemReportModelService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改报告模板
	 * @author liuxd
	 * @date 2019-07-30 10:02:06
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemReportModelService.edit(model, pkId);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
