package com.tried.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tried.demo.model.util.CreateTool;
import org.tried.demo.model.util.Util;

import com.tried.base.action.BaseAction;
import com.tried.common.Page;
import com.tried.system.model.SystemTableModel;
import com.tried.system.service.SystemTableModelService;

/**
 * @Description 表/视图结构 管理
 * @author liuxd
 * @date 2015-09-21 19:47:17
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemTableModelAction extends BaseAction<SystemTableModel> {
	private static Logger logger = Logger.getLogger(SystemTableModelAction.class);  
	private static final long serialVersionUID = 1L;
	@Resource
	SystemTableModelService systemTableModelService;

	
	/**
	  * @Description 初始化树
	  * @author liuxd
	  * @date 2015-9-9 下午8:47:24
	  * @version V1.0
	 */
	public void initTree() {
		try {
			outSuccessJson(systemTableModelService.findAll("from SystemTableModel where parentId='0' or parentId='1'"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	
	/**
	 * @Description 分页显示表/视图结构
	 * @author liuxd
	 * @date 2015-09-21 19:47:17
	 * @version V1.0
	 */
	public void list() {
		try {
			if (null != model.getParentId() && !model.getParentId().isEmpty()) {
				this.condition = " and parentId like '" + model.getParentId() + "'";
			}
			this.condition = this.condition + "  order by sequence asc";
			outJsonData(systemTableModelService.findPage(new Page<SystemTableModel>(page, rows), "from SystemTableModel where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * @Description 添加表/视图结构
	 * @author liuxd
	 * @date 2015-09-21 19:47:17
	 * @version V1.0
	 */
	public void add() {
		try {
			
			model.setRecordUser(Util.getParam().get("author").toString());
			model.setRecordDate(new Date());
			systemTableModelService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除表/视图结构
	 * @author liuxd
	 * @date 2015-09-21 19:47:17
	 * @version V1.0
	 */
	public void del() {
		try {
			String[] ids = recordIdS.split(",");
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].isEmpty()) {
					sqls.add("delete from tried_system_table_model   where id='" + ids[i]+"'");
				}
			}
			systemTableModelService.dbBeatchSql(sqls);
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	/**
	 * @Description 修改表/视图结构
	 * @author liuxd
	 * @date 2015-09-21 19:47:17
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordUser("lxd");
			model.setRecordDate(new Date());
			systemTableModelService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}


	
	/**
	  * @Description 树节点拖动
	  * @author liuxd
	  * @date 2015-09-21 19:47:17
	  * @version V1.0
	 */
	public void drop(){
		try {
			List<String> sqls=new ArrayList<String>();
			String[] ids=recordIdS.split(",");
			for(int i=0;i<ids.length;i++){
				sqls.add(" update  tried_system_table_model  set parentId='"+model.getParentId()+"' where id='"+ids[i]+"'");
			}
			systemTableModelService.dbBeatchSql(sqls);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	/**
	  * @Description 排序
	  * @author liuxd
	  * @date 2015-9-10 下午3:41:35
	  * @version V1.0
	 */
	public void sort(){
		try {
			List<String> sqls=new ArrayList<String>();
			String[] ids=recordIdS.split(",");
			for(int i=0;i<ids.length;i++){
				sqls.add(" update  tried_system_table_model  set sequence="+i+" where id='"+ids[i]+"'");
			}
			systemTableModelService.dbBeatchSql(sqls);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}
	
	/**
	  * @Description 生成对象
	  * @author liuxd
	  * @date 2015-9-21 下午10:05:09
	  * @version V1.0
	 */
	public void createModel() {
		try {
			String[] ids=recordIdS.split(",");
			for(String id:ids){
				if(id.isEmpty()){
					continue;
				}else{
					SystemTableModel  tableInfo= systemTableModelService.getById(id);
					List<SystemTableModel>  ColumnInfo= systemTableModelService.findAll("from SystemTableModel where parentId='"+id+"'" );
					CreateTool.createModel(tableInfo,ColumnInfo,model.getTableIsCreate(),model.getPageType());
				}
			}
			outSuccessJson("执行成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("执行失败");
		}
	}
}
