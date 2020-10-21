package com.tried.system.service;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemSerial;

/**
 * @Description - 服务接口
 * @author liuxd
 * @date 2018-02-05 22:35:27
 * @version V1.0
 */
public interface SystemSerialService extends BaseService<SystemSerial> {

	 
	/**
	 * 根据类型和规格设置编号
	 * @param sysType
	 * @param sysStandard
	 * @return
	 * @throws Exception
	 */
	public void setSerialInfo(String sysType,String sysStandard) throws Exception;
	
	/**
	 * 根据类型搜索对于的序列编码；
	 * @desciption 
	 * @author lxd
	 * 2018-2-5 下午10:52:50
	 */
	public String getSerialInfo(String sysType) throws Exception;
	
	/**
	 * 根据年度，类型搜索对于的序列编码；
	 * @desciption 
	 * @author lxd
	 * 2018-2-5 下午10:52:50
	 */
	public String getSerialInfo(String year,String sysType) throws Exception;
	/**
	 * 根据类型和规格设置编号
	 * @param sysType
	 * @param sysStandard
	 * @return
	 * @throws Exception
	 */
	public void setSerialInfo(String sysType) throws Exception;
	/**
	 * 根据类型获取历史编号
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	public String getHisSerialInfo(String sysType) throws Exception;
}
