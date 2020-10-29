package com.tried.zjsys.testDataSrc.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.common.FileCommonUtils;
import com.tried.zjsys.basics.model.DataCircle;
import com.tried.zjsys.basics.model.DataWlInfo;
import com.tried.zjsys.basics.service.DataCircleService;
import com.tried.zjsys.basics.service.DataWlInfoService;
import com.tried.zjsys.basics.thread.ThreadStaticVariable;
import com.tried.zjsys.testDataSrc.model.YingguangFlyBxy;
import com.tried.zjsys.testDataSrc.model.YingguangSrcBxy;
import com.tried.zjsys.testDataSrc.model.ZjTanliuSrc;
import com.tried.zjsys.testDataSrc.service.YingguangFlyBxyService;
import com.tried.zjsys.testDataSrc.service.YingguangSrcBxyService;
import com.tried.zjsys.testDataSrc.service.YinguangFlyService;
import com.tried.zjsys.testDataSrc.service.YinguangSrcService;
import com.tried.zjsys.testDataSrc.service.ZjLiangreSrcService;
/**
 * @Description - 服务接口实现
 * @author sunlunan
 * @date 2020-06-23 09:30:34
 * @version V1.0
 */
@Service
public class YingguangSrcBxyServiceImpl extends BaseServiceImpl<YingguangSrcBxy> implements YingguangSrcBxyService {
	private static Logger logger = Logger.getLogger(YingguangSrcBxyServiceImpl.class);
	@Resource
	DataCircleService dataCircleService;
	@Resource
	YingguangFlyBxyService yingguangFlyBxyService;
	@Resource
	ZjLiangreSrcService zjLiangreSrcService;
	@Resource
	DataWlInfoService dataWlInfoService;
	/**
	 * 判断记录是否重复
	 * @param midMap
	 * @return
	 * @throws Exception 
	 */
	private Map<String, String> getSampleDbMap(String dateTime,String deviceNum) throws Exception {
		Map<String, String> srcMap=new HashMap<String, String>();		
		List<YingguangSrcBxy> resList=this.findAll(" from YingguangSrcBxy where substring(dataTime, 0, 11)='"+dateTime+"' and deviceNum='"+deviceNum+"'");
		for(YingguangSrcBxy src:resList){
			srcMap.put(src.getSampleNum()+src.getDataTime(), src.getDataTime());	
		}
		return srcMap;
	}

