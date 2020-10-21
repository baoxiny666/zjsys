package com.tried.zjsys.testDataSrc.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.testDataSrc.model.ZjTanliuSrc;

/**
 * @Description - 服务接口
 * @author liuxd
 * @date 2020-06-24 22:35:41
 * @version V1.0
 */
public interface ZjTanliuSrcService extends BaseService<ZjTanliuSrc> {

	/**
	 * 数据采集同步
	 * @throws Exception
	 */
	public void synCollect(String deviceNum,String riqi)throws Exception;
	/**
	 * 删除
	 * @param ids
	 * @throws Exception
	 */
	void del(String[] ids ) throws Exception;
	/**
	 * 恢复
	 * @param ids
	 * @throws Exception
	 */
	void recovery(String[] ids ) throws Exception;
}
