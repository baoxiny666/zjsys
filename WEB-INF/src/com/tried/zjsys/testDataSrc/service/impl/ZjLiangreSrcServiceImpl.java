package com.tried.zjsys.testDataSrc.service.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.zjsys.basics.model.DataCircle;
import com.tried.zjsys.basics.thread.ThreadStaticVariable;
import com.tried.zjsys.testDataSrc.model.ZjHandInputFly;
import com.tried.zjsys.testDataSrc.model.ZjLiangreFly;
import com.tried.zjsys.testDataSrc.model.ZjLiangreSrc;
import com.tried.zjsys.testDataSrc.service.ZjHandInputFlyService;
import com.tried.zjsys.testDataSrc.service.ZjLiangreFlyService;
import com.tried.zjsys.testDataSrc.service.ZjLiangreSrcService;

/**
 * @Description - 服务接口实现
 * @author lyw
 * @date 2020-06-28 21:31:05
 * @version V1.0
 */
@Service
public class ZjLiangreSrcServiceImpl extends BaseServiceImpl<ZjLiangreSrc> implements ZjLiangreSrcService {
	private static Logger logger = Logger.getLogger(ZjLiangreSrcServiceImpl.class);

	@Resource
	ZjLiangreFlyService zjLiangreFlyService;

	@Override
	public void flySrcData(String[] ids, ZjLiangreSrc model) throws Exception {
		StringBuilder idstr = new StringBuilder("'-1'");
		for (String id : ids) {
			idstr.append(",'" + id + "'");
		}
		List<ZjLiangreSrc> ylist = this.findAll(" from ZjLiangreSrc where id in (" + idstr.toString() + ")");
		for (ZjLiangreSrc obj : ylist) {
			obj.setDataStatus("已提交");
			this.update(obj);
			ZjLiangreFly newObj = new ZjLiangreFly();
			newObj.setCircleId(obj.getCircleId());
			newObj.setDataStatus("已提交");
			newObj.setSrcId(obj.getId());
			newObj.setTestDate(obj.getTestDate() + " " + obj.getStartTime());
			newObj.setTest_ttfrValue(obj.getTest_ttfrValue());
			newObj.setTest_kgjgw(obj.getTest_kgjgw());
			newObj.setTest_sdjdw(obj.getTest_sdjdw());
			newObj.setSampleNum(obj.getSampleNum());
			newObj.setYqNum(obj.getTest_yqNum());
			newObj.setRecordTime(model.getRecordTime());
			newObj.setRecordUser(model.getRecordUser());
			zjLiangreFlyService.add(newObj);

		}

	}

	@Override
	public void synCollect(String deviceNum) throws Exception {
		try {
			String searchDate = ConfigUtils.dataToSimpleString(new Date());
			String searchTime = "00:00:00";
			List<ZjLiangreSrc> srcList = this.getTopRecord("from ZjLiangreSrc where testDate='" + searchDate + "'  order by recordTime desc", 1);
			if (srcList != null && srcList.size() > 0) {
				if (srcList.get(0).getTestDate() != null && !srcList.get(0).getTestDate().isEmpty() && srcList.get(0).getStartTime() != null
						&& !srcList.get(0).getStartTime().isEmpty()) {
					searchDate = srcList.get(0).getTestDate();
					searchTime = srcList.get(0).getStartTime();
				}
			}
			if (ThreadStaticVariable.DataCircleMap.get(deviceNum) != null) {
				DataCircle dataCircle = ThreadStaticVariable.DataCircleMap.get(deviceNum);
				Class.forName(dataCircle.getDriverClass());
				Connection conn = java.sql.DriverManager.getConnection(dataCircle.getJdbcUrl(), dataCircle.getUsername(), dataCircle.getPassword());
				String sql = "select 测试日期,开始时间,试样编号,弹筒发热量,仪器编号,空干基高位热值,收到基底位热值 from 发热量数据  where 测试日期>=#" + searchDate + "# and 开始时间>#" + searchTime + "# ";
				List<Object[]> objList = ConfigUtils.dbFindList(conn, sql);

				for (Object[] obj : objList) {
					String sampleNum=(obj[2] != null) ? obj[2].toString() : "";
					if (obj[0] != null&&(sampleNum.length()>=ThreadStaticVariable.invialLength)) {
						ZjLiangreSrc zjLingreSrc = new ZjLiangreSrc();
						zjLingreSrc.setTestDate((obj[0] != null) ? obj[0].toString().substring(0, 10) : "");
						zjLingreSrc.setStartTime((obj[1] != null) ? obj[1].toString() : "");
						zjLingreSrc.setSampleNum(sampleNum);
						zjLingreSrc.setTest_ttfrValue((obj[3] != null) ? obj[3].toString() : "");
						zjLingreSrc.setTest_yqNum((obj[4] != null) ? obj[4].toString() : "");
						zjLingreSrc.setTest_kgjgw((obj[5] != null) ? obj[5].toString() : "");
						zjLingreSrc.setTest_sdjdw((obj[6] != null) ? obj[6].toString() : "");
						zjLingreSrc.setFileName(deviceNum);
						zjLingreSrc.setDataStatus("原始数据");
						zjLingreSrc.setRecordTime(new Date());

						this.add(zjLingreSrc);
					}
				}
			}
		} catch (Exception e) {
			logger.error("ZJ-ZX-045" + "：" + e.getMessage());
		}
	}

