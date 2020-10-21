package com.tried.system.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.system.model.FileManager;
import com.tried.system.model.FileManagerDel;
import com.tried.system.model.TemplateManager;
import com.tried.system.service.FileManagerDelService;
import com.tried.system.service.FileManagerService;
import com.tried.system.service.TemplateManagerService;


/**
 * @Description 文件存储表 服务接口实现
 * @author liuxd
 * @date 2016-05-30 09:36:51
 * @version V1.0
 */
@Service
public class FileManagerServiceImpl extends BaseServiceImpl<FileManager> implements FileManagerService {
	@Resource
	FileManagerDelService fileManagerDelService;
	@Resource
	TemplateManagerService templateManagerService;
	@Override
	public boolean add(String columnName, String fileId, String pkId, String modelID) throws Exception {
		 boolean flag=true;
		if(null==columnName||columnName.isEmpty()){  new Exception("字段名不能为空"); }
		if(null==pkId||pkId.isEmpty()){ new Exception("关联主键不能为空");}
		if(null==modelID||modelID.isEmpty()){ new Exception("关联Id不能为空");}
	  
		List<FileManager> fileManagerList = this.findAll("from FileManager where  pkColumn='" + columnName + "' and (pkId='" + pkId + "' OR pkId='"
				+ modelID + "') ");
		for (FileManager fileManager : fileManagerList) {
			if (fileId.contains(fileManager.getId())) {
				if(!modelID.equals(fileManager.getPkId())){
					flag=false;
				} 
					fileManager.setPkId(modelID);
					this.update(fileManager);
			} else {
				//不做物理物理删除
				FileManagerDel fileManagerDel=new FileManagerDel();
				fileManagerDel.setBackTime(new Date());
				fileManagerDel.setFilename(fileManager.getFilename());
				fileManagerDel.setFileSize(fileManager.getFileSize());
				fileManagerDel.setDesciption("add方法删除"+fileManager.getDesciption());
				fileManagerDel.setFileSavePath(fileManager.getFileSavePath());
				fileManagerDel.setFileSaveName(fileManager.getFileSaveName());
				fileManagerDel.setPkId(fileManager.getPkId());
				fileManagerDel.setPkTable(fileManager.getPkTable());
				fileManagerDel.setPkColumn(fileManager.getPkColumn());
				fileManagerDel.setRecordUser(fileManager.getRecordUser());
				fileManagerDel.setRecordTime(fileManager.getRecordTime());
				fileManagerDel.setAppPkId(fileManager.getAppPkId());
				fileManagerDelService.add(fileManagerDel);
				this.delete(fileManager);
				flag=false;
			}
		}
		return flag;
	}

	@Override
	public void delFile(String pkId, String pkColumn, String fileName) throws Exception {
		
		if(null==pkId||pkId.isEmpty()){return  ;}
		if(null==pkColumn||pkColumn.isEmpty()){return  ;}
		if(null==fileName||fileName.isEmpty()){return  ;}
		List<FileManager> list = this.findAll("from FileManager where pkId='" + pkId + "' and pkColumn='" + pkColumn + "' and  filename='"
				+ fileName + "'");
		
		
		for (FileManager fileManager : list) {
			//不做物理物理删除
//			File delFile = new File(fileManager.getFileSavePath() + fileManager.getFileSaveName());
//			if (delFile.exists()) {
//				delFile.delete();
//			}
			FileManagerDel fileManagerDel=new FileManagerDel();
			fileManagerDel.setBackTime(new Date());
			fileManagerDel.setFilename(fileManager.getFilename());
			fileManagerDel.setFileSize(fileManager.getFileSize());
			fileManagerDel.setDesciption("delFile方法删除"+fileManager.getDesciption());
			fileManagerDel.setFileSavePath(fileManager.getFileSavePath());
			fileManagerDel.setFileSaveName(fileManager.getFileSaveName());
			fileManagerDel.setPkId(fileManager.getPkId());
			fileManagerDel.setPkTable(fileManager.getPkTable());
			fileManagerDel.setPkColumn(fileManager.getPkColumn());
			fileManagerDel.setRecordUser(fileManager.getRecordUser());
			fileManagerDel.setRecordTime(fileManager.getRecordTime());
			fileManagerDel.setAppPkId(fileManager.getAppPkId());
			fileManagerDelService.add(fileManagerDel);
			this.delete(fileManager.getId());
		}
	}

