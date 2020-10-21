package com.tried.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemUser;
import com.tried.system.service.SysemUserService;
@Service
public class SysemUserServiceImpl extends BaseServiceImpl<SystemUser> implements SysemUserService {

	
	@Override
	public String getUserSignById(String uid) throws Exception {
		String path="";
		String str=" select distinct u.id,f.fileSavePath+f.fileSaveName   from tried_system_user u,tried_file_manager f "+
                   "  where  f.pkId=u.id  and f.pkColumn='system_user_sign_name' and ( u.id='"+uid+"' or  u.userName='"+uid+"')";
	
		List<Object[]> objList=this.dbFindList(str, null);  
	       
		for(Object[] obj:objList){				
		 path=obj[1]!=null?obj[1].toString():"";			
		}
		
		return path;
	}
	
}
