package com.tried.zjsys.basics.thread;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.tried.common.ApplicationContextUtil;
import com.tried.zjsys.testDataSrc.service.YingguangSrcBxyService;
import com.tried.zjsys.testDataSrc.service.ZjDaiganglixueSrcService;
import com.tried.zjsys.testDataSrc.service.ZjTanliuSrcService;
public class YingGuangCircleThread030 implements Runnable{
	private static Logger logger = Logger.getLogger(YingGuangCircleThread030.class);
	ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
   
	YingguangSrcBxyService yingguangSrcBxyService = (YingguangSrcBxyService) springContext.getBean("yingguangSrcBxyServiceImpl");
	@Override
	public void run() {
		while(true){
			long circleTime=1000*10;//默认循环时间
			try {
				//不为空的时候执行采集，否则无限循环
				if(ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-030")!=null){
					circleTime=ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-030").getCircleTimeNum()*1000;
					yingguangSrcBxyService.synCollect("ZJ-ZX-030",null);
				}
				Thread.sleep(circleTime);
			} catch (Exception e) {
				try {
					Thread.sleep(circleTime);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logger.error("荧光仪ZJ-ZX-030："+e.getMessage());
			}
		}
		
	}

}
