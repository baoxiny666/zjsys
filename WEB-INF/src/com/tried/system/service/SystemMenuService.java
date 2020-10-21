package com.tried.system.service;

import java.util.List;
import java.util.Map;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemMenu;

/**
 * @Description 功能管理 服务接口
 * @author liuxd
 * @date 2015-12-25 16:08:34
 * @version V1.0
 */
public interface SystemMenuService extends BaseService<SystemMenu> {

	/**
	  * @Description 获取功能树
	  * @author liuxd
	  * @date 2016-3-28 下午4:12:24
	  * @version V1.0
	 */
	public List<SystemMenu> menuTree(String userId)throws Exception;
	
	public List<Map<String,Object>> menuObject(String userId)throws Exception;
}
