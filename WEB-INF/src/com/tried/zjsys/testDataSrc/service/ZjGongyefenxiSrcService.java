package com.tried.zjsys.testDataSrc.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiSrc;

/**
 * @Description - 服务接口
 * @author sunlunan
 * @date 2020-06-28 10:03:57
 * @version V1.0
 */
public interface ZjGongyefenxiSrcService extends BaseService<ZjGongyefenxiSrc> {
	   /**
	    * 工业分析仪ZJ-ZX-039数据采集
	    * @param string
	    * @throws Exception
	    */
		void synCollect039(String deviceNum,String monStr) throws Exception;
	   /**
	    * 工业分析仪ZJ-ZX-040数据采集
	    * @param string
	    * @throws Exception
	    */
		void synCollect040(String deviceNum,String monStr) throws Exception;
	   /**
	    * 工业分析仪ZJ-ZX-041数据采集
	    * @param string
	    * @throws Exception
	    */
/*	void synCollect041(String string)throws Exception;*/
	
	
	/**
	 * 设备编号：ZJ-ZX-041
	 * 型号：YX-GF/V7705
	 * @param deviceNum
	 * @throws Exception
	 */
	void synCollect038(String deviceNum,String monStr)throws Exception;
	/**
	 * 设备编号：ZJ-ZX-041
	 * 型号：YX-GF/V7701
	 * @param deviceNum
	 * @throws Exception
	 */
	void synCollect041(String deviceNum,String monStr)throws Exception;
	
	/**
	 * 提交数据到待审页
	 * @param ids
	 * @param model
	 */
	void flySrcData(String[] ids, ZjGongyefenxiSrc model) throws Exception;
	/**
	 * 置位
	 * @param ids
	 */
	void del(String[] ids) throws Exception;
	/**
	 * 恢复数据
	 * @param ids
	 * @throws Exception
	 */
	void recovery(String[] ids)throws Exception;

}
