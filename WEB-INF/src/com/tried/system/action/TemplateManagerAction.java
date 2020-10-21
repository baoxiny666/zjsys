package com.tried.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.TemplateManager;
import com.tried.system.service.FileManagerService;
import com.tried.system.service.TemplateManagerService;
import com.tried.system.model.FileManager;
/**
 * @Description - 管理
 * @author lyw
 * @date 2018-12-24 11:30:17
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class TemplateManagerAction extends BaseAction<TemplateManager> {
	private static Logger logger = Logger.getLogger(TemplateManagerAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	TemplateManagerService templateManagerService;
	@Resource
	FileManagerService fileManagerService;
	/**
	 * @Description 分页显示-
	 * @author lyw
	 * @date 2018-12-24 11:30:17
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getTemplateName())) {
				this.condition += " and templateName like '%" + model.getTemplateName() + "%'";
			}
			if(strIsNotNull(model.getTemplateType())){
				this.condition += " and templateType like '%" + model.getTemplateType() + "%'";	
			}
			this.condition = this.condition + "  " + this.getOrderColumn();
			outJsonData(templateManagerService.findPage(new Page<TemplateManager>(page, rows), "from TemplateManager where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author lyw
	 * @date 2018-12-24 11:30:17
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			templateManagerService.addModel(model,pkId);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author lyw
	 * @date 2018-12-24 11:30:17
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_template_manager   where id='" + ids[i]+"'");
				}
			}
			templateManagerService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改-
	 * @author lyw
	 * @date 2018-12-24 11:30:17
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			templateManagerService.updateModel(model,pkId);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	 * 根据名称获取template实体
	 * @Description:getTemplateFile
	 * @author SUNLUNAN
	 * @date 2019-3-28 上午10:51:46
	 * @version V1.0
	 */
	public void getTemplateFile(){
		try {
			TemplateManager manager=templateManagerService.getFirstRecordByField(" from TemplateManager where templateName ='"+model.getTemplateName()+"'  order by recordTime desc");
            outSuccessJson(manager);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
		
		
	} 
	
	/**
	 * 根据名称获取template实体List
	 * @Description:getTemplateFile
	 * @author SUNLUNAN
	 * @date 2019-3-28 上午10:51:46
	 * @version V1.0
	 */
	public void getTemplateFileList(){
		try {
			String templateName=model.getTemplateName().replaceAll(",", "','");
			List<TemplateManager> managerList=templateManagerService.findAll(" from TemplateManager where templateName  in ('"+templateName+"')  order by recordTime desc");
            outSuccessJson(managerList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
		
		
	} 
	
	
	public void changeModel(){
		try {
			String filePkId=templateManagerService.changeKeyWord(model,pkId);		 
            outSuccessJson("替换成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败！");
		}
		
			
	}
	
	
	
	
}
