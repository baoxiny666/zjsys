package com.tried.zjsys.basics.thread;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.tried.common.ApplicationContextUtil;
import com.tried.zjsys.testDataSrc.service.ZjDaiganglixueSrcService;
import com.tried.zjsys.testDataSrc.service.ZjTanliuSrcService;
public class DaiGangLXuYiCircleThread031 implements Runnable{
	private static Logger logger = Logger.getLogger(DaiGangLXuYiCircleThread031.class);
	ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
    //带钢力学仪ZJ-LX-031
	ZjDaiganglixueSrcService zjDaiganglixueSrcService = (ZjDaiganglixueSrcService) springContext.getBean("zjDaiganglixueSrcServiceImpl");
	@Override
	public void run() {
		while(true){
			long circleTime=1000*10;//默认循环时间
			try {
				//不为空的时候执行采集，否则无限循环
				if(ThreadStaticVariable.DataCircleMap.get("ZJ-LX-031")!=null){
					circleTime=ThreadStaticVariable.DataCircleMap.get("ZJ-LX-031").getCircleTimeNum()*1000;
					zjDaiganglixueSrcService.synCollect031("ZJ-LX-031",null);
				}
				Thread.sleep(circleTime);
			} catch (Exception e) {
				try {
					Thread.sleep(circleTime);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logger.error("带钢力学仪ZJ-LX-031："+e.getMessage());
			}
		}
		
	}

}
