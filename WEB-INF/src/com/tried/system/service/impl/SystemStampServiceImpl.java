package com.tried.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemStamp;
import com.tried.system.service.FileManagerService;
import com.tried.system.service.SystemStampService;
/**
 * @Description - 服务接口实现
 * @author liuxd
 * @date 2019-07-19 09:20:17
 * @version V1.0
 */
@Service
public class SystemStampServiceImpl extends BaseServiceImpl<SystemStamp> implements SystemStampService {
	@Resource
	FileManagerService fileManagerService;
	@Override
	public void addStampObj(SystemStamp model, String pkId) throws Exception {
    this.add(model);	
	fileManagerService.add("system_stamp_file", model.getSystem_stamp_file(), pkId, model.getId());			
	}
	@Override
	public void updateStampObj(SystemStamp model, String pkId) throws Exception {
	 this.update(model);	
	 fileManagerService.add("system_stamp_file", model.getSystem_stamp_file(), pkId, model.getId());		
	
	}
	@Override
	public SystemStamp getByName(String name,String type) throws Exception {
		List<SystemStamp> stampList = this.findAll("from SystemStamp where name = '"+name+"' and type ='"+type+"'");
		if(stampList.size()>0){
			return stampList.get(0);
		}
		return null;
	}

}
