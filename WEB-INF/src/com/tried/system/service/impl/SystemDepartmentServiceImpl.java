package com.tried.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemDepartment;
import com.tried.system.model.SystemUser;
import com.tried.system.service.SystemDepartmentService;
import com.tried.system.service.SystemUserService;

/**
 * @Description 部门信息 服务接口实现
 * @author liuxd
 * @date 2015-09-07 10:27:53
 * @version V1.0
 */
@Service
public class SystemDepartmentServiceImpl extends BaseServiceImpl<SystemDepartment> implements SystemDepartmentService {
	@Resource
	SystemUserService systemUserService;
	@Override
	public List<Map<String, Object>> depTree() throws Exception {
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		List<SystemDepartment> departmentList = this.findAll("from SystemDepartment where flag='是' and dataType='部门'");
		Map<String, Object> parentMap = new HashMap<String, Object>();
		treeMenuList(parentMap,departmentList, "0");
		if(parentMap.get("children")!=null){
			resultList=(List<Map<String, Object>>)parentMap.get("children");
		}
		return resultList;
	}

	public void treeMenuList(Map<String, Object> parentMap,List<SystemDepartment> menuList, String parentId) {
	
		for (SystemDepartment dep : menuList) {
	
			if (dep.getParentId().equals(parentId)) {
					 Map<String, Object> childMap = new HashMap<String, Object>();
					 childMap.put("id", dep.getId());
					 childMap.put("text", dep.getName());
					 treeMenuList(childMap,menuList, dep.getId());
				
				if(parentMap.get("children")!=null){
					List<Map<String, Object>> map=(List<Map<String, Object>>)parentMap.get("children");
					map.add(childMap);
					parentMap.put("children", map);					
				}else{
					List<Map<String, Object>> map=new ArrayList<Map<String,Object>>();
					map.add(childMap);
					parentMap.put("children", map);
				}

			}
		}
	}

	@Override
	public List<SystemDepartment> findJob(String depId) throws Exception {
		List<SystemDepartment> departmentList = this.findAll("from SystemDepartment where parentId='"+depId+"'  and flag='是' and dataType='职务'");
		return departmentList;
	}

	@Override
	public 	List<SystemUser> findDepPeople(String depId) throws Exception {
            
		List<String> idList=new ArrayList<String>();
		String[] deptIds=depId.split(",");
		for(String id:deptIds){
			idList.add(id);
			String sql="with temp (  id, parentId,name) as ( "
						+" select id, parentId,name "
						+" from tried_system_department "
						+" where id = '"+id+"' and dataType='部门' "    //+" where parentId = '"+id+"' and dataType='部门' "
						+" union all select a.id, a.parentId,a.name  "
						+" from tried_system_department a "
						+" inner join temp on a.parentId = temp.id and a.dataType='部门' "
						+" ) select * from temp   ";
			List<Object[]> dbList=this.dbFindList(sql, null);
			for(Object[] obj:dbList){
				if(!idList.contains(obj[0].toString())){
					idList.add(obj[0].toString());
				}
			}	
		}

		
		
		String condition="";
		String partCondition="";
		String ids="";
		for(String s:idList){
			ids+=",'"+s+"'";
		}
		if(ids.length()>0){
			ids=ids.substring(1);
			condition=" depId in("+ids+")";
			partCondition=" d.parentId in("+ids+") ";
		}else{
			condition=" 1!=1";
			partCondition =" 1!=1";
		}
		
		
		List<SystemUser> userList=	systemUserService.findAll("from SystemUser where "+condition);
		
		//获取兼职人员
		String sqljz="select up.userId from tried_system_user_partTimeWork up,tried_system_department d "
			+" where up.workId=d.id and "+partCondition;
		 StringBuilder userBuilder=new StringBuilder();
		List<Object[]> jzList=this.dbFindList(sqljz, null);
			for(Object[] obj:jzList){
				if(obj[0]!=null&&!obj[0].toString().isEmpty()){
					if(userBuilder.indexOf(obj[0].toString())==-1){
						boolean flag=false;
						for(SystemUser user:userList){
							if(user.getId().equals(obj[0].toString())){
								 flag=true;
								 break;
							}
						}
						if(!flag){
						  userBuilder.append(",'"+obj[0].toString()+"'");
						}
					}
				}
			}
		  if(userBuilder.length()>0){
			  List<SystemUser> partUserList=	systemUserService.findAll("from SystemUser where id in ("+userBuilder.substring(1)+")");
			  for(SystemUser user:partUserList){
				  userList.add(user);
			  }
		  }
		 
		return userList;
	}

	

