package com.tried.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.system.model.SystemSerial;
import com.tried.system.model.SystemSerialMax;
import com.tried.system.service.SystemSerialMaxService;
import com.tried.system.service.SystemSerialService;

/**
 * @Description - 服务接口实现
 * @author liuxd
 * @date 2018-02-05 22:35:27
 * @version V1.0
 */
@Service
public class SystemSerialServiceImpl extends BaseServiceImpl<SystemSerial> implements SystemSerialService {

	@Resource
	SystemSerialMaxService systemSerialMaxService;
	 
	@Override
	public void setSerialInfo(String sysType, String sysStandard) throws Exception {
		List<SystemSerial> systemSerialList = this.findAll("from SystemSerial where sysType='" + sysType + "' and  sysStandard='" + sysStandard + "'");
		if (systemSerialList.size() > 0) {
			SystemSerial systemSerial = systemSerialList.get(0);
			systemSerial.setSysSerialNum(systemSerial.getSysSerialNum() + 1);
			this.update(systemSerial);

		}
	}

	@Override
	public String getSerialInfo(String sysType) throws Exception {
		String resultNUM=null;
		int xhlen=0;
		String xhStr = "";
		 SystemSerial systemSerial = this.getFirstRecordByField("from SystemSerial where sysType='" + sysType + "'  ");
		 if(systemSerial!=null){
		   resultNUM=systemSerial.getSysStandard();
			if(systemSerial.getSysStandard().indexOf("%YYYY%")!=-1){
				resultNUM=resultNUM.replace("%YYYY%",ConfigUtils.dateYearToString(new Date()));
			}
			if(systemSerial.getSysStandard().indexOf("%YY%")!=-1){
				resultNUM=resultNUM.replace("%YY%",ConfigUtils.dateYearToString(new Date()).substring(0, 2));
			}
			
			if(systemSerial.getSysStandard().indexOf("%MM%")!=-1){
				resultNUM=resultNUM.replace("%MM%",ConfigUtils.dataToMonthString(new Date()));
			}
			if(systemSerial.getSysStandard().indexOf("%DD%")!=-1){
				resultNUM=resultNUM.replace("%DD%",ConfigUtils.dataToDayString(new Date()));
			}
			
			
			SystemSerialMax systemSerialMax=systemSerialMaxService.getFirstRecordByField("from SystemSerialMax where serialId='"+systemSerial.getId()+"' and sysStandardValue='"+resultNUM+"' ");
			if(systemSerialMax!=null){
			    systemSerialMax.setSysSerialNum(systemSerialMax.getSysSerialNum()+1);
			    systemSerialMaxService.update(systemSerialMax);
			}else{
				     systemSerialMax=new SystemSerialMax();
				     systemSerialMax.setRecordTime(new Date());
				     systemSerialMax.setSerialId(systemSerial.getId());
				     systemSerialMax.setSysSerialNum(systemSerial.getSysSerialNum());
				     systemSerialMax.setSysStandardValue(resultNUM);
				     systemSerialMaxService.add(systemSerialMax);
			}
			
			if(systemSerial.getSysStandard().indexOf("XX")!=-1){
				int start=systemSerial.getSysStandard().indexOf("XX")-1;
				int end=systemSerial.getSysStandard().lastIndexOf("XX")+1;
				xhlen=end-start;
				for (int i = 0; i < xhlen; i++) {
					xhStr += "X";
				}
				//存在继续序号添加处理，不存在序号从1开始从新计数
				 resultNUM=resultNUM.replace(xhStr,ConfigUtils.decimalNumToString(systemSerialMax.getSysSerialNum(), xhlen));
			
			}
			 
		 }
		 
		return resultNUM;
	}
	
