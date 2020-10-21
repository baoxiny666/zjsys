package com.tried.system.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.ConfigUtils;
import com.tried.common.LogFileUtil;
import com.tried.system.model.SystemLog;

/**
 * @Description 系统日志 管理
 * @author liuyw
 * @date 2016-04-02 10:27:53
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class SystemLoggerAction  extends BaseAction<SystemLog>{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SystemLoggerAction.class);  
	private InputStream inputStream;
	private String filename;
	/**
	 * @Description 系统日志列表
	 * @author liuyw
	 * @date 2016-04-02 10:27:53
	 * @version V1.0
	 */
	public void list(){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			//得到log日志的路径
			String path = ConfigUtils.getPropertyByName("log4j.properties", "log4j.appender.R.File");
			String basePath = path.substring(0,path.lastIndexOf("/"))+"/";
			File file = new File(basePath);
			if(file.isDirectory()){//判断是否是文件夹
				String[] dir = file.list();//获取所有文件的个数
				for(int i=0;i<dir.length;i++){
					String tempTime = LogFileUtil.getFileCreateTime(basePath+"/"+dir[i]);
					if(null!=model.getLogTime() && !model.getLogTime().isEmpty()){
						if(model.getLogTime().equals(tempTime.substring(0, 10))){
							Map<String,String> temp = new HashMap<String,String>();
							temp.put("id", ConfigUtils.random32());
							temp.put("fileName", dir[i]);
							temp.put("pathName", basePath+"/"+dir[i]);
							//获取文件的修改事件
							temp.put("logTime", tempTime);
							list.add(temp);
						}
					}else{
						Map<String,String> temp = new HashMap<String,String>();
						temp.put("id", ConfigUtils.random32());
						temp.put("fileName", dir[i]);
						temp.put("pathName", basePath+"/"+dir[i]);
						temp.put("logTime", tempTime);
						list.add(temp);
					}
				}
			}
			map.put("rows", list);
			outJsonData(map);
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	/**
	 * @Description 系统日志 下载
	 * @author liuyw
	 * @date 2016-04-02 10:27:53
	 * @version V1.0
	 */
	public String downLoad(){
		filename = model.getFileName();
		try{
			inputStream = new FileInputStream(model.getPathName());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "download";
	}
	
	public void readFile(){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			List<Map<String,Object>> list = null;
			/* 分页
			if(getSession().get(model.getPathName())==null){
				list = LogFileUtil.getTotalFileLineContent(model.getPathName());
				getSession().put(model.getPathName(), list);
			}else{
				list = (List<Map<String,Object>>)getSession().get(model.getPathName());
			}
			map.put("total", list.size());
			List<Map<String,Object>> pageList = new ArrayList<Map<String,Object>>();
			for(int i=rows*(page-1);i<((rows*page)>list.size()?list.size():(rows*page));i++){
				Map<String,Object> temp = list.get(i);
				pageList.add(temp);
			}
			map.put("rows", pageList);
			outJsonData(map);*/
			list = LogFileUtil.getTotalFileLineContent(model.getPathName());
			outJsonList(list);
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
	
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
