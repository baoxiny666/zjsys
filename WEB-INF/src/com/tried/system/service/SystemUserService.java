package com.tried.system.service;

import java.util.List;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemUser;

/**
 * @Description 用户信息 服务接口
 * @author liuxd
 * @date 2015-09-09 22:43:36
 * @version V1.0
 */
public interface SystemUserService extends BaseService<SystemUser> {

	/**
	  * @Description 根据用户名密码获取用户信息
	  * @author liuxd
	  * @date 2016-3-28 上午10:08:09
	  * @version V1.0
	 */
	public SystemUser userInfo(String userName) throws Exception;

	public List<SystemUser> findOnlines(String loginUsers) throws Exception;
	
	/**
	  * @Description 添加用户
	  * @author wangxi
	  * @date 2016-12s-7 上午14:46:37
	  * @version V1.0
	 */
	public void add(SystemUser systemUser,String pkId,String workJzId) throws Exception;
	
	public void userUpdate(SystemUser systemUser,String pkId,String workJzId)throws Exception;
	
	/**
	  * @Description 修改用户
	  * @author wangxi
	  * @date 2016-12s-7 上午14:46:37
	  * @version V1.0
	 */
	public void edit(SystemUser systemUser,String pkId,String workJzId)throws Exception;
	
	public List<SystemUser> getWorkTextById(String id) throws Exception;

	public SystemUser findByName(String checkPerson) throws Exception;

	public List<SystemUser> getPlanUserListByPlanId(String planId) throws Exception;
}
