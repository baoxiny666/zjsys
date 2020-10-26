package com.tried.base.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.tried.common.ApplicationContextUtil;
import com.tried.zjsys.basics.model.DataCircle;
import com.tried.zjsys.basics.service.DataCircleService;

import com.tried.zjsys.basics.thread.DaiGangLXuYiCircleThread031;
import com.tried.zjsys.basics.thread.DaiGangLXuYiCircleThread032;
import com.tried.zjsys.basics.thread.DaiGangLXuYiCircleThread033;
import com.tried.zjsys.basics.thread.DaiGangLXuYiCircleThread034;
import com.tried.zjsys.basics.thread.GongYeFXCircleThread039;
import com.tried.zjsys.basics.thread.GongYeFXCircleThread040;
import com.tried.zjsys.basics.thread.DingyiCircleThread042;
import com.tried.zjsys.basics.thread.DingyiCircleThread850;
import com.tried.zjsys.basics.thread.GongYeFXCircleThread041;
import com.tried.zjsys.basics.thread.GongYeFXCircleThread038;
import com.tried.zjsys.basics.thread.LingReYiCircleThread;
import com.tried.zjsys.basics.thread.ThreadStaticVariable;
import com.tried.zjsys.basics.thread.TluyiCircleThread028;
import com.tried.zjsys.basics.thread.TluyiCircleThread029;
import com.tried.zjsys.basics.thread.YingGuangCircleThread030;
import com.tried.zjsys.basics.thread.YingGuangCircleThread031;

/**
 * @Description 任务定时器处理
 * @author liuxd
 * @date 2018-10-23上午8:59:17
 * @version V1.0
 */
public class TimerCjListener extends HttpServlet implements HttpSessionListener, ServletContextListener {
	private static Logger logger = Logger.getLogger(TimerCjListener.class);

	/**
	 * @desciption
	 * @author lxd 2018-2-9 下午3:07:11
	 */
	private static final long serialVersionUID = -3179315430959199390L;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ApplicationContext springContext = ApplicationContextUtil.getApplicationContext();
	    //初始化定时器信息
		DataCircleService dataCircleService = (DataCircleService) springContext.getBean("dataCircleServiceImpl");
		try {
			List<DataCircle> dataCircleList=  dataCircleService.findAll();
		    for(DataCircle dataCircle:dataCircleList){
		    	ThreadStaticVariable.DataCircleMap.put(dataCircle.getDeviceNum(), dataCircle);
		    	System.out.println("设备名称"+dataCircle.getDeviceNum());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		    (new Thread((Runnable)new YingGuangCircleThread030())).start();
		   
//		new Thread(new TluyiCircleThread028()).start();//碳硫仪ZJ-ZX-028
//	    new Thread(new TluyiCircleThread029()).start();//碳硫仪ZJ-ZX-029
//		new Thread(new YingGuangCircleThread030()).start();//荧光仪 030
//		new Thread(new YingGuangCircleThread031()).start();//荧光仪 031
//        new Thread(new DingyiCircleThread850()).start();//定硫仪YX-DLA8500
//        new Thread(new DingyiCircleThread042()).start();//定硫仪ZJ-ZX-042
//		new Thread(new GongYeFXCircleThread040()).start();//工业分析仪ZJ-ZX-040
//        new Thread(new GongYeFXCircleThread039()).start();//工业分析仪ZJ-ZX-039
//	    new Thread(new GongYeFXCircleThread038()).start();//工业分析仪ZJ-ZX-038
//	    new Thread(new GongYeFXCircleThread041()).start();//工业分析仪ZJ-ZX-041	
//		new Thread(new LingReYiCircleThread()).start();//量热仪ZJ-ZX-045
// 	    new Thread(new DaiGangLXuYiCircleThread031()).start();//带钢力学仪 ZJ-LX-031
// 	 	new Thread(new DaiGangLXuYiCircleThread032()).start();//带钢力学仪  ZJ-LX-031
// 	 	new Thread(new DaiGangLXuYiCircleThread033()).start();//带钢力学仪  ZJ-LX-031
// 	  	new Thread(new DaiGangLXuYiCircleThread034()).start();//带钢力学仪 ZJ-LX-031
	}

	
	/**
	 * @Description
	 * @author liuxd
	 * @date 2018-10-23上午9:14:34
	 * @version V1.0
	 */

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
