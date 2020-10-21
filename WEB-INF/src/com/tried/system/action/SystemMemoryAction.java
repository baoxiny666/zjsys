package com.tried.system.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.system.model.SystemMemory;
import com.tried.system.service.SystemMemoryService;
@Controller
@Scope("prototype")
public class SystemMemoryAction  extends BaseAction<SystemMemory>{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SystemMenuAction.class);  
	@Resource
	SystemMemoryService systemMemoryService;
	/**
	* @Description 获取大数据对象
	* @author liuxd
	* @date 2018-7-20 上午11:39:17
	* @version V1.0
	 */
	public void findMemory(){
		try {
			 
			SystemMemory systemMemory=systemMemoryService.getSingleMemory(model.getRelationId(),model.getRelationColumn());
			if(systemMemory!=null){
				content=systemMemory.getRelationContext();
			}
			outSuccessJson(content);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	
	 
}
