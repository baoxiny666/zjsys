package com.tried.zjsys.basics.service;

import com.tried.base.service.BaseService;
import com.tried.zjsys.basics.model.DataWlInfo;

/**
 * @Description 物料名称 服务接口
 * @author liuxd
 * @date 2020-07-06 16:20:50
 * @version V1.0
 */
public interface DataWlInfoService extends BaseService<DataWlInfo> {
/**
 * 复制
 * @param id
 * @throws Exception
 */
	public void copy(String id,String userId)throws Exception;
}