	public void delColumnFile(String pkId, String pkColumn) throws Exception {
		if(null==pkId||pkId.isEmpty()){return  ;}
		if(null==pkColumn||pkColumn.isEmpty()){return  ;}
		
		List<FileManager> list = this.findAll("from FileManager where pkId='" + pkId + "' and pkColumn='" + pkColumn + "'");
		for (FileManager fileManager : list) {
//			File delFile = new File(fileManager.getFileSavePath() + fileManager.getFileSaveName());
//			if (delFile.exists()) {
//				delFile.delete();
//			}
			//不做物理物理删除
			FileManagerDel fileManagerDel=new FileManagerDel();
			fileManagerDel.setBackTime(new Date());
			fileManagerDel.setFilename(fileManager.getFilename());
			fileManagerDel.setFileSize(fileManager.getFileSize());
			fileManagerDel.setDesciption("delColumnFile方法删除"+fileManager.getDesciption());
			fileManagerDel.setFileSavePath(fileManager.getFileSavePath());
			fileManagerDel.setFileSaveName(fileManager.getFileSaveName());
			fileManagerDel.setPkId(fileManager.getPkId());
			fileManagerDel.setPkTable(fileManager.getPkTable());
			fileManagerDel.setPkColumn(fileManager.getPkColumn());
			fileManagerDel.setRecordUser(fileManager.getRecordUser());
			fileManagerDel.setRecordTime(fileManager.getRecordTime());
			fileManagerDel.setAppPkId(fileManager.getAppPkId());
			fileManagerDelService.add(fileManagerDel);
			this.delete(fileManager.getId());
		}
	}

	@Override
	public void delAllFile(String pkId) throws Exception {
		if(null==pkId||pkId.isEmpty()){return ;}
		List<FileManager> list = this.findAll("from FileManager where pkId='" + pkId + "'");
		for (FileManager fileManager : list) {
			//不做物理物理删除
			FileManagerDel fileManagerDel=new FileManagerDel();
			fileManagerDel.setBackTime(new Date());
			fileManagerDel.setFilename(fileManager.getFilename());
			fileManagerDel.setFileSize(fileManager.getFileSize());
			fileManagerDel.setDesciption("delAllFile方法删除"+fileManager.getDesciption());
			fileManagerDel.setFileSavePath(fileManager.getFileSavePath());
			fileManagerDel.setFileSaveName(fileManager.getFileSaveName());
			fileManagerDel.setPkId(fileManager.getPkId());
			fileManagerDel.setPkTable(fileManager.getPkTable());
			fileManagerDel.setPkColumn(fileManager.getPkColumn());
			fileManagerDel.setRecordUser(fileManager.getRecordUser());
			fileManagerDel.setRecordTime(fileManager.getRecordTime());
			fileManagerDel.setAppPkId(fileManager.getAppPkId());
			fileManagerDelService.add(fileManagerDel);
			this.delete(fileManager.getId());
		}
	}

	@Override
	public List<FileManager> fileInfoList(String pkId) throws Exception {
		if(null==pkId||pkId.isEmpty()){return null ;}
		return this.findAll("from FileManager where pkId='" + pkId + "' order by recordTime asc");
	}

	@Override
	public String findFilePath(String pkId, String pkColumn, String fileName) throws Exception {
		
		String path = "";
		if(null==pkId||pkId.isEmpty()){return path ;}
		if(null==pkColumn||pkColumn.isEmpty()){return path ;}
		if(null==fileName||fileName.isEmpty()){return path ;}
		List<FileManager> fileManagerList = this.findAll("from FileManager where pkId='" + pkId + "' and pkColumn='" + pkColumn
				+ "' and filename='" + fileName + "' ");
		for (FileManager fileManager : fileManagerList) {
			path = fileManager.getFileSavePath() + fileManager.getFileSaveName();
		}
		return path;
	}

	@Override
	public String findFilePath(String pkId, String pkColumn) throws Exception {
		String path = "";
		if(null==pkId||pkId.isEmpty()){return path ;}
		if(null==pkColumn||pkColumn.isEmpty()){return path ;}
		List<FileManager> fileManagerList = this.findAll("from FileManager where pkId='" + pkId + "' and pkColumn='" + pkColumn + "' ");
		for (FileManager fileManager : fileManagerList) {
			path = fileManager.getFileSavePath() + fileManager.getFileSaveName();
		}
		return path;
	}

	@Override
	public List<FileManager> fileInfoList(String pkId, String pkColumn) throws Exception {
		 
		if(null==pkId||pkId.isEmpty()){return null ;}
		if(null==pkColumn||pkColumn.isEmpty()){return null ;}
		return this.findAll("from FileManager where pkId='" + pkId + "' and pkColumn ='"+pkColumn+"' order by recordTime asc");	 
	}

