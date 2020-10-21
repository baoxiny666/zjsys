package com.tried.zjsys.testDataSrc.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.linuxense.javadbf.DBFReader;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.common.FileCommonUtils;
import com.tried.zjsys.basics.model.DataCircle;
import com.tried.zjsys.basics.thread.ThreadStaticVariable;
import com.tried.zjsys.testDataSrc.model.ZjDingliuFly;
import com.tried.zjsys.testDataSrc.model.ZjDingliuSrc;
import com.tried.zjsys.testDataSrc.model.ZjLiangreSrc;
import com.tried.zjsys.testDataSrc.service.ZjDingliuFlyService;
import com.tried.zjsys.testDataSrc.service.ZjDingliuSrcService;
import com.tried.zjsys.testDataSrc.service.ZjLiangreSrcService;
/**
 * @Description - 服务接口实现
 * @author liuxd
 * @date 2020-06-28 21:07:24
 * @version V1.0
 */
@Service
public class ZjDingliuSrcServiceImpl extends BaseServiceImpl<ZjDingliuSrc> implements ZjDingliuSrcService {
	private static Logger logger = Logger.getLogger(ZjDingliuSrcServiceImpl.class);
	@Resource
	ZjDingliuFlyService zjDingliuFlyService;
	@Resource
	ZjLiangreSrcService zjLiangreSrcService;
	@Override
	public void synCollect8500(String deviceNum) throws Exception {
		try{
			String riqi=ConfigUtils.dataToSimpleString(new Date());
			String riqiTime="00:00:00";
			List<ZjDingliuSrc> srcList=this.getTopRecord("from ZjDingliuSrc where deviceNum='"+deviceNum+"' order by recordTime  desc", 1);
			if(srcList!=null&&srcList.size()>0){
				if(srcList.get(0).getTesDate()!=null&&!srcList.get(0).getTesDate().isEmpty()&&srcList.get(0).getEndTime()!=null&&!srcList.get(0).getEndTime().isEmpty()){
					riqi=srcList.get(0).getTesDate();
					riqiTime=srcList.get(0).getEndTime();
				}
			}
		// TODO Auto-generated method stub
		if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
			DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
			 //无法读取共享目录 带用户密码的的数据库，所以先把共享文件采集到本地，再本地打开
			    String jdbcUrl=dataCircle.getJdbcUrl();
			    int dbqIndex=jdbcUrl.indexOf("DBQ=");
			    String  srcFile=jdbcUrl.substring(dbqIndex+4);
			   if(  FileCommonUtils.copyFile(srcFile, dataCircle.getDataSavePath(), true)){
				   jdbcUrl=jdbcUrl.substring(0,dbqIndex+4)+dataCircle.getDataSavePath();
					Class.forName(dataCircle.getDriverClass());
					Connection	conn = java.sql.DriverManager.getConnection(jdbcUrl, dataCircle.getUsername(), dataCircle.getPassword());
					String sql="SELECT  AutoNo,SampleNo,Mad,Stad,Std,TestDate,BeginTime,EndTime FROM TestData where TestDate>=#"+riqi+"# and EndTime>#"+riqiTime+"# ";
					List<Object[]> objList=ConfigUtils.dbFindList(conn,sql);
					for(Object[] obj:objList){
						if(obj[0]!=null&&obj[1]!=null){
							if(obj[1].toString().length()< ThreadStaticVariable.invialLength){
								continue;
							}
							ZjDingliuSrc zjDingliuSrc=new ZjDingliuSrc();
							zjDingliuSrc.setDeviceNum(deviceNum);
							zjDingliuSrc.setDataStatus("原始数据");
							zjDingliuSrc.setRecordTime(new Date());
							zjDingliuSrc.setAutoNo((obj[0]!=null)?obj[0].toString():"");
							zjDingliuSrc.setSample_no((obj[1]!=null)?obj[1].toString():"");
							zjDingliuSrc.setTest_mad((obj[2]!=null)?obj[2].toString():"");
							zjDingliuSrc.setTest_stad((obj[3]!=null)?obj[3].toString():"");
							zjDingliuSrc.setTest_std((obj[4]!=null)?obj[4].toString():"");
							zjDingliuSrc.setTesDate((obj[5]!=null)?obj[5].toString():"");
							zjDingliuSrc.setStartTime((obj[6]!=null)?obj[6].toString():"");
							zjDingliuSrc.setEndTime((obj[7]!=null)?obj[7].toString():"");
							zjLiangreSrcService.checkAddSampleNum(zjDingliuSrc.getSample_no());
							this.add(zjDingliuSrc);
						}
					}
			   }
			 
		}
		}catch(Exception e){
			logger.error(deviceNum+"："+e.getMessage());
		}
		
	}
	
	
	@Override
	public void synCollect8500(String deviceNum,String riqi) throws Exception {
		try{
			if(riqi!=null){
				String riqiTime="00:00:00";
				List<ZjDingliuSrc> srcList=this.getTopRecord("from ZjDingliuSrc where tesDate='"+riqi+"' and deviceNum='"+deviceNum+"' order by recordTime  desc", 1);
				if(srcList!=null&&srcList.size()>0){
					if(srcList.get(0).getTesDate()!=null&&!srcList.get(0).getTesDate().isEmpty()&&srcList.get(0).getEndTime()!=null&&!srcList.get(0).getEndTime().isEmpty()){
						riqi=srcList.get(0).getTesDate();
						riqiTime=srcList.get(0).getEndTime();
					}
				}
			// TODO Auto-generated method stub
			if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
				DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
				
				 String jdbcUrl=dataCircle.getJdbcUrl();
				    int dbqIndex=jdbcUrl.indexOf("DBQ=");
				    String  srcFile=jdbcUrl.substring(dbqIndex+4);
				   if(  FileCommonUtils.copyFile(srcFile, dataCircle.getDataSavePath(), true)){
					   jdbcUrl=jdbcUrl.substring(0,dbqIndex+4)+dataCircle.getDataSavePath();
						Class.forName(dataCircle.getDriverClass());
						Connection	conn = java.sql.DriverManager.getConnection(jdbcUrl, dataCircle.getUsername(), dataCircle.getPassword());
						String sql="SELECT  AutoNo,SampleNo,Mad,Stad,Std,TestDate,BeginTime,EndTime FROM TestData where TestDate=#"+riqi+"# and EndTime>#"+riqiTime+"# ";
						List<Object[]> objList=ConfigUtils.dbFindList(conn,sql);
						for(Object[] obj:objList){
							if(obj[0]!=null&&obj[1]!=null){
								if(obj[1].toString().length()< ThreadStaticVariable.invialLength){
									continue;
								}
								ZjDingliuSrc zjDingliuSrc=new ZjDingliuSrc();
								zjDingliuSrc.setDeviceNum(deviceNum);
								zjDingliuSrc.setDataStatus("原始数据");
								zjDingliuSrc.setRecordTime(new Date());
								zjDingliuSrc.setAutoNo((obj[0]!=null)?obj[0].toString():"");
								zjDingliuSrc.setSample_no((obj[1]!=null)?obj[1].toString():"");
								zjDingliuSrc.setTest_mad((obj[2]!=null)?obj[2].toString():"");
								zjDingliuSrc.setTest_stad((obj[3]!=null)?obj[3].toString():"");
								zjDingliuSrc.setTest_std((obj[4]!=null)?obj[4].toString():"");
								zjDingliuSrc.setTesDate((obj[5]!=null)?obj[5].toString():"");
								zjDingliuSrc.setStartTime((obj[6]!=null)?obj[6].toString():"");
								zjDingliuSrc.setEndTime((obj[7]!=null)?obj[7].toString():"");
								zjLiangreSrcService.checkAddSampleNum(zjDingliuSrc.getSample_no());
								this.add(zjDingliuSrc);
							}
						}
				   }
			}
		}
		}catch(Exception e){
		//	new Exception("失败");
		e.printStackTrace();
			logger.error(deviceNum+"："+e.getMessage());
		}
		
	}


	@Override
	public void synCollect042(String deviceNum,String month) throws Exception {
		 InputStream fis = null;
		try{
			if(month==null){
				month=ConfigUtils.dataToYearMonthString(new Date());
			}
			//S2020-05.DBF
			String srcFileName="S"+month+".DBF";
			DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
			if(dataCircle!=null){
				String strFile=dataCircle.getDataSavePath()+srcFileName;
				File file=new File(strFile);
				if(file.exists()){
					int fileIndex=0;
					List<ZjDingliuSrc> srcList=this.getTopRecord("from ZjDingliuSrc where  tesDate like '"+month+"%' and deviceNum='"+deviceNum+"'  order by testIndex  desc", 1);
					if(srcList!=null&&srcList.size()>0){
						if(srcList.get(0).getTestIndex()!=null){
							fileIndex = srcList.get(0).getTestIndex();
						}
					}
					 fis = new FileInputStream(file);
			         DBFReader reader = new DBFReader(fis); // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
			         Object[] rowValues;
			         int readerCount=reader.getRecordCount();
			         for(int i=0;i<=readerCount;i++){
			        	 if(i>fileIndex){
			        		 rowValues=reader.nextRecord();
			        		 String sampleNum=(rowValues[0]!=null)?rowValues[0].toString():"";
			        		 if(sampleNum.length()< ThreadStaticVariable.invialLength){continue;}
			        		 ZjDingliuSrc zjDingliuSrc=new ZjDingliuSrc();
								zjDingliuSrc.setDeviceNum(deviceNum);
								zjDingliuSrc.setDataStatus("原始数据");
								zjDingliuSrc.setRecordTime(new Date());
								zjDingliuSrc.setSample_no(sampleNum);
								zjDingliuSrc.setTestIndex(i);
								zjDingliuSrc.setTest_stad((rowValues[3]!=null)?rowValues[3].toString():"");
								zjDingliuSrc.setTest_mad((rowValues[4]!=null)?rowValues[4].toString():"");
								zjDingliuSrc.setTest_std((rowValues[5]!=null)?rowValues[5].toString():"");
								zjDingliuSrc.setTesDate((rowValues[1]!=null)?rowValues[1].toString():"");
								zjLiangreSrcService.checkAddSampleNum(zjDingliuSrc.getSample_no());
								this.add(zjDingliuSrc); 
			        	 }
			         }
				}
			}
			}catch(Exception e){
			logger.error(deviceNum+"："+e.getMessage());
		}
	}
	
	@Override
	public void fly(String recordIdS, String userId) throws Exception {
		// TODO Auto-generated method stub
			String[] idArray=	recordIdS.split(";");
			Date curDate=new Date();
		    for(String srcId:idArray){
			ZjDingliuSrc src=this.getById(srcId);
			src.setDataStatus("已提交");
			this.update(src);
			ZjDingliuFly fly=new ZjDingliuFly();
			fly.setSrcId(src.getId());
			fly.setDeviceNum(src.getDeviceNum());
			fly.setSample_no(src.getSample_no());
			fly.setTesDate(src.getTesDate());
			fly.setStad_value(src.getTest_stad());
			fly.setRecordTime(curDate);
			fly.setRecordUser(userId);
			fly.setDataStatus("已提交");
			 zjLiangreSrcService.checkAddSampleNum(fly.getSample_no());//核对量热仪、手录标样品编号
			zjDingliuFlyService.add(fly);
			
		}
	}
	@Override
	public void del(String[] ids) throws Exception {
		for (String id : ids) {
			ZjDingliuSrc zjDingliuSrc = this.getById(id);
			if ("原始数据".equals(zjDingliuSrc.getDataStatus())) {
				zjDingliuSrc.setDataStatus("作废数据");
				this.update(zjDingliuSrc);
			}
		}
	}

	@Override
	public void recovery(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		for (String id : ids) {
			ZjDingliuSrc zjDingliuSrc  = this.getById(id);
			if ("作废数据".equals(zjDingliuSrc.getDataStatus())) {
				zjDingliuSrc.setDataStatus("原始数据");
				this.update(zjDingliuSrc);
			}
		}
	}


	@Override
	public void flyJunZhi(ZjDingliuSrc model) throws Exception {
		// TODO Auto-generated method stub
		ZjDingliuFly fly=new ZjDingliuFly();
		fly.setSrcId("均值");
		fly.setDeviceNum(model.getDeviceNum());
		fly.setSample_no(model.getSample_no());
		fly.setTesDate(model.getTesDate());
		fly.setStad_value(model.getTest_stad());
		fly.setRecordTime(new Date());
		fly.setRecordUser(model.getRecordUser());
		fly.setDataStatus("已提交");
		 zjLiangreSrcService.checkAddSampleNum(fly.getSample_no());
		zjDingliuFlyService.add(fly);
		String[] idArray=	model.getId().split(";");
		for(String id:idArray){
			ZjDingliuSrc zjDingliuSrc=this.getById(id);
			zjDingliuSrc.setDataStatus("已提交");
			this.update(zjDingliuSrc);
		}
	}

}
