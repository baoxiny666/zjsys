package com.tried.base.action;

import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.tried.common.ConfigUtils;
import com.tried.common.Page;
import com.tried.system.model.SystemUser;
import com.tried.system.service.SystemMemoryService;
import com.tried.system.service.SystemRoleService;
import com.tried.system.service.SystemUserService;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	private static Logger logger = Logger.getLogger(BaseAction.class);  
	protected String mxVirtualId = "";
	protected T model;
	protected Integer rows; 
	protected Integer page;
	protected String sort; 
	protected String order ;
	protected Page<T> PAGE =null;
	protected String pkObjName="";
	protected String pkId="";
	protected String recordId=""; //单条记录id
	protected String recordIdType;//记录类型
	protected String searchVal=""; //检索信息
 	protected String recordIdS=""; //多条记录id
 	protected String dataNum="";
	protected String objStartMonth="";
	protected String objEndMonth="";
	protected String objStartTime="";
	protected String objEndTime="";
	protected Map<String, Object> resultData = new HashMap<String, Object>();
	protected String orderColumn="";
	protected String condition=""; //附件检索条件
	protected String content="";//备注信息
	protected String rowsTitle="";//行标题
	protected String rowsData="";//行数据
	protected String memoryId; //内存id
	@Resource
	protected	SystemMemoryService systemMemoryService;
	@Resource
	SystemRoleService systemRoleService;
	@Resource
	SystemUserService systemUserService;
	
	public BaseAction() {
		try {
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class clazz = (Class) pt.getActualTypeArguments()[0];
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
		 * @Description 判断对象是否为空，不为空 返回 ture ；否则false
		 * @author liuxd
		 * @date 2018-12-17 下午8:07:20
		 * @version V1.0
	 */
	public  boolean strIsNotNull(String obj){
		if(obj!=null&&!obj.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	/**
	  * @Description 获取当前用户的角色代码拼接成符合jbpm的sql语句
	  * @author liuxd
	  * @date 2016-6-15 上午9:31:50
	  * @version V1.0
	
	 */
	public String getUserRoleForJbpm() throws Exception{
		String condtion="";
		Map<String,String>roleMap=systemRoleService.fingAllRoleBySystemUser(getCurrentUser());
		String groupName="";
		HashSet<String> roleSet=new HashSet<String>();
		
		for(String key:roleMap.keySet()){				
			 String [] deptAndRole=(roleMap.get(key)).split("@");				
			 groupName+=",'"+key+"'";		
			 if(!roleSet.contains(deptAndRole[1])){
				 groupName+=",'"+deptAndRole[1]+"'";		
			 } 
		}
		if(!groupName.isEmpty()){
			condtion= " and (assignee = '" + getCurrentUser().getId() + "' "
					 +" or  auditUser ='" + getCurrentUser().getId() + "'  "
					 +" or (groupName in ( "+groupName.substring(1)+")))";
		}else{
			condtion = " and (assignee = '" + getCurrentUser().getId() + "' "
					 +" or  auditUser ='" + getCurrentUser().getId() + "' )";
		}
		return condtion;
	}
	/**
	 * 获取个人或所属权限的个人sql语句
	 * @title: getUserDbRoleForJbpm
	 * @author: lyw
	 * @date : 2018-11-6 上午9:13:19
	 * @version: v1.0
	 */
	public String getUserDbRoleForJbpm() throws Exception{
		String condtion="";
		Map<String,String>roleMap=systemRoleService.fingAllRoleBySystemUser(getCurrentUser());
		String groupName="";
		HashSet<String> roleSet=new HashSet<String>();
		
		for(String key:roleMap.keySet()){				
			 String [] deptAndRole=(roleMap.get(key)).split("@");				
			 groupName+=",'"+key+"'";		
			 if(!roleSet.contains(deptAndRole[1])){
				 groupName+=",'"+deptAndRole[1]+"'";		
			 } 
		}
		if(!groupName.isEmpty()){
			condtion = " and (assignee = '" + getCurrentUser().getId() + "' "
					 + " or( groupName in ( "+groupName.substring(1)+")))";
		}else{
			condtion = " and (assignee = '" + getCurrentUser().getId() + "') ";
		}
		return condtion;
	}
	 
	public String getUserDbRoleForJbpm(SystemUser user) throws Exception{
		String condtion="";
		Map<String,String>roleMap=systemRoleService.fingAllRoleBySystemUser(user);
		String groupName="";
		HashSet<String> roleSet=new HashSet<String>();
		
		for(String key:roleMap.keySet()){				
			 String [] deptAndRole=(roleMap.get(key)).split("@");				
			 groupName+=",'"+key+"'";		
			 if(!roleSet.contains(deptAndRole[1])){
				 groupName+=",'"+deptAndRole[1]+"'";		
			 } 
		}
		if(!groupName.isEmpty()){
			condtion = " and (assignee = '" + user.getId() + "' "
					 + " or( groupName in ( "+groupName.substring(1)+")))";
		}else{
			condtion = " and (assignee = '" + user.getId() + "') ";
		}
		return condtion;
	}
	 
	public String getOrderColumn(){
		if(null!=sort&&null!=order && !sort.trim().isEmpty()){
			orderColumn= " order by "+sort+" "+order;
		}
		return orderColumn;
	}
	public T getModel() {
		return model;
	}

	public enum JsonType {
		Array, Object
	}

	 
	/**
	 * 取得上下文的请求对象
	 * 
	 * @return 上下文请求对象
	 */
	@JSON(serialize = false)
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得上下文的响应对象
	 * 
	 * @return 上下文响应对象
	 */
	@JSON(serialize = false)
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	/**
	 * 获取application Map
	 * 
	 * @return application Map
	 */
	@JSON(serialize = false)
	public Map getApplication() {
		return ActionContext.getContext().getApplication();
	}
	
	/**
	 * 获取session Map
	 * 
	 * @return session Map
	 */
	@JSON(serialize = false)
	public Map getSession() {
		return ActionContext.getContext().getSession();
	}

	/**
	 * 获取 parameter 中 name 的值
	 * 
	 * @param name
	 *            参数名
	 * @return 参数值
	 */
	@JSON(serialize = false)
	public String getParameter(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}

	/**
	 * 以xml格式的响应输出
	 * 
	 * @param xmlStr
	 *            xml字符串
	 * @throws Exception
	 */
	public void outXMLString(String xmlStr) {
		try {
			outString(xmlStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	  * @Description
	  * @author liuxd
	  * @date 2016-3-29 上午11:18:39
	  * @version V1.0
	 */
	public boolean checkSessionUser(){
		if(null!=getSession().get("user")){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 
	  * @Description 输出session失效
	  * @author liuxd
	  * @date 2016-3-29 上午11:15:49
	  * @version V1.0
	 */
	public void outNoLoginJson() {
		try {
			resultData.put(ConfigUtils.RETURN_STATUS, ConfigUtils.RETURN_STATUS_VALUE.NOLOGIN.toString());
			outJsonData(resultData);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 输出正确json信息
	 * 
	 * @author liuxd
	 * @date 2013-12-18 上午09:56:39
	 * @version V1.0
	 * @throws Exception
	 */
	public void outSuccessJson(Object msg) {
		try {
			//参数从左到右 -- STATUS
			resultData.put(ConfigUtils.RETURN_STATUS, ConfigUtils.RETURN_STATUS_VALUE.SUCCESS.toString());
			resultData.put(ConfigUtils.RETURN_DATA, msg);
			outJsonData(resultData);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
/**
  * @Description 输出警告信息
  * @author liuxd
  * @date 2016-8-19 上午10:09:33
  * @version V1.0
 */
	public void outAlertJson(Object msg) {
		try {
			resultData.put(ConfigUtils.RETURN_STATUS, ConfigUtils.RETURN_STATUS_VALUE.ALERT.toString());
			resultData.put(ConfigUtils.RETURN_DATA, msg);
			outJsonData(resultData);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
 
	/**
	 * 输出错误json信息
	 * 
	 * @author liuxd
	 * @date 2013-12-18 上午09:56:39
	 * @version V1.0
	 * @throws Exception
	 */
	public void outErrorJson(Object msg)  {
		try {
			resultData.put(ConfigUtils.RETURN_STATUS, ConfigUtils.RETURN_STATUS_VALUE.FAIL.toString());
			resultData.put(ConfigUtils.RETURN_DATA, msg);
			outJsonData(resultData);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 将对象以json数据格式输出
	 *  
	 * @param obj
	 *            对象
	 * @throws Exception
	 */
	public void outJsonData(Object obj) {
		try {
			JSONObject resultJson= JSONObject.fromObject(obj);
			JSONArray newRows=new JSONArray();
			if(resultJson.containsKey("rows")){
				JSONArray rows=resultJson.getJSONArray("rows");
				for(int i=0;i<rows.size();i++){
					Object json = new JSONTokener(rows.get(i).toString()).nextValue();
					if(json instanceof JSONObject){
						outString(JSONObject.fromObject(obj).toString());
						return;
					}else if (json instanceof JSONArray){{
						JSONArray manyTable=(JSONArray)rows.get(i);
						JSONObject row=new JSONObject();
						for(int j=0;j<manyTable.size();j++){
							JSONObject table=(JSONObject)manyTable.get(j);
							Iterator<String> it=table.keySet().iterator();
							while(it.hasNext()){
								String key=it.next();
								if(j==0){
									 row.put(key, (table.get(key)!=null)?table.get(key):"");
								}else{
									 row.put(key+(j+1), (table.get(key)!=null)?table.get(key):"");
								}
							}
						 }
						newRows.add(row);
						}
					}
				}
				resultJson.put("rows", newRows);
			}
		 
			outString(JSONObject.fromObject(resultJson).toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 
	  * @Description 无分页列表
	  * @author liuxd
	  * @date 2016-11-16 下午2:27:34
	  * @version V1.0
	 */
	public void outRowsData(Object obj) {
		try {
			    JSONArray newRows=new JSONArray();
				JSONArray rows=JSONArray.fromObject(obj);
				for(int i=0;i<rows.size();i++){
					Object json = new JSONTokener(rows.get(i).toString()).nextValue();
					if(json instanceof JSONObject){
						Map<String,Object> resultMap=new HashMap<String, Object>();
						resultMap.put("rows", obj);
						resultMap.put("total", 0);
						outString(JSONObject.fromObject(resultMap).toString());
						
						return;
					}else if (json instanceof JSONArray){{
						JSONArray manyTable=(JSONArray)rows.get(i);
						JSONObject row=new JSONObject();
						for(int j=0;j<manyTable.size();j++){
							JSONObject table=(JSONObject)manyTable.get(j);
							Iterator<String> it=table.keySet().iterator();
							while(it.hasNext()){
								String key=it.next();
								if(j==0){
									 row.put(key, (table.get(key)!=null)?table.get(key):"");
								}else{
									 row.put(key+(j+1), (table.get(key)!=null)?table.get(key):"");
								}
							  }
							}
						newRows.add(row);
						}
					}
				}
			Map<String,Object> resultMap=new HashMap<String, Object>();
			resultMap.put("rows", newRows);
			resultMap.put("total", 0);
			outString(JSONObject.fromObject(resultMap).toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 将数组，List以json数据格式输出
	 * 
	 * @param array
	 *            数组
	 * @throws Exception
	 */
	public void outJsonList(Object array) {
		try {
			outString(JSONArray.fromObject(array).toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void outString(String str) throws Exception {
		getResponse().setContentType("text/html");
		getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = getResponse().getWriter();
		out.write(str);
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @return
	 */
	protected SystemUser getCurrentUser() {
		return (SystemUser) ActionContext.getContext().getSession().get("user");
	}
	
	protected List<SystemUser> getUsersByWorkId(String workId) throws Exception{
		List<SystemUser> userList = systemUserService.findAll("from SystemUser su where su.workId='"+workId+"'");
		return userList;
	}
	/**
	 * 获取登录用户Ip
	 * @return IP
	 */
	protected String loginIp(){
		String ip = getRequest().getHeader("x-forwarded-for");
		if(ip == null || ip.length()==0|| "unknown".equalsIgnoreCase(ip)){
			ip = getRequest().getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = getRequest().getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = getRequest().getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = getRequest().getRemoteAddr();
		}
		return ip;
	}
	
	public String getMxVirtualId() {
		return mxVirtualId;
	}

	public void setMxVirtualId(String mxVirtualId) {
		this.mxVirtualId = mxVirtualId;
	}




	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRecordIdS() {
		return recordIdS;
	}

 
	public void setRecordIdS(String recordIdS) {
		this.recordIdS = recordIdS;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getPkObjName() {
		return pkObjName;
	}
	public void setPkObjName(String pkObjName) {
		this.pkObjName = pkObjName;
	}
	public String getDataNum() {
		return dataNum;
	}
	public void setDataNum(String dataNum) {
		this.dataNum = dataNum;
	}
	public String getObjStartTime() {
		return objStartTime;
	}
	public void setObjStartTime(String objStartTime) {
		this.objStartTime = objStartTime;
	}
	public String getObjEndTime() {
		return objEndTime;
	}
	public void setObjEndTime(String objEndTime) {
		this.objEndTime = objEndTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRowsTitle() {
		return rowsTitle;
	}
	public void setRowsTitle(String rowsTitle) {
		this.rowsTitle = rowsTitle;
	}
	public String getRowsData() {
		return rowsData;
	}
	public void setRowsData(String rowsData) {
		this.rowsData = rowsData;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}

	public String getMemoryId() {
		return memoryId;
	}

	public void setMemoryId(String memoryId) {
		this.memoryId = memoryId;
	}

	public String getRecordIdType() {
		return recordIdType;
	}

	public void setRecordIdType(String recordIdType) {
		this.recordIdType = recordIdType;
	}

	public String getObjStartMonth() {
		return objStartMonth;
	}

	public void setObjStartMonth(String objStartMonth) {
		this.objStartMonth = objStartMonth;
	}

	public String getObjEndMonth() {
		return objEndMonth;
	}

	public void setObjEndMonth(String objEndMonth) {
		this.objEndMonth = objEndMonth;
	}
 
 
}
