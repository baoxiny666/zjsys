package com.tried.system.service;

import java.util.List;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemMemory;

public interface SystemMemoryService extends BaseService<SystemMemory> {
	/**
	 * @Description 添加相同relationId的数据到大文字区内容 
	 * @author liuxd
	 * @date 2018-7-13 上午11:21:32
	 * @version V1.0
	 */
	public void manyAddMemory(String relationId, String relationColumn, String relationContext) throws Exception;

	/**
	 * @Description 添加d数据库大文字区内容 
	 * @author liuxd
	 * @date 2018-7-13 上午11:21:32
	 * @version V1.0
	 */
	public void singleAddMemory(String relationId, String relationColumn, String relationContext) throws Exception;

	/**
	 * @Description 编辑数据库大文字区内容
	 * @author liuxd
	 * @date 2018-7-13 上午11:21:32
	 * @version V1.0
	 */
	public void editMemory(String relationId, String relationColumn, String relationContext) throws Exception;

	/**
	 * @Description 删除数据库大文字区内容
	 * @author liuxd
	 * @date 2018-7-13 上午11:11:11
	 * @version V1.0
	 */
	public void delMemory(String relationId, String relationColumn) throws Exception;

	/**
	 * 
		 * @Description 删除历史脏数据
		 * @author liuxd
		 * @date 2019-2-19 下午1:13:20
		 * @version V1.0
	 */
	public void delTempMemory()  throws Exception;
	/**
	 * @Description 获取对应的数据库大文字区内容列表  
	 * @author liuxd
	 * @date 2018-10-15下午2:34:58
	 * @version V1.0
	 */
	public List<SystemMemory> getManyMemory(String relationId, String relationColumn) throws Exception;

	/**
	 * @Description 获取对应的数据库大文字区内容
	 * @author liuxd
	 * @date 2018-7-13 上午11:11:11
	 * @version V1.0
	 */
	public SystemMemory getSingleMemory(String pkId, String relationColumn) throws Exception;

	/**
	 * @Description 核对数据库大文字区内容是否存在
	 * @author liuxd
	 * @date 2018-7-13 上午10:57:05
	 * @version V1.0
	 */
	public boolean checkExitMemory(String relationId, String relationColumn) throws Exception;
	
	
	/**
	 * @Description 添加临时数据到大文字区内容 
	 * @author liuxd
	 * @date 2018-7-13 上午11:21:32
	 * @version V1.0
	 */
	public void tempAddMemory(String relationId, String relationContext) throws Exception;
	 
	 /**
	 	 * @Description 编辑临时数据
	 	 * @author liuxd
	 	 * @date  2018-10-16上午10:21:16
	 	 * @version V1.0
	  */
	public void tempEditMemory(String relationId, String relationContext) throws Exception;
	/**
		 * @Description 删除临时数据
		 * @author liuxd
		 * @date  2018-10-16上午10:22:10
		 * @version V1.0
	 */
	public void delTempMemory(String id) throws Exception;
	/**
	 * 
		 * @Description 批量删除临时数据
		 * @author liuxd
		 * @date 2018-12-18 上午11:42:50
		 * @version V1.0
	 */
	public void delBeachTempMemory(String[] idArray) throws Exception;
	/**
	 * 复制
		 * @Description 
		 * @author liuxd
		 * @date 2019-7-21 下午5:07:45
		 * @version V1.0
	 */
	public void copyTempMemory(String id)throws Exception;
	/**
	 * @Description 获取临时数据 内容列表  
	 * @author liuxd
	 * @date 2018-10-15下午2:34:58
	 * @version V1.0
	 */
	public List<SystemMemory> getTempMemory(String relationId) throws Exception;

	
}
