package com.tried.${systemName}.${packName}.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.${systemName}.${packName}.model.${ModelName};
import com.tried.${systemName}.${packName}.service.${ModelName}Service;

/**
 * @Description ${modelTitle} 管理
 * @author ${author}
 * @date ${date}
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ${ModelName}Action extends BaseAction<${ModelName}> {
	private static Logger logger = Logger.getLogger(${ModelName}Action.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	${ModelName}Service ${modelName}Service;

	/**
	 * @Description 分页显示${modelTitle}
	 * @author ${author}
	 * @date ${date}
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.get${SearchName}())) {
				this.condition = " and ${searchName} like '%" + model.get${SearchName}() + "%'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(${modelName}Service.findPage(new Page<${ModelName}>(page, rows), "from ${ModelName} where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加${modelTitle}
	 * @author ${author}
	 * @date ${date}
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			${modelName}Service.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除${modelTitle}
	 * @author ${author}
	 * @date ${date}
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			${modelName}Service.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改${modelTitle}
	 * @author ${author}
	 * @date ${date}
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			${modelName}Service.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
