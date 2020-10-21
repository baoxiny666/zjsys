package com.tried.zjsys.testDataSrc.service.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.common.FileCommonUtils;
import com.tried.zjsys.basics.model.DataCircle;
import com.tried.zjsys.basics.thread.ThreadStaticVariable;
import com.tried.zjsys.testDataSrc.model.ZjLiangreSrc;
import com.tried.zjsys.testDataSrc.model.ZjTanliuSrc;
import com.tried.zjsys.testDataSrc.service.ZjLiangreSrcService;
import com.tried.zjsys.testDataSrc.service.ZjTanliuSrcService;
/**
 * @Description - 服务接口实现
 * @author liuxd
 * @date 2020-06-24 22:35:41
 * @version V1.0
 */
@Service
public class ZjTanliuSrcServiceImpl extends BaseServiceImpl<ZjTanliuSrc> implements ZjTanliuSrcService {
	private static Logger logger = Logger.getLogger(ZjTanliuSrcServiceImpl.class);
	@Resource
	ZjLiangreSrcService zjLiangreSrcService;
	@Override
	public void synCollect(String deviceNum,String refreshDate) throws Exception {
		
	
		String startTime=ConfigUtils.dataToSimpleString(new Date())+" 00:00:00";
		String endTime=ConfigUtils.dataToSimpleString(new Date())+" 23:59:59";
		if(refreshDate!=null){//手动刷新
			 startTime=refreshDate+" 00:00:00";
			 endTime=refreshDate+" 23:59:59";
		}
		try{
			
			List<ZjTanliuSrc> srcList=this.findAll("from ZjTanliuSrc where time like '"+refreshDate+"%' and deviceNum='"+deviceNum+"' order by time desc");
		/*	if(srcList!=null&&srcList.size()>0){
				if(srcList.get(0).getTime()!=null&&!srcList.get(0).getTime().isEmpty()){
					startTime=srcList.get(0).getTime();
				}
			}*/
			for(ZjTanliuSrc src:srcList){
				if(src.getSampleNum()!=null){
				ThreadStaticVariable.CheckKeyTime(deviceNum,src.getTime());
				}
			}
		
		if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){			
			DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
			//无法读取共享目录 带用户密码的的数据库，所以先把共享文件采集到本地，在本地打开
			 String jdbcUrl=dataCircle.getJdbcUrl();
			 int dbqIndex=jdbcUrl.indexOf("DBQ=");
			 String  srcFile=jdbcUrl.substring(dbqIndex+4);
		if(FileCommonUtils.copyFile(srcFile, dataCircle.getDataSavePath(), true)){
			jdbcUrl=jdbcUrl.substring(0,dbqIndex+4)+dataCircle.getDataSavePath();
			Class.forName(dataCircle.getDriverClass());
			Connection	conn = java.sql.DriverManager.getConnection(jdbcUrl, dataCircle.getUsername(), dataCircle.getPassword());
			String	sql="select AnalyseNO,SampleID,SampleName,Result_C,Result_S,Time from AnalyseResult where time>#"+startTime+"# and time<=#"+endTime+"#";
			List<Object[]> objList=ConfigUtils.dbFindList(conn,sql);
			logger.info("ZJ-ZX-028"+"："+startTime);
			for(Object[] obj:objList){
				if(obj[0]!=null&&obj[2]!=null&&obj[5]!=null){
					if(obj[2].toString().length()<ThreadStaticVariable.invialLength){
						continue;
					}
					if(ThreadStaticVariable.CheckKeyTime(deviceNum,obj[5].toString())){
						ZjTanliuSrc zjTanliuSrc=new ZjTanliuSrc();
						zjTanliuSrc.setDataStatus("原始数据");
						zjTanliuSrc.setRecordTime(new Date());
						zjTanliuSrc.setDeviceNum(deviceNum);
						zjTanliuSrc.setAnalyseNo((obj[0]!=null)?obj[0].toString():"");
						zjTanliuSrc.setSampleId((obj[1]!=null)?obj[1].toString():"");
						zjTanliuSrc.setSampleName((obj[2]!=null)?obj[2].toString():"");
						zjTanliuSrc.setSampleNum((obj[2]!=null)?obj[2].toString():"");
						zjTanliuSrc.setResultc((obj[3]!=null)?obj[3].toString():"");
						zjTanliuSrc.setResults((obj[4]!=null)?obj[4].toString():"");
						zjTanliuSrc.setTime((obj[5]!=null)?obj[5].toString():"");
						zjLiangreSrcService.checkAddSampleNum(zjTanliuSrc.getSampleNum());//核对量热仪、手录标样品编号
						this.add(zjTanliuSrc);
					}
				}
			}
		}
	  }
		}catch(Exception e){
			logger.error("ZJ-ZX-028"+"："+e.getMessage());
		}
		
	}
	@Override
	public void recovery(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		for (String id : ids) {
			ZjTanliuSrc zjTanliuSrc = this.getById(id);
			if ("作废数据".equals(zjTanliuSrc.getDataStatus())) {
				zjTanliuSrc.setDataStatus("原始数据");
				this.update(zjTanliuSrc);
			}
		}
	}

	@Override
	public void del(String[] ids) throws Exception {
		for (String id : ids) {
			ZjTanliuSrc zjTanliuSrc = this.getById(id);
			if ("原始数据".equals(zjTanliuSrc.getDataStatus())) {
				zjTanliuSrc.setDataStatus("作废数据");
				this.update(zjTanliuSrc);
			}
		}
	}

	
}
