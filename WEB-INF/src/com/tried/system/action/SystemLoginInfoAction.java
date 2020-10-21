package com.tried.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemLoginInfo;
import com.tried.system.service.SystemLoginInfoService;

/**
 * @Description 用户登录记录表 管理
 * @author liuyw
 * @date 2016-11-18 10:44:55
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemLoginInfoAction extends BaseAction<SystemLoginInfo> {
	private static Logger logger = Logger.getLogger(SystemLoginInfoAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemLoginInfoService systemLoginInfoService;

	/**
	 * @Description 分页显示用户登录记录表
	 * @author liuyw
	 * @date 2016-11-18 10:44:55
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getLoginName() && !model.getLoginName().isEmpty()) {
				this.condition = " and loginName like '%" + model.getLoginName() + "%'";
			}
			if(this.getOrderColumn()!=null&&!this.getOrderColumn().isEmpty()){
				this.condition = this.condition + "  " + this.getOrderColumn();
			}else{
				this.condition = this.condition + " order by recordTime desc ";
			}
			outJsonData(systemLoginInfoService.findPage(new Page<SystemLoginInfo>(page, rows), "from SystemLoginInfo where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	/**
	 * @Description 当前用户用户登录记录表
	 * @author wangxi
	 * @date 2016-12-113 10:44:55
	 * @version V1.0
	 */
	public void currentUserList() {
		try {
			String id = getCurrentUser().getId();
			if(page==null){
				page=1;
			}
			if(rows==null){
				rows=10;
			}
			outJsonData(systemLoginInfoService.findPage(new Page<SystemLoginInfo>(page, rows), "from SystemLoginInfo where 1=1 and recordUser='"+id+"' order by recordTime desc " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加用户登录记录表
	 * @author liuyw
	 * @date 2016-11-18 10:44:55
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemLoginInfoService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除用户登录记录表
	 * @author liuyw
	 * @date 2016-11-18 10:44:55
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_login_info   where id='" + ids[i]+"'");
				}
			}
			systemLoginInfoService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改用户登录记录表
	 * @author liuyw
	 * @date 2016-11-18 10:44:55
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemLoginInfoService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	 
}
