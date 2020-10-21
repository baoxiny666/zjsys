package com.tried.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.FileManager;
import com.tried.system.model.FileManagerDel;
import com.tried.system.service.FileManagerDelService;
import com.tried.system.service.FileManagerService;
/**
 * @Description 作废文件存储区 服务接口实现
 * @author liuxd
 * @date 2016-07-28 09:43:12
 * @version V1.0
 */
@Service
public class FileManagerDelServiceImpl extends BaseServiceImpl<FileManagerDel> implements FileManagerDelService {

	@Resource
	FileManagerService fileManagerService;
	
	@Override
	public void addDelFile(String pkId, String pkColumn,Date recordTime,String recordUser) throws Exception {
		if(null==pkId||pkId.isEmpty()){return;}
		
		List<FileManager> list =new ArrayList<FileManager>();
		
		if(null==pkColumn||pkColumn.isEmpty()){
			 list = fileManagerService.findAll("from FileManager where pkId='" + pkId + "'");
		}else{
		     list = fileManagerService.findAll("from FileManager where pkId='" + pkId + "' and pkColumn='"+pkColumn+"'");
		}
		for (FileManager fileManager : list) {
			FileManagerDel fileManagerDel	=new FileManagerDel();
			fileManagerDel.setId(fileManager.getId());
			fileManagerDel.setFilename(fileManager.getFilename());
			fileManagerDel.setFileSaveName(fileManager.getFileSaveName());
			fileManagerDel.setFileSavePath(fileManager.getFileSavePath());
			fileManagerDel.setFileSize(fileManager.getFileSize());
			fileManagerDel.setPkColumn(fileManager.getPkColumn());
			fileManagerDel.setPkId(fileManager.getPkId());
			fileManagerDel.setPkTable(fileManager.getPkTable());
			fileManagerDel.setRecordTime(fileManager.getRecordTime());
			fileManagerDel.setRecordUser(recordUser);
			fileManagerDel.setDesciption(fileManager.getDesciption());
			fileManagerDel.setBackTime(recordTime);
			this.add(fileManagerDel);
			fileManagerService.delete(fileManager.getId());
		}
		
	}

}
