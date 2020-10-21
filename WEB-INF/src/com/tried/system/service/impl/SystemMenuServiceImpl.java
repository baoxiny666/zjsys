package com.tried.system.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.system.model.SystemMenu;
import com.tried.system.service.SystemMenuService;
/**
 * @Description 功能管理 服务接口实现
 * @author liuxd
 * @date 2015-12-25 16:08:34
 * @version V1.0
 */
@Service
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenu> implements SystemMenuService {

	@Override
	public List<SystemMenu> menuTree(String userId) throws Exception {

		String sql="select distinct m.menuId from  tried_system_user u, tried_system_dep_role dr, tried_system_role_menu m "
				+" where u.id = '"+userId+"' and u.workId = dr.depId  and dr.roleId = m.roleId "
				+" union "
				+" select distinct  m.menuId  from   tried_system_user_partTimeWork up,tried_system_dep_role dr,tried_system_role_menu m "
				+" where up.userId='"+userId+"' and up.workId=dr.depId and dr.roleId=m.roleId ";
			  List<Object[]>  listId=this.dbFindList(sql,null);
		      StringBuffer ids=new StringBuffer();
		      for(Object[] obj:listId){
		    	  ids.append("'"+obj[0].toString()+"',");
		      }
		      if(ids.length()>0){
		    	  return this.findAll("from SystemMenu where flag ='是' and id in("+ids.substring(0, ids.length()-1).toString()+") order by sequence asc ");
		      }
		      else{
		    	  return new ArrayList<SystemMenu>();
		      }
		     
			}

	@Override
	public List<Map<String,Object>> menuObject(String userId) throws Exception {
		List<Map<String,Object>> returnList=new LinkedList<Map<String,Object>>();
		String sql="select distinct m.menuId from  tried_system_user u, tried_system_dep_role dr, tried_system_role_menu m "
				+" where u.id = '"+userId+"' and u.workId = dr.depId  and dr.roleId = m.roleId "
				+" union "
				+" select distinct  m.menuId  from   tried_system_user_partTimeWork up,tried_system_dep_role dr,tried_system_role_menu m "
				+" where up.userId='"+userId+"' and up.workId=dr.depId and dr.roleId=m.roleId ";
			  List<Object[]>  listId=this.dbFindList(sql,null);
		      StringBuffer ids=new StringBuffer();
		      for(Object[] obj:listId){
		    	  ids.append("'"+obj[0].toString()+"',");
		      }
		      if(ids.length()>0){
		    	  List<SystemMenu> systemMenuList =this.findAll("from SystemMenu where flag ='是'  and id in("+ids.substring(0, ids.length()-1).toString()+") order by sequence asc ");
		    	  Map<String,Object> map=new LinkedHashMap<String, Object>();
		    	  map.put("id", "0");
		    	  map.put("name", "跟节点");
		    	  
		    	  map.put("childMenus",null);
		    	 
		    	  returnList.add(map);
		    	  iteratorMenu(map,systemMenuList,"0");
		    	  System.out.println(JSONArray.fromObject(map.get("childMenus")).toString());
		    	  if(map.get("childMenus")!=null){
		    		  returnList=(LinkedList<Map<String,Object>>)map.get("childMenus");
		    	  }
		      }
		     return returnList;
	}
	public void iteratorMenu(Map<String,Object> objMap,List<SystemMenu> systemMenuList,String parentId){
		 for(SystemMenu menu:systemMenuList){
			 if(menu.getParentId().equals(parentId)){
				 Map<String,Object> map=new LinkedHashMap<String, Object>();
				  map.put("name", menu.getName());
	    		  map.put("url", menu.getAddress());
	    		  map.put("icon", menu.getIcon());
	    		  map.put("id", menu.getId());
	    		  map.put("tempId",ConfigUtils.random32());
	    		  map.put("childMenus",null);
	    		  iteratorMenu( map, systemMenuList,menu.getId());
	    		  if(objMap.get("childMenus")==null){
	    			  List<Map<String,Object>> cList=new LinkedList<Map<String,Object>>();
	    			  cList.add(map);
	    			  objMap.put("childMenus", cList);
	    		  }else{
	    			  ((LinkedList<Map<String,Object>>) objMap.get("childMenus")).add(map);
	    		  }
			 } 
		 }
		 
	}

}
