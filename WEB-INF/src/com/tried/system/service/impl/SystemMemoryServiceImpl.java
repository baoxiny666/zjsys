package com.tried.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.system.model.SystemMemory;
import com.tried.system.service.SystemMemoryService;

@Service
public class SystemMemoryServiceImpl extends BaseServiceImpl<SystemMemory> implements SystemMemoryService {
	@Override
	public boolean checkExitMemory(String relationId,String relationColumn) throws Exception {
		List<SystemMemory> systemMemoryList = this.findAll("from SystemMemory where relationId='" + relationId + "' and relationColumn='"+relationColumn+"'");
		if (systemMemoryList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void delMemory(String relationId,String relationColumn) throws Exception {
		List<SystemMemory> systemMemoryList = this.findAll("from SystemMemory where relationId='" + relationId + "' and relationColumn='"+relationColumn+"'");
		for (SystemMemory systemMemory : systemMemoryList) {
			this.delete(systemMemory);
		}
	}

	@Override
	public SystemMemory getSingleMemory(String relationId,String relationColumn) throws Exception {

		List<SystemMemory> systemMemoryList = this.findAll("from SystemMemory where relationId='" + relationId + "' and relationColumn='"+relationColumn+"'");
		if (systemMemoryList.size() > 0) {
			return systemMemoryList.get(0);
		} else {
			return null;
		}
	}
	@Override
	public List<SystemMemory> getManyMemory(String relationId,String relationColumn) throws Exception {

			return  this.findAll("from SystemMemory where relationId='" + relationId + "' and relationColumn='"+relationColumn+"'");
		 
	}
	@Override
	public void singleAddMemory(String relationId,String relationColumn, String relationContext) throws Exception {
		if (checkExitMemory(relationId,relationColumn)) {
			delMemory(relationId,relationColumn);
		}
		
		SystemMemory systemMemory = new SystemMemory();
		systemMemory.setRelationId(relationId);
		systemMemory.setRelationColumn(relationColumn);
		systemMemory.setRelationContext(relationContext);
		systemMemory.setRecordTime(new Date());
		this.add(systemMemory);
	}

	@Override
	public void editMemory(String relationId, String relationColumn, String relationContext) throws Exception {
		List<SystemMemory> systemMemoryList = this.findAll("from SystemMemory where relationId='" + relationId + "' and relationColumn='"+relationColumn+"'");
		if (systemMemoryList.size() > 0) {
			SystemMemory systemMemory=  systemMemoryList.get(0);
			systemMemory.setRelationContext(relationContext);
			this.update(systemMemory);
		} else{
			singleAddMemory(relationId,  relationColumn,  relationContext);
		} 
		
	}

	@Override
	public void manyAddMemory(String relationId, String relationColumn, String relationContext) throws Exception {
		
		// TODO Auto-generated method stub
		SystemMemory systemMemory = new SystemMemory();
		systemMemory.setRelationId(relationId);
		systemMemory.setRelationColumn(relationColumn);
		systemMemory.setRelationContext(relationContext);
		systemMemory.setRecordTime(new Date());
		this.add(systemMemory);
	}
	
	
	@Override
	public void tempAddMemory(String relationId, String relationContext) throws Exception {
		// TODO Auto-generated method stub
		SystemMemory systemMemory = new SystemMemory();
		systemMemory.setRelationId(relationId);
		systemMemory.setRelationColumn("temp_Memory");
		systemMemory.setRelationContext(relationContext);
		systemMemory.setRecordTime(new Date());
		this.add(systemMemory);
		delTempMemory();
	}

	@Override
	public void tempEditMemory(String id, String relationContext) throws Exception {
		SystemMemory systemMemory=this.getById(id);
		if (systemMemory!=null) {
			systemMemory.setRelationContext(relationContext);
			this.update(systemMemory);
		} 
	}

	@Override
	public void delTempMemory(String id) throws Exception {
		 this.delete(id);
	}

	@Override
	public List<SystemMemory> getTempMemory(String relationId) throws Exception {
		if(relationId!=null){
			return this.findAll("from SystemMemory where relationId='"+relationId+"' and relationColumn = 'temp_Memory'");
		}
		else{
			return new ArrayList<SystemMemory>();
		}
	}

	/**
	 * @Description 删除临时历史数据
	 * @author liuxd
	 * @date 2018-10-16上午10:27:01
	 * @version V1.0
	 */
	@Override
	public void delTempMemory() throws Exception {
		String oldTime=ConfigUtils.dataToSimpleString(new Date())+" 00:00:00";
		List<SystemMemory> systemMemoryList = this.findAll("from SystemMemory where recordTime<'"+oldTime+"' and relationColumn = 'temp_Memory'");
		for (SystemMemory systemMemory : systemMemoryList) {
			this.delete(systemMemory);
		}
	}

	@Override
	public void delBeachTempMemory(String[] idArray) throws Exception {
		for (String id : idArray) {
			this.delete(id);
		}

	}
	public void copyTempMemory(String id)throws Exception{
		SystemMemory memory=this.getById(id);
		SystemMemory systemMemory=new SystemMemory();
		systemMemory.setRelationId(memory.getRelationId());
		systemMemory.setRelationColumn(memory.getRelationColumn());
		JSONObject tempJson=JSONObject.fromObject(memory.getRelationContext());
		String sampleId=ConfigUtils.getUUID();
		tempJson.put("id", sampleId);
		systemMemory.setRelationContext(tempJson.toString());
		systemMemory.setRecordTime(new Date());
		this.add(systemMemory);
	}
	 

}