   /**
    * 发送数据
    */
	@Override
	public void flySrcData(String[] ids,YingguangSrcBxy model) throws Exception {
		StringBuilder idstr=new StringBuilder("'-1'");
      for(String id:ids){
    	  idstr.append(",'"+id+"'");	
      } 
	 List<YingguangSrcBxy> ylist=this.findAll(" from YingguangSrcBxy where id in ("+idstr.toString()+")");
	 for(YingguangSrcBxy obj:ylist){		 
		 obj.setDataStatus("已提交"); 	 
		 this.update(obj);		 
		 YingguangFlyBxy newObj=new YingguangFlyBxy();
		 newObj.setCircleId(obj.getCircleId());
		 newObj.setDataStatus("已提交");
		 newObj.setSrcId(obj.getId());
		 newObj.setTest_Al2O3(obj.getTest_Al2O3()!=null?obj.getTest_Al2O3():"");
		 newObj.setTest_As(obj.getTest_As()!=null?obj.getTest_As():"");
		 newObj.setTest_Au2O(obj.getTest_Au2O()!=null?obj.getTest_Au2O():"");
		 newObj.setTest_CaO(obj.getTest_CaO()!=null?obj.getTest_CaO():"");
		 newObj.setTest_Co2O3(obj.getTest_Co2O3()!=null?obj.getTest_Co2O3():"");
		 newObj.setTest_Cr2O3(obj.getTest_Cr2O3()!=null?obj.getTest_Cr2O3():"");
		 newObj.setTest_Cu(obj.getTest_Cu()!=null?obj.getTest_Cu():"");
		 newObj.setTest_Fe2O3(obj.getTest_Fe2O3()!=null?obj.getTest_Fe2O3():"");
		 newObj.setTest_K2O(obj.getTest_K2O()!=null?obj.getTest_K2O():"");
		 newObj.setTest_MgO(obj.getTest_MgO()!=null?obj.getTest_MgO():"");
		 newObj.setTest_MnO(obj.getTest_MnO()!=null?obj.getTest_MnO():"");
		 newObj.setTest_Na2O(obj.getTest_Na2O()!=null?obj.getTest_Na2O():"");
		 newObj.setTest_Ni(obj.getTest_Ni()!=null?obj.getTest_Ni():"");
		 newObj.setTest_P(obj.getTest_P()!=null?obj.getTest_P():"");
		 newObj.setTest_Pb(obj.getTest_Pb()!=null?obj.getTest_Pb():"");
		 newObj.setTest_R2(obj.getTest_R2()!=null?obj.getTest_R2():"");
		 newObj.setTest_S(obj.getTest_S()!=null?obj.getTest_S():"");
		 newObj.setTest_SiO2(obj.getTest_SiO2()!=null?obj.getTest_SiO2():"");
		 newObj.setTest_TFe(obj.getTest_TFe()!=null?obj.getTest_TFe():"");
		 newObj.setTest_TiO2(obj.getTest_TiO2()!=null?obj.getTest_TiO2():"");
		 newObj.setTest_V2O5(obj.getTest_V2O5()!=null?obj.getTest_V2O5():"");
		 newObj.setTest_Zn(obj.getTest_Zn()!=null?obj.getTest_Zn():"");		
		 //20201015 boxy 新加
		 newObj.setTest_R(obj.getTest_R()!=null?obj.getTest_R():"");	
		 newObj.setTest_P2O5(obj.getTest_P2O5()!=null?obj.getTest_P2O5():"");	
		 newObj.setTest_S2(obj.getTest_S2()!=null?obj.getTest_S2():"");	
		 newObj.setTest_Si(obj.getTest_Si()!=null?obj.getTest_Si():"");	
		 newObj.setTest_Mn(obj.getTest_Mn()!=null?obj.getTest_Mn():"");	
		 newObj.setTest_Ti(obj.getTest_Ti()!=null?obj.getTest_Ti():"");	
		 newObj.setTest_TMn(obj.getTest_TMn()!=null?obj.getTest_TMn():"");	
		 newObj.setTest_FeO(obj.getTest_FeO()!=null?obj.getTest_FeO():"");	
		 newObj.setTest_Cr(obj.getTest_Cr()!=null?obj.getTest_Cr():"");	
		
		 
		 
		 newObj.setDataTime(obj.getDataTime());
		 newObj.setSampleNum(obj.getSampleNum());
		 newObj.setBelongcompany(obj.getBelongcompany());
		 newObj.setRecordTime(model.getRecordTime());
		 newObj.setRecordUser(model.getRecordUser());	
		 newObj.setDeviceNum(obj.getDeviceNum());
		 yingguangFlyBxyService.add(newObj);		
		 zjLiangreSrcService.checkAddSampleNum(newObj.getSampleNum());//核对量热仪、手录标样品编号
	 }
		
	}



@Override
public void synCollect(String deviceNo,String cjtime) throws Exception {
  if(cjtime==null){cjtime=ConfigUtils.dataToSimpleString(new Date());}
	if(ThreadStaticVariable.DataCircleMap.get(deviceNo)==null){return;}
	  DataCircle device=ThreadStaticVariable.DataCircleMap.get(deviceNo); 
	  //List<YingguangSrcBxy> resList=new ArrayList<YingguangSrcBxy>();
	  Map<String,YingguangSrcBxy> resMap=new HashMap<String,YingguangSrcBxy>();
	  Map<String,YingguangSrcBxy> DBMap=getAllYinguangSrcBxyByTime(cjtime,deviceNo);
	  Map<String,String> eleMap=getAllElementMap(); 
      BufferedReader reader = null;
 try {
//找出TXT文件	  
   File fileyc = new File(device.getDataSavePath());
   if(fileyc.exists()){
	   ///
	if(FileCommonUtils.copyFile(device.getDataSavePath(),device.getJdbcUrl(), true)){
		File filebd = new File(device.getJdbcUrl());
        String lineString = null;
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(filebd), "gbk"));	 //以行为单位读取文件内容，一次读一整行：
        //元素汇总      
        YingguangSrcBxy	srcObj=null;        
        while ((lineString = reader.readLine()) != null) {
        	
        	String  tempString=lineString.replaceAll(" ", "");
        	String [] dataArray=lineString.replace("%", "").split("\\s+");
        	
        	if(tempString.isEmpty()){continue;}
        	if(tempString.contains("样品:")){	//txt文档样品编号打头        		
        		    srcObj=new YingguangSrcBxy();
        	        String snum=tempString.replace("样品:", "").trim();	        		
        			srcObj.setSampleNum(snum);
        			srcObj.setCircleId(device.getId());
        			srcObj.setDeviceNum(deviceNo);
        			srcObj.setRecordTime(new Date());
        			srcObj.setFileName("result_last.txt");
        			srcObj.setDataStatus("原始数据");
        			
        			
        			 
        			//新加的内容 截取荧光仪化验文件中的字符串 进行判断化学物质 boxy 20201029 11:11
        			if(snum.indexOf("20")>-1) {
        				String xinname = snum.substring(0,snum.indexOf("20"));
            			List<DataWlInfo> changeTypeList =dataWlInfoService.findAll("FROM DataWlInfo where wlCode ='"+xinname+"'");
            			String wlCompany="";
            			if(null != changeTypeList || changeTypeList.size() !=0 ) {
            				for(DataWlInfo KEY :changeTypeList){
                				wlCompany=KEY.getBelongcompany().toString();
                			}
                			srcObj.setBelongcompany(wlCompany);
            			}
        			}
        			
        			
        			
        			
        			if(snum.length()< ThreadStaticVariable.invialLength){srcObj=null;}
        	   }
        	if(srcObj!=null){
        		if(tempString.contains("日期:")){
        		    String dtime=lineString.replace("日期    :", "").trim();	
        		    if(!dtime.contains(cjtime)){
        		    	srcObj=null;	
        		    }else{
        		    	srcObj.setDataTime(dtime);   
        		    }      		      		
        	  }else if(tempString.contains("组:")){
    		    String group=tempString.replace("组:", "").trim();	        
    		    srcObj.setBegroup(group);      		
    	      }
        	  
        	  /*else if(tempString.contains("合量")){   	    	 
    	    	  resList.add(srcObj);
    	    	  boolean tag=isUnique(srcObj.getSampleNum(),srcObj.getDataTime(),deviceNo);
    	          if(tag){
    	          	this.add(srcObj);
    	          }
      	      }*/
        	  
        	  else if(eleMap.containsKey(dataArray[0])){       		
        			String key=dataArray[0];
        			if(eleMap.get(key).equals("TFe")){
        			    srcObj.setTest_TFe(dataArray[1].trim());  
        			}else if(eleMap.get(key).equals("SiO2")){
        			    srcObj.setTest_SiO2(dataArray[1].trim());	
        			}else if(eleMap.get(key).equals("CaO")){
        				srcObj.setTest_CaO(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("MgO")){
        				srcObj.setTest_MgO(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("Al2O3")){
        				srcObj.setTest_Al2O3(dataArray[1].trim());	
        			}else if(eleMap.get(key).equals("MnO")){
        				 srcObj.setTest_MnO(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("TiO2")){
        				srcObj.setTest_TiO2(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("V2O5")){
        				srcObj.setTest_V2O5(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("P")){
        				 srcObj.setTest_P(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("S")){
        				srcObj.setTest_S(dataArray[1].trim());		
        			}else if(eleMap.get(key).equals("K2O")){
        				srcObj.setTest_K2O(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("Na2O")){
        				srcObj.setTest_Na2O(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("Co2O3")){
        				srcObj.setTest_Co2O3(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("Pb")){
        				srcObj.setTest_Pb(dataArray[1].trim());	
        			}else if(eleMap.get(key).equals("As")){
        				 srcObj.setTest_As(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("Zn")){
        				 srcObj.setTest_Zn(dataArray[1].trim());
        			}else if(eleMap.get(key).equals("Cu")){
        				 srcObj.setTest_Cu(dataArray[1].trim());
       			    }else if(eleMap.get(key).equals("Ni")){
       			    	srcObj.setTest_Ni(dataArray[1].trim());
       			    }else if(eleMap.get(key).equals("Cr2O3")){
       			        srcObj.setTest_Cr2O3(dataArray[1].trim());
       			    }else if(eleMap.get(key).equals("Au2O")){
       			        srcObj.setTest_Au2O(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("R2")){
       			    	srcObj.setTest_R2(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("Fe2O3")){
       			    	srcObj.setTest_Fe2O3(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("R")){
       			    	srcObj.setTest_R(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("P2O5")){
       			    	srcObj.setTest_P2O5(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("S2")){
       			    	srcObj.setTest_S2(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("Si")){
       			    	srcObj.setTest_Si(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("Mn")){
       			    	srcObj.setTest_Mn(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("Ti")){
       			    	srcObj.setTest_Ti(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("TMn")){
       			    	srcObj.setTest_TMn(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("FeO")){
       			    	srcObj.setTest_FeO(dataArray[1].trim());	
       			    }else if(eleMap.get(key).equals("Cr")){
       			    	srcObj.setTest_Cr(dataArray[1].trim());	
       			    }          
        			
        			
        			//20201015  boxy新加
								
        	}	       	   
       //保存实体到缓存，相同覆盖
		 if(srcObj!=null&&srcObj.getSampleNum()!=null&&srcObj.getDataTime()!=null){
		  String key=srcObj.getSampleNum()+","+srcObj.getDataTime();
		  resMap.put(key, srcObj);
		  
	  }		
  }	
 }              	
        reader.close();
}       
        //////
} 
   //保存实体到数据库，查重sampleNum-datatime
   for(String keys:resMap.keySet()){
	   YingguangSrcBxy saveObj=resMap.get(keys);
       if(!DBMap.containsKey(keys)){
    	   this.add(saveObj);
       }	   
   }   
   } catch (Exception e) {
    	logger.error(deviceNo+"："+e.getMessage());
    }finally{
        if(reader != null){
            try {
                reader.close();
            } catch (IOException e) {
            	logger.error(deviceNo+"："+e.getMessage());
            }
        }
    }

}




/**
 * 鍒ゆ柇璁板綍鏄惁閲嶅
 * @param midMap
 * @return
 * @throws Exception 
 */
private boolean isUnique(String sampleNum,String dataTime,String deviceNum) throws Exception {
	Boolean flag=true;
	List<YingguangSrcBxy> resList=this.findAll(" from YingguangSrcBxy where sampleNum='"+sampleNum+"' and deviceNum='"+deviceNum+"' and dataTime='"+dataTime+"' ");
	if(resList.size()>=1){
		flag=false;
	}		
	return flag;
}


/**
 * 查找当前设备在当前日期的记录
 * @param sampleNum
 * @param dataTime
 * @param deviceNum
 * @return
 * @throws Exception
 */
private Map<String,YingguangSrcBxy> getAllYinguangSrcBxyByTime(String dataTime,String deviceNum) throws Exception {	
	Map<String,YingguangSrcBxy> dataMap=new HashMap<String,YingguangSrcBxy>();
	List<YingguangSrcBxy> resList=this.findAll(" from YingguangSrcBxy where  deviceNum='"+deviceNum+"' and dataTime like '"+dataTime.substring(0, 10)+"%' ");
	for(YingguangSrcBxy src:resList){
		String key=src.getSampleNum()+","+src.getDataTime();
		dataMap.put(key, src);
	}
	return dataMap;
}


public Map<String,String> getAllElementMap(){
	//荧光仪元素汇总
	Map<String,String> resultMap=new HashMap<String,String>();	
	resultMap.put("TFe", "TFe");
	resultMap.put("SiO2", "SiO2");
	resultMap.put("CaO", "CaO");
	resultMap.put("MgO", "MgO");
	resultMap.put("Al2O3", "Al2O3");
	resultMap.put("MnO", "MnO");
	resultMap.put("TiO2", "TiO2");
	resultMap.put("V2O5", "V2O5");
	resultMap.put("P", "P");
	resultMap.put("S", "S");
	resultMap.put("K2O", "K2O");
	resultMap.put("Na2O", "Na2O");
	resultMap.put("Co2O3", "Co2O3");
	resultMap.put("Pb", "Pb");
	resultMap.put("As", "As");	
	resultMap.put("Zn", "Zn");
	resultMap.put("Cu", "Cu");
	resultMap.put("Ni", "Ni");
	resultMap.put("Cr2O3", "Cr2O3");
	resultMap.put("Au2O", "Au2O");
	resultMap.put("R2", "R2");
	resultMap.put("Fe2O3", "Fe2O3");
	
	//20201015  boxy新加
	resultMap.put("R", "R");
	resultMap.put("P2O5", "P2O5");
	resultMap.put("S2", "S2");
	resultMap.put("Si", "Si");
	resultMap.put("Mn", "Mn");
	resultMap.put("Ti", "Ti");
	resultMap.put("TMn", "TMn");
	resultMap.put("FeO", "FeO");
	resultMap.put("Cr", "Cr");
	return resultMap;
	
}
@Override
public void recovery(String[] ids) throws Exception {
	// TODO Auto-generated method stub
	for (String id : ids) {
		YingguangSrcBxy YingguangSrcBxy = this.getById(id);
		if ("作废数据".equals(YingguangSrcBxy.getDataStatus())) {
			YingguangSrcBxy.setDataStatus("原始数据");
			this.update(YingguangSrcBxy);
		}
	}
}

@Override
public void del(String[] ids) throws Exception {
	for (String id : ids) {
		YingguangSrcBxy YingguangSrcBxy = this.getById(id);
		if ("原始数据".equals(YingguangSrcBxy.getDataStatus())) {
			YingguangSrcBxy.setDataStatus("作废数据");
			this.update(YingguangSrcBxy);
		}
	}
}

}
