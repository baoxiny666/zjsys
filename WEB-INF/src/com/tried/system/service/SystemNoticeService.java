package com.tried.system.service;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemNotice;

/**
 * @Description 公告管理 服务接口
 * @author liuxd
 * @date 2016-09-06 16:30:37
 * @version V1.0
 */
public interface SystemNoticeService extends BaseService<SystemNotice> {

	/**
	  * @Description 添加公告信息
	  * @author liuxd
	  * @date 2016-9-7 上午8:46:37
	  * @version V1.0
	 */
	public void add(SystemNotice systemNotice,String pkId) throws Exception;
	/**
	  * @Description 修改公告信息
	  * @author liuxd
	  * @date 2016-9-7 上午8:46:49
	  * @version V1.0
	 */
	public void edit(SystemNotice systemNotice,String pkId)throws Exception;
	/**
	  * @Description 删除公告信息
	  * @author liuxd
	  * @date 2016-9-7 上午8:47:22
	  * @version V1.0
	 */
	public void del(SystemNotice systemNotice)throws Exception;
}
