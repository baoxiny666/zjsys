package com.tried.zjsys.testDataSrc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.tried.base.action.BaseAction;
import com.tried.common.ConfigUtils;
import com.tried.common.Page;
import com.tried.zjsys.basics.model.DataWlInfo;
import com.tried.zjsys.basics.service.DataWlInfoService;
import com.tried.zjsys.testDataSrc.model.DataKeyMaxMin;
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
	private	String wlCode;
	/**
	 * @Description 分页显示-
	 * @author sunlunan
	 * @date 2020-07-08 14:56:17
	 * @version V1.0
	 */
	public void list() {
		try {
			if(strIsNotNull(model.getDataStatus())){
				this.condition += " and dataStatus ='" + model.getDataStatus()+ "'";
			}
			if(strIsNotNull(model.getSampleNum())){
				this.condition += " and sampleNum like'%" + model.getSampleNum()+ "%'";
			}
			if(strIsNotNull(model.getDataStatus())){
				this.condition += " and dataStatus ='" + model.getDataStatus()+ "'";
			}
			this.condition +=  this.getOrderColumn();
			outJsonData(zjHandInputFlyService.findPage(new Page<ZjHandInputFly>(page, rows), "from ZjHandInputFly where 1=1 " + this.condition).getResult());
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("失败");
		}
	}
		
			
	
	/**
	 * 根据物料代码获取物料表头
	 */
	public void wlHead(){
		try {

			String wlCode_encode = java.net.URLDecoder.decode(wlCode, "UTF-8");
			if(strIsNotNull(wlCode_encode)){
				outJsonList(dataKeyMaxMinService.findAll("FROM DataKeyMaxMin where deviceName='"+wlCode_encode+"' order by cast(viewpaiXu  as int)  asc "));
			}else{
				outJsonList(dataKeyMaxMinService.findAll());
			}
		}catch (Exception e) {
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
	
	public void listFly(){
		try {
			List<DataKeyMaxMin> keyList=dataKeyMaxMinService.findAll("FROM DataKeyMaxMin where deviceName='"+wlCode+"'");
			String wlType="";
			for(DataKeyMaxMin KEY :keyList){
				if(KEY.getFieldName().indexOf("gongye")!=-1){
					wlType="燃料"; //燃料视图
				}
				if(KEY.getFieldName().indexOf("yingguang")!=-1){
					wlType="原料";//原料视图
				}
			}
			 if(strIsNotNull(getObjStartTime())){
				 this.condition+=" and handInput_dataTime>='"+getObjStartTime()+"' ";
			 } if(strIsNotNull(getObjEndTime())){
				 this.condition+=" and handInput_dataTime<='"+getObjEndTime()+"' "; 
			 }
			 if(strIsNotNull(model.getDataStatus())){
				 this.condition+=" and dataStatus='"+model.getDataStatus()+"' "; 
			 }
			 if(strIsNotNull(model.getSampleNum())){
				 this.condition+=" and handInput_sampleNum like '%"+model.getSampleNum()+"%' "; 
			 }
			 this.condition+=" and handInput_sampleNum like '"+wlCode+"%'";
			
			 this.condition +=  this.getOrderColumn();
			 
			if("燃料".equals(wlType)){
				outRowsData(viewRanliaoService.findAll("from ViewRanliao where 1=1  "+this.condition));
			}
			else if("原料".equals(wlType)){
				outRowsData(viewYuanliaoService.findAll("from ViewYuanliao where  1=1  "+this.condition));
			}else{
				outRowsData(viewHandInputService.findAll("from ViewHandInput where  1=1   "+this.condition));
			}
		
		}catch (Exception e) {
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
		try{
			    ZjHandInputFly hand= zjHandInputFlyService.getById(recordId);
 				List<String> listSql=new ArrayList<String>();
 				//
 				listSql.add("delete from tried_zj_handInput_fly where id='"+recordId+"'");
 				
 				listSql.add("delete from  tried_yinguang_fly where sampleNum='"+hand.getSampleNum()+"'");
 				listSql.add("update tried_yinguang_src  set dataStatus ='原始数据' where sampleNum='"+hand.getSampleNum()+"'");
 				
 				listSql.add("delete from  tried_zj_dingliu_fly where sample_no='"+hand.getSampleNum()+"'");
 				listSql.add("update tried_zj_dingliu_src  set dataStatus ='原始数据' where sample_no='"+hand.getSampleNum()+"'");
 				
 				
 				listSql.add("delete from  tried_zj_gongyefenxi_fly where sampleNum='"+hand.getSampleNum()+"'");
 				listSql.add("update tried_zj_gongyefenxi_src  set dataStatus ='原始数据' where sampleNum='"+hand.getSampleNum()+"'");
 				
 				
 				listSql.add("delete from  tried_zj_liangre_fly where sampleNum='"+hand.getSampleNum()+"'");
 				listSql.add("update tried_zj_liangre_src  set dataStatus ='原始数据' where sampleNum='"+hand.getSampleNum()+"'");
 				
 				listSql.add("delete from  tried_zj_tanliu_fly where sampleNum='"+hand.getSampleNum()+"'");
 				listSql.add("update tried_zj_tanliu_src  set dataStatus ='原始数据' where sampleNum='"+hand.getSampleNum()+"'");
 			 
 				zjHandInputFlyService.dbBeatchSql(listSql);
// 			}
			outSuccessJson("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			outErrorJson("删除失败");
		}
	}
	
	public static void main(String[] args) {
		String a="SSSA2";
		
	}
 
	public void updateHand() {
		try {
			
			 JSONObject row =JSONObject.fromObject( java.net.URLDecoder.decode(getRowsData(),"UTF-8"));
		    	 Iterator<String> rowIt= row.keySet().iterator();
		    	 Map<String,String> dataMap=new HashMap<String, String>();
		    	 while(rowIt.hasNext()){
		    		String key=(String)rowIt.next();
		    		
		    		String value=row.get(key).toString();
		    		key=key.replace("handInput_", "");
		    		dataMap.put(key, value);
		    	}
		    	 ZjHandInputFly hand=new ZjHandInputFly();
		    	 if(dataMap.containsKey("id")&&dataMap.get("id")!=null&&!dataMap.get("id").isEmpty()){
		    		 hand= zjHandInputFlyService.getById(dataMap.get("id"));
		    	 } 
		    	if(dataMap.containsKey("sampleNum")){
		    		ZjHandInputFly	hand1= zjHandInputFlyService.getFirstRecordByField("from ZjHandInputFly where sampleNum='"+dataMap.get("sampleNum")+"'");
		    		if(hand1!=null){
		    			hand=hand1;
		    		}
		    	}
		    	 hand.setSampleNum((dataMap.containsKey("sampleNum"))?dataMap.get("sampleNum"):"");
		    	 hand.setBurnLoss((dataMap.containsKey("burnLoss"))?dataMap.get("burnLoss"):"");
		    	 hand.setH2o((dataMap.containsKey("h2o"))?dataMap.get("h2o"):"");
		    	 hand.setH2o1((dataMap.containsKey("h2o1"))?dataMap.get("h2o1"):"");
		    	 hand.setH2o2((dataMap.containsKey("h2o2"))?dataMap.get("h2o2"):"");
		    	 hand.setFu200mm((dataMap.containsKey("fu200mm"))?dataMap.get("fu200mm"):"");
		    	 hand.setFeo((dataMap.containsKey("feo"))?dataMap.get("feo"):"");
		    	 hand.setM6_3((dataMap.containsKey("m6_3"))?dataMap.get("m6_3"):"");
		    	 hand.setM0_5((dataMap.containsKey("m0_5"))?dataMap.get("m0_5"):"");
		    	 hand.setFu200mu((dataMap.containsKey("fu200mu"))?dataMap.get("fu200mu"):"");
		    	 hand.setReactivity((dataMap.containsKey("reactivity"))?dataMap.get("reactivity"):"");
		    	 hand.setGjcs_rate((dataMap.containsKey("gjcs_rate"))?dataMap.get("gjcs_rate"):"");
		    	 hand.setMm0_5((dataMap.containsKey("mm0_5"))?dataMap.get("mm0_5"):"");
		    	 hand.setRalone((dataMap.containsKey("ralone"))?dataMap.get("ralone"):"");
		    	 hand.setMfe((dataMap.containsKey("mfe"))?dataMap.get("mfe"):"");
		    	 hand.setDy_80mm((dataMap.containsKey("dy_80mm"))?dataMap.get("dy_80mm"):"");
		    	 hand.setXy25mm((dataMap.containsKey("xy25mm"))?dataMap.get("xy25mm"):"");
		    	 hand.setCa_oh2((dataMap.containsKey("ca_oh2"))?dataMap.get("ca_oh2"):"");
		    	 hand.setFu10mm((dataMap.containsKey("fu10mm"))?dataMap.get("fu10mm"):"");
		    	 hand.setNahco3((dataMap.containsKey("nahco3"))?dataMap.get("nahco3"):"");
		    	 hand.setPh((dataMap.containsKey("ph"))?dataMap.get("ph"):"");
		    	 hand.setSic((dataMap.containsKey("sic"))?dataMap.get("sic"):"");
		    	 hand.setYlc((dataMap.containsKey("ylc"))?dataMap.get("ylc"):"");
		    	 hand.setPzrong((dataMap.containsKey("pzrong"))?dataMap.get("pzrong"):"");
		    	 hand.setJzjia((dataMap.containsKey("jzjia"))?dataMap.get("jzjia"):"");
		    	 hand.setXs_rate((dataMap.containsKey("xs_rate"))?dataMap.get("xs_rate"):"");
		    	 hand.setXl_size((dataMap.containsKey("xl_size"))?dataMap.get("xl_size"):"");
		    	 hand.setMtshi((dataMap.containsKey("mtshi"))?dataMap.get("mtshi"):"");
		    	 hand.setPalone((dataMap.containsKey("palone"))?dataMap.get("palone"):"");
		    	 hand.setSalone((dataMap.containsKey("salone"))?dataMap.get("salone"):"");
		    	 hand.setCalone((dataMap.containsKey("calone"))?dataMap.get("calone"):"");
		    	 hand.setSi((dataMap.containsKey("si"))?dataMap.get("si"):"");
		    	 hand.setMn((dataMap.containsKey("mn"))?dataMap.get("mn"):"");
		    	 hand.setAl((dataMap.containsKey("al"))?dataMap.get("al"):"");
		    	 hand.setCa((dataMap.containsKey("ca"))?dataMap.get("ca"):"");
		    	 hand.setBa((dataMap.containsKey("ba"))?dataMap.get("ba"):"");
		    	 hand.setMt((dataMap.containsKey("mt"))?dataMap.get("mt"):"");
		    	 hand.setM10((dataMap.containsKey("m10"))?dataMap.get("m10"):"");
		    	 hand.setM25((dataMap.containsKey("m25"))?dataMap.get("m25"):"");
		    	 hand.setMad((dataMap.containsKey("mad"))?dataMap.get("mad"):"");
		    	 hand.setAd((dataMap.containsKey("ad"))?dataMap.get("ad"):"") ;
		    	 hand.setVd((dataMap.containsKey("vd"))?dataMap.get("vd"):"");
		    	 hand.setVdaf((dataMap.containsKey("vdaf"))?dataMap.get("vdaf"):"");
		    	 hand.setFcad((dataMap.containsKey("fcad"))?dataMap.get("fcad"):"");
		    	 hand.setS((dataMap.containsKey("s"))?dataMap.get("s"):"");
		    	 hand.setGjgw((dataMap.containsKey("gjgw"))?dataMap.get("gjgw"):"");
		    	 hand.setDwrz((dataMap.containsKey("dwrz"))?dataMap.get("dwrz"):"");
		    	 
		    	 hand.setCo2((dataMap.containsKey("co2"))?dataMap.get("co2"):"");
		    	 hand.setO2((dataMap.containsKey("o2"))?dataMap.get("o2"):"");
		    	 hand.setCo((dataMap.containsKey("co"))?dataMap.get("co"):"");
		    	 hand.setLidu((dataMap.containsKey("lidu"))?dataMap.get("lidu"):"");
		    	 
		    	 //boolean rrr = dataMap.containsKey("na2o");
		    	 //String nnnn  = dataMap.get("na2o").toString();
		    	 //System.out.println(rrr);
		    	 //System.out.println(nnnn);
		    	 hand.setNa2o((dataMap.containsKey("na2o"))?dataMap.get("na2o"):"");
		    	
		    	 
		    	 hand.setTfe((dataMap.containsKey("tfe"))?dataMap.get("tfe"):"");
		    	 hand.setCao((dataMap.containsKey("cao"))?dataMap.get("cao"):"");
		    	 hand.setMgo((dataMap.containsKey("mgo"))?dataMap.get("mgo"):"");
		    	 hand.setSio2((dataMap.containsKey("sio2"))?dataMap.get("sio2"):"");
		    	 hand.setAl2o3((dataMap.containsKey("al2o3"))?dataMap.get("al2o3"):"");
		    	 hand.setTio2((dataMap.containsKey("tio2"))?dataMap.get("tio2"):"");
		    	 hand.setMno((dataMap.containsKey("mno"))?dataMap.get("mno"):"");
		    	 hand.setCr2o3((dataMap.containsKey("cr2o3"))?dataMap.get("cr2o3"):"");
		    	 hand.setK2o((dataMap.containsKey("k2o"))?dataMap.get("k2o"):"");
		    	 hand.setPb((dataMap.containsKey("pb"))?dataMap.get("pb"):"");
		    	 hand.setZn((dataMap.containsKey("zn"))?dataMap.get("zn"):"");
		    	 hand.setNi((dataMap.containsKey("ni"))?dataMap.get("ni"):"");
		  
		    	 
		    	 hand.setRecordTime(new Date());
		    	 hand.setRecordUser(getCurrentUser().getId());
		    	 hand.setDataStatus("已提交");
		    	 if(dataMap.containsKey("id")&&dataMap.get("id")!=null&&!dataMap.get("id").isEmpty()){
		    		   zjHandInputFlyService.update(hand);
		    	 } else{
		    		    hand.setDataTime(ConfigUtils.dataToSimpleString(new Date()));
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
			ZjHandInputFly hand= zjHandInputFlyService.getById(recordId);
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
	public void fly(){

		try {
			
			ZjHandInputFly hand= zjHandInputFlyService.getById(recordId);
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
