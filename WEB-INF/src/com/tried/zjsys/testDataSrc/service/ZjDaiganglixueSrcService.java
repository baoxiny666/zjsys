package com.tried.zjsys.testDataSrc.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.testDataSrc.model.ZjDaiganglixueSrc;

/**
 * @Description - 服务接口
 * @author sunlunan
 * @date 2020-07-01 10:39:20
 * @version V1.0
 */
public interface ZjDaiganglixueSrcService extends BaseService<ZjDaiganglixueSrc> {

	/**
	 * 采集带钢力学仪ZJ-LX-031 数据
	 * @param string
	 * @throws Exception
	 */
	void synCollect031(String deviceNum,String dtime) throws Exception;
	/**
	 * 采集带钢力学仪ZJ-LX-032 数据
	 * @param string
	 * @throws Exception
	 */
	void synCollect032(String deviceNum, String dtime)throws Exception;

	/**
	 * 采集带钢力学仪ZJ-LX-033 数据
	 * @param string
	 * @throws Exception
	 */
	void synCollect033(String deviceNum, String dtime)throws Exception;	
	/**
	 * 采集带钢力学仪ZJ-LX-034 数据
	 * @param string
	 * @throws Exception
	 */
	void synCollect034(String deviceNum, String dtime)throws Exception;
	
	/**
	 * 发送数据等待审核
	 * @param ids
	 * @param model
	 * @throws Exception
	 */
	void flySrcData(String[] ids, ZjDaiganglixueSrc model)throws Exception;
	
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