	@Override
	public List<FileManager> findLogFilePath(String process_id, String pkColumn)throws Exception {
		List<FileManager> fileList = new ArrayList<FileManager>();
		String sql ="select tfm.id,filename,fileSize,desciption,fileSavePath,fileSaveName,pkId,pkColumn,tfm.recordUser,tfm.recordTime " +
				" from tried_file_manager tfm,JBPM_TASK_LOG jtl " +
				" where tfm.pkId = jtl.id  and tfm.pkColumn='"+pkColumn+"'" +
				" and jtl.process_id like '"+process_id+"%'";
		List<Object[]> res = this.dbFindList(sql, null);
		for(Object[] obj : res){
			FileManager fileManager = new FileManager();
			fileManager.setId(obj[0]==null?"":obj[0].toString());
			fileManager.setFilename(obj[1]==null?"":obj[1].toString());
			fileManager.setFileSize(Double.parseDouble(obj[2]==null?"0.0":obj[2].toString()));
			fileManager.setDesciption(obj[3]==null?"":obj[3].toString());
			fileManager.setFileSavePath(obj[4]==null?"":obj[4].toString());
			fileManager.setFileSaveName(obj[5]==null?"":obj[5].toString());
			fileManager.setPkId(obj[6]==null?"":obj[6].toString());
			fileManager.setPkColumn(obj[7]==null?"":obj[7].toString());
			fileManager.setRecordUser(obj[8]==null?"":obj[8].toString());
			fileManager.setRecordTime(obj[9]==null?null:ConfigUtils.stringToData(obj[9].toString()));
			fileList.add(fileManager);
		}
		return fileList;
	}

	@Override
	public FileManager findFileByType(String contractType) throws Exception {
		FileManager manager = new FileManager();
		String sql = "select m.filename,m.fileSavePath,m.fileSaveName " +
				" from tried_file_manager m " +
				" where m.pkId=( select top(1) tcp.id " +
				" from tried_contract_panel tcp " +
				" where tcp.contractType='"+contractType+"' " +
				" order by tcp.recordTime desc)";
		
		List<Object[]> res = this.dbFindList(sql, null);
		for(Object[] obj : res){
			manager.setFilename(obj[0]==null?"":obj[0].toString());
			manager.setFileSavePath(obj[1]==null?"":obj[1].toString());
			manager.setFileSaveName(obj[2]==null?"":obj[2].toString());
		}
		return manager;
	}

	@Override
	public FileManager fileLastSignName(String recordUser, String pkColumn) throws Exception {
		List<FileManager> fileManagerList = this.findAll("from FileManager where pkId = '"+recordUser+"' and pkColumn = '"+pkColumn+"' order by recordTime desc");
		if(fileManagerList.size()>0){
			return fileManagerList.get(0);
		}
		return null;
	}

	@Override
	public FileManager getBypkId(String pkId) throws Exception {
		List<FileManager> list = this.fileInfoList(pkId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<FileManager> findByPkIds(String pkIds, String pkColumn) throws Exception {
		List<FileManager> fileManagerList = this.findAll("from FileManager where pkId in '("+pkIds+")' and pkColumn = '"+pkColumn+"'");
		return fileManagerList;
	}

	@Override
	public void copyModelAndFile(FileManager cardWord) throws Exception {
		 String destPath = ConfigUtils.getPropertyByName("config.properties", "uppath") + "/" + ConfigUtils.dataToSimpleString(new Date())
					+ "/" + ConfigUtils.getUUID()+"/";
		 File destFilePath = new File(destPath);
		 if (!destFilePath.exists()) {
			 destFilePath.mkdirs();
		 }
		 FileChannel inputChannel = null;    
	     FileChannel outputChannel = null;  
	     String sourceFile=cardWord.getFileSavePath()+cardWord.getFileSaveName();
		 String destFile=destPath+cardWord.getFileSaveName();
		 try{
			 inputChannel = new FileInputStream(sourceFile).getChannel();
			 outputChannel = new FileOutputStream(destFile).getChannel();
			 outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 inputChannel.close();
		     outputChannel.close();
		 }
		 cardWord.setFileSavePath(destPath);//更新为新的路径
		 this.add(cardWord);
	}

	@Override
	public FileManager findCommonCard() throws Exception {
		
		TemplateManager templateManager=templateManagerService.getFirstRecordByField("from TemplateManager where templateName ='检测任务卡通用模板' AND templateType='任务卡通用模板'");
		if(templateManager!=null){
			List<FileManager> list = this.findAll("from FileManager where pkId='"+templateManager.getId()+"' and pkColumn='templateFile' order by recordTime desc");
			if(list.size()>0){
				return list.get(0);
			}
		}
		return null;
	}


	
 

	/**
	 * 根据模板名字获取模板对象
		 * @Description 
		 * @author liuxd
		 * @date 2019-4-3 上午11:19:03
		 * @version V1.0
	 * @throws Exception 
	 */
	public List<FileManager> findTempletFile(String fileName,String modelName) throws Exception{
		List<FileManager>  fileManagerList=new ArrayList<FileManager>();
		TemplateManager templateManager=templateManagerService.getFirstRecordByField("from TemplateManager where templateName ='"+fileName+"' AND templateType='"+modelName+"'");
		if(templateManager!=null){
			fileManagerList = this.findAll("from FileManager where pkId='"+templateManager.getId()+"' and pkColumn='templateFile' order by recordTime desc");
		}
		return fileManagerList;
	}

	@Override
	public FileManager findTakeSignManager(String pkId, String fileName,String pkColumn) throws Exception {
		List<FileManager> list = this.findAll("from FileManager where pkId ='"+pkId+"'  and filename ='"+fileName+"' and pkColumn = '"+pkColumn+"'");
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

 

 
	
	
}
