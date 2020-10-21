package com.tried.zjsys.testDataSrc.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.testDataSrc.model.ZjHandInputFly;

/**
 * @Description - 服务接口
 * @author sunlunan
 * @date 2020-07-08 14:56:17
 * @version V1.0
 */
public interface ZjHandInputFlyService extends BaseService<ZjHandInputFly> {
   
	void del(String[] ids) throws Exception;

	void recovery(String[] ids)throws Exception;

	void flySrcData(String[] ids, ZjHandInputFly model) throws Exception;

}
