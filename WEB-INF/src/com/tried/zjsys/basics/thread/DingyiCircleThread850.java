package com.tried.zjsys.basics.thread;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.tried.common.ApplicationContextUtil;
import com.tried.zjsys.testDataSrc.service.ZjDingliuSrcService;
public class DingyiCircleThread850 implements Runnable{
	private static Logger logger = Logger.getLogger(DingyiCircleThread850.class);
	ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
    //定硫仪定时器信息
	ZjDingliuSrcService zjDingliuSrcService = (ZjDingliuSrcService) springContext.getBean("zjDingliuSrcServiceImpl");
 
	@Override
	public void run() {
		while(true){
			long circleTime=1000*10;//默认循环时间
			try {
				//不为空的时候执行采集，否则无限循环
				if(ThreadStaticVariable.DataCircleMap.get("YX-DLA8500")!=null){
					circleTime=ThreadStaticVariable.DataCircleMap.get("YX-DLA8500").getCircleTimeNum()*1000;
					zjDingliuSrcService.synCollect8500("YX-DLA8500");
				}
				Thread.sleep(circleTime);
			} catch (Exception e) {
				try {
					Thread.sleep(circleTime);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logger.error("定硫仪："+e.getMessage());
			}
		}
		
	}

}
