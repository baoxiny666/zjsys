package com.tried.zjsys.basics.thread;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.tried.common.ApplicationContextUtil;
import com.tried.zjsys.testDataSrc.service.ZjGongyefenxiSrcService;

public class GongYeFXCircleThread038 implements Runnable{
	private static Logger logger = Logger.getLogger(GongYeFXCircleThread038.class);
	ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
	    //工业分析仪实时信息
	 ZjGongyefenxiSrcService zjGongyefenxiSrcService = (ZjGongyefenxiSrcService) springContext.getBean("zjGongyefenxiSrcServiceImpl");
		@Override
		public void run() {
			while(true){
				long circleTime=1000*10;//默认循环时间
				try {
					//不为空的时候执行采集，否则无限循环
					if(ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-038")!=null){
						circleTime=ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-038").getCircleTimeNum()*1000;
						zjGongyefenxiSrcService.synCollect038("ZJ-ZX-038",null);
					}
					Thread.sleep(circleTime);
				} catch (Exception e) {
					try {
						Thread.sleep(circleTime);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					logger.error("工业分析仪："+e.getMessage());
				}
			}
			
		}

	
	
	
	
	
	
	
	
	

}
