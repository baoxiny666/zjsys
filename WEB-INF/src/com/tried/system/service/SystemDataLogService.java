package com.tried.system.service;

import com.tried.base.service.BaseService;
import com.tried.system.model.SystemDataLog;

/**
 * @Description 内容修改日志表 服务接口
 * @author liuxd
 * @date 2018-07-20 15:11:32
 * @version V1.0
 */
public interface SystemDataLogService extends BaseService<SystemDataLog> {

	public void logInfo(String modelType,String modelId,String modelName,String oldContent,String newContent,String userId )throws Exception;
}
