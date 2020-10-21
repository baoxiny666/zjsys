package com.tried.zjsys.basics.thread;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.tried.common.ApplicationContextUtil;
import com.tried.zjsys.testDataSrc.service.YinguangSrcService;
import com.tried.zjsys.testDataSrc.service.ZjDaiganglixueSrcService;
import com.tried.zjsys.testDataSrc.service.ZjTanliuSrcService;
public class YingGuangCircleThread031 implements Runnable{
	private static Logger logger = Logger.getLogger(YingGuangCircleThread030.class);
	ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
    //带钢力学仪ZJ-LX-031
	YinguangSrcService yinguangSrcService = (YinguangSrcService) springContext.getBean("yinguangSrcServiceImpl");
	@Override
	public void run() {
		while(true){
			long circleTime=1000*10;//默认循环时间
			try {
				//不为空的时候执行采集，否则无限循环
				if(ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-031")!=null){
					circleTime=ThreadStaticVariable.DataCircleMap.get("ZJ-ZX-031").getCircleTimeNum()*1000;
					yinguangSrcService.synCollect031("ZJ-ZX-031",null);
				}
				Thread.sleep(circleTime);
			} catch (Exception e) {
				try {
					Thread.sleep(circleTime);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logger.error("荧光仪ZJ-ZX-031："+e.getMessage());
			}
		}
		
	}

}
