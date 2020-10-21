package com.tried.zjsys.basics.thread;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.tried.common.ApplicationContextUtil;
import com.tried.zjsys.testDataSrc.service.ZjLiangreSrcService;

public class LingReYiCircleThread implements Runnable{
	private static Logger logger = Logger.getLogger(LingReYiCircleThread.class);
	ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
    //量热仪
	ZjLiangreSrcService zjLiangreSrcService = (ZjLiangreSrcService) springContext.getBean("zjLiangreSrcServiceImpl");
 
	@Override
	public void run() {
		while(true){
			long circleTime=1000*10;//默认循环时间
			try {
				//不为空的时候执行采集，否则无限循环
				if(ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-045")!=null){
					circleTime=ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-045").getCircleTimeNum()*1000;
					zjLiangreSrcService.synCollect("ZJ-ZX-045");
				}
				Thread.sleep(circleTime);
			} catch (Exception e) {
				try {
					Thread.sleep(circleTime);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logger.error("碳硫仪："+e.getMessage());
			}
		}
		
	}

}