	@Override
	public void synCollectHand(String deviceNum, String riqi) throws Exception {
		try {
			String searchTime = "00:00:00";
			List<ZjLiangreSrc> srcList = this.getTopRecord("from ZjLiangreSrc where testDate='" + riqi + "' order by recordTime desc", 1);
			if (srcList != null && srcList.size() > 0) {
				if (srcList.get(0).getTestDate() != null && !srcList.get(0).getTestDate().isEmpty() && srcList.get(0).getStartTime() != null
						&& !srcList.get(0).getStartTime().isEmpty()) {
					searchTime = srcList.get(0).getStartTime();
				}
			}
			if (ThreadStaticVariable.DataCircleMap.get(deviceNum) != null) {
				DataCircle dataCircle = ThreadStaticVariable.DataCircleMap.get(deviceNum);
				Class.forName(dataCircle.getDriverClass());
				Connection conn = java.sql.DriverManager.getConnection(dataCircle.getJdbcUrl(), dataCircle.getUsername(), dataCircle.getPassword());
				String sql = "select 测试日期,开始时间,试样编号,弹筒发热量,仪器编号,空干基高位热值,收到基低位热值  from 发热量数据  where 测试日期=#" + riqi + "# and 开始时间>#" + searchTime + "# ";
				List<Object[]> objList = ConfigUtils.dbFindList(conn, sql);

				for (Object[] obj : objList) {
					String sampleNum=(obj[2] != null) ? obj[2].toString() : "";					
					if (obj[0] != null&&(sampleNum.length()>=ThreadStaticVariable.invialLength)) {
						ZjLiangreSrc zjLingreSrc = new ZjLiangreSrc();
						zjLingreSrc.setTestDate((obj[0] != null) ? obj[0].toString().substring(0, 10) : "");
						zjLingreSrc.setStartTime((obj[1] != null) ? obj[1].toString() : "");
						zjLingreSrc.setSampleNum(sampleNum);
						zjLingreSrc.setTest_ttfrValue((obj[3] != null) ? obj[3].toString() : "");

						zjLingreSrc.setTest_yqNum((obj[4] != null) ? obj[4].toString() : "");
						zjLingreSrc.setTest_kgjgw((obj[5] != null) ? obj[5].toString() : "");
						zjLingreSrc.setTest_sdjdw((obj[6] != null) ? obj[6].toString() : "");
						zjLingreSrc.setFileName(deviceNum);
						zjLingreSrc.setDataStatus("原始数据");
						zjLingreSrc.setRecordTime(new Date());

						this.add(zjLingreSrc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ZJ-ZX-045" + "：" + e.getMessage());
		}

	}

	@Override
	public void del(String[] ids) throws Exception {
		for (String id : ids) {
			ZjLiangreSrc zjLingreSrc = this.getById(id);
			if ("原始数据".equals(zjLingreSrc.getDataStatus())) {
				zjLingreSrc.setDataStatus("作废数据");
				this.update(zjLingreSrc);
			}
		}
	}

	@Override
	public void recovery(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		for (String id : ids) {
			ZjLiangreSrc zjLingreSrc = this.getById(id);
			if ("作废数据".equals(zjLingreSrc.getDataStatus())) {
				zjLingreSrc.setDataStatus("原始数据");
				this.update(zjLingreSrc);
			}
		}
	}

	@Resource
	ZjHandInputFlyService zjHandInputFlyService;
	@Override
	public void checkAddSampleNum(String sampleNum) throws Exception {
	List<ZjLiangreSrc>  srcList=this.findAll("from ZjLiangreSrc where sampleNum='"+sampleNum+"'");
		if(srcList.size()==0){
			ZjLiangreSrc zjLiangreSrc=new ZjLiangreSrc();
			zjLiangreSrc.setDataStatus("原始数据");
			zjLiangreSrc.setSampleNum(sampleNum);
			zjLiangreSrc.setTestDate(ConfigUtils.dataToSimpleString(new Date()));
			this.add(zjLiangreSrc);
		}
		List<ZjHandInputFly>  handList=zjHandInputFlyService.findAll("from ZjHandInputFly where sampleNum='"+sampleNum+"'");
		if(handList.size()==0){
			ZjHandInputFly zjHandInputFly=new ZjHandInputFly();
			zjHandInputFly.setSampleNum(sampleNum);
			zjHandInputFly.setDataTime(ConfigUtils.dataToSimpleString(new Date()));
			zjHandInputFlyService.add(zjHandInputFly);
		}
	}
		
}
