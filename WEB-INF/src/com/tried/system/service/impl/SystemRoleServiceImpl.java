package com.tried.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemMenu;
import com.tried.system.model.SystemRole;
import com.tried.system.model.SystemUser;
import com.tried.system.service.SystemRoleService;
/**
 * @Description 角色信息 服务接口实现
 * @author liuxd
 * @date 2015-09-07 10:09:02
 * @version V1.0
 */
@Service
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRole> implements SystemRoleService {

	@Override
	public List<SystemMenu> roleMenuTree(String roleId) throws Exception {
		String sql="SELECT  m.id ,m.parentId ,m.name, m.icon,rm.roleId FROM ( select * from tried_system_menu where flag='是')  m left  join tried_system_role_menu rm "
				+" on m.flag='是' and m.id=rm.menuId and rm.roleId='"+roleId+"'";
			List<Object[]> list=this.dbFindList(sql, null);
			List<SystemMenu> resultList=new LinkedList<SystemMenu>();
			for(Object[] obj:list){
				SystemMenu menu=new SystemMenu();
				menu.setId((obj[0]!=null)?obj[0].toString():"");
				menu.setParentId((obj[1]!=null)?obj[1].toString():"");
				menu.setName((obj[2]!=null)?obj[2].toString():"");
				menu.setIcon((obj[3]!=null)?obj[3].toString():"");
			    if(obj[4]!=null){
			    	menu.setChecked(true);	
			    }
				resultList.add(menu);
			}
		return resultList;
	}

	@Override
	public void roleSetMenu(String roleId, String menuIdS) throws Exception {
		List<String> sqls=new LinkedList<String>();
		sqls.add("delete from tried_system_role_menu where roleId='"+roleId+"'");
		String[] menus=menuIdS.split(",");
		for(String menuId:menus){
			sqls.add("insert into tried_system_role_menu(roleId,menuId)values('"+roleId+"','"+menuId+"')");
		}
		this.dbBeatchSql(sqls);
	}

	@Override
	public List<String> getRoleCodeByUser(String userId) throws Exception {
		List<Object[]> resultList= this.dbFindList(" select r.roleCode from tried_system_user tsu, tried_system_dep_role tsdr,tried_system_role r where tsu.id='"+userId+"' and tsu.workId = tsdr.depId and  tsdr.roleId=r.id and r.flag='是'", null);
		List<String> roleCodeList=new ArrayList<String>();
		for(Object[] obj:resultList){
			if(obj[0]!=null&&!obj[0].toString().isEmpty())
			roleCodeList.add(obj[0].toString());
		}
		
		return roleCodeList;
	}

	@Override
	public List<SystemUser> findRoleUser(String roleName) throws Exception {
		String sql="SELECT  u.id,u.userName,r.roleName FROM tried_system_user u ,tried_system_dep_role dr,tried_system_role r "
				 +" where  r.roleCode='"+roleName+"' and u.workId=dr.depId and dr.roleId=r.id  and r.flag='是'" 
				 +"  union "
				 +" select u.id,u.userName,r.roleName from tried_system_user u,tried_system_user_partTimeWork up,tried_system_dep_role dr,tried_system_role r "
				 +" where  r.roleCode='"+roleName+"' and  u.id=up.userId and up.workId=dr.depId and dr.roleId=r.id and r.flag='是'";
		List<Object[]> 	 dbList=this.dbFindList(sql, null);
		List<SystemUser> userList=new LinkedList<SystemUser>();
		 for(Object[] obj:dbList){
			 SystemUser systemUser=new SystemUser();
			 systemUser.setId((obj[0]!=null)?obj[0].toString():"");
			 systemUser.setUserName((obj[1]!=null)?obj[1].toString():"");
			 userList.add(systemUser);
		 }
		return userList;
	}

	@Override
	public List<SystemUser> findDepRoleUser(String recordId, String roleName)throws Exception {
		String sql="with hgo as ( select *,0 as rank from tried_system_department where id ='"+recordId+"'  " 
				+" union all select h.*,h1.rank+1 from tried_system_department h join hgo h1 on h.parentId=h1.id "
				+"	)select * from hgo where dataType='职务'";
		List<Object[]> 	 dbList=this.dbFindList(sql, null);
		System.out.println("输出1"+sql);
		String ids="";
		for(Object[] obj:dbList){
			if(null!=obj[0]){
				ids+=",'"+obj[0].toString()+"'";
			}
		}
		if(!ids.isEmpty()){
			  sql=" SELECT  u.id,u.userName,r.roleName  FROM tried_system_user u ,tried_system_dep_role dr,tried_system_role r "
				  + " where u.workId=dr.depId and dr.depId in  ( "+ids.substring(1)+")  and dr.roleId=r.id "
				  +" and r.roleCode='"+roleName+"'  and r.flag='是' "
				  +" union "
				  +"  select  u.id,u.userName,r.roleName from "
				  +"  tried_system_department d ,tried_system_user_partTimeWork up,tried_system_dep_role dr,tried_system_role r ,tried_system_user u "
			     +"  where d.id  in  ( "+ids.substring(1)+")  and d.id =up.workId   and  up.workId=dr.depId "   //  where d.parentId  in  ( "+ids.substring(1)+")  and d.id =up.workId   and  up.workId=dr.depId  
			      +"  and  dr.roleId=r.id and r.roleCode='"+roleName+"'  and  up.userId =u.id and     r.flag='是'";
		}
		System.out.println("输出2"+sql);
		dbList=this.dbFindList(sql, null);
		List<SystemUser> userList=new LinkedList<SystemUser>();
		 for(Object[] obj:dbList){
			 SystemUser systemUser=new SystemUser();
			 systemUser.setId((obj[0]!=null)?obj[0].toString():"");
			 systemUser.setUserName((obj[1]!=null)?obj[1].toString():"");
			 userList.add(systemUser);
		 }
		return userList;
	}

	@Override
	public Map<String, String> fingAllRoleBySystemUser(SystemUser currentUser) throws Exception {
		Map<String, String> resultMap=new HashMap<String, String>();
        String  sqlStr=" select u.depId,r.roleCode from tried_system_user u, tried_system_dep_role dr,tried_system_role r "+
                       " where  u.id='"+currentUser.getId()+"' and u.workId = dr.depId and  dr.roleId=r.id and r.flag='是' "+
                       " union "+
                       " select  work.parentId,r.roleCode from tried_system_user_partTimeWork w,tried_system_dep_role dr,tried_system_department work,tried_system_role r "+
                       " where  w.userId='"+currentUser.getId()+"' and w.workId=dr.depId and dr.roleId= r.id and work.id=w.workId and r.flag='是' ";
   
        List<Object[]>objList=this.dbFindList(sqlStr, null);	
	   for(Object[] obj:objList){		   
		  String key=obj[0].toString().trim()+"@"+ (obj[1]!=null?obj[1].toString().trim():"");		   
		  resultMap.put(key, key);  		   
	   }			
		return resultMap;
	}	
	
}
