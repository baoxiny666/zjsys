package com.tried.system.service;

import java.util.List;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemTableModel;

/**
 * @Description 表/视图结构 服务接口
 * @author liuxd
 * @date 2015-09-21 19:47:17
 * @version V1.0
 */
public interface SystemTableModelService extends BaseService<SystemTableModel> {

	/**
	  * @Description 生产表结构
	  * @author liuxd
	  * @date 2015-10-29 上午10:45:43
	  * @version V1.0
	 */
	public void createTable(List<String> recordId)throws Exception;
	
	/**
	  * @Description 生产页面
	  * @author liuxd
	  * @date 2015-10-29 上午10:45:43
	  * @version V1.0
	 */
	public void createPage(List<String> recordId)throws Exception;
	
}
