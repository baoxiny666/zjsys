package com.tried.system.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.TemplateSerial;
import com.tried.system.service.TemplateSerialService;

/**
 * @Description 模板序列 管理
 * @author liuxd
 * @date 2020-06-02 22:31:20
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class TemplateSerialAction extends BaseAction<TemplateSerial> {
	private static Logger logger = Logger.getLogger(TemplateSerialAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	TemplateSerialService templateSerialService;

	/**
	 * @Description 分页显示模板序列
	 * @author liuxd
	 * @date 2020-06-02 22:31:20
	 * @version V1.0
	 */
	public void list() {
		try {
			
			if (strIsNotNull(model.getTemplateId())) {
				this.condition = " and templateId = '" + model.getTemplateId() + "'";
			    this.condition +=  this.getOrderColumn();
			    outJsonData(templateSerialService.findPage(new Page<TemplateSerial>(page, rows), "from TemplateSerial where 1=1 " + this.condition).getResult());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	


	/**
	 * @Description 添加模板序列
	 * @author liuxd
	 * @date 2020-06-02 22:31:20
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			templateSerialService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除模板序列
	 * @author liuxd
	 * @date 2020-06-02 22:31:20
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			templateSerialService.beachDelete(ids);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改模板序列
	 * @author liuxd
	 * @date 2020-06-02 22:31:20
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			templateSerialService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	/**
	 * 核对关键字是不是重复了
	 */
	public void checkKey(){
		try {
			List<TemplateSerial> serialList= templateSerialService.findAll("from TemplateSerial where templateId='"+model.getTemplateId()+"' and serialKey='"+model.getSerialKey()+"' ");
			if(serialList.size()>0){
				outSuccessJson(false);	
			}else{
				outSuccessJson(true);	
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson(false);
		}
	}
	 
}
