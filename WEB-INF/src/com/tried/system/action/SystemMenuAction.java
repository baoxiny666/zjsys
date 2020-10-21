package com.tried.system.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemMenu;
import com.tried.system.service.SystemMenuService;

/**
 * @Description 功能管理 管理
 * @author liuxd
 * @date 2015-12-25 16:08:34
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemMenuAction extends BaseAction<SystemMenu> {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SystemMenuAction.class);  
	@Resource
	SystemMenuService systemMenuService;

	
	/**
	  * @Description 初始化树
	  * @author liuxd
	  * @date 2015-9-9 下午8:47:24
	  * @version V1.0
	 */
	public void initTree() {
		try {
			outSuccessJson(getSession().get("menu"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	/**
	  * @Description 获取功能树
	  * @author liuxd
	  * @date 2016-3-31 下午3:02:15
	  * @version V1.0
	 */
	public void initMenu() {
		try {
			outSuccessJson(systemMenuService.findAll("from SystemMenu where flag='是' order by sequence asc "));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	
	/**
	 * @Description 分页显示功能管理
	 * @author liuxd
	 * @date 2015-12-25 16:08:34
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getParentId() && !model.getParentId().isEmpty()) {
				this.condition = " and parentId = '" + model.getParentId() + "'";
			}
			this.condition = this.condition + "  order by sequence asc";
			outJsonData(systemMenuService.findPage(new Page<SystemMenu>(page, rows), "from SystemMenu where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加功能管理
	 * @author liuxd
	 * @date 2015-12-25 16:08:34
	 * @version V1.0
	 */
	public void add() {
		try {
			systemMenuService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除功能管理
	 * @author liuxd
	 * @date 2015-12-25 16:08:34
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_menu   where id='" + ids[i]+"'");
				}
			}
			systemMenuService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改功能管理
	 * @author liuxd
	 * @date 2015-12-25 16:08:34
	 * @version V1.0
	 */
	public void edit() {
		try {
			systemMenuService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}


	
	/**
	  * @Description 树节点拖动
	  * @author liuxd
	  * @date 2015-12-25 16:08:34
	  * @version V1.0
	 */
	public void drop(){
		try {
			List<String> sqls=new ArrayList<String>();
			String[] ids=recordIdS.split(",");
			for(int i=0;i<ids.length;i++){
				sqls.add(" update  tried_system_menu  set parentId='"+model.getParentId()+"' where id='"+ids[i]+"'");
			}
			systemMenuService.dbBeatchSql(sqls);
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
				sqls.add(" update  tried_system_menu  set sequence="+i+" where id='"+ids[i]+"'");
			}
			systemMenuService.dbBeatchSql(sqls);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
}
