package com.tried.zjsys.basics.thread;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.tried.common.ApplicationContextUtil;
import com.tried.zjsys.testDataSrc.service.ZjTanliuSrcService;
public class TluyiCircleThread029 implements Runnable{
	private static Logger logger = Logger.getLogger(TluyiCircleThread029.class);
	ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
    //碳留意时器信息
	ZjTanliuSrcService zjTanliuSrcService = (ZjTanliuSrcService) springContext.getBean("zjTanliuSrcServiceImpl");
 
	@Override
	public void run() {
		while(true){
			long circleTime=1000*10;//默认循环时间
			try {
				//不为空的时候执行采集，否则无限循环
				if(ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-029")!=null){
					circleTime=ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-029").getCircleTimeNum()*1000;
					zjTanliuSrcService.synCollect("ZJ-ZX-029",null);
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