	@Override
	public List<SystemUser> findDepManager(String depId) throws Exception {
		 List<SystemUser> userList=new ArrayList<SystemUser>();
		List<String> idList=new ArrayList<String>();
		idList.add(depId);
		String sql="with temp (  id, parentId,name) as ( "
					+" select id, parentId,name "
					+" from tried_system_department "
					+" where parentId = '"+depId+"' and dataType='部门' "
					+" union all select a.id, a.parentId,a.name  "
					+" from tried_system_department a "
					+" inner join temp on a.parentId = temp.id and a.dataType='部门' "
					+" ) select * from temp   ";
		List<Object[]> dbList=this.dbFindList(sql, null);
		for(Object[] obj:dbList){
			if(!idList.contains(obj[0].toString())){
				idList.add(obj[0].toString());
			}
		}
		String condition="";
		String upCondition="";
		String ids="";
		for(String s:idList){
			ids+=",'"+s+"'";
		}
		if(ids.length()>0){
		sql="SELECT  u.id,u.userName,d.name FROM tried_system_user u,tried_system_department d "
			+" where  u.workId=d.id and d.dataType='职务' "
	  	    +" and (d.name like '%组长' or d.name like '%部长') and u.depId  in("+ids.substring(1)+") "
	  	    +" union "
			+" select u.id,u.userName,d.name from  "
			+" tried_system_user u,tried_system_department d,tried_system_user_partTimeWork up "
			+" where  d.parentId  in ("+ids.substring(1)+") and u.id=up.userId and d.id=up.workId  "
			+" and d.dataType='职务' and (d.name like '%组长' or d.name like '%部长')  ";
		
		 dbList=this.dbFindList(sql, null);
		  userList=new LinkedList<SystemUser>();
		 for(Object[] obj:dbList){
			 SystemUser systemUser=new SystemUser();
			 systemUser.setId((obj[0]!=null)?obj[0].toString():"");
			 systemUser.setUserName((obj[1]!=null)?obj[1].toString():"");
			 systemUser.setWorkName((obj[2]!=null)?obj[2].toString():"");
			 userList.add(systemUser);
		 }
		}
		return userList;
	}

	@Override
	public List<Map<String, Object>> depTree(String id) throws Exception {
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		String sqlStr="";
		if(null!=id&&!id.isEmpty()){
			sqlStr="from SystemDepartment where flag='是' and  id<>'"+id+"'";
		}else{			
			sqlStr="from SystemDepartment where flag='是' ";
		}
		List<SystemDepartment> departmentList = this.findAll(sqlStr);
		Map<String, Object> parentMap = new HashMap<String, Object>();
		treeMenuList(parentMap,departmentList, "0");
		if(parentMap.get("children")!=null){
			resultList=(List<Map<String, Object>>)parentMap.get("children");
		}
		return resultList;
	}
	@Override
	public List<Map<String, Object>> depChliTree(String depId) throws Exception {
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		List<String> idList=new ArrayList<String>();
		String sql="with temp (  id, parentId,name) as ( "
				+" select id, parentId,name "
				+" from tried_system_department "
				+" where parentId = '"+depId+"' and dataType='部门' "
				+" union all select a.id, a.parentId,a.name  "
				+" from tried_system_department a "
				+" inner join temp on a.parentId = temp.id and a.dataType='部门' "
				+" ) select * from temp   ";
		List<Object[]> dbList=this.dbFindList(sql, null);
		for(Object[] obj:dbList){
			if(!idList.contains(obj[0].toString())){
				idList.add(obj[0].toString());
			}
		}
		if(idList.size()!=0){
			StringBuffer idCondition=new StringBuffer();
			   for(String id:idList){
				   idCondition.append(",'"+id+"'");
			   };
			String sqlStr="from SystemDepartment where flag='是' and id in ("+idCondition.substring(1)+") ";
			List<SystemDepartment> departmentList = this.findAll(sqlStr);
			Map<String, Object> parentMap = new HashMap<String, Object>();
			treeMenuList(parentMap,departmentList, depId);
			if(parentMap.get("children")!=null){
				resultList=(List<Map<String, Object>>)parentMap.get("children");
			}
		}
		return resultList;
		
	}
	@Override
	public List<Map<String, Object>> personalWorks(String userId)
			throws Exception {
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		StringBuilder sqlstr=new StringBuilder("");
		StringBuilder ids=new StringBuilder("");
	   List<Object[]>objList=this.dbFindList(" select  u.workId   from   tried_system_user  u where u.id='"+userId+"'  " +
	  		                                 " union " +
	  		                                 "  select  w.workId  from  tried_system_department d,tried_system_user_partTimeWork w where w.workId=d.id  and w.userId='"+userId+"' " 
	  		                                 , null);	
		for(Object[] obj:objList){		
			if(obj[0]!=null&&!obj[0].toString().isEmpty()){
					
				ids.append(obj[0].toString().trim()).append(",");
			}
		}
		Map<String,String> idsMap= getAllParentIds(ids.substring(0,ids.length()-1));
		for(String id:idsMap.keySet()){
			sqlstr.append("'").append(id).append("'").append(",");			
		}	
		String sqlstr2=" from SystemDepartment where flag='是' and  id in ("+sqlstr.substring(0,sqlstr.length()-1)+")";	  
		List<SystemDepartment> departmentList = this.findAll(sqlstr2);
		Map<String, Object> parentMap = new HashMap<String, Object>();
		treeMenuList(parentMap,departmentList, "0");
		if(parentMap.get("children")!=null){
			resultList=(List<Map<String, Object>>)parentMap.get("children");
		}
		return resultList;
	}
 
