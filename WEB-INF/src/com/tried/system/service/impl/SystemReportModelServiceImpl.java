package com.tried.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemReportModel;
import com.tried.system.service.FileManagerService;
import com.tried.system.service.SystemReportModelService;
import com.tried.system.service.SystemSerialService;
@Service
public class SystemReportModelServiceImpl extends BaseServiceImpl<SystemReportModel> implements SystemReportModelService {
	@Resource
	FileManagerService fileManagerService;
	@Resource
	SystemSerialService systemSerialService;
	@Override
	public void save(SystemReportModel model, String pkId) throws Exception {
		if (model.getReportNum() == null || model.getReportNum().length() == 0) {
			model.setReportNum(systemSerialService.getSerialInfo("报告模板-样机编号"));
		}	
		this.add(model);
		fileManagerService.add("system_report_model_file", model.getSystem_report_model_file(), pkId, model.getId());
			
	}

	@Override
	public void edit(SystemReportModel model, String pkId) throws Exception {
		// TODO Auto-generated method stub
		fileManagerService.add("system_report_model_file", model.getSystem_report_model_file(), pkId, model.getId());
		this.update(model);
	}

	 

}
