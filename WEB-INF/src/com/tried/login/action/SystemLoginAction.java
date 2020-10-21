package com.tried.login.action;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.ConfigUtils;
import com.tried.common.EncryptUtil;
import com.tried.system.model.SystemLoginInfo;
import com.tried.system.model.SystemMenu;
import com.tried.system.model.SystemUser;
import com.tried.system.service.SystemLoginInfoService;
import com.tried.system.service.SystemMenuService;
import com.tried.system.service.SystemUserService;
/**
 * @Description 用户登录管理类
 * @author liuxd
 * @date 2016-3-25 下午4:15:14
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemLoginAction extends BaseAction<SystemUser>{
	private static final long serialVersionUID = -3824241580550458257L;
	@Resource
	SystemUserService systemUserService;
	@Resource
	SystemMenuService systemMenuService;
	@Resource
	SystemLoginInfoService systemLoginInfoService;
	/**
	  * @Description 登录系统
	  * @author liuxd
	  * @date 2016-3-31 下午2:49:01
	  * @version V1.0
	 */
    public void login(){
    	try {
    		SystemUser systemUser =systemUserService.userInfo(model.getLoginName());
    		 if(null!=systemUser){
    			 if(null!=systemUser&&passIsTrue(systemUser, model.getLoginPass())){
    				 Map<String,HttpSession>loginUserMap = (Map<String,HttpSession>)getApplication().get("loginUserMap");
        			 if(loginUserMap == null){
    					 loginUserMap = new HashMap<String,HttpSession>();
    				 } 
            		 if(loginUserMap.containsKey(systemUser.getLoginName())){
            			 HttpSession sessionUser= loginUserMap.get(systemUser.getLoginName());
            			 //清除session
            			 sessionUser.removeAttribute("user"); 
            			 loginUserMap.remove(systemUser.getLoginName());       			 
            		 }
    				//单人登录
    				loginUserMap.put(systemUser.getLoginName(), getRequest().getSession());
					
					//获取功能菜单数组
    				
    				//	List<SystemMenu> menuList=systemMenuService.menuTree(systemUser.getId());//树形菜单
//					for(SystemMenu menu:menuList){
//						//设置临时Id
//						menu.setTempId(ConfigUtils.random32());
//					}
//				
    				List<Map<String,Object>> menuList=systemMenuService.menuObject(systemUser.getId());
			
					SystemLoginInfo loginInfo = new SystemLoginInfo();
					loginInfo.setRecordTime(new Date());
					loginInfo.setRecordUser(systemUser.getId());
					loginInfo.setLoginIP(loginIp());
					loginInfo.setLoginName(systemUser.getLoginName());
					loginInfo.setContext("登录");
					systemLoginInfoService.addLogin(loginInfo);
					
					getSession().put("user", systemUser);
					getApplication().put("loginUserMap", loginUserMap);
					getSession().put("menu", menuList);
					getSession().put("allmenu", systemMenuService.findAll("from SystemMenu where flag='是'"));
					
					outSuccessJson("成功");
	    		}else{
	    			outErrorJson("密码不正确");
	    		}
			}else{
				outErrorJson("用户名不存在");
			}
		} catch (Exception e) {
			outErrorJson("网络异常");
		}
    }
    public static void main(String[] args) {
    	try{
    	System.out.println(EncryptUtil.decrypt("RUWwqS9d2AzjDLDoKN9Gqw==", "6d179ced698b400296fe71632b33c0f0"));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
    public boolean passIsTrue(SystemUser systemUser,String loginPass) throws Exception{
    	if(systemUser.getLoginPass().equals(EncryptUtil.encrypt(loginPass, systemUser.getPassKey()))){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
      * @Description 退出系统
      * @author liuxd
      * @date 2016-3-31 下午2:49:16
      * @version V1.0
     */
	public void logout() throws Exception{
			//保存注销信息
			SystemLoginInfo loginInfo = new SystemLoginInfo();
			loginInfo.setRecordTime(new Date());
			loginInfo.setRecordUser(getCurrentUser().getId());
			loginInfo.setLoginIP(loginIp());
			loginInfo.setLoginName(getCurrentUser().getLoginName());
			loginInfo.setContext("注销");
			systemLoginInfoService.addLogout(loginInfo);
			
			Map<String,HttpSession>loginUserMap = (Map<String,HttpSession>)getApplication().get("loginUserMap");
			if(loginUserMap!=null&&loginUserMap.containsKey(getCurrentUser().getLoginName())){
				loginUserMap.remove(getCurrentUser().getLoginName());
			}
			getSession().remove("menu");
			getSession().remove("user");
			getSession().remove("allmenu");
			outSuccessJson("退出成功");
	}
	
	public void getLoginUser(){
		try{
			SystemUser user = getCurrentUser();
			outJsonData(user);
		}catch(Exception e){
			outErrorJson("获取当前用户失败!");
		}
	}
 
	/**
	 * 强制注销其他用户
	 */
	public void logoutOther(){
		try{
			Map<String,HttpSession>loginUserMap = (Map<String,HttpSession>)getApplication().get("loginUserMap");
			if(model.getLoginName()!=null&&!model.getLoginName().isEmpty()){
				if(loginUserMap.containsKey(model.getLoginName())){
					HttpSession sessionUser = loginUserMap.get(model.getLoginName());
					sessionUser.removeAttribute("user");
					loginUserMap.remove(model.getLoginName());
					getApplication().put("loginUserMap", loginUserMap);
				}
			}
		}catch(Exception e){
			outErrorJson("操作失败");
		}
	}
	/**
	 * 查看重复登录
	 */
	public void checkRepeat(){
		try{
			Map<String,HttpSession>loginUserMap = (Map<String,HttpSession>)getApplication().get("loginUserMap");
			if(loginUserMap!=null){
				if(loginUserMap.containsKey(model.getLoginName())){//判断是否重复登录
					SystemLoginInfo loginInfo = systemLoginInfoService.getByLoginName(model.getLoginName());
					outSuccessJson(loginInfo);
				}else{
					outSuccessJson("YES");
				}
			}else{
				outSuccessJson("YES");
			}
		}catch(Exception e){
			outErrorJson("检查失败");
		}
	}
	 
}
