package com.tried.zjsys.testDataSrc.action;


import javax.annotation.Resource;




import org.apache.log4j.Logger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.tried.base.action.BaseAction;

import com.tried.zjsys.basics.service.DataWlInfoService;

import com.tried.zjsys.testDataSrc.model.ZhilrbYuanlMenu;

import com.tried.zjsys.testDataSrc.service.DataKeyMaxMinService;
import com.tried.zjsys.testDataSrc.service.ZhilrbYuanlService;
import com.tried.zjsys.testDataSrc.service.ZjHandInputFlyService;

/**
 * @Description - 原料质量日报
 * @author boxy
 * @date 2020-11-11 10:38:56
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZhilrbYuanlAction extends BaseAction<ZhilrbYuanlMenu> {
	private static Logger logger = Logger.getLogger(ZhilrbYuanlAction.class);

	private static final long serialVersionUID = 1L;
	@Resource
	ZhilrbYuanlService zhilrbYuanlService;

	private String wlType;
	private String wlCode;

	
	
	/**
	 * @Description 质量日报菜单栏
	 * @author boxy
	 * @date 2020-11-11 10:28:16
	 * @version V1.0
	 */
	public void zhilrbInitMenu() {
		try {
			outSuccessJson(zhilrbYuanlService.findAll("from ZhilrbYuanlMenu where flag='是'  order by sequence asc "));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	

	

	public String getWlType() {
		return wlType;
	}

	public void setWlType(String wlType) {
		this.wlType = wlType;
	}

	public String getWlCode() {
		return wlCode;
	}

	public void setWlCode(String wlCode) {
		this.wlCode = wlCode;
	}

}
