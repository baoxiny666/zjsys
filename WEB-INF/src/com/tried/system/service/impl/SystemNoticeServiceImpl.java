package com.tried.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.system.model.SystemNotice;
import com.tried.system.service.FileManagerService;
import com.tried.system.service.SystemMemoryService;
import com.tried.system.service.SystemNoticeService;
/**
 * @Description 公告管理 服务接口实现
 * @author liuxd
 * @date 2016-09-06 16:30:37
 * @version V1.0
 */
@Service
public class SystemNoticeServiceImpl extends BaseServiceImpl<SystemNotice> implements SystemNoticeService {

	@Resource
	FileManagerService fileManagerService;
	@Resource
	SystemMemoryService systemMemoryService;
	@Override
	public void add(SystemNotice systemNotice, String pkId) throws Exception {
	this.add(systemNotice);
	systemMemoryService.singleAddMemory(systemNotice.getId(),"notice_content",systemNotice.getNotice_content());
	fileManagerService.add("notice_file", systemNotice.getNotice_file(), pkId, systemNotice.getId());
	}

	@Override
	public void edit(SystemNotice systemNotice, String pkId)  throws Exception{
		 this.update(systemNotice);
		 systemMemoryService.editMemory(systemNotice.getId(),"notice_content",systemNotice.getNotice_content());
		 fileManagerService.add("notice_file", systemNotice.getNotice_file(), pkId, systemNotice.getId());
	}

	@Override
	public void del(SystemNotice systemNotice)  throws Exception{
		this.delete(systemNotice.getId());
		systemMemoryService.delMemory(systemNotice.getId(),"notice_content");
		fileManagerService.delColumnFile(systemNotice.getId(),"notice_file");
	}

}
