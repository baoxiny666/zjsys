package com.tried.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemLoginInfo;
import com.tried.system.service.SystemLoginInfoService;
/**
 * @Description 用户登录记录表 服务接口实现
 * @author liuyw
 * @date 2016-11-18 10:44:55
 * @version V1.0
 */
@Service
public class SystemLoginInfoServiceImpl extends BaseServiceImpl<SystemLoginInfo> implements SystemLoginInfoService {

	
	@Override
	public SystemLoginInfo getByLoginName(String loginName) throws Exception{
		SystemLoginInfo loginInfo = new SystemLoginInfo();
		String sql = "select top(1) recordUser,recordTime,loginIP,context,loginName from tried_system_login_info where loginName = '"+loginName+"' order by recordTime desc";
		List<Object[]> res = this.dbFindList(sql, null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Object[] obj : res){
			loginInfo.setRecordUser(obj[0].toString());
			loginInfo.setRecordTime(sdf.parse(obj[1].toString()));
			loginInfo.setLoginIP(obj[2].toString());
			loginInfo.setContext(obj[3].toString());
			loginInfo.setLoginName(obj[4].toString());
		}
		
		return loginInfo;
	}

	@Override
	public void addLogout(SystemLoginInfo loginInfo) throws Exception {
		//删除在线用户
	 	this.add(loginInfo);
		
		
	}
	@Override
	public void addLogin(SystemLoginInfo loginInfo) throws Exception {
		this.add(loginInfo);
		
		 
	}

}
