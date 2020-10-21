package com.tried.zjsys.testDataSrc.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.testDataSrc.model.ZjDingliuSrc;

/**
 * @Description - 服务接口
 * @author liuxd
 * @date 2020-06-28 21:07:24
 * @version V1.0
 */
public interface ZjDingliuSrcService extends BaseService<ZjDingliuSrc> {
	/**
	 * 设备编号：YX-DLA8500 自动数据采集同步
	 * @param deviceNum
	 * @param riqi
	 * @throws Exception
	 */
	public void synCollect8500(String deviceNum)throws Exception;
	/**
	 * 设备编号：YX-DLA8500 手动数据采集同步
	 * @throws Exception
	 */
	public void synCollect8500(String deviceNum,String riqi)throws Exception;
	/**
	 * 设备编号：ZJ-ZX-042数据采集同步
	 * @throws Exception
	 */
	public void synCollect042(String deviceNum,String month)throws Exception;
	 
	/**
	 * 发送均值
	 * @param model
	 */
	public void flyJunZhi(ZjDingliuSrc model)throws Exception;
	/**
	 * 提交
	 * @param model
	 * @throws Exception
	 */
	public void fly(String recordIdS ,String userId)throws Exception;
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
