package com.tried.system.service;

import java.util.List;
import java.util.Map;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemMenu;
import com.tried.system.model.SystemRole;
import com.tried.system.model.SystemUser;

/**
 * @Description 角色信息 服务接口
 * @author liuxd
 * @date 2015-09-07 10:09:02
 * @version V1.0
 */
public interface SystemRoleService extends BaseService<SystemRole> {
	/**
	  * @Description 根据角色id获取已有粒子
	  * @author liuxd
	  * @date 2016-2-20 下午9:44:49
	  * @version V1.0
	 */
	public List<SystemMenu> roleMenuTree(String roleId)throws Exception;

	/**
	  * @Description 角色关联功能
	  * @author liuxd
	  * @date 2016-2-20 下午10:15:42
	  * @version V1.0
	 */
	public void roleSetMenu(String roleId,String menuIdS)throws Exception;
	/**
	  * @Description 根据用户id获取用户所有角色代码
	  * @author liuxd
	  * @date 2016-6-15 上午9:19:15
	  * @version V1.0
	 */
	public List<String> getRoleCodeByUser(String userId)throws Exception;
	
	/**
	  * @Description 根据角色代码获取角色对应的用户
	  * @author liuxd
	  * @date 2016-10-27 下午2:59:57
	  * @version V1.0
	 */
	public List<SystemUser> findRoleUser(String roleName)throws Exception;
	/**
	  * @Description 根据部门和角色代码获取角色对应的用户
	  * @author liuxd
	  * @date 2016-10-27 下午2:59:57
	  * @version V1.0
	 */
	public  List<SystemUser> findDepRoleUser(String recordId, String roleName) throws Exception;
	/**
	 * 查找当前用户所有部门-岗位——角色
	 * @param currentUser
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> fingAllRoleBySystemUser(SystemUser currentUser) throws Exception;
}
