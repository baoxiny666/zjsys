package com.tried.${systemName}.${packName}.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tried.${systemName}.base.action.BaseAction;
import com.tried.${systemName}.common.Page;
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
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(${ModelName}Action.class);  
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
			//if (null != model.get${SearchName}() && !model.get${SearchName}().isEmpty()) {
		    //  this.condition = " and ${searchName} like '%" + model.get${SearchName}() + "%'";
			//}
			 this.condition = this.condition + "  " + this.getOrderColumn();
			String sql="${jdbcSql} "+this.condition ;
			List<Object[]> rd =${modelName}Service.dbFindList(sql, null);
	        List<${ModelName}> result=new LinkedList<${ModelName}>();
	        for(Object[] obj:rd){
	        	${ModelName} ${modelName}=new ${ModelName}();
	        	${zdySet}
	        	result.add(${modelName});
	        }
			outJsonList(result);
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}


	 
}
