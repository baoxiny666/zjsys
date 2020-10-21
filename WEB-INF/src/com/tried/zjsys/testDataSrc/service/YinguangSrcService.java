package com.tried.zjsys.testDataSrc.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.testDataSrc.model.YinguangSrc;

/**
 * @Description - 服务接口
 * @author sunlunan
 * @date 2020-06-23 09:30:34
 * @version V1.0
 */
public interface YinguangSrcService extends BaseService<YinguangSrc> {
    /**
     * 上传数据
     * @param model
     * @return
     * @throws Exception
     */
	/*String uploadData(String deviceNum) throws Exception;*/
   /**
    * 发送数据
    * @param ids
    * @throws Exception
    */
	 void flySrcData(String[] ids,YinguangSrc model)throws Exception;
	 
     void synCollect030(String deviceNum,String dtime)throws Exception;
      
     void synCollect031(String deviceNum,String dtime)throws Exception;
  	/**
  	 * 删除
  	 * @param ids
  	 * @throws Exception
  	 */
  	void del(String[] ids) throws Exception;
  	/**
  	 * 恢复
  	 * @param ids
  	 * @throws Exception
  	 */
  	void recovery(String[] ids ) throws Exception;

}
