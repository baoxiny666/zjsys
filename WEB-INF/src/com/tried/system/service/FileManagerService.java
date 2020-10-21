package com.tried.system.service;

import java.util.List;

import com.tried.base.service.BaseService;
import com.tried.system.model.FileManager;

/**
 * @Description 文件存储表 服务接口
 * @author liuxd
 * @date 2016-05-30 09:36:51
 * @version V1.0
 */
public interface FileManagerService extends BaseService<FileManager> {
	
	
	/**
	  * @Description 业务实体表和文件存储管理表建立关系
	  * @author liuxd
	  * @date 2016-7-28 上午10:35:44
	  * @version V1.0
	 */
	public  boolean add(String columnName,String fileId, String pkVal,String modelID)throws Exception;
	/**
	  * @Description 根据pkid,字段名删除指定文件
	  * @author liuxd
	  * @date 2016-5-30 上午10:20:38
	  * @version V1.0
	 */
	public  void delFile(String pkId,String fileColumn,String fileName)throws Exception;
	/**
	 * 
	  * @Description 删除临时文件
	  * @author liuxd
	  * @date 2016-7-12 下午3:21:01
	  * @version V1.0
	 */
	public void delColumnFile(String pkId, String fileColumn) throws Exception;
	/**
	  * @Description 删除pkID关联的所有文件
	  * @author liuxd
	  * @date 2016-5-30 上午10:43:48
	  * @version V1.0
	 */
	public  void delAllFile(String pkId)throws Exception;
	 
	/**
	 * 
	  * @Description 获取指定对象的文件列表
	  * @author liuxd
	  * @date 2016-5-30 下午12:21:44
	  * @version V1.0
	 */
	public List<FileManager> fileInfoList(String pkId)throws Exception;
	/**
	 * 
	  * @Description  获取指定列对应的的文件列表
	  * @author liuxd
	  * @date 2016-7-28 上午11:21:26
	  * @version V1.0
	 */
	public List<FileManager> fileInfoList(String pkId,String pkColumn)throws Exception;
	
	/**
	  * @Description 根据关联id、字段名、文件名检索文件
	  * @author liuxd
	  * @date 2016-6-1 上午11:20:42
	  * @version V1.0
	 */
	public String findFilePath(String pkId, String fileColumn, String fileName)throws Exception;
	/**
	  * @Description 根据关联id、字段名检索文件
	  * @author liuxd
	  * @date 2016-6-1 上午11:20:42
	  * @version V1.0
	 */
	public String findFilePath(String pkId, String pkColume) throws Exception;
	/**
	  * @Description 根据流程Id、字段名检索文件
	  * @author liuyw
	  * @date 2016-12-28 上午11:20:42
	  * @version V1.0
	 */
	public List<FileManager> findLogFilePath(String process_id, String pkColumn) throws Exception;
	/**
	  * @Description 根据类型查找模板
	  * @author liuyw
	  * @date 2016-12-28 上午11:20:42
	  * @version V1.0
	 */
	public FileManager findFileByType(String contractType) throws Exception;
	/**
	 * 查找最新的电子签名
	 * @param recordUser
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public FileManager fileLastSignName(String recordUser, String string) throws Exception;
	/**
	 * 根据pkId获取最新文件
	 * @title: getBypkId
	 * @author: lyw
	 * @date : 2018-12-24 下午2:22:15
	 * @version: v1.0
	 */
	public FileManager getBypkId(String pkId) throws Exception;
	/**
	 * 
	 * @title: findByPkIds
	 * @author: lyw
	 * @date : 2019-1-30 下午2:12:47
	 * @version: v1.0
	 */
	public List<FileManager> findByPkIds(String pkIds, String pkColumn) throws Exception;
	/**
	 * 复制实体连同copy文件
	 * @title: copyModelAndFile
	 * @author: lyw
	 * @date : 2019-1-31 上午11:10:47
	 * @version: v1.0
	 */
	public void copyModelAndFile(FileManager cardWord) throws Exception;
	/**
	 * 查找通用任务模板卡
	 * @title: findCommonCard
	 * @author: lyw
	 * @date : 2019-1-31 下午2:40:00
	 * @version: v1.0
	 */
	public FileManager findCommonCard() throws Exception;
	 
	/**
	 * 查找领取签名
	 * @title: findTakeSignManager
	 * @author: lyw
	 * @date : 2019-4-15 下午3:09:31
	 * @version: v1.0
	 */
	public FileManager findTakeSignManager(String pkId, String fileName,
			String pkColumn)throws Exception;
	 
}
