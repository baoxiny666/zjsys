package com.tried.zjsys.basics.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.zjsys.basics.model.DataWlInfo;
import com.tried.zjsys.basics.service.DataWlInfoService;
import com.tried.zjsys.testDataSrc.model.DataKeyMaxMin;
import com.tried.zjsys.testDataSrc.service.DataKeyMaxMinService;
/**
 * @Description 物料名称 服务接口实现
 * @author liuxd
 * @date 2020-07-06 16:20:50
 * @version V1.0
 */
@Service
public class DataWlInfoServiceImpl extends BaseServiceImpl<DataWlInfo> implements DataWlInfoService {
	@Resource
	DataKeyMaxMinService dataKeyMaxMinService;
	@Override
	public void copy(String id,String userId) throws Exception {
		// TODO Auto-generated method stub
		DataWlInfo dataWlInfo=this.getById(id);
		DataWlInfo model=new DataWlInfo();
		model.setWlCode(dataWlInfo.getWlCode()+"-1");
		model.setWlName(dataWlInfo.getWlName());
		model.setWlType(dataWlInfo.getWlType());
		model.setRecordTime(new Date());
		model.setRecordUser(userId);
		this.add(model);
		List<DataKeyMaxMin>  listKey= dataKeyMaxMinService.findAll("from DataKeyMaxMin where deviceName='"+dataWlInfo.getWlCode()+"'");
		for(DataKeyMaxMin data:listKey){
			DataKeyMaxMin d=new DataKeyMaxMin();
			d.setDeviceName(data.getDeviceName()+"-1");
			d.setFieldName(data.getFieldName());
			d.setKeyMax(data.getKeyMax());
			d.setKeyMin(data.getKeyMin());
			d.setKeyName(data.getKeyName());
			d.setRecordTime(new Date());
			d.setRecordUser(userId);
			dataKeyMaxMinService.add(d);
		}
	}

}
