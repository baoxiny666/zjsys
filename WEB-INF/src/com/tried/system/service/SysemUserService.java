package com.tried.system.service;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemUser;

public interface SysemUserService extends BaseService<SystemUser> {
	/**
	 * 根据名字和id找人员的电子签名
	 * @Description:getUserSignById
	 * @author SUNLUNAN
	 * @date 2019-6-20 下午3:38:32
	 * @version V1.0
	 */
	public String getUserSignById(String uid) throws Exception;
}
