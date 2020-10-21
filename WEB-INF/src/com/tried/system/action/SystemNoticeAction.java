package com.tried.system.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jboss.errai.bus.server.util.ConfigUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.ConfigUtils;
import com.tried.common.Page;
import com.tried.system.model.SystemMemory;
import com.tried.system.model.SystemNotice;
import com.tried.system.service.SystemMemoryService;
import com.tried.system.service.SystemNoticeService;

/**
 * @Description 公告管理 管理
 * @author liuxd
 * @date 2016-09-06 16:30:37
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemNoticeAction extends BaseAction<SystemNotice> {
	private static Logger logger = Logger.getLogger(SystemNoticeAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemNoticeService systemNoticeService;
	@Resource
	SystemMemoryService systemMemoryService;

	/**
	 * @Description 分页显示公告管理
	 * @author liuxd
	 * @date 2016-09-06 16:30:37
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getNotice_title() && !model.getNotice_title().isEmpty()) {
				this.condition = " and notice_title like '%" + model.getNotice_title() + "%'";
			}
			if (null != model.getNotice_status() && !model.getNotice_status().isEmpty()) {
				this.condition = " and notice_status = '" + model.getNotice_status() + "'";
			}
			this.condition = this.condition + "  " + this.getOrderColumn();
			outJsonData(systemNoticeService.findPage(new Page<SystemNotice>(page, rows), "from SystemNotice where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * 
	  * @Description 显示部分公告信息
	  * @author liuxd
	  * @date 2016-9-9 上午9:29:48
	  * @version V1.0
	 */
	public void part_list(){
		try {
			outJsonList(systemNoticeService.findPage(new Page<SystemNotice>(1, rows), "from SystemNotice   order by recordTime desc").getResult().get("rows"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}	
	}
	
	/**
	 * 显示5日内公告
	 */
	public void slideList(){
		
	String 	day5=ConfigUtils.dataToString(new Date(System.currentTimeMillis()-5*24*60*60*1000));
		
	try {
		List<SystemNotice> noticeList=systemNoticeService.findAll(" from SystemNotice where recordTime >='"+day5+"'");
		if(noticeList.size()==0){
			noticeList=(List<SystemNotice>)systemNoticeService.findPage(new Page<SystemNotice>(1, rows), "from SystemNotice   order by recordTime desc").getResult().get("rows");	
		}
		outJsonList(noticeList);		
	} catch (Exception e) {
		logger.error(e.getMessage());
		outErrorJson("失败");
	}
		
		
	}
	
	/**
	 * @Description 添加公告管理
	 * @author liuxd
	 * @date 2016-09-06 16:30:37
	 * @version V1.0
	 */
	public void add() {
		try {
			
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemNoticeService.add(model,pkId);
			
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除公告管理
	 * @author liuxd
	 * @date 2016-09-06 16:30:37
	 * @version V1.0
	 */
	public void del() {
		try {
			systemNoticeService.delete(model.getId());
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改公告管理
	 * @author liuxd
	 * @date 2016-09-06 16:30:37
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			systemNoticeService.edit(model,pkId);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	public void noticInfo(){
		try {
			SystemMemory systemMemory=systemMemoryService.getSingleMemory(model.getId(),"notice_content");
			SystemNotice systemNotice=systemNoticeService.getById(model.getId());
			systemNotice.setNotice_content(systemMemory.getRelationContext());
			outSuccessJson(systemNotice);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("获取失败");
		}
	}
	 
}
