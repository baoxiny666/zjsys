package com.tried.zjsys.testDataSrc.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.testDataSrc.model.ZjTanliuFly;

/**
 * @Description - 服务接口
 * @author liuxd
 * @date 2020-06-24 22:35:40
 * @version V1.0
 */
public interface ZjTanliuFlyService extends BaseService<ZjTanliuFly> {
	/**
	 * 提交数据
	 */
	void submitData(String recordIdS,String userId)throws Exception;
}
