package com.tried.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.system.model.SystemUser;
import com.tried.system.service.FileManagerService;
import com.tried.system.service.SystemUserService;
/**
 * @Description 用户信息 服务接口实现
 * @author liuxd
 * @date 2015-09-09 22:43:36
 * @version V1.0
 */
@Service
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUser> implements SystemUserService {
	@Resource
	FileManagerService fileManagerService;
	@Override
	public SystemUser userInfo(String userName) throws Exception {
		List<SystemUser> userList=this.findAll("from SystemUser where loginName='"+userName+"'");
		if(userList.size()>0){
			SystemUser	systemUser=userList.get(0);
			return systemUser;
		}
		return null;
	}

	@Override
	public List<SystemUser> findOnlines(String loginUsers) throws Exception {
		List<SystemUser> userList = new ArrayList<SystemUser>();
		String sql = "select tsu.loginName,tsu.userName,tsd.name from tried_system_user tsu,tried_system_department tsd where tsu.id in("+loginUsers+") and tsu.depId = tsd.id order by tsd.name";
		List<Object[]> res = this.dbFindList(sql, null);
		for(Object[] obj : res){
			SystemUser user = new SystemUser();
			user.setLoginName(obj[0]==null?"":obj[0].toString());
			user.setUserName(obj[1]==null?"":obj[1].toString());
			user.setDepName(obj[2]==null?"":obj[2].toString());
			userList.add(user);
		}
		return userList;
	}

	@Override
	public void add(SystemUser systemUser, String pkId,String workJzId) throws Exception {
		this.add(systemUser);
		fileManagerService.add("system_user_sign_name", systemUser.getSystem_user_sign_name(), pkId, systemUser.getId());	
		if(null!=workJzId&&!workJzId.isEmpty()){
			List<String>sqlList=new ArrayList<String>();
			 String [] 	workIds=workJzId.split(",");
			 for(String workId:workIds){
				 String ids=ConfigUtils.random32();
				 sqlList.add("insert into tried_system_user_partTimeWork(id,workId,userId) values('"+ids+"','"+workId+"','"+systemUser.getId()+"') ");			
			 }
			 this.dbBeatchSql(sqlList);
			}
		
		
	}

	@Override
	public void edit(SystemUser systemUser, String pkId,String workJzId) {
		try {
			this.update(systemUser);
			 fileManagerService.add("system_user_sign_name", systemUser.getSystem_user_sign_name() , pkId, systemUser.getId());  	
			 List<String>sqlList=new ArrayList<String>(); 
			   sqlList.add("delete from tried_system_user_partTimeWork where userId='"+systemUser.getId()+"'"); 
			 if(null!=workJzId&&!workJzId.isEmpty()){			
				 String [] wIds=workJzId.split(",");
				 for(String wId:wIds){
					 String ids=ConfigUtils.random32();
					 sqlList.add("insert into tried_system_user_partTimeWork(id,workId,userId) values('"+ids+"','"+wId+"','"+systemUser.getId()+"') ");			 
				 }				 			 
			 }
			 this.dbBeatchSql(sqlList);	
		} catch (Exception e) {			
			e.printStackTrace();
		}
			
	}

	@Override
	public List<SystemUser> getWorkTextById(String id) throws Exception {		
	       List<SystemUser> resultList=new ArrayList<SystemUser>();
        StringBuilder sqlStr=new StringBuilder("select u.id, u.userName,u.loginName,d.dataType,d.name,d.id  from tried_system_user_partTimeWork w,tried_system_user u,tried_system_department d "+
                "  where w.userId=u.id and w.workId=d.id ");  		      
        if(null!=id&&!id.isEmpty()){
        	sqlStr.append(" and u.id='"+id+"' ");
        }        
	List<Object[]>objList=this.dbFindList(sqlStr.toString(), null);
	for(Object[] obj:objList){
		SystemUser user=new SystemUser();
		 user.setId(obj[0].toString().trim());
		 user.setWorkName(obj[4]!=null?obj[4].toString().trim():"");
		 user.setWorkId(obj[5]!=null?obj[5].toString().trim():"");
		 resultList.add(user);
	}		
	return resultList;
	}
	
	@Override
	public SystemUser findByName(String checkPerson) throws Exception {
		if(checkPerson!=null&&!checkPerson.isEmpty()){
			String[] names = checkPerson.split(";");
			if(names.length>0){
				List<SystemUser> list =  this.findAll(" from SystemUser where userName='"+names[0]+"'");
				if(list.size()>0){
					return list.get(0);
				}
			}
		}
		return null;
	}

	@Override
	public List<SystemUser> getPlanUserListByPlanId(String planId) throws Exception {
		List<SystemUser> resultList=new ArrayList<SystemUser>();
		String sqlstr="select u.id,userName from tried_system_user u,tried_tool_maintain_plan_user p where u.id=p.userId and p.planId='"+planId+"'";
		List<Object[]>sqlList=this.dbFindList(sqlstr, null);
		for(Object[] obj:sqlList){			
			String id=obj[0].toString().trim();
			String userName=obj[1].toString().trim();
			SystemUser user=new SystemUser();
			user.setId(id);
			user.setUserName(userName);
			resultList.add(user);			
		}			
		return resultList;
	}

	@Override
	public void userUpdate(SystemUser systemUser, String pkId, String workJzId) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.update(systemUser);
			fileManagerService.add("system_user_sign_name", systemUser.getSystem_user_sign_name() , pkId, systemUser.getId());  	
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

}
