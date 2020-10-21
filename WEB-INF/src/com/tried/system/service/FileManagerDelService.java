package com.tried.system.service;

import java.util.Date;

import com.tried.base.service.BaseService;
import com.tried.system.model.FileManagerDel;

/**
 * @Description 作废文件存储区 服务接口
 * @author liuxd
 * @date 2016-07-28 09:43:12
 * @version V1.0
 */
public interface FileManagerDelService extends BaseService<FileManagerDel> {

	 
	/**
	  * @Description 将文件存储表指定外建字段数据移植到文件存储删除备份表 FileManager——>FileManagerDel;
	  * @author liuxd
	  * @date 2016-7-28 上午10:37:42
	  * @version V1.0
	 */
	public void addDelFile(String pkId,String pkColumn,Date recordTime,String recordUser)throws Exception;
}
