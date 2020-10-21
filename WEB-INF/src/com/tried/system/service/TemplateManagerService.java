package com.tried.system.service;

import com.tried.base.service.BaseService;
import com.tried.system.model.TemplateManager;
/**
 * @Description - 服务接口实现
 * @author lyw
 * @date 2018-12-24 11:30:17
 * @version V1.0
 */
public interface TemplateManagerService extends BaseService<TemplateManager>{

	/**
	 * 添加模板
	 * @title: addModel
	 * @author: lyw
	 * @date : 2018-12-24 下午1:35:19
	 * @version: v1.0
	 */
	void addModel(TemplateManager model, String pkId) throws Exception;

	/**
	 * 修改模板
	 * @title: updateModel
	 * @author: lyw
	 * @date : 2018-12-24 下午1:37:32
	 * @version: v1.0
	 */
	void updateModel(TemplateManager model, String pkId) throws Exception;

	/**
	 * 根据类型查找
	 * @title: getByType
	 * @author: lyw
	 * @date : 2018-12-24 下午2:16:13
	 * @version: v1.0
	 */
	TemplateManager getByType(String string) throws Exception;
	
	/**
	 * 根据类型查找
	 * @title: getByType
	 * @author: lyw
	 * @date : 2018-12-24 下午2:16:13
	 * @version: v1.0
	 */
	TemplateManager getByName(String name) throws Exception;
/**
 * 替换上传模板的关键字
 * @param model
 * @param pkId
 * @return
 * @throws Exception
 */
	String changeKeyWord(TemplateManager model, String pkId)throws Exception;

}
