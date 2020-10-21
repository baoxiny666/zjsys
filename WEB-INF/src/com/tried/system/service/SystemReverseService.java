package com.tried.system.service;

import java.util.List;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemReverse;
import com.tried.system.model.SystemTableModel;

/**
 * @Description 逆向工具 服务接口
 * @author liuxd
 * @date 2016-01-28 10:26:06
 * @version V1.0
 */
public interface SystemReverseService extends BaseService<SystemReverse> {
	/**
	  * @Description 查看所有table和视图
	  * @author liuxd
	  * @date 2016-1-28 上午10:29:45
	  * @version V1.0
	 */
	public List<SystemReverse> findTableView(String modelName)throws Exception;

	/**
	  * @Description 根据表、视图名字检索对应对象详情信息
	  * @author liuxd
	  * @date 2016-2-1 上午11:23:45
	  * @version V1.0
	 */
	public SystemTableModel findModelInfo(String modelName)throws Exception;

	/**
	  * @Description 根据表、视图名字检索对应字段详情信息
	  * @author liuxd
	  * @date 2016-2-1 上午11:23:45
	  * @version V1.0
	 */
	public List<SystemTableModel> findModelColumnInfo(String modelName,String modelType)throws Exception;
}
