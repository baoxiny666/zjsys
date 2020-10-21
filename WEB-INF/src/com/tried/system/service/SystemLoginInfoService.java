package com.tried.system.service;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemLoginInfo;

/**
 * @Description 用户登录记录表 服务接口
 * @author liuyw
 * @date 2016-11-18 10:44:55
 * @version V1.0
 */
public interface SystemLoginInfoService extends BaseService<SystemLoginInfo> {
	
	/**
	 * 根据用户Id查询在线用户信息
	 * @param id
	 * @return
	 */
	SystemLoginInfo getByLoginName(String loginName) throws Exception;

	/**
	 * 退出登录
	 * @param loginInfo
	 * @throws Exception
	 */
	void addLogout(SystemLoginInfo loginInfo) throws Exception;

	/**
	 * 登录系统
	 * @param loginInfo
	 * @throws Exception
	 */
	void addLogin(SystemLoginInfo loginInfo) throws Exception;

}
