package com.tried.system.service;

import java.util.List;
import java.util.Map;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemDepartment;
import com.tried.system.model.SystemUser;

/**
 * @Description 部门信息 服务接口
 * @author liuxd
 * @date 2015-09-07 10:27:53
 * @version V1.0
 */
public interface SystemDepartmentService extends BaseService<SystemDepartment> {

	
	/**
	  * @Description 获取组织机构数对象
	  * @author liuxd
	  * @date 2016-7-4 下午4:28:12
	  * @version V1.0
	 */
	public  List<Map<String, Object>> depTree()throws Exception;
	
	/**
	  * @Description 兼职职务树除职务Id之外的数
	  * @author sunlunan
	  * @date 2016-7-4 下午4:28:12
	  * @version V1.0
	 */	
	public List<Map<String, Object>> depTree(String id) throws Exception;
	
	/**
	 * 
		 * @Description 获取子部门树
		 * @author liuxd
		 * @date  2018-10-11上午10:21:39
		 * @version V1.0
	 */
	public List<Map<String, Object>> depChliTree(String id) throws Exception;
	
	/**
	  * @Description 个人信息，个人所有岗位显示
	  * @author sunlunan
	  * @date 2016-7-4 下午4:28:12
	  * @version V1.0
	 */	
	public List<Map<String, Object>>personalWorks(String userId) throws Exception;
	
	/**
	  * @Description 根据部门id获取部门所有职务
	  * @author liuxd
	  * @date 2016-7-5 下午4:14:35
	  * @version V1.0
	 */
	public  List<SystemDepartment> findJob(String depId)throws Exception;
	
	/**
	  * @Description 根据部门id获取部门人员信息
	  * @author liuxd
	  * @date 2016-7-7 上午11:43:46
	  * @version V1.0
	 */
	public   	List<SystemUser> findDepPeople(String depId)throws Exception;
	
	/**
	  * @Description 根据部门id获取部门的管理者
	  * @author liuxd
	  * @date 2016-10-27 下午2:21:08
	  * @version V1.0
	 */
	public List<SystemUser> findDepManager(String depId)throws Exception;

	/**
	 * 查找用户的兼职的部门
	 * @title: getPartDepIds
	 * @author: lyw
	 * @date : 2019-7-8 下午1:06:15
	 * @version: v1.0
	 */
	public List<SystemDepartment> getPartDepIds(String userId)throws Exception;
}
