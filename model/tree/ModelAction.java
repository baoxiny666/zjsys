package com.tried.${systemName}.${packName}.action;

import java.util.ArrayList;
import java.util.Date;
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
	private static Logger logger = Logger.getLogger(${ModelName}Action.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	${ModelName}Service ${modelName}Service;

	
	/**
	  * @Description 初始化树
	  * @author liuxd
	  * @date 2015-9-9 下午8:47:24
	  * @version V1.0
	 */
	public void initTree() {
		try {
			outSuccessJson(${modelName}Service.findAll());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	
	/**
	 * @Description 分页显示${modelTitle}
	 * @author ${author}
	 * @date ${date}
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.get${SearchName}() && !model.get${SearchName}().isEmpty()) {
				this.condition = " and ${searchName} like '%" + model.get${SearchName}() + "%'";
			}
			this.condition = this.condition + "  order by sequence asc";
			outJsonData(${modelName}Service.findPage(new Page<${ModelName}>(page, rows), "from ${ModelName} where parentId = '"+model.getParentId()+"'  " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson(e.getMessage());
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
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from ${tableName}   where id='" + ids[i]+"'");
				}
			}
			${modelName}Service.dbBeatchSql(sqls);
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


	
	/**
	  * @Description 树节点拖动
	  * @author ${author}
	  * @date ${date}
	  * @version V1.0
	 */
	public void drop(){
		try {
			List<String> sqls=new ArrayList<String>();
			String[] ids=recordIdS.split(",");
			for(int i=0;i<ids.length;i++){
				sqls.add(" update  ${tableName}  set parentId='"+model.getParentId()+"' where id='"+ids[i]+"'");
			}
			${modelName}Service.dbBeatchSql(sqls);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	/**
	  * @Description 排序
	  * @author liuxd
	  * @date 2015-9-10 下午3:41:35
	  * @version V1.0
	 */
	public void sort(){
		try {
			List<String> sqls=new ArrayList<String>();
			String[] ids=recordIdS.split(",");
			for(int i=0;i<ids.length;i++){
				sqls.add(" update  ${tableName}  set sequence="+i+" where id='"+ids[i]+"'");
			}
			${modelName}Service.dbBeatchSql(sqls);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
}
