package com.tried.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.ConfigUtils;
import com.tried.common.EncryptUtil;
import com.tried.common.Page;
import com.tried.system.model.SystemUser;
import com.tried.system.service.FileManagerService;
import com.tried.system.service.SystemUserService;

/**
 * @Description 用户信息 管理
 * @author liuxd
 * @date 2015-09-09 22:43:36
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemUserAction extends BaseAction<SystemUser> {
	private static Logger logger = Logger.getLogger(SystemUserAction.class);
	private static final long serialVersionUID = 1L;
	@Resource
	SystemUserService systemUserService;
	@Resource
	FileManagerService fileManagerService;

	/**
	 * @Description 分页显示用户信息
	 * @author liuxd
	 * @date 2015-09-09 22:43:36
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getLoginName() && !model.getLoginName().isEmpty()) {
				this.condition = " and loginName like '%" + model.getLoginName() + "%'";
			}
			if (null != model.getDepName() && !model.getDepName().isEmpty()) {
				this.condition = " and (depName like '%" + model.getDepName() + "%' or parentDepName like '%" + model.getDepName() + "%')";

			}
			this.condition = this.condition + "  " + this.getOrderColumn();
			outJsonData(systemUserService.findPage(new Page<SystemUser>(page, rows), "from SystemUser where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 获取所有用户
	 * @author wangxi
	 * @date 2016-12-13
	 * @version V1.0
	 */
	public void allUserlist() {
		try {
			if (null != model.getUserName() && !model.getUserName().isEmpty()) {
				this.condition = " and userName like '%" + model.getUserName() + "%'";
			}
			if (null != model.getDepName() && !model.getDepName().isEmpty()) {
				this.condition = " and (depName like '%" + model.getDepName() + "%' or parentDepName like '%" + model.getDepName() + "%')";
			}
			if (null != model.getWorkName() && !model.getWorkName().isEmpty()) {
				this.condition = " and workName like '%" + model.getWorkName() + "%'";
			}
			outJsonList(systemUserService.findAll("from SystemUser where 1=1  " + this.condition+" order by userName "));
			//outJsonList(systemUserService.findAll("from SystemUser where 1=1 and loginName!='admin' " + this.condition));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	/**
	 * 获取用户姓名、部门、职务
	 */
	public void userCombolist(){
		try {
			if (null != model.getUserName() && !model.getUserName().isEmpty()) {
				this.condition = " and userName like '%" + model.getUserName() + "%'";
			}
			if (null != model.getDepName() && !model.getDepName().isEmpty()) {
				this.condition = " and (depName like '%" + model.getDepName() + "%' or parentDepName like '%" + model.getDepName() + "%')";
			}
			if (null != model.getWorkName() && !model.getWorkName().isEmpty()) {
				this.condition = " and workName like '%" + model.getWorkName() + "%'";
			}
			outJsonList(systemUserService.findAll("from SystemUser where 1=1 " + this.condition));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	

	/**
	 * @Description 获取当前用户
	 * @author wangxi
	 * @date 2016-12-13
	 * @version V1.0
	 */
	public void getLoginedUser() {
		try {
			SystemUser user = systemUserService.getById(getCurrentUser().getId());
			getSession().put("user", user);
			outJsonData(getCurrentUser());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 用户修改信息
	 * @author wangxi
	 * @date 2016-12-13
	 * @version V1.0
	 */
	public void userUpdate() {
		try {
			List<SystemUser> userList = systemUserService.findAll("from SystemUser where loginName='" + model.getLoginName() + "'");
			if (userList.size() > 0) {
				for (SystemUser user : userList) {
					if (!user.getId().equals(model.getId())) {
						outAlertJson("账户已存在");
						return;
					}
				}
			}
			SystemUser user = systemUserService.getById(model.getId());
			user.setUserBrithday(model.getUserBrithday());
			user.setUserName(model.getUserName());
			user.setUserSex(model.getUserSex());
			user.setSystem_user_sign_name(model.getSystem_user_sign_name());
			user.setRecordDate(new Date());
			user.setRecordUser(getCurrentUser().getId());
			systemUserService.userUpdate(user, pkId, "");
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	 * @Description 添加用户信息
	 * @author liuxd
	 * @date 2015-09-09 22:43:36
	 * @version V1.0
	 */
	public void add() {
		try {
			List<SystemUser> userList = systemUserService.findAll("from SystemUser where loginName='" + model.getLoginName() + "'");
			if (userList.size() > 0) {
				outAlertJson("账户已存在");
			} else {
				String passKey = ConfigUtils.random32();
				model.setPassKey(passKey);
				model.setRecordDate(new Date());
				model.setRecordUser(getCurrentUser().getId());
				model.setLoginPass(EncryptUtil.encrypt("1111", passKey));
				systemUserService.add(model, pkId, recordIdS);
				outSuccessJson("添加成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	public void partWork() {

		try {
			List<SystemUser> ulist = systemUserService.getWorkTextById(model.getId());
			outJsonList(ulist);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * 密码重置
	 * 
	 * @Description
	 * @author liuxd
	 * @date 2016-7-5 下午1:51:41
	 * @version V1.0
	 */
	public void resetPass() {
		try {
			SystemUser user = systemUserService.getById(recordId);
			String passKey = ConfigUtils.random32();
			user.setPassKey(passKey);
			user.setRecordDate(new Date());
			user.setRecordUser(getCurrentUser().getId());
			user.setLoginPass(EncryptUtil.encrypt("1111", passKey));
			systemUserService.update(user);
			outSuccessJson("处理成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("处理失败");
		}
	}

	/**
	 * @Description 删除用户信息
	 * @author liuxd
	 * @date 2015-09-09 22:43:36
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_user   where id='" + ids[i] + "'");
					sqls.add("delete from tried_system_user_partTimeWork where userId='" + ids[i] + "'");
				}
			}
			systemUserService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改用户信息
	 * @author liuxd
	 * @date 2015-09-09 22:43:36
	 * @version V1.0
	 */
	public void edit() {
		try {
			List<SystemUser> userList = systemUserService.findAll("from SystemUser where loginName='" + model.getLoginName() + "'");
			if (userList.size() > 0) {
				for (SystemUser user : userList) {
					if (!user.getId().equals(model.getId())) {
						outAlertJson("账户已存在");
						return;
					}
				}
			}
			SystemUser user = systemUserService.getById(model.getId());
			user.setLoginName(model.getLoginName());
			user.setDepId(model.getDepId());
			user.setWorkId(model.getWorkId());
			user.setUserBrithday(model.getUserBrithday());
			user.setUserName(model.getUserName());
			user.setUserSex(model.getUserSex());
			user.setFlag(model.getFlag());
			user.setSystem_user_sign_name(model.getSystem_user_sign_name());
			user.setRecordDate(new Date());
			user.setRecordUser(getCurrentUser().getId());

			systemUserService.edit(user, pkId, recordIdS);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	 * @Description 获取用户未赋予的角色和已赋予角色
	 * @author liuxd
	 * @date 2015-9-14 下午8:38:05
	 * @version V1.0
	 */
	public void userGetRole() {
		Map<String, Map<String, String>> allMultiple = new HashMap<String, Map<String, String>>();
		Map<String, String> leftMultiple = new HashMap<String, String>();
		Map<String, String> rightMultiple = new HashMap<String, String>();
		try {
			// 未赋予的角色
			String sql = "select r.id, r.roleName from tried_system_role r where r.id not in(select ur.roleId from tried_system_user_role ur where ur.userId='" + model.getId()
					+ "')";
			List<Object[]> teacherNotRoles = systemUserService.dbFindList(sql, null);
			for (Object[] objs : teacherNotRoles) {
				leftMultiple.put(objs[0].toString(), objs[1].toString());
			}
			// 已赋予角色
			sql = "select r.id, r.roleName from tried_system_user_role ur ,tried_system_role r where ur.roleId=r.id and ur.userId= '" + model.getId() + "' and r.flag='是' ";
			List<Object[]> teacherRoles = systemUserService.dbFindList(sql, null);
			for (Object[] objs : teacherRoles) {
				rightMultiple.put(objs[0].toString(), objs[1].toString());
			}
			allMultiple.put("leftMultiple", leftMultiple);
			allMultiple.put("rightMultiple", rightMultiple);
			outSuccessJson(allMultiple);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * 
	 * @Description 设置用户角色
	 * @author liuxd
	 * @date 2015-9-14 下午9:06:36
	 * @version V1.0
	 */
	public void userSetRole() {
		try {
			String[] roleIds = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			sqls.add("delete from tried_system_user_role where userId='" + model.getId() + "'");
			for (int i = 0; i < roleIds.length; i++) {
				if (null != roleIds[i] && !roleIds[i].isEmpty()) {
					sqls.add("insert into tried_system_user_role(userId,roleId)values('" + model.getId() + "','" + roleIds[i] + "')");
				}
			}
			systemUserService.dbBeatchSql(sqls);
			outSuccessJson("成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * 原密码是否正确
	 */
	public void checkPass() {
		try {
			SystemUser user = getCurrentUser();
			String oldPass = EncryptUtil.decrypt(user.getLoginPass(), user.getPassKey());
			if (oldPass.equals(model.getOldPassword())) {
				outSuccessJson("正确");
			} else {
				outErrorJson("原密码错误");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("原密码错误");
		}
	}

	/**
	 * 个人密码修改
	 */
	public void changeKey() {
		try {
			SystemUser user = getCurrentUser();
			String oldPass = EncryptUtil.decrypt(user.getLoginPass(), user.getPassKey());
			if (oldPass.equals(model.getOldPassword())) {// 与原密码相同
				user.setLoginPass(EncryptUtil.encrypt(model.getNowPassword(), user.getPassKey()));
				systemUserService.update(user);
			} else {
				throw new RuntimeException("旧密码与原密码不同");
			}
			outSuccessJson("修改成功!");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	 * @Description 根据部门id 获取部门及子部门下所有的用户
	 * @author liuxd
	 * @date 2018-7-27 下午4:02:03
	 * @version V1.0
	 */
	public void findDepUser() {
		try {
			List<SystemUser> resultList=new LinkedList<SystemUser>();
			List<Object[]> depList = systemUserService.findChildsByParentId("tried_system_department", recordId,null);
			String depIdCondition="";
			for(Object[] obj:depList){
				if(obj[0]!=null&&!obj[0].toString().isEmpty()){
					depIdCondition+=",'"+obj[0].toString()+"'";
				}
			}
			if(depIdCondition.length()>0){
				resultList=systemUserService.findAll("from SystemUser where depId in ("+depIdCondition.substring(1)+") or workId in ("+depIdCondition.substring(1)+") ");
			}
			outSuccessJson(resultList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	
	public  void getPlanUserList(){		
		try {
			List<SystemUser>resultList=systemUserService.getPlanUserListByPlanId(recordId);
			outJsonList(resultList);
		} catch (Exception e) {			
			e.printStackTrace();
			outErrorJson("失败");
		}
		
		
		
		
	}
	
	
	
}
