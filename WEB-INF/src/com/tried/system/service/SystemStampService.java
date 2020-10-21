package com.tried.system.service;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemStamp;

/**
 * @Description - 服务接口
 * @author liuxd
 * @date 2019-07-19 09:20:17
 * @version V1.0
 */
public interface SystemStampService extends BaseService<SystemStamp> {

	void addStampObj(SystemStamp model, String pkId) throws Exception;
	void updateStampObj(SystemStamp model, String pkId) throws Exception;
	SystemStamp getByName(String string,String type)throws Exception;
}
