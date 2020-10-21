package com.tried.zjsys.testDataSrc.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.testDataSrc.model.ZjLiangreSrc;

/**
 * @Description - 服务接口
 * @author lyw
 * @date 2020-06-28 21:31:05
 * @version V1.0
 */
public interface ZjLiangreSrcService extends BaseService<ZjLiangreSrc> {

	/**
	 * 发送数据
	 * @param ids
	 * @param model
	 * @throws Exception
	 */
	void flySrcData(String[] ids, ZjLiangreSrc model) throws Exception;

	/**
	 * 定时采集
	 * @throws Exception
	 */
	void synCollect(String deviceNum) throws Exception;
	/**
	 * //手动采集
	 * @param deviceNum
	 * @throws Exception
	 */
	void synCollectHand(String deviceNum,String riqi) throws Exception;
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
	
	/**
	 * 检查样品
	 * @param ids
	 * @throws Exception
	 */
	void checkAddSampleNum(String sampleNum ) throws Exception;
	
}
