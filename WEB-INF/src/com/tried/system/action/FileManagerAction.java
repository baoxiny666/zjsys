package com.tried.system.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tried.base.action.BaseAction;
import com.tried.common.ConfigUtils;
import com.tried.common.ExcelCommon;
import com.tried.common.Page;
import com.tried.common.WordUtil;
import com.tried.system.model.FileManager;
import com.tried.system.service.FileManagerService;

@Controller
@Scope("prototype")
/**
 * @Description  文件上传管理类
 * @author liuxd
 * @date 2016-5-29 下午10:39:15
 * @version V1.0
 */
public class FileManagerAction extends BaseAction<FileManager> {
	private static final long serialVersionUID = 3662753211192306778L;
	private static Logger logger = Logger.getLogger(FileManagerAction.class);
	private File file;
	private String fileFileName;
	private String fileContentType;
    private boolean flag=true;//文件上传个数 true:允许多个 false:允许一个
    private InputStream inputStream; 
	@Resource
	FileManagerService fileManagerService;

	/**
	 * @Description 上传文件
	 * @author liuxd
	 * @date 2016-5-26 下午2:50:37
	 * @version V1.0
	 */
	public void save() {
		try{
		String savePath = ConfigUtils.getPropertyByName("config.properties", "uppath") + "/" + ConfigUtils.dataToSimpleString(new Date())
				+ "/" + ConfigUtils.getUUID()+"/";
		File filePath = new File(savePath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		if (fileFileName!=null&&fileFileName.lastIndexOf(".") >= 0) {
			String extName = fileFileName.substring(fileFileName.lastIndexOf("."));
			if (extName.toUpperCase().equals(".EXE") || extName.toUpperCase().equals(".BAT")) {
				outErrorJson(fileFileName + "该类型文件禁止上传");
				logger.error("用户:"+getCurrentUser().getUserName()+"；文件："+ fileFileName+" 禁止上传");
				return;
			} else {
				
				File newFile = new File(savePath + ConfigUtils.getUUID()+extName);
				file.renameTo(newFile);
				model.setFilename(fileFileName==null?"-":fileFileName);
				model.setFileSize(Double.valueOf(newFile.length()));
				model.setFileSavePath(savePath);
				model.setFileSaveName(newFile.getName());
				model.setRecordTime(new Date());
				model.setRecordUser(getCurrentUser().getId());
				if(model.getPkId()==null||model.getPkId().isEmpty()){
					model.setPkId(ConfigUtils.getUUID());
				}
				fileManagerService.add(model);
				Map<String,String> result=new HashMap<String,String>();
				result.put("pkId", model.getPkId());
				result.put("fileId", model.getId());
				result.put("fileName", fileFileName==null?"-":fileFileName);
				outSuccessJson(result);
				logger.info("用户:"+getCurrentUser().getUserName()+"；文件："+ fileFileName+" 上传成功");
			}
		} else {
			logger.error("用户:"+getCurrentUser().getUserName()+"；文件："+ fileFileName+" 文件无后缀名");
			outErrorJson("文件无后缀名或文件为空！");
		}
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson(e.getMessage());
		}
	}
	/**
	 * @Description 替换文件
	 * @author liuxd
	 * @date 2016-5-26 下午2:50:37
	 * @version V1.0
	 */
	public void replaceFile() {
		try{
		String savePath = ConfigUtils.getPropertyByName("config.properties", "uppath") + "/" + ConfigUtils.dataToSimpleString(new Date())
				+ "/" + ConfigUtils.getUUID()+"/";
		File filePath = new File(savePath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		if (fileFileName.lastIndexOf(".") >= 0) {
			String extName = fileFileName.substring(fileFileName.lastIndexOf("."));
			if (extName.toUpperCase().equals(".EXE") || extName.toUpperCase().equals(".BAT")) {
				outErrorJson(fileFileName + "该类型文件禁止上传");
				logger.error("用户:"+getCurrentUser().getUserName()+"；文件："+ fileFileName+" 禁止上传");
				return;
			} else {
				FileManager fm = fileManagerService.getById(model.getId());//原来文件
				File newFile = new File(savePath + ConfigUtils.getUUID()+extName);
				file.renameTo(newFile);
				model.setFilename(fileFileName);
				model.setFileSize(Double.valueOf(newFile.length()));
				model.setFileSavePath(savePath);
				model.setFileSaveName(newFile.getName());
				model.setRecordTime(new Date());
				model.setRecordUser(getCurrentUser().getId());
				model.setPkId(fm.getPkId());//
				model.setPkColumn(fm.getPkColumn());
				fileManagerService.add(model);
				fm.setPkColumn(fm.getPkColumn()+"_replace");
				fm.setRecordTime(new Date());//记录更改日期
				fileManagerService.update(fm);//将原来报告后缀加上“_replace”
				outSuccessJson("替换成功");
				logger.info("用户:"+getCurrentUser().getUserName()+"；文件："+ fileFileName+" 替换成功");
			}
		} else {
			logger.error("用户:"+getCurrentUser().getUserName()+"；文件："+ fileFileName+" 文件无后缀名");
			outErrorJson("文件无后缀名");
		}
		}catch(Exception e){
			logger.error(e.getMessage());
			outErrorJson(e.getMessage());
		}
	}
	
	/**
	 * @Description 批量上传文件
	 * @author liuyw
	 * @date 2016-5-26 下午2:50:37
	 * @version V1.0
	 */
	public void batchSave(){
		try{
			String savePath = ConfigUtils.getPropertyByName("config.properties", "uppath") + "/" + ConfigUtils.dataToSimpleString(new Date())
					+ "/" + ConfigUtils.getUUID()+"/";
			File filePath = new File(savePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			
			String[] tempFileName = fileFileName.split(",");
			List<Map<String,String>> fileResList = new ArrayList<Map<String,String>>();
			String tempPkId =  ConfigUtils.getUUID();
			for(String tempName : tempFileName){
				if (tempName.lastIndexOf(".") >= 0) {
					String extName = tempName.substring(tempName.lastIndexOf("."));
					if (extName.toUpperCase().equals(".EXE") || extName.toUpperCase().equals(".BAT")) {
						outErrorJson(tempName + "该类型文件禁止上传");
						logger.error("用户:"+getCurrentUser().getUserName()+"；文件："+ tempName+" 禁止上传");
						return;
					} else {
						File newFile = new File(savePath + ConfigUtils.getUUID()+extName);
						file.renameTo(newFile);
						model.setFilename(tempName);
						model.setFileSize(Double.valueOf(newFile.length()));
						model.setFileSavePath(savePath);
						model.setFileSaveName(newFile.getName());
						model.setRecordTime(new Date());
						model.setRecordUser(getCurrentUser().getId());
						model.setPkId(tempPkId);
						/*if(model.getPkId()==null||model.getPkId().isEmpty()){
							model.setPkId(ConfigUtils.getUUID());
						}*/
						fileManagerService.add(model);
						Map<String,String> result=new HashMap<String,String>();
						result.put("pkId", model.getPkId());
						result.put("fileId", model.getId());
						result.put("fileName", tempName);
						fileResList.add(result);
					}
				} else {
					logger.error("用户:"+getCurrentUser().getUserName()+"；文件："+ fileFileName+" 文件无后缀名");
					outErrorJson("文件无后缀名");
				}
			}
			outSuccessJson(fileResList);
			logger.info("用户:"+getCurrentUser().getUserName()+"-----批量文件上传成功");
			}catch(Exception e){
				logger.error(e.getMessage());
				outErrorJson(e.getMessage());
			}
	}
	
	
	/**
	  * @Description 文件列表
	  * @author liuxd
	  * @date 2016-5-30 下午1:03:30
	  * @version V1.0
	 */
	public void list(){
		
		try {
			if(null!=model.getPkColumn()&&!model.getPkColumn().isEmpty()){
			outSuccessJson(fileManagerService.fileInfoList(recordId,model.getPkColumn()));
			}
			else{
				outSuccessJson(fileManagerService.fileInfoList(recordId));
			}
		//	logger.info("用户:"+getCurrentUser().getUserName()+"进行检索文件");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson(e.getMessage());
		}
	}
	
	
	/**
	 * 查找附件list
	 */
	public void fileList(){

		try {
			if (null != model.getPkId() && !model.getPkId().isEmpty()) {
				this.condition = " and pkId ='"+model.getPkId()+"'";
			}
			if (null != model.getPkColumn() && !model.getPkColumn().isEmpty()) {
				this.condition += " and pkColumn ='"+model.getPkColumn()+"'";
			}
			this.condition = this.condition + "  " + this.getOrderColumn();
			outJsonData(fileManagerService.findPage(new Page<FileManager>(page, rows), "from FileManager where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
		
		
		
		
	}
	
	
	
	/**
	  * @Description 删除文件
	  * @author liuxd
	  * @date 2016-5-30 下午1:40:38
	  * @version V1.0
	 */
	public void delSimpleFile() {
		try {
			fileManagerService.delFile(recordId,model.getPkColumn(),model.getFilename());
			outSuccessJson("删除成功");
			logger.info("用户:"+getCurrentUser().getUserName()+" 删除文件"+model.getFilename());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson(e.getMessage());
		}
		
	}
	/**
	  * @Description 下载文件
	  * @author liuxd
	  * @date 2016-6-1 上午11:19:09
	  * @version V1.0
	 * @throws Exception 
	 */
	public String downFile() throws Exception{
		String filePath="";
		if(model.getId()!=null&&!model.getId().isEmpty()){
			 FileManager fileManager=fileManagerService.getById(model.getId());
			 filePath=fileManager.getFileSavePath()+fileManager.getFileSaveName();
			 model.setFilename(fileManager.getFilename());
		}
		else{
			 filePath=fileManagerService.findFilePath(model.getPkId(), model.getPkColumn(), model.getFilename());
		}
		File file=new File(filePath);
		if(file.exists()){
			inputStream=new FileInputStream(filePath);
			String systemcode = SystemUtils.FILE_ENCODING;
			String realName=model.getFilename();			
			//fileFileName= URLEncoder.encode(model.getFilename(), "UTF-8");
			fileFileName=new String(realName.getBytes(systemcode), "ISO-8859-1");
		}
		return "download";
	}
	public String downPdfFile() throws Exception{ 
		String pdfPath="";
		String fileName="";
		FileManager fileDoc=fileManagerService.getFirstRecordByField(" from FileManager where pkId='"+model.getPkId()+"' and pkColumn='"+model.getPkColumn()+"' order by recordTime desc ");
		if(fileDoc!=null){
			String saveName = fileDoc.getFileSaveName();
			String suffix = saveName.substring(saveName.lastIndexOf(".") + 1); 
			if("docx".equals(suffix)){//是word直接转换  其他格式直接下载
				pdfPath=fileDoc.getFileSavePath()+ConfigUtils.getUUID()+".pdf";
				fileName=fileDoc.getFilename().replace(".docx", "")+".pdf";
				String srcPath=fileDoc.getFileSavePath() + fileDoc.getFileSaveName();
				WordUtil.word2PDF(srcPath, pdfPath);		
			}else{//其他格式直接下载
				fileName = fileDoc.getFilename(); 
				pdfPath = fileDoc.getFileSavePath() + fileDoc.getFileSaveName();
			}
		}
		inputStream=new FileInputStream(pdfPath);
		System.out.println("输出PDF路径"+pdfPath);
		String systemcode = SystemUtils.FILE_ENCODING;		
		fileFileName=new String(fileName.getBytes(systemcode), "ISO-8859-1");				
		return "download";	
	}
	/**
	 * 下载临时文件
	 * @return
	 * @throws Exception
	 */
	public String downTempFile() throws Exception{
		String filePath = ConfigUtils.getPropertyByName("config.properties", "tempfile") + File.separator + ConfigUtils.dataToSimpleString(new Date())+File.separator+recordId;
		File file = new File(filePath);
		if(file.exists()){
			String realName = model.getFilename()+"."+ConfigUtils.getExtensionName(recordId);
			inputStream = new FileInputStream(filePath);
			String systemcode = SystemUtils.FILE_ENCODING;
			fileFileName = new String(realName.getBytes(systemcode),"ISO-8859-1");
		}
		return "download";
	}
	
	/**
	  * @Description  铁水质量看板导出excel
	  * @author liuxd
	  * @date  2017-11-13 下午9:43:02
	  * @version V1.0
	 */
	public void exportExcel(){
		try {
			 Map<String,String> headMap=new LinkedHashMap<String, String>();
			 JSONObject headObj=JSONObject.fromObject( java.net.URLDecoder.decode(getRowsTitle(),"UTF-8"));
			 Iterator<String> headIt= headObj.keySet().iterator();
			 while(headIt.hasNext()){
				 String key=headIt.next();
				headMap.put(key, headObj.getString(key));
			 }
			 List<Map<String,String>> dataList=new LinkedList<Map<String,String>>();
			 JSONArray rowList=JSONArray.fromObject( java.net.URLDecoder.decode(getRowsData(),"UTF-8"));
			
		     for(int i=0;i<rowList.size();i++){
		    	 JSONObject row=rowList.getJSONObject(i);
		    	 Iterator<String> rowIt= row.keySet().iterator();
		    	 Map<String,String> dataMap=new HashMap<String, String>();
		    	 while(rowIt.hasNext()){
		    		String key=(String)rowIt.next();
		    		String value=row.get(key).toString();
		    		dataMap.put(key, value);
		    	}
		    	 dataList.add(dataMap);
		     }
		 
			String filePath= new ExcelCommon().creatExcel(model.getExcelTitle(),model.getExcelTitle(),headMap,dataList);
			File file=new File(filePath);
			outSuccessJson(file.getName());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("导出失败");
		}
	}
	
	
	public String reportDownFile() throws Exception{
		String filePath="";
		if(model.getId()!=null&&!model.getId().isEmpty()){
			 FileManager fileManager=fileManagerService.getById(model.getId());
			 filePath=fileManager.getFileSavePath()+fileManager.getFileSaveName();
			 model.setFilename(fileManager.getFilename());
		}
		else{
			 filePath=fileManagerService.findFilePath(model.getPkId(), model.getPkColumn(), model.getFilename());
		}
		File file=new File(filePath);
		if(file.exists()){
			String baseReportPath = ConfigUtils.getPropertyByName("config.properties", "userFilePath") + "/"+getCurrentUser()+"/reportFiles/";
			inputStream=new FileInputStream(filePath);
			fileFileName= URLEncoder.encode(model.getFilename(), "utf-8");
		}
		return "download";
	}
	
	
	
	
	
	/**
	 * 显示图片列表
		 * @Description 
		 * @author liuxd
		 * @date 2019-3-18 下午1:10:25
		 * @version V1.0
	 */
	public void showImg(){
	try {
		List<FileManager> fileManagerList=fileManagerService.fileInfoList(model.getPkId(), model.getPkColumn());
		for(FileManager file:fileManagerList){
			String rootDir=ConfigUtils.getPropertyByName("config.properties", "signImg");
			if(file!=null&&file.getFileSavePath()!=null&&file.getFileSaveName()!=null){
				file.setFileSavePath(file.getFileSavePath().replace(rootDir, ""));
			}
		}
		outSuccessJson(fileManagerList);
	} catch (Exception e) {
		logger.error(e.getMessage());
		outErrorJson(true);
	}
	
}
	
	public void deleteFileManager(){
		try {
			List<FileManager> filelist=fileManagerService.findAll(" from FileManager where pkId='"+model.getPkId()+"' and pkColumn='"+model.getPkColumn()+"'");
			for(FileManager manager:filelist){
				fileManagerService.delete(manager.getId());
			}
			outSuccessJson("删除成功！");
		} catch (Exception e) {
			outErrorJson("失败");
		}
		
	
	
		
	}
	/**
	 * 根据fileManager、Id获取文件信息
	 */
	 
	public void singleFile(){
		
		try {
			
			if(strIsNotNull(recordId)){
				 logger.info("用户:"+getCurrentUser().getUserName()+"进行检索文件");
				outSuccessJson(fileManagerService.getById(recordId));
			}else{
				logger.error("检索条件为空");
				outErrorJson("检索条件为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson(e.getMessage());
		}
	}
	
	public String getFileFileName() {
		return fileFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

 

 

}
