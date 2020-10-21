package com.tried.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemDepartment;
import com.tried.system.service.SystemDepartmentService;
import com.tried.system.service.SystemUserService;

/**
 * @Description 部门信息 管理
 * @author liuxd
 * @date 2015-09-07 10:27:53
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemDepartmentAction extends BaseAction<SystemDepartment> {
	private static Logger logger = Logger.getLogger(SystemDepartmentAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemDepartmentService systemDepartmentService;
	@Resource
	SystemUserService  systemUserService;

	/**
	 * @Description 分页显示部门信息
	 * @author liuxd
	 * @date 2015-09-07 10:27:53
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getName() && !model.getName().isEmpty()) {
				this.condition = " and name like '%" + model.getName() + "%'";
			}
			this.condition=this.condition+ " and parentId='"+model.getParentId()+"'";
			
			this.condition = this.condition + "  order by sequence asc";
			outJsonData(systemDepartmentService.findPage(new Page<SystemDepartment>(page, rows), "from SystemDepartment where 1=1 " + this.condition).getResult());
		} catch (Exception e) {	
			logger.error(e.getMessage());
		
			outErrorJson("失败");
		}
	}

	/**
	  * @Description 初始化树
	  * @author liuxd
	  * @date 2015-9-9 下午8:47:24
	  * @version V1.0
	 */
	public void initTree() {
		try {
			outSuccessJson(systemDepartmentService.findAll("from SystemDepartment order by sequence asc"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	
	/**
	 * @Description 添加部门信息
	 * @author liuxd
	 * @date 2015-09-07 10:27:53
	 * @version V1.0
	 */
	public void add() {
		try {
			systemDepartmentService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除部门信息
	 * @author liuxd
	 * @date 2015-09-07 10:27:53
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_department   where id='" + ids[i]+"'");
				}
			}
			systemDepartmentService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改部门信息
	 * @author liuxd
	 * @date 2015-09-07 10:27:53
	 * @version V1.0
	 */
	public void edit() {
		try {
			systemDepartmentService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	  * @Description 树节点拖动
	  * @author liuxd
	  * @date 2015-9-9 下午8:22:10
	  * @version V1.0
	 */
	public void drop(){
		try {
			List<String> sqls=new ArrayList<String>();
			String[] ids=recordIdS.split(",");
			for(int i=0;i<ids.length;i++){
				sqls.add(" update tried_system_department set parentId='"+model.getParentId()+"' where id='"+ids[i]+"'");
			}
			systemDepartmentService.dbBeatchSql(sqls);
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
				sqls.add(" update  tried_system_department  set sequence="+i+" where id='"+ids[i]+"'");
			}
			systemDepartmentService.dbBeatchSql(sqls);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	/**
	  * @Description 部门机构数
	  * @author liuxd
	  * @date 2016-7-4 下午4:10:22
	  * @version V1.0
	 */
	public void findDepTree(){
		try{
			outJsonList(systemDepartmentService.depTree());
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	/**
	 * 
	  * @Description 根据部门id获取所属部门职务
	  * @author liuxd
	  * @date 2016-7-5 下午4:16:52
	  * @version V1.0
	 */
	public void findAllJob(){
		try{
			outJsonList(systemDepartmentService.findJob(recordId));
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	
	/**
	  * @Description 获取检测部门
	  * @author liuxd
	  * @date 2016-7-6 下午3:24:11
	  * @version V1.0
	 */
	public void findChildrenDep(){
		try{
			if(recordId!=null&&!recordId.isEmpty()){
				List<SystemDepartment> deptIdList=systemDepartmentService.findAll("from SystemDepartment where parentId ='"+recordId+"' and  dataType='部门' and flag='是' ");
			outJsonList(systemDepartmentService.findAll("from SystemDepartment where parentId ='"+recordId+"' and  dataType='部门' and flag='是' "));
			}
			else{
				outJsonList(systemDepartmentService.findAll("from SystemDepartment where   dataType='部门' and flag='是' "));
					
			}
			}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	
	public void findJcDepAndGroup(){
		try{
			List<SystemDepartment> list = systemDepartmentService.findAll("from SystemDepartment where parentId ='"+recordId+"' and  dataType='部门' and flag='是' ");
			String sql = "select id,name,parentId,flag,dataType from  tried_system_department where parentId = '402881eb55b49f550155b4c0a220000f' and  dataType='部门' and flag='是' ";
			List<Object[]> res = systemDepartmentService.dbFindList(sql, null);
			for(int i=0;i<res.size();i++){
				SystemDepartment depart = new SystemDepartment();
				depart.setId(res.get(i)[0].toString());
				depart.setName(res.get(i)[1].toString());
				depart.setParentId(res.get(i)[2]==null?"":res.get(i)[2].toString());
				depart.setDataType(res.get(i)[3]==null?"":res.get(i)[3].toString());
				depart.setFlag(res.get(i)[4]==null?"":res.get(i)[4].toString());
				list.add(i,depart);
			}
			outJsonList(list);
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	/**
	 * 
	  * @Description 获取部门所有人员
	  * @author liuxd
	  * @date 2016-7-7 上午11:29:49
	  * @version V1.0
	 */
	public void findJcPeople(){
		try{
			outJsonList(systemDepartmentService.findDepPeople(recordId));
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	
	/**
	  * @Description 获取职务未赋予的角色和已赋予角色
	  * @author liuxd
	  * @date 2015-9-14 下午8:38:05
	  * @version V1.0
	 */
	public void depGetRole(){
		Map<String, Map<String, String>> allMultiple = new HashMap<String, Map<String, String>>();
		Map<String, String> leftMultiple = new LinkedHashMap<String, String>();
		Map<String, String> rightMultiple = new LinkedHashMap<String, String>();
		try {
			// 未赋予的角色 
			String sql = "select r.id, r.roleName from tried_system_role r where r.flag='是' and r.id not in(select dr.roleId from tried_system_dep_role dr where dr.depId='"
					+ model.getId() + "') order by r.roleName desc";
			List<Object[]> teacherNotRoles = systemDepartmentService.dbFindList(sql, null);
			for (Object[] objs : teacherNotRoles) {
				leftMultiple.put(objs[0].toString(), objs[1].toString());
			}
			// 已赋予角色
			sql = "select r.id, r.roleName from tried_system_dep_role dr ,tried_system_role r where r.flag='是' and dr.roleId=r.id and dr.depId= '" + model.getId()+ "' and r.flag='是'  order by r.roleName desc ";
			List<Object[]> teacherRoles = systemDepartmentService.dbFindList(sql, null);
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
	  * @Description 获取用户部门角色
	  * @author liuxd
	  * @date 2016-11-24 上午9:43:45
	  * @version V1.0
	 */
	
	public void findDepRole(){
		Map<String, String> leftMultiple = new LinkedHashMap<String, String>();
		try {
			// 未赋予的角色 
			String sql = "select r.id, r.roleName from tried_system_role r where r.flag='是' and r.id not in(select dr.roleId from tried_system_dep_role dr where dr.depId='"
					+ model.getId() + "') and r.roleName like '%"+model.getName()+"%' order by r.roleName desc";
			List<Object[]> teacherNotRoles = systemDepartmentService.dbFindList(sql, null);
			for (Object[] objs : teacherNotRoles) {
				leftMultiple.put(objs[0].toString(), objs[1].toString());
			}
			outSuccessJson(leftMultiple);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	
	/**
	 * 
	  * @Description 设置部门
	  * @author liuxd
	  * @date 2015-9-14 下午9:06:36
	  * @version V1.0
	 */
	public void depSetRole(){
		 try{
			 String[] roleIds= recordIdS.split(",");
			 List<String> sqls=new ArrayList<String>();
			 sqls.add("delete from tried_system_dep_role where depId='"+ model.getId()+"'");
			 for(int i=0;i<roleIds.length;i++){
				 if(null!=roleIds[i]&&!roleIds[i].isEmpty()){
					 sqls.add("insert into tried_system_dep_role(depId,roleId)values('"+model.getId()+"','"+roleIds[i]+"')");
				 }
			 }
			 systemDepartmentService.dbBeatchSql(sqls);
			 outSuccessJson("成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	/**
	  * @Description 获取部门树
	  * @author liuxd
	  * @date 2016-9-2 上午10:04:11
	  * @version V1.0
	 */
	public void initDepTree() {
		try {
			outSuccessJson(systemDepartmentService.findAll("from SystemDepartment where flag='是' and dataType='部门'"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	/**
	  * @Description 根据部门id获取部门下所有用户
	  * @author liuxd
	  * @date 2016-9-2 上午10:04:51
	  * @version V1.0
	 */
	public void depUser(){
		try {
			outJsonList(systemUserService.findAll("from  SystemUser where depId='"+recordId+"' "));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	/**
	 * 
	  * @Description 获得部门的部长、组长信息
	  * @author liuxd
	  * @date 2016-10-27 下午2:38:54
	  * @version V1.0
	 */
	public void findDepManagerPeople(){
		try{
			outJsonList(systemDepartmentService.findDepManager(recordId));
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	
	/**
	  * @Description 兼职职务树
	  * @author sunlunan
	  * @date 2016-7-4 下午4:10:22
	  * @version V1.0
	 */
	public void findPartWorkTree(){
		try{
			outJsonList(systemDepartmentService.depTree(model.getId()));
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	/**
	 * 
		 * @Description 获取子部门树
		 * @author liuxd
		 * @date  2018-10-11上午10:21:00
		 * @version V1.0
	 */
	public void depChliTree(){
		try{
			outJsonList(systemDepartmentService.depChliTree(getRecordId()));
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	
	
	/**
	  * @Description 个人信息树
	  * @author sunlunan
	  * @date 2016-7-4 下午4:10:22
	  * @version V1.0
	 */
	public void findPersonalWorkTree(){
		try{
			outJsonList(systemDepartmentService.personalWorks(getCurrentUser().getId()));
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	
	
	
	
}
