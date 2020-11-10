package com.tried.zjsys.testDataSrc.action;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.lang.dsl.DSLMapParser.variable_definition2_return;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sun.mail.iap.Response;
import com.tried.base.action.BaseAction;
import com.tried.common.ConfigUtils;
import com.tried.common.Page;
import com.tried.zjsys.basics.model.DataWlInfo;
import com.tried.zjsys.basics.service.DataWlInfoService;
import com.tried.zjsys.testDataSrc.model.DataKeyMaxMin;
import com.tried.zjsys.testDataSrc.model.ViewHandInput;
import com.tried.zjsys.testDataSrc.model.ViewYuanliao;
import com.tried.zjsys.testDataSrc.model.YingguangFlyBxy;
import com.tried.zjsys.testDataSrc.model.YingguangSrcBxy;
import com.tried.zjsys.testDataSrc.model.YinguangFly;
import com.tried.zjsys.testDataSrc.model.ZjDingliuFly;
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiFly;
import com.tried.zjsys.testDataSrc.model.ZjHandInputFly;
import com.tried.zjsys.testDataSrc.model.ZjLiangreFly;
import com.tried.zjsys.testDataSrc.model.ZjTanliuFly;
import com.tried.zjsys.testDataSrc.service.DataKeyMaxMinService;
import com.tried.zjsys.testDataSrc.service.ViewHandInputService;
import com.tried.zjsys.testDataSrc.service.ViewRanliaoService;
import com.tried.zjsys.testDataSrc.service.ViewYuanliaoService;
import com.tried.zjsys.testDataSrc.service.YinguangFlyService;
import com.tried.zjsys.testDataSrc.service.ZjDingliuFlyService;
import com.tried.zjsys.testDataSrc.service.ZjGongyefenxiFlyService;
import com.tried.zjsys.testDataSrc.service.ZjHandInputFlyService;
import com.tried.zjsys.testDataSrc.service.ZjLiangreFlyService;
import com.tried.zjsys.testDataSrc.service.ZjTanliuFlyService;

import java.net.URLDecoder;

