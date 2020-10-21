package com.tried.system.service;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemReportModel;

/**
 * @Description 报告模板 服务接口
 * @author liuxd
 * @date 2019-07-30 10:02:06
 * @version V1.0
 */
public interface SystemReportModelService extends BaseService<SystemReportModel> {
	public void	save(SystemReportModel model,String pkId)throws Exception;
	public void	edit(SystemReportModel model,String pkId)throws Exception;
}
