package com.tried.system.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemDataLog;
import com.tried.system.service.SystemDataLogService;
/**
 * @Description 内容修改日志表 服务接口实现
 * @author liuxd
 * @date 2018-07-20 15:11:32
 * @version V1.0
 */
@Service
public class SystemDataLogServiceImpl extends BaseServiceImpl<SystemDataLog> implements SystemDataLogService {

	@Override
	public void logInfo(String modelType,String modelId, String modelName, String oldContent, String newContent,String userId) throws Exception {
		// TODO Auto-generated method stub
		SystemDataLog systemDataLog=new SystemDataLog();
		systemDataLog.setContent_new(newContent);
		systemDataLog.setContent_old(oldContent);
		systemDataLog.setModel_id(modelId);
		systemDataLog.setModel_name(modelName);
		systemDataLog.setContent_title(modelType);
		systemDataLog.setRecordUser(userId);
		systemDataLog.setRecordTime(new Date());
		this.add(systemDataLog);
	}

}