/**
 * @Description - 管理
 * @author sunlunan
 * @date 2020-07-08 14:56:17
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class ZjHandInputFlyAction extends BaseAction<ZjHandInputFly> {
	private static Logger logger = Logger.getLogger(ZjHandInputFlyAction.class);

	private static final long serialVersionUID = 1L;
	@Resource
	ZjHandInputFlyService zjHandInputFlyService;
	@Resource
	DataWlInfoService dataWlInfoService;
	@Resource
	DataKeyMaxMinService dataKeyMaxMinService;
	private String wlType;
	private String wlCode;

	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-07-08 14:56:17
	 * @version V1.0
	 */
	public void list() {
		try {
			if (strIsNotNull(model.getDataStatus())) {
				this.condition += " and dataStatus ='" + model.getDataStatus() + "'";
			}
			if (strIsNotNull(model.getSampleNum())) {
				this.condition += " and sampleNum like'%" + model.getSampleNum() + "%'";
			}
			if (strIsNotNull(model.getDataStatus())) {
				this.condition += " and dataStatus ='" + model.getDataStatus() + "'";
			}
			this.condition += this.getOrderColumn();
			outJsonData(zjHandInputFlyService
					.findPage(new Page<ZjHandInputFly>(page, rows), "from ZjHandInputFly where 1=1 " + this.condition)
					.getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}

	/**
	 * 根据物料代码获取物料表头
	 */
	public void wlHead() {
		try {

			String wlCode_encode = java.net.URLDecoder.decode(wlCode, "UTF-8");
			if (strIsNotNull(wlCode_encode)) {
				outJsonList(dataKeyMaxMinService.findAll("FROM DataKeyMaxMin where deviceName='" + wlCode_encode
						+ "' order by cast(viewpaiXu  as int)  asc "));
			} else {
				outJsonList(dataKeyMaxMinService.findAll());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	 * 获取物料信息
	 */

	@Resource
	ViewHandInputService viewHandInputService;
	@Resource
	ViewRanliaoService viewRanliaoService;
	@Resource
	ViewYuanliaoService viewYuanliaoService;

	public void listFly() {
		try {
			List<DataKeyMaxMin> keyList = dataKeyMaxMinService
					.findAll("FROM DataKeyMaxMin where deviceName='" + wlCode + "'");
			List<DataWlInfo> changeTypeList = dataWlInfoService
					.findAll("FROM DataWlInfo where wlCode ='" + wlCode + "'");
			String wlType = "";
			/*
			 * for(DataKeyMaxMin KEY :keyList){
			 * if(KEY.getFieldName().indexOf("gongye")!=-1){ wlType="燃料"; //燃料视图 }
			 * if(KEY.getFieldName().indexOf("yingguang")!=-1){ wlType="原料";//原料视图 } }
			 */
			for (DataWlInfo KEY : changeTypeList) {
				if ("原料".equals(KEY.getWlType().toString())) {
					wlType = "原料";
				}

				if ("手录".equals(KEY.getWlType().toString())) {
					wlType = "手录";
				}
			}
			if (strIsNotNull(getObjStartTime())) {
				this.condition += " and CONVERT(varchar(10), handInput_dataTime, 23) >='" + getObjStartTime() + "' ";
			}
			if (strIsNotNull(getObjEndTime())) {
				this.condition += " and CONVERT(varchar(10), handInput_dataTime, 23) <='" + getObjEndTime()  + "' ";
			}
			
			if (strIsNotNull(model.getDataStatus())) {
				this.condition += " and dataStatus='" + model.getDataStatus() + "' ";
			}

			if (strIsNotNull(getCompanyType())) {
				this.condition += " and belongcompany ='" + getCompanyType() + "' ";
			}

			if (strIsNotNull(model.getSampleNum())) {
				this.condition += " and handInput_sampleNum like '%" + model.getSampleNum() + "%' ";
			}
			this.condition += " and handInput_sampleNum like '" + wlCode + "%'";

			this.condition += this.getOrderColumn();

			/*
			 * if("燃料".equals(wlType)){
			 * outRowsData(viewRanliaoService.findAll("from ViewRanliao where 1=1  "+this.
			 * condition)); } else
			 */
			if ("原料".equals(wlType)) {
				outRowsData(viewYuanliaoService.findAll("from ViewYuanliao where  1=1  " + this.condition));
			} else {
				outRowsData(viewHandInputService.findAll("from ViewHandInput where  1=1   " + this.condition));
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	/**
	 * @Description 添加-
	 * @author sunlunan
	 * @date 2020-07-08 14:56:17
	 * @version V1.0
	 */
	public void add() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			model.setDataStatus("原始数据");
			zjHandInputFlyService.add(model);
			outSuccessJson("添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("添加失败");
		}
	}

	/**
	 * @Description 删除-
	 * @author sunlunan
	 * @date 2020-07-08 14:56:17
	 * @version V1.0
	 */
	@Resource
	YinguangFlyService yinguangFlyService;
	@Resource
	ZjDingliuFlyService zjDingliuFlyService;
	@Resource
	ZjGongyefenxiFlyService zjGongyefenxiFlyService;
	@Resource
	ZjLiangreFlyService zjLiangreFlyService;
	@Resource
	ZjTanliuFlyService zjTanliuFlyService;

	public void del() {
		try {
			ZjHandInputFly hand = zjHandInputFlyService.getById(recordId);
			List<String> listSql = new ArrayList<String>();
			//
			listSql.add("delete from tried_zj_handInput_fly where id='" + recordId + "'");

			listSql.add("delete from  tried_yinguang_fly where sampleNum='" + hand.getSampleNum() + "'");
			listSql.add(
					"update tried_yinguang_src  set dataStatus ='原始数据' where sampleNum='" + hand.getSampleNum() + "'");

			listSql.add("delete from  tried_zj_dingliu_fly where sample_no='" + hand.getSampleNum() + "'");
			listSql.add("update tried_zj_dingliu_src  set dataStatus ='原始数据' where sample_no='" + hand.getSampleNum()
					+ "'");

			listSql.add("delete from  tried_zj_gongyefenxi_fly where sampleNum='" + hand.getSampleNum() + "'");
			listSql.add("update tried_zj_gongyefenxi_src  set dataStatus ='原始数据' where sampleNum='"
					+ hand.getSampleNum() + "'");

			listSql.add("delete from  tried_zj_liangre_fly where sampleNum='" + hand.getSampleNum() + "'");
			listSql.add("update tried_zj_liangre_src  set dataStatus ='原始数据' where sampleNum='" + hand.getSampleNum()
					+ "'");

			listSql.add("delete from  tried_zj_tanliu_fly where sampleNum='" + hand.getSampleNum() + "'");
			listSql.add(
					"update tried_zj_tanliu_src  set dataStatus ='原始数据' where sampleNum='" + hand.getSampleNum() + "'");

			zjHandInputFlyService.dbBeatchSql(listSql);
// 			}
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}

	public static void main(String[] args) {
		String a = "SSSA2";

	}

	public void updateHand() {
		try {

			JSONObject row = JSONObject.fromObject(java.net.URLDecoder.decode(getRowsData(), "UTF-8"));
			Iterator<String> rowIt = row.keySet().iterator();
			Map<String, String> dataMap = new HashMap<String, String>();
			while (rowIt.hasNext()) {
				String key = (String) rowIt.next();

				String value = row.get(key).toString();
				key = key.replace("handInput_", "");
				dataMap.put(key, value);
			}

			List<DataWlInfo> changeTypeList = dataWlInfoService
					.findAll("FROM DataWlInfo where wlCode ='" + dataMap.get("currentwlCode").toString() + "'");
			String wlType = "";
			for (DataWlInfo KEY : changeTypeList) {
				if ("原料".equals(KEY.getWlType().toString())) {
					wlType = "原料";
				}

				if ("手录".equals(KEY.getWlType().toString())) {
					wlType = "手录";
				}
			}

			/**
			 * 更新 原料的 所属厂商
			 */
			if ("原料".equals(wlType)) {
				String currentBelongCom = (dataMap.containsKey("belongcompany")) ? dataMap.get("belongcompany") : "";
				String currentSampleNum = (dataMap.containsKey("sampleNum")) ? dataMap.get("sampleNum") : "";
				List addBelongComList = new ArrayList();
				addBelongComList.add("update tried_yinguang_fly  set belongcompany ='" + currentBelongCom
						+ "' where sampleNum='" + currentSampleNum + "'");
				addBelongComList.add("update tried_yinguang_src  set belongcompany ='" + currentBelongCom
						+ "' where sampleNum='" + currentSampleNum + "'");
				zjHandInputFlyService.dbBeatchSql(addBelongComList);
			}

			ZjHandInputFly hand = new ZjHandInputFly();
			if (dataMap.containsKey("id") && dataMap.get("id") != null && !dataMap.get("id").isEmpty()) {
				hand = zjHandInputFlyService.getById(dataMap.get("id"));
			}
			if (dataMap.containsKey("sampleNum")) {
				ZjHandInputFly hand1 = zjHandInputFlyService.getFirstRecordByField(
						"from ZjHandInputFly where sampleNum='" + dataMap.get("sampleNum") + "'");
				if (hand1 != null) {
					hand = hand1;
				}
			}

			hand.setSampleNum((dataMap.containsKey("sampleNum")) ? dataMap.get("sampleNum") : "");
			hand.setBelongcompany((dataMap.containsKey("belongcompany")) ? dataMap.get("belongcompany") : "");
			hand.setBurnLoss((dataMap.containsKey("burnLoss")) ? dataMap.get("burnLoss") : "");
			hand.setH2o((dataMap.containsKey("h2o")) ? dataMap.get("h2o") : "");
			hand.setH2o1((dataMap.containsKey("h2o1")) ? dataMap.get("h2o1") : "");
			hand.setH2o2((dataMap.containsKey("h2o2")) ? dataMap.get("h2o2") : "");
			hand.setFu200mm((dataMap.containsKey("fu200mm")) ? dataMap.get("fu200mm") : "");
			hand.setFeo((dataMap.containsKey("feo")) ? dataMap.get("feo") : "");
			hand.setM6_3((dataMap.containsKey("m6_3")) ? dataMap.get("m6_3") : "");
			hand.setM0_5((dataMap.containsKey("m0_5")) ? dataMap.get("m0_5") : "");
			hand.setFu200mu((dataMap.containsKey("fu200mu")) ? dataMap.get("fu200mu") : "");
			hand.setReactivity((dataMap.containsKey("reactivity")) ? dataMap.get("reactivity") : "");
			hand.setGjcs_rate((dataMap.containsKey("gjcs_rate")) ? dataMap.get("gjcs_rate") : "");
			hand.setMm0_5((dataMap.containsKey("mm0_5")) ? dataMap.get("mm0_5") : "");
			hand.setRalone((dataMap.containsKey("ralone")) ? dataMap.get("ralone") : "");
			hand.setMfe((dataMap.containsKey("mfe")) ? dataMap.get("mfe") : "");
			hand.setDy_80mm((dataMap.containsKey("dy_80mm")) ? dataMap.get("dy_80mm") : "");
			hand.setXy25mm((dataMap.containsKey("xy25mm")) ? dataMap.get("xy25mm") : "");
			hand.setCa_oh2((dataMap.containsKey("ca_oh2")) ? dataMap.get("ca_oh2") : "");
			hand.setFu10mm((dataMap.containsKey("fu10mm")) ? dataMap.get("fu10mm") : "");
			hand.setNahco3((dataMap.containsKey("nahco3")) ? dataMap.get("nahco3") : "");
			hand.setPh((dataMap.containsKey("ph")) ? dataMap.get("ph") : "");
			hand.setSic((dataMap.containsKey("sic")) ? dataMap.get("sic") : "");
			hand.setYlc((dataMap.containsKey("ylc")) ? dataMap.get("ylc") : "");
			hand.setPzrong((dataMap.containsKey("pzrong")) ? dataMap.get("pzrong") : "");
			hand.setJzjia((dataMap.containsKey("jzjia")) ? dataMap.get("jzjia") : "");
			hand.setXs_rate((dataMap.containsKey("xs_rate")) ? dataMap.get("xs_rate") : "");
			hand.setXl_size((dataMap.containsKey("xl_size")) ? dataMap.get("xl_size") : "");
			hand.setMtshi((dataMap.containsKey("mtshi")) ? dataMap.get("mtshi") : "");
			hand.setPalone((dataMap.containsKey("palone")) ? dataMap.get("palone") : "");
			hand.setSalone((dataMap.containsKey("salone")) ? dataMap.get("salone") : "");
			hand.setCalone((dataMap.containsKey("calone")) ? dataMap.get("calone") : "");
			hand.setSi((dataMap.containsKey("si")) ? dataMap.get("si") : "");
			hand.setMn((dataMap.containsKey("mn")) ? dataMap.get("mn") : "");
			hand.setAl((dataMap.containsKey("al")) ? dataMap.get("al") : "");
			hand.setCa((dataMap.containsKey("ca")) ? dataMap.get("ca") : "");
			hand.setBa((dataMap.containsKey("ba")) ? dataMap.get("ba") : "");
			hand.setMt((dataMap.containsKey("mt")) ? dataMap.get("mt") : "");
			hand.setM10((dataMap.containsKey("m10")) ? dataMap.get("m10") : "");
			hand.setM25((dataMap.containsKey("m25")) ? dataMap.get("m25") : "");
			hand.setMad((dataMap.containsKey("mad")) ? dataMap.get("mad") : "");
			hand.setAd((dataMap.containsKey("ad")) ? dataMap.get("ad") : "");
			hand.setVd((dataMap.containsKey("vd")) ? dataMap.get("vd") : "");
			hand.setVdaf((dataMap.containsKey("vdaf")) ? dataMap.get("vdaf") : "");
			hand.setFcad((dataMap.containsKey("fcad")) ? dataMap.get("fcad") : "");
			hand.setS((dataMap.containsKey("s")) ? dataMap.get("s") : "");
			hand.setGjgw((dataMap.containsKey("gjgw")) ? dataMap.get("gjgw") : "");
			hand.setDwrz((dataMap.containsKey("dwrz")) ? dataMap.get("dwrz") : "");

			hand.setCo2((dataMap.containsKey("co2")) ? dataMap.get("co2") : "");
			hand.setO2((dataMap.containsKey("o2")) ? dataMap.get("o2") : "");
			hand.setCo((dataMap.containsKey("co")) ? dataMap.get("co") : "");
			hand.setLidu((dataMap.containsKey("lidu")) ? dataMap.get("lidu") : "");
			hand.setCaf2((dataMap.containsKey("caf2")) ? dataMap.get("caf2") : "");

			// boolean rrr = dataMap.containsKey("na2o");
			// String nnnn = dataMap.get("na2o").toString();
			// System.out.println(rrr);
			// System.out.println(nnnn);
			hand.setNa2o((dataMap.containsKey("na2o")) ? dataMap.get("na2o") : "");

			hand.setTfe((dataMap.containsKey("tfe")) ? dataMap.get("tfe") : "");
			hand.setCao((dataMap.containsKey("cao")) ? dataMap.get("cao") : "");
			hand.setMgo((dataMap.containsKey("mgo")) ? dataMap.get("mgo") : "");
			hand.setSio2((dataMap.containsKey("sio2")) ? dataMap.get("sio2") : "");
			hand.setAl2o3((dataMap.containsKey("al2o3")) ? dataMap.get("al2o3") : "");
			hand.setTio2((dataMap.containsKey("tio2")) ? dataMap.get("tio2") : "");
			hand.setMno((dataMap.containsKey("mno")) ? dataMap.get("mno") : "");
			hand.setCr2o3((dataMap.containsKey("cr2o3")) ? dataMap.get("cr2o3") : "");
			hand.setK2o((dataMap.containsKey("k2o")) ? dataMap.get("k2o") : "");
			hand.setPb((dataMap.containsKey("pb")) ? dataMap.get("pb") : "");
			hand.setZn((dataMap.containsKey("zn")) ? dataMap.get("zn") : "");
			hand.setNi((dataMap.containsKey("ni")) ? dataMap.get("ni") : "");

			hand.setRecordTime(new Date());
			hand.setRecordUser(getCurrentUser().getId());
			if ("手录".equals(wlType)) {
				hand.setDataTime((dataMap.containsKey("dataTime")) ? dataMap.get("dataTime") : "");
			}
			hand.setDataStatus("已保存");
			if (dataMap.containsKey("id") && dataMap.get("id") != null && !dataMap.get("id").isEmpty()) {
				zjHandInputFlyService.update(hand);
			} else {
				// hand.setDataTime(ConfigUtils.dataToSimpleString(new Date()));--之前需要放开的
				zjHandInputFlyService.add(hand);
			}
			outSuccessJson("保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("保存失败");
		}
	}

	/**
	 * 恢复数据
	 */
	public void recovery() {
		try {
			ZjHandInputFly hand = zjHandInputFlyService.getById(recordId);
			hand.setRecordTime(new Date());
			hand.setRecordUser(getCurrentUser().getId());
			hand.setDataStatus("已提交");
			zjHandInputFlyService.update(hand);
			outSuccessJson("恢复成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("恢复失败");
		}
	}

	/**
	 * 发送数据
	 */
	public void fly() {

		try {

			ZjHandInputFly hand = zjHandInputFlyService.getById(recordId);
			hand.setRecordTime(new Date());
			hand.setRecordUser(getCurrentUser().getId());
			hand.setDataStatus("已发送");
			zjHandInputFlyService.update(hand);
			outSuccessJson("发送成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("发送失败");
		}

	}

	/**
	 * @Description 修改-
	 * @author sunlunan
	 * @date 2020-07-08 14:56:17
	 * @version V1.0
	 */
	public void edit() {
		try {
			model.setRecordTime(new Date());
			model.setRecordUser(getCurrentUser().getId());
			zjHandInputFlyService.update(model);
			outSuccessJson("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("修改失败");
		}
	}

	public void downloadexcell() {

		try {
			/*
			 * String currentwlCodeString_no =
			 * getRequest().getParameter("currentwlCode")==null?"":getRequest().getParameter
			 * ("currentwlCode").toString(); String currentwlCodeString =
			 * URLDecoder.decode(currentwlCodeString_no,"UTF-8");
			 * 
			 * String wlCodeString_no =
			 * getRequest().getParameter("wlCode")==null?"":getRequest().getParameter(
			 * "wlCode").toString(); String wlCodeString =
			 * URLDecoder.decode(wlCodeString_no,"UTF-8");
			 * 
			 * 
			 * String companyTypeString_no =
			 * getRequest().getParameter("companyType")==null?"":getRequest().getParameter(
			 * "companyType").toString(); String companyTypeString="";
			 * if("undefined".equals(companyTypeString_no)) { companyTypeString = ""; }else
			 * { companyTypeString = URLDecoder.decode(companyTypeString_no,"UTF-8"); }
			 * 
			 * 
			 * 
			 * String objStartTimeString =
			 * getRequest().getParameter("objStartTime")==null?"":getRequest().getParameter(
			 * "objStartTime").toString();
			 * 
			 * 
			 * String objEndTimeString =
			 * getRequest().getParameter("objEndTime")==null?"":getRequest().getParameter(
			 * "objEndTime").toString();
			 * 
			 * 
			 * String sampleNumString_on =
			 * getRequest().getParameter("sampleNum")==null?"":getRequest().getParameter(
			 * "sampleNum").toString(); String sampleNumString =
			 * URLDecoder.decode(sampleNumString_on,"UTF-8");
			 * 
			 */
			String objStartTimeString = model.getObjStartTimeString();
			String objEndTimeString = model.getObjEndTimeString();
			String currentwlCodeString = model.getCurrentwlCodeString()==null?"":model.getCurrentwlCodeString();
			String wlCodeString = model.getWlCodeString()==null?"":model.getWlCodeString();
			String companyTypeString="";
			if("undefined".equals(model.getCompanyTypeString())) {
				companyTypeString = "";
			}else {
				companyTypeString = model.getCompanyTypeString()==null?"":model.getCompanyTypeString();
			}
			String sampleNumString = model.getSampleNumString()==null?"":model.getSampleNumString();
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFSheet sheet = workbook.createSheet("sheet");
			sheet.setColumnWidth(0, 256*30+184);
			sheet.setColumnWidth(1, 256*30+184);
			sheet.setColumnWidth(2, 256*50+184);
			// 第一行
			HSSFRow row0 = sheet.createRow(0);
			HSSFCell cell_00 = row0.createCell(0);
			cell_00.setCellStyle(style);
			cell_00.setCellValue("选中类型：");

			HSSFCell cell_01 = row0.createCell(1);
			cell_01.setCellStyle(style);
			cell_01.setCellValue(currentwlCodeString);

			Date dd = new Date();
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sim.format(dd);

			HSSFCell cell_02 = row0.createCell(2);
			cell_02.setCellStyle(style);
			cell_02.setCellValue("日期从："+objStartTimeString + "到" + objEndTimeString);

		
			/*
			 * HSSFCell cell_03 = row0.createCell(3); cell_03.setCellStyle(style);
			 * cell_03.setCellValue(row.get("objStartTime").toString() + "到" +
			 * row.get("objEndTime"));
			 */

			// 第二行
			HSSFRow row1 = sheet.createRow(1);
			HSSFCell cell_10 = row1.createCell(0);
			cell_10.setCellStyle(style);
			cell_10.setCellValue("样品编号");

			HSSFCell cell_11 = row1.createCell(1);
			cell_11.setCellStyle(style);
			cell_11.setCellValue("分析日期");

			HSSFCell cell_12 = row1.createCell(2);
			cell_12.setCellStyle(style);
			cell_12.setCellValue("所属厂");

			// 填充表头动态元素
			List<DataKeyMaxMin> datakeymaxminList = dataKeyMaxMinService
					.findAll("FROM DataKeyMaxMin where deviceName ='" + currentwlCodeString
							+ "'  order by cast(viewpaiXu  as int) asc  ");
			int biaozhi = 3;
			int biaozhisize = datakeymaxminList.size() + 3;
			List list = new ArrayList();
			for (int i = 0; i < datakeymaxminList.size(); i++) {
				String currentStringHead = datakeymaxminList.get(i).getKeyName();
				String fieldName = datakeymaxminList.get(i).getFieldName();
				HSSFCell forrowcell = row1.createCell(biaozhi);
				forrowcell.setCellStyle(style);
				forrowcell.setCellValue(currentStringHead);
				list.add(fieldName);
				biaozhi++;
			}

			HSSFCell cell999 = row1.createCell(biaozhisize);
			cell999.setCellStyle(style);
			cell999.setCellValue("操作时间");

			HSSFCell cell998 = row1.createCell(biaozhisize + 1);
			cell998.setCellStyle(style);
			cell998.setCellValue("操作人");

			/***************************************************************/
			/*****************************************************************/

			List<DataKeyMaxMin> keyList = dataKeyMaxMinService
					.findAll("FROM DataKeyMaxMin where deviceName='" + wlCodeString + "'");
			List<DataWlInfo> changeTypeList = dataWlInfoService
					.findAll("FROM DataWlInfo where wlCode ='" + wlCodeString  + "'");
			String wlType = "";
			this.condition = "";
			for (DataWlInfo KEY : changeTypeList) {
				if ("原料".equals(KEY.getWlType().toString())) {
					wlType = "原料";
				}

				if ("手录".equals(KEY.getWlType().toString())) {
					wlType = "手录";
				}
			}
			if (strIsNotNull(objStartTimeString)) {
				this.condition += " and CONVERT(varchar(10), handInput_dataTime, 23) >= '" + objStartTimeString + "' ";
			}
			if (strIsNotNull(objEndTimeString)) {
				this.condition += " and CONVERT(varchar(10), handInput_dataTime, 23) <= '" + objEndTimeString  + "' ";
			}

			if (strIsNotNull(companyTypeString)) {
				this.condition += " and belongcompany ='" + companyTypeString  + "' ";
			}

			if (strIsNotNull(sampleNumString )) {
				this.condition += " and handInput_sampleNum like '%" + sampleNumString  + "%' ";
			}
			this.condition += " and handInput_sampleNum like '" + wlCodeString  + "%'";
			this.condition +=" order by  handInput_dataTime,belongcompany desc ";
			// this.condition += this.getOrderColumn();

			/*
			 * if("燃料".equals(wlType)){
			 * outRowsData(viewRanliaoService.findAll("from ViewRanliao where 1=1  "+this.
			 * condition)); } else
			 */
			if ("原料".equals(wlType)) {
				List<ViewYuanliao> findAllViewYuanliao = viewYuanliaoService
						.findAll("from ViewYuanliao where  1=1  " + this.condition);
				List<Map> convertListBean2ListMapViewYuanliao = convertListBean2ListMap(findAllViewYuanliao,
						ViewYuanliao.class);

				int yuanliaoline = 2;
				for (int uu = 0; uu < convertListBean2ListMapViewYuanliao.size(); uu++) {
					HSSFRow rowrecord = sheet.createRow(yuanliaoline);
					Map yunaliaoMap = convertListBean2ListMapViewYuanliao.get(uu);

					// 分割线
					HSSFCell cellyuanexceptYuansu = rowrecord.createCell(0);
					cellyuanexceptYuansu.setCellStyle(style);
					cellyuanexceptYuansu.setCellValue(yunaliaoMap.get("handInput_sampleNum") == null ? ""
							: yunaliaoMap.get("handInput_sampleNum").toString());

					HSSFCell cellyuanexceptYuansu1 = rowrecord.createCell(1);
					cellyuanexceptYuansu1.setCellStyle(style);
					cellyuanexceptYuansu1.setCellValue(yunaliaoMap.get("handInput_dataTime") == null ? ""
							: yunaliaoMap.get("handInput_dataTime").toString());

					HSSFCell cellyuanexceptYuansu2 = rowrecord.createCell(2);
					cellyuanexceptYuansu2.setCellStyle(style);
					cellyuanexceptYuansu2.setCellValue(yunaliaoMap.get("belongcompany") == null ? ""
							: yunaliaoMap.get("belongcompany").toString());

					int biaozhiyuanliao = 3;
					int biaozhiyuanliaolistsize = list.size() + 3;

					for (int hh = 0; hh < list.size(); hh++) {
						HSSFCell cellyuanliao = rowrecord.createCell(biaozhiyuanliao);
						cellyuanliao.setCellStyle(style);
						cellyuanliao.setCellValue(
								yunaliaoMap.get(list.get(hh)) == null ? "" : yunaliaoMap.get(list.get(hh)).toString());

						biaozhiyuanliao++;
					}

					// 分割线
					HSSFCell cellyuanexceptYuansu3 = rowrecord.createCell(biaozhiyuanliaolistsize);
					cellyuanexceptYuansu3.setCellStyle(style);

					cellyuanexceptYuansu3.setCellValue(
							yunaliaoMap.get("recordTime") == null ? "" : yunaliaoMap.get("recordTime").toString());

					HSSFCell cellyuanexceptYuansu4 = rowrecord.createCell(biaozhiyuanliaolistsize + 1);
					cellyuanexceptYuansu4.setCellStyle(style);
					cellyuanexceptYuansu4.setCellValue(yunaliaoMap.get("recordUserName") == null ? ""
							: yunaliaoMap.get("recordUserName").toString());

					yuanliaoline++;

				}
			} else {
				List<ViewHandInput> findAllViewHandInput = viewHandInputService
						.findAll("from ViewHandInput where  1=1   " + this.condition);

				List<Map> convertListBean2ListMapViewHandInput = convertListBean2ListMap(findAllViewHandInput,
						ViewHandInput.class);

				int handInputLine = 2;
				for (int uu = 0; uu < convertListBean2ListMapViewHandInput.size(); uu++) {
					HSSFRow rowrecord = sheet.createRow(handInputLine);
					Map handInputMap = convertListBean2ListMapViewHandInput.get(uu);

					// 分割线
					HSSFCell cellyuanexceptHandInput = rowrecord.createCell(0);
					cellyuanexceptHandInput.setCellStyle(style);
					cellyuanexceptHandInput.setCellValue(handInputMap.get("handInput_sampleNum") == null ? ""
							: handInputMap.get("handInput_sampleNum").toString());

					HSSFCell cellyuanexceptHandInput1 = rowrecord.createCell(1);
					cellyuanexceptHandInput1.setCellStyle(style);
					cellyuanexceptHandInput1.setCellValue(handInputMap.get("handInput_dataTime") == null ? ""
							: handInputMap.get("handInput_dataTime").toString());

					HSSFCell cellyuanexceptHandInput2 = rowrecord.createCell(2);
					cellyuanexceptHandInput2.setCellStyle(style);
					cellyuanexceptHandInput2.setCellValue(handInputMap.get("belongcompany") == null ? ""
							: handInputMap.get("belongcompany").toString());

					int biaozhihandInput = 3;
					int biaozhihandInputlistsize = list.size() + 3;

					for (int hh = 0; hh < list.size(); hh++) {
						HSSFCell cellyuanliao = rowrecord.createCell(biaozhihandInput);
						cellyuanliao.setCellStyle(style);
						cellyuanliao.setCellValue(handInputMap.get(list.get(hh)) == null ? ""
								: handInputMap.get(list.get(hh)).toString());

						biaozhihandInput++;
					}

					// 分割线
					HSSFCell cellyuanexceptHandInput3 = rowrecord.createCell(biaozhihandInputlistsize);
					cellyuanexceptHandInput3.setCellStyle(style);

					cellyuanexceptHandInput3.setCellValue(
							handInputMap.get("recordTime") == null ? "" : handInputMap.get("recordTime").toString());

					HSSFCell cellyuanexceptHandInput4 = rowrecord.createCell(biaozhihandInputlistsize + 1);
					cellyuanexceptHandInput4.setCellStyle(style);
					cellyuanexceptHandInput4.setCellValue(handInputMap.get("recordUserName") == null ? ""
							: handInputMap.get("recordUserName").toString());

					handInputLine++;

				}

			}

			/*************************************************************/
			
		
		      ByteArrayOutputStream os = new ByteArrayOutputStream();
		      workbook.write(os);
		      byte[] content = os.toByteArray();
		      InputStream is = new ByteArrayInputStream(content);
		      // 设置response参数，可以打开下载页面
		      getResponse().reset();
		      getResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
		      getResponse().setHeader("Content-Disposition", "attachment;filename="+ new String((wlCodeString  + ".xls").getBytes(), "iso-8859-1"));
			
		      ServletOutputStream out = getResponse().getOutputStream();
		      BufferedInputStream bis = null;
		      BufferedOutputStream bos = null;

		      try {
		        bis = new BufferedInputStream(is);
		        bos = new BufferedOutputStream(out);
		        byte[] buff = new byte[2048];
		        int bytesRead;
		        // Simple read/write loop.
		        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
		          bos.write(buff, 0, bytesRead);
		        }
		      } catch (Exception e) {
		        // TODO: handle exception
		        e.printStackTrace();
		      } finally {
		        if (bis != null)
		          bis.close();
		        if (bos != null)
		          bos.close();
		      }


			  
			  
			 

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static <T> List<Map> convertListBean2ListMap(List<T> beanList, Class<T> T) throws Exception {
		List<Map> mapList = new ArrayList();
		for (int i = 0, n = beanList.size(); i < n; i++) {
			Object bean = beanList.get(i);
			Map map = convertBean2Map(bean);
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map convertBean2Map(Object bean) throws Exception {
		Class<? extends Object> type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!"class".equals(propertyName)) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, null);
				}
			}
		}
		return returnMap;
	}

	public String getWlType() {
		return wlType;
	}

	public void setWlType(String wlType) {
		this.wlType = wlType;
	}

	public String getWlCode() {
		return wlCode;
	}

	public void setWlCode(String wlCode) {
		this.wlCode = wlCode;
	}

}
