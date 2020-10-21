package com.tried.system.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemRole;
import com.tried.system.service.SystemRoleService;

/**
 * @Description 角色信息 管理
 * @author liuxd
 * @date 2015-09-07 10:09:02
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemRoleAction extends BaseAction<SystemRole> {
	private static Logger logger = Logger.getLogger(SystemRoleAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemRoleService systemRoleService;

	/**
	 * @Description 分页显示角色信息
	 * @author liuxd
	 * @date 2015-09-07 10:09:02
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getRoleName() && !model.getRoleName().isEmpty()) {
				this.condition = " and roleName like '%" + model.getRoleName() + "%'";
			}
			this.condition = this.condition + "  " + this.getOrderColumn();
			outJsonData(systemRoleService.findPage(new Page<SystemRole>(page, rows), "from SystemRole where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加角色信息
	 * @author liuxd
	 * @date 2015-09-07 10:09:02
	 * @version V1.0
	 */
	public void add() {
		try {
			systemRoleService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除角色信息
	 * @author liuxd
	 * @date 2015-09-07 10:09:02
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_role   where id='" + ids[i]+"'");
				}
			}
			systemRoleService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改角色信息
	 * @author liuxd
	 * @date 2015-09-07 10:09:02
	 * @version V1.0
	 */
	public void edit() {
		try {
			systemRoleService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
 
	public void roleMenuTree(){
		try {
			outSuccessJson(systemRoleService.roleMenuTree(recordId));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
		
	}
	
	public void roleSetMenu(){
		
		try {
			systemRoleService.roleSetMenu(recordId,recordIdS);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	
	/**
	  * @Description 根据角色获取角色用户
	  * @author liuxd
	  * @date 2016-10-27 下午2:58:07
	  * @version V1.0
	 */
	public void findRoleUser(){
		try{
			outJsonList(systemRoleService.findRoleUser(recordId));
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	/**
	  * @Description 根据部门角色获取角色用户
	  * @author liuyw
	  * @date 2016-12-23 下午2:58:07
	  * @version V1.0
	 */
	public void findDepRoleUser(){
		try{
			outJsonList(systemRoleService.findDepRoleUser(recordId,model.getRoleName()));
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
}
