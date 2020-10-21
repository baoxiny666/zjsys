package com.tried.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tried.demo.model.util.ReverseTool;

import com.tried.base.action.BaseAction;
import com.tried.system.model.SystemReverse;
import com.tried.system.model.SystemTableModel;
import com.tried.system.service.SystemReverseService;

/**
 * @Description 逆向工具 管理
 * @author liuxd
 * @date 2016-01-29 15:54:08
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemReverseAction extends BaseAction<SystemReverse> {
	private static Logger logger = Logger.getLogger(SystemReverseAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemReverseService systemReverseService;

	/**
	 * @Description 分页显示逆向工具
	 * @author liuxd
	 * @date 2016-01-29 15:54:08
	 * @version V1.0
	 */
	public void list() {
		try {
			Map<String, Object> result=new HashMap<String, Object>();
			result.put("rows", systemReverseService.findTableView(model.getTable_name()));
			outJsonData(result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	public void createModel(){
		
		try {
			String[] tableNames=recordIdS.split(",");
			for(String tableName:tableNames){
				if(tableName.isEmpty()){
					continue;
				}else{
 					SystemTableModel  tableInfo= systemReverseService.findModelInfo(tableName);
 					tableInfo.setPackName(model.getPageName());
 					List<SystemTableModel>  ColumnInfo= systemReverseService.findModelColumnInfo(tableName,tableInfo.getObjectType());
 					ReverseTool.createReverse(tableInfo,ColumnInfo,model.getPageType());
				}
			}
		outSuccessJson("执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			outErrorJson("执行失败");
		}
	}


	 
}
