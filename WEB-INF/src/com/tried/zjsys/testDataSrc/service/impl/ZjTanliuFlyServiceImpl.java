package com.tried.zjsys.testDataSrc.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.zjsys.testDataSrc.model.ZjTanliuFly;
import com.tried.zjsys.testDataSrc.model.ZjTanliuSrc;
import com.tried.zjsys.testDataSrc.service.ZjTanliuFlyService;
import com.tried.zjsys.testDataSrc.service.ZjTanliuSrcService;
/**
 * @Description - 服务接口实现
 * @author liuxd
 * @date 2020-06-24 22:35:40
 * @version V1.0
 */
@Service
public class ZjTanliuFlyServiceImpl extends BaseServiceImpl<ZjTanliuFly> implements ZjTanliuFlyService {
	@Resource
	ZjTanliuSrcService zjTanliuSrcService;
	@Override
	public void submitData(String recordIdS, String userId) throws Exception {
		
		if(recordIdS!=null&&!recordIdS.isEmpty()){
			Date currentDate=new Date();
		 String[] srcIdList=recordIdS.split(";");
		 for(String srcId:srcIdList){
			ZjTanliuSrc  zjTanliuSrc= zjTanliuSrcService.getById(srcId);
			zjTanliuSrc.setDataStatus("已提交");
			ZjTanliuFly zjTanliuFly=new ZjTanliuFly();
			zjTanliuFly.setDataStatus("已提交");
			zjTanliuFly.setSrcId(srcId);
			zjTanliuFly.setDeviceNum(zjTanliuSrc.getDeviceNum());
			zjTanliuFly.setSampleNum(zjTanliuSrc.getSampleNum());
			zjTanliuFly.setResultc(zjTanliuSrc.getResultc());
			zjTanliuFly.setTime(zjTanliuSrc.getTime());
			zjTanliuFly.setResults(zjTanliuSrc.getResults());
			zjTanliuFly.setRecordTime(currentDate);
			zjTanliuFly.setRecordUser(userId);
			this.add(zjTanliuFly);
			
		 }
		
			
		}
		
	}

}