	@Override
	public String getHisSerialInfo(String sysType) throws Exception {
		String resultNUM=null;
		int xhlen=0;
		String xhStr = "";
		 SystemSerial systemSerial = this.getFirstRecordByField("from SystemSerial where sysType='" + sysType + "'  ");
		 if(systemSerial!=null){
		   resultNUM=systemSerial.getSysStandard();
			if(systemSerial.getSysStandard().indexOf("%YYYY%")!=-1){
				resultNUM=resultNUM.replace("%YYYY%",ConfigUtils.dateYearToString(new Date()));
			}
			if(systemSerial.getSysStandard().indexOf("%YY%")!=-1){
				resultNUM=resultNUM.replace("%YY%",ConfigUtils.dateYearToString(new Date()).substring(0, 2));
			}
			if(systemSerial.getSysStandard().indexOf("%MM%")!=-1){
				resultNUM=resultNUM.replace("%MM%",ConfigUtils.dataToMonthString(new Date()));
			}
			if(systemSerial.getSysStandard().indexOf("%DD%")!=-1){
				resultNUM=resultNUM.replace("%DD%",ConfigUtils.dataToDayString(new Date()));
			}						
			SystemSerialMax systemSerialMax=systemSerialMaxService.getFirstRecordByField("from SystemSerialMax where serialId='"+systemSerial.getId()+"' and sysStandardValue='"+resultNUM+"' ");			
			if(systemSerialMax!=null){
			    systemSerialMax.setSysSerialNum(systemSerialMax.getSysSerialNum());			   
			}else{
				     systemSerialMax=new SystemSerialMax();
				     systemSerialMax.setRecordTime(new Date());
				     systemSerialMax.setSerialId(systemSerial.getId());
				     systemSerialMax.setSysSerialNum(systemSerial.getSysSerialNum());
				     systemSerialMax.setSysStandardValue(resultNUM);				    
			}		
				if(systemSerial.getSysStandard().indexOf("XX")!=-1){
					int start=systemSerial.getSysStandard().indexOf("XX")-1;
					int end=systemSerial.getSysStandard().lastIndexOf("XX")+1;
					xhlen=end-start;
					for (int i = 0; i < xhlen; i++) {
						xhStr += "X";
					}	
					//存在继续序号添加处理，不存在序号从1开始从新计数
					 resultNUM=resultNUM.replace(xhStr,ConfigUtils.decimalNumToString(systemSerialMax.getSysSerialNum(), xhlen));	
			}	
			 
		 }
		 
		return resultNUM;
	}
	

	@Override
	public void setSerialInfo(String sysType) throws Exception {
		List<SystemSerial> systemSerialList = this.findAll("from SystemSerial where sysType='" + sysType + "'  ");
		if (systemSerialList.size() > 0) {
			SystemSerial systemSerial = systemSerialList.get(0);
			systemSerial.setSysSerialNum(systemSerial.getSysSerialNum() + 1);
			this.update(systemSerial);

		}
		
	}

	@Override
	public String getSerialInfo(String ymd, String sysType) throws Exception {
		String resultNUM=null;
		int xhlen=0;
		String xhStr = "";
		 SystemSerial systemSerial = this.getFirstRecordByField("from SystemSerial where sysType='" + sysType + "'  ");
		 if(systemSerial!=null){
		   resultNUM=systemSerial.getSysStandard();
			if(systemSerial.getSysStandard().indexOf("%YYYY%")!=-1){
				resultNUM=resultNUM.replace("%YYYY%",ymd.substring(0,4));
			}
			if(systemSerial.getSysStandard().indexOf("%YY%")!=-1){
				resultNUM=resultNUM.replace("%YY%",ConfigUtils.dateYearToString(new Date()).substring(0, 2));
			}
			if(systemSerial.getSysStandard().indexOf("%MM%")!=-1){
				resultNUM=resultNUM.replace("%MM%",ymd.substring(5,7));
			}
			if(systemSerial.getSysStandard().indexOf("%DD%")!=-1){
				resultNUM=resultNUM.replace("%DD%",ymd.substring(8,10));
			}
			
			
			SystemSerialMax systemSerialMax=systemSerialMaxService.getFirstRecordByField("from SystemSerialMax where serialId='"+systemSerial.getId()+"' and sysStandardValue='"+resultNUM+"' ");
			if(systemSerialMax!=null){
			    systemSerialMax.setSysSerialNum(systemSerialMax.getSysSerialNum()+1);
			    systemSerialMaxService.update(systemSerialMax);
			}else{
				     systemSerialMax=new SystemSerialMax();
				     systemSerialMax.setRecordTime(new Date());
				     systemSerialMax.setSerialId(systemSerial.getId());
				     systemSerialMax.setSysSerialNum(systemSerial.getSysSerialNum());
				     systemSerialMax.setSysStandardValue(resultNUM);
				     systemSerialMaxService.add(systemSerialMax);
			}
			
			if(systemSerial.getSysStandard().indexOf("XX")!=-1){
				int start=systemSerial.getSysStandard().indexOf("XX")-1;
				int end=systemSerial.getSysStandard().lastIndexOf("XX")+1;
				xhlen=end-start;
				for (int i = 0; i < xhlen; i++) {
					xhStr += "X";
				}
				//存在继续序号添加处理，不存在序号从1开始从新计数
				 resultNUM=resultNUM.replace(xhStr,ConfigUtils.decimalNumToString(systemSerialMax.getSysSerialNum(), xhlen));
			
			}
			 
		 }
		 
		return resultNUM;
	}
}
