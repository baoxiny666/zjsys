package com.tried.common.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.tried.common.ApplicationContextUtil;
import com.tried.system.model.SystemMenu;
import com.tried.system.model.SystemUser;
import com.tried.system.service.SystemUserService;

/**
 * 
 * @Description 用户session拦截器
 * @author liuxd
 * @date 2016-3-29 下午3:49:46
 * @version V1.0
 */
public class CheckUserFilter extends HttpServlet implements Filter {
	private FilterConfig filterConfig;
	private static Logger log = Logger.getLogger(CheckUserFilter.class);
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
		HttpServletRequest myRequest = (HttpServletRequest) request;
		HttpServletResponse myResponse = (HttpServletResponse) response;
		HttpSession session = myRequest.getSession(true);
		log.info("能够获得的最大内存："+Runtime.getRuntime().maxMemory()/1024/1024 + " M ；" +
				"已经获得内存"+Runtime.getRuntime().totalMemory()/1024/1024 + " M"+
				"多余内存"+Runtime.getRuntime().freeMemory()/1024/1024 + " M");
		String path = myRequest.getRequestURI();
		// 获取当前用户
		SystemUser user = (SystemUser) session.getAttribute("user");
		//app授权
		String requestType = myRequest.getHeader("user-agent");
		 if(requestType.contains("iPhone")||requestType.contains("Android")||requestType.contains("Window")){
			         if(path.endsWith(".action")&&myRequest.getParameter("appUserId")!=null){
			        		ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
			        		SystemUserService systemUserService = (SystemUserService) springContext.getBean("systemUserServiceImpl");
							try {
								SystemUser systemUser = systemUserService.getById(myRequest.getParameter("appUserId"));
								session.setAttribute("user", systemUser);
							} catch (Exception e) {
								log.error(e.getMessage());
							}
			         }
					filter.doFilter(request, response);
					return;
		  }
		
		//如果是登录跳转系统不做拦截
		 if (path.endsWith("systemLoginAction_login.action")||path.endsWith("systemLoginAction_checkRepeat.action")||path.endsWith("appFilePostService_signImg.action")) {
			filter.doFilter(request, response);
			return;
		} else {
			//处理用户为空的情况
			if (user == null) {
				//pate为login.html时不做拦截
				if (path.endsWith("login.html")||path.endsWith("appdown.html")) {
					filter.doFilter(request, response);
					return;
				} else {
					//拦截所用session为空，跳转到登录页面
					myResponse.sendRedirect(myRequest.getContextPath() + "/index/login.html");
				}
			} 
			//处理用户不为空的情况
			else {
				String tempId = myRequest.getParameter("tempId");
				//获取所有功能页信息
				List<SystemMenu> allmenuList=(List<SystemMenu>)session.getAttribute("allmenu");
			    boolean pathStatus=false;
			    //判断地址是否是功能页
				for(SystemMenu menu:allmenuList){
					if((myRequest.getContextPath()+menu.getAddress()).equals(path)){
						pathStatus=true;
					}
				}
				if(pathStatus){
					//判断tempId是否为空 等于空时跳转到无权限页面
					if(null==tempId){  
						log.error("用户:"+user.getUserName()+"密匙为空");
						myResponse.sendRedirect(myRequest.getContextPath() + "/page/common/noFunction.html");
					}else{
						List<SystemMenu> menuList=(List<SystemMenu>)session.getAttribute("menu");
						
						String selfTempId="";
						for(SystemMenu menu:menuList){
							if((myRequest.getContextPath()+menu.getAddress()).equals(path)){
								
								selfTempId=menu.getTempId();
							}
						}
						//判断地址是否等于session中功能页并且tempid是否等于session中的tempId，
						if(tempId.equals(selfTempId)){
							log.info("用户:"+user.getUserName()+";功能页面："+path);
							filter.doFilter(request, response);
							return;
						}else{
							log.error("用户:"+user.getUserName()+";访问页面："+path+"的密匙不对");
							myResponse.sendRedirect(myRequest.getContextPath() + "/page/common/noFunction.html");
						}
					}
				}
				//不是功能页的不做拦截。如果拦截的话需要将所有跳转地址注入的数据库，或是用白名单和黑名单管理，
				else{
					log.info("用户:"+user.getUserName()+";操作路径："+path);
					filter.doFilter(request, response);
					 return;
				}
				
			}

		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