	   Map<String,String>getAllParentIds(String ids) throws Exception{
		   Map<String,String>resultMap=new HashMap<String, String>();
			String sqlStr=" select d.id,d.parentId  from tried_system_department d where flag='是' ";
			Map<String,String>cldAndPatMap=new HashMap<String, String>();
			List<Object[]>objList=this.dbFindList(sqlStr, null);
			for(Object[] obj:objList){
				String id=obj[0].toString().trim();
				String parentId=obj[1].toString().trim();
				cldAndPatMap.put(id, parentId);			
			}		 
			String [] idArray=ids.split(",");		
			for(int i=0;i<idArray.length;i++){			
				String key=idArray[i].toString();
				resultMap.put(key, key);
				while(cldAndPatMap.containsKey(key)){				
					resultMap.put(cldAndPatMap.get(key), cldAndPatMap.get(key));	
					key=cldAndPatMap.get(key);
				}			
			}	
			return resultMap;						
		}

	@Override
	public List<SystemDepartment> getPartDepIds(String userId) throws Exception{
		List<SystemDepartment> list = new ArrayList<SystemDepartment>();
		//查询用户兼职职务
		String sql = "select workId from tried_system_user_partTimeWork where userId = '"+userId+"'";
		List<Object[]> res = this.dbFindList(sql, null);
		String workId = "";
		for(Object[] obj: res){
			workId += ",'"+obj[0].toString()+"'";
		}
		if(!"".equals(workId)){
			sql = "WITH tab AS ( SELECT id ,parentId,name,dataType " +
					" FROM  tried_system_department WITH (NOLOCK) " +
					" WHERE id in( "+workId.substring(1)+")" +
					" UNION ALL " +
					" SELECT b.id,b.parentId,b.name,b.dataType " +
					" FROM  tab a ,tried_system_department b WITH (NOLOCK) " +
					" WHERE a.parentId = b.id )" +
					" SELECT  * " +
					" FROM  tab WITH (NOLOCK) " +
					" where tab.parentId!='0' and tab.parentId!='4028f481550a6b9e01550a78ff630018' and  tab.dataType = '部门'";
			res = this.dbFindList(sql, null);
			for(Object[] obj: res){
				SystemDepartment dep = new SystemDepartment();
				dep.setId(obj[0].toString());
				dep.setParentId(obj[1].toString());
				dep.setName(obj[2].toString());
				dep.setDataType(obj[3].toString());
				list.add(dep);
			}
		}
		
		
		return list;
	}
	
	
	
}
