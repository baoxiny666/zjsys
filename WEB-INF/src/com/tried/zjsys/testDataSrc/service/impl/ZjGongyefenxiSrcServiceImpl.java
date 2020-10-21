package com.tried.zjsys.testDataSrc.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.drools.lang.dsl.DSLMapParser.condition_key_return;
import org.springframework.stereotype.Service;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.common.FileCommonUtils;
import com.tried.zjsys.basics.model.DataCircle;
import com.tried.zjsys.basics.thread.ThreadStaticVariable;
import com.tried.zjsys.testDataSrc.model.YinguangFly;
import com.tried.zjsys.testDataSrc.model.YinguangSrc;
import com.tried.zjsys.testDataSrc.model.ZjDingliuSrc;
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiFly;
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiSrc;
import com.tried.zjsys.testDataSrc.model.ZjTanliuSrc;
import com.tried.zjsys.testDataSrc.service.ZjGongyefenxiFlyService;
import com.tried.zjsys.testDataSrc.service.ZjGongyefenxiSrcService;
import com.tried.zjsys.testDataSrc.service.ZjLiangreSrcService;
/**
 * @Description - 服务接口实现
 * @author sunlunan
 * @date 2020-06-28 10:03:57
 * @version V1.0
 */
@Service
public class ZjGongyefenxiSrcServiceImpl extends BaseServiceImpl<ZjGongyefenxiSrc> implements ZjGongyefenxiSrcService {

	private static Logger logger = Logger.getLogger(ZjGongyefenxiSrcServiceImpl.class);
	@Resource
	ZjLiangreSrcService zjLiangreSrcService;
	@Resource
	ZjGongyefenxiFlyService  zjGongyefenxiFlyService;
	@Override
	public void synCollect040(String deviceNum,String month) throws Exception {	
		
		if(month==null){
			 month=ConfigUtils.dataToYearMonthString(new Date());
		}
		 Long fileIndex_hf=0l;
		 try{		
			List<ZjGongyefenxiSrc> hfList=this.findAll("from ZjGongyefenxiSrc  where  test_syrq like '"+month+"%' and testType='灰分' and deviceNum='"+deviceNum+"' ");		
			Set<String> set=new HashSet<String>();
			for(ZjGongyefenxiSrc src:hfList){
				if(src.getTest_syrq()!=null&&src.getTest_sybh()!=null){
			    	set.add(src.getTest_syrq()+src.getTest_sybh());
				}				
			}
			
			  if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
		      DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
		      String srcFileName_hf="M"+month+".DBF";
		      if(dataCircle!=null&&dataCircle.getDataSavePath()!=null){
		    	  insertDataToSql("hf",dataCircle.getDataSavePath()+"/"+srcFileName_hf,srcFileName_hf,fileIndex_hf,dataCircle,set);			    	         
		      }						
		}
		}catch(Exception e){
			logger.error(deviceNum+"："+e.getMessage());
		}			
	}

	@Override
	public void synCollect039(String deviceNum,String month) throws Exception {				
		try{
			if(month==null){
			 month=ConfigUtils.dataToYearMonthString(new Date());
		    }
		    Long fileIndex_hfa=0l;		 
		    List<ZjGongyefenxiSrc> hlist=this.findAll("from ZjGongyefenxiSrc  where  test_syrq like '"+month+"%' and  testType='挥发分' and deviceNum='"+deviceNum+"'  ");			
			Set<String> set=new HashSet<String>();
			for(ZjGongyefenxiSrc src:hlist){
				if(src.getTest_syrq()!=null&&src.getTest_sybh()!=null){
			    	set.add(src.getTest_syrq()+src.getTest_sybh());
				}
				
			}
			  if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
		      DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
		      String srcFileName_hfa="F"+month+".DBF";
		      if(dataCircle!=null&&dataCircle.getDataSavePath()!=null){		    	 
		    	  insertDataToSql("hfa",dataCircle.getDataSavePath()+"/"+srcFileName_hfa,srcFileName_hfa,fileIndex_hfa,dataCircle,set);          
		      }						
		}
	}catch(Exception e){
	  logger.error(deviceNum+"："+e.getMessage());
	}		
}
	
	
	
	/**
	 * 
	 * @param tag 标志灰分、挥发分
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @param sqlTime 数据时间
	 * @param sql_sybh 试验编号
	 * @throws Exception
	 */
	private void insertDataToSql(String tag, String filePath,String fname, Long index,DataCircle dataCircle,Set<String> set) throws Exception {		
        InputStream fis = null;
  try {
      File readFile=new File(filePath);
      if(readFile.exists()){   		 
        fis = new FileInputStream(filePath); // 读取文件的输入流
       
        DBFReader reader = new DBFReader(fis); // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息       
            Object[] rowVal;
            int readerCount=reader.getRecordCount();
            for(int row=0;row<readerCount;row++){
            	//if(row>index){
            	 rowVal=reader.nextRecord();   
            	 //样品编号小于10位不采
            	 if(rowVal[13]==null||rowVal[13].toString().length()<ThreadStaticVariable.invialLength){
            		continue; 
            	 }
            	 
            	 ZjGongyefenxiSrc gyfxObj=new ZjGongyefenxiSrc();    
            	 if(tag.equals("hf")){
            		 gyfxObj.setTestType("灰分");
            		 
            		 
            	 }else{
            		 gyfxObj.setTestType("挥发分");
            	 }  
            	 if(rowVal[0]==null){continue;}
            	 if(rowVal[1]==null){continue;}
            	 if(set.contains(rowVal[0].toString().trim()+rowVal[1].toString().trim())){
            		 continue;
            	 }
            	
            	 gyfxObj.setTest_syrq(rowVal[0]!=null?rowVal[0].toString().trim():"");
            	 gyfxObj.setTest_sybh(rowVal[1]!=null?rowVal[1].toString().trim():"");
            	 gyfxObj.setTest_jgzl(rowVal[2]!=null?rowVal[2].toString().trim():"0");
            	 gyfxObj.setTest_syzzl(rowVal[3]!=null?rowVal[3].toString().trim():"0");
            	 gyfxObj.setTest_mad(rowVal[4]!=null?rowVal[4].toString().trim():"0");
            	 gyfxObj.setTest_b9(rowVal[5]!=null?rowVal[5].toString().trim():"0");
            	 gyfxObj.setTest_vad(rowVal[6]!=null?rowVal[6].toString().trim():"0"); 
            	 gyfxObj.setTest_b10(rowVal[7]!=null?rowVal[7].toString().trim():"0");
            	 gyfxObj.setTest_aad(rowVal[8]!=null?rowVal[8].toString().trim():"0");  
            	 gyfxObj.setTest_m1(rowVal[9]!=null?rowVal[9].toString().trim():"0");
            	 gyfxObj.setTest_s1(rowVal[10]!=null?rowVal[10].toString().trim():"0");
            	 gyfxObj.setTest_s2(rowVal[11]!=null?rowVal[11].toString().trim():"0");
            	 gyfxObj.setTest_s3(rowVal[12]!=null?rowVal[12].toString().trim():"0");
            	 gyfxObj.setTest_s4(rowVal[13]!=null?rowVal[13].toString().trim():"");
            	 gyfxObj.setTest_mz(rowVal[14]!=null?rowVal[14].toString().trim():"0");
            	 gyfxObj.setTest_jzh(rowVal[15]!=null?rowVal[15].toString().trim():"0");
            	 gyfxObj.setTest_rz(rowVal[16]!=null?rowVal[16].toString().trim():"0");
                gyfxObj.setTestIndex(Long.parseLong(row+""));
                gyfxObj.setCircleId(dataCircle.getId());
             	gyfxObj.setFileName(fname);
             	gyfxObj.setDataStatus("原始数据");
             	gyfxObj.setRecordTime(new Date());
             	gyfxObj.setSampleNum(gyfxObj.getTest_s4());          	
             	//double vad=Double.parseDouble(gyfxObj.getTest_vad());
             	double aad=Double.parseDouble(gyfxObj.getTest_aad());
             	double mad=Double.parseDouble(gyfxObj.getTest_mad());
             	double ad=(aad/(100-mad))*100;
             	//double vd=vad/(100-mad);//煤挥发分
             //	double fcad=100-mad-aad-vad;
             //	double vdaf=vad/(100-mad-aad);//焦炭挥发分
             	gyfxObj.setCalcula_ad(String.format("%.6f", ad));//灰分(String.format("%.2f", f));
             	//gyfxObj.setTest_vad(String.format("%.6f", vad));
             	//gyfxObj.setCalcula_fcad(String.format("%.6f", fcad)+"");//固定碳
             	/*if(gyfxObj.getSampleNum().contains("JTLT")){//焦炭
             		gyfxObj.setCalcula_vdvdaf(String.format("%.6f", vdaf)+"");//焦炭挥发分    
             	}else{
             		gyfxObj.setCalcula_vdvdaf(String.format("%.6f", vd)+"");//煤挥发分   
             	}    */        	       
             	gyfxObj.setDeviceNum(dataCircle.getDeviceNum());
             	
             	this.add(gyfxObj);           		           	
            }
        }
   // }
        } catch (Exception e) {
        	logger.error(dataCircle.getDeviceNum()+"："+e.getMessage());
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }		
	}

	

	@Override
	public void flySrcData(String[] ids, ZjGongyefenxiSrc model) throws Exception {
		
	 StringBuilder idstr=new StringBuilder("'-1'");
      for(String id:ids){
    	  idstr.append(",'"+id+"'");	
      } 
     Map<String,List<ZjGongyefenxiSrc>> midMap=new HashMap<String,List<ZjGongyefenxiSrc>>();
	 List<ZjGongyefenxiSrc> ylist=this.findAll(" from ZjGongyefenxiSrc where id in ("+idstr.toString()+")");
	 for(ZjGongyefenxiSrc obj:ylist){		 
		 obj.setDataStatus("已提交"); 	 
		 this.update(obj);
		 if(midMap.containsKey(obj.getTestType())){
			 midMap.get(obj.getTestType()).add(obj);
		 }else{
			 List<ZjGongyefenxiSrc> zglist=new ArrayList<ZjGongyefenxiSrc>();
			 zglist.add(obj);
			 midMap.put(obj.getTestType(), zglist);
		 } 
	 }
	 ZjGongyefenxiFly newObj=new ZjGongyefenxiFly();
	 newObj.setCircleId("");
	 newObj.setDataStatus("已提交");
	 newObj.setSrcId("");	
	 double vd=0;
	 double ad=0;
	 double mad=0;
	 double aad=0;
	 double fcad=0;
	 double vad=0;
	 double vdaf=0;	
	 //灰分
	 List<ZjGongyefenxiSrc> hfList= midMap.get("灰分");	 
	 for(ZjGongyefenxiSrc hf:hfList){
		 if(hf.getTest_mad()==null||hf.getTest_mad().isEmpty()){hf.setTest_mad("0");}
		 if(hf.getTest_aad()==null||hf.getTest_aad().isEmpty()){hf.setTest_aad("0");}
		 if(hf.getCalcula_ad()==null||hf.getCalcula_ad().isEmpty()){hf.setCalcula_ad("0");}
		  mad=mad+Double.parseDouble(hf.getTest_mad());
		  aad=aad+Double.parseDouble(hf.getTest_aad());	
		  ad=ad+Double.parseDouble(hf.getCalcula_ad());			  
		  newObj.setSampleNum(hf.getSampleNum());
		  newObj.setTest_syrq(hf.getTest_syrq());
	 }
	 mad=mad/hfList.size();aad=aad/hfList.size();ad=ad/hfList.size();
	 
	// 挥发分
	 List<ZjGongyefenxiSrc> hfaList= midMap.get("挥发分");	 
	 for(ZjGongyefenxiSrc hfa:hfaList){
		 if(hfa.getTest_vad()==null||hfa.getTest_vad().isEmpty()){hfa.setTest_vad("0");}		
		  vad=vad+Double.parseDouble(hfa.getTest_vad());		  
	  }
	 vad=vad/hfaList.size();
	 fcad=100-mad-aad-vad;
	 newObj.setCalcula_Ad(String.format("%.2f", ad)+"");
	 newObj.setCalcula_FCad(String.format("%.2f", fcad)+"");
	 newObj.setTest_Mad(String.format("%.2f", mad)+"");
	 if(newObj.getSampleNum().contains("JTLT")){
		vdaf=(vad/(100-mad-aad))*100;
		newObj.setCalcula_VdVdaf(String.format("%.2f", vdaf));
	 }else{
		vd=(vad/(100-mad))*100; 
		newObj.setCalcula_VdVdaf(String.format("%.2f", vd));
	 }
	 newObj.setRecordTime(model.getRecordTime());
	 newObj.setRecordUser(model.getRecordUser());
	 zjLiangreSrcService.checkAddSampleNum(newObj.getSampleNum());//核对量热仪、手录标样品编号
	 zjGongyefenxiFlyService.add(newObj); 
	 
}

	@Override
	public void synCollect038(String deviceNum,String month) throws Exception {
		if(month==null){
			 month=ConfigUtils.dataToYearMonthString(new Date());
		    }
		int testIndex=0;
		Map<Long,String> sqldbMap=new HashMap<Long,String>();
		try{
			List<ZjGongyefenxiSrc> srcList=this.findAll("from ZjGongyefenxiSrc where deviceNum='"+deviceNum+"' and  testType='挥发分' and test_syrq like '"+month+"%' order by testIndex  desc");
		      for(ZjGongyefenxiSrc zjobj:srcList){
		    	  sqldbMap.put(zjobj.getTestIndex(), zjobj.getTestIndex()+"");
		      }
						
		if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
			DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
			 //无法读取共享目录 带用户密码的的数据库，所以先把共享文件采集到本地，再本地打开
		    String jdbcUrl=dataCircle.getJdbcUrl();
		    int dbqIndex=jdbcUrl.indexOf("DBQ=");
		    String  srcFile=jdbcUrl.substring(dbqIndex+4);
		   if(FileCommonUtils.copyFile(srcFile, dataCircle.getDataSavePath(), true)){	
			jdbcUrl=jdbcUrl.substring(0,dbqIndex+4)+dataCircle.getDataSavePath();
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(ConfigUtils.stringToSimpleData(month+"-01"));
		    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Class.forName(dataCircle.getDriverClass());
			Connection	conn = java.sql.DriverManager.getConnection(jdbcUrl, dataCircle.getUsername(), dataCircle.getPassword());
			String sql="SELECT  SampleId,TestId,Mad,Vad,Aad,MadVad,regtime,vad1,Id FROM Tvalue where regtime>=#"+month+"-01"+"# and regtime<=#"+ConfigUtils.dataToSimpleString(calendar.getTime())+"#";
			System.out.println("cccccsql:"+sql);
			List<Object[]> objList=ConfigUtils.dbFindList(conn,sql);			
			for(Object[] obj:objList){
				Long indx=Long.parseLong((obj[8]!=null&&!(obj[8].toString().trim().isEmpty()))?obj[8].toString():"0");
				if(obj[0]!=null&&(!obj[0].toString().trim().isEmpty())&&(obj[0].toString().trim().length()>=ThreadStaticVariable.invialLength)&&(!sqldbMap.containsKey(indx))){
					ZjGongyefenxiSrc gyfxObj=new ZjGongyefenxiSrc();
					gyfxObj.setDeviceNum(deviceNum);
					gyfxObj.setDataStatus("原始数据");
					gyfxObj.setRecordTime(new Date());
					gyfxObj.setTestIndex(indx);//Id
					gyfxObj.setTestType("挥发分");
					gyfxObj.setCircleId(dataCircle.getId());
					gyfxObj.setFileName("gyfx.mdb");		             	
					gyfxObj.setTest_s4(obj[0]!=null?obj[0].toString().trim():"");				 
					gyfxObj.setSampleNum(obj[0]!=null?obj[0].toString().trim():"");
					gyfxObj.setTest_sybh(obj[1]!=null?obj[1].toString().trim():"");
					gyfxObj.setTest_syrq(obj[6]!=null?obj[6].toString().trim():"");
					gyfxObj.setTest_aad((obj[4]!=null&&(!obj[4].toString().equals("")))?obj[4].toString().trim():"0");
					gyfxObj.setTest_mad((obj[2]!=null&&(!obj[2].toString().equals("")))?obj[2].toString().trim():"0");
					gyfxObj.setTest_vad((obj[3]!=null&&(!obj[3].toString().equals("")))?obj[3].toString().trim():"0");
					
		             	//double vad=Double.parseDouble(gyfxObj.getTest_vad());
		             //	double aad=Double.parseDouble(gyfxObj.getTest_aad());
		             //	double mad=Double.parseDouble(gyfxObj.getTest_mad());
		             	//double ad=(aad/(100-mad))*100;
		             	//double vd=vad/(100-mad);
		             	//double fcad=100-mad-aad-vad;
		             //	double vdaf=vad/(100-mad-aad);  
		             	
		             	
		             	//gyfxObj.setCalcula_ad(String.format("%.6f", ad));//灰分(String.format("%.2f", f));
		             	//gyfxObj.setCalcula_fcad(String.format("%.6f", fcad)+"");//固定碳
		             	/*if(gyfxObj.getSampleNum().contains("JTLT")){//焦炭
		             		gyfxObj.setCalcula_vdvdaf(String.format("%.6f", vdaf));//焦炭挥发分    
		             	}else{
		             		gyfxObj.setCalcula_vdvdaf(String.format("%.6f", vd));//煤挥发分   
		             	}  */           	        
		             	gyfxObj.setDeviceNum(dataCircle.getDeviceNum());
		             	
		             	this.add(gyfxObj);           		     					
				}
			}
		   }
		}
		}catch(Exception e){
			logger.error(deviceNum+"："+e.getMessage());
			logger.error(deviceNum+"："+e.getMessage());
		}
		
	}

	@Override
	public void synCollect041(String deviceNum,String month) throws Exception {
		InputStream fis = null;
		try{
			//S2020-05.DBF
			if(month==null){
				 month=ConfigUtils.dataToYearMonthString(new Date());
			}
			String srcFileName="M"+month+".DBF";
			DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
			if(dataCircle!=null&&dataCircle.getDataSavePath()!=null){
				String strFile=dataCircle.getDataSavePath()+"/"+srcFileName;
				File file=new File(strFile);
				if(file.exists()){
					Long fileIndex=0l;
					//根据设备编号，月度，按索引倒序检索，获取最后一个索引
					List<ZjGongyefenxiSrc> hfList=this.findAll("from ZjGongyefenxiSrc where  test_syrq like '"+month+"%' and deviceNum='"+deviceNum+"' and  testType='灰分'  ");
					Set<String> set=new HashSet<String>();
					for(ZjGongyefenxiSrc src:hfList){
						if(src.getTest_syrq()!=null&&src.getTest_sybh()!=null){
					    	set.add(src.getTest_syrq()+src.getTest_sybh());
						}
					}
					insertDataToSql("hf",strFile,srcFileName,fileIndex,dataCircle,set);
				}
			}
			}catch(Exception e){
			logger.error(deviceNum+"："+e.getMessage());
		}		
	}

	@Override
	public void del(String[] ids) throws Exception {
		for (String id : ids) {
			ZjGongyefenxiSrc zjGongyefenxiSrc = this.getById(id);
			if ("原始数据".equals(zjGongyefenxiSrc.getDataStatus())) {
				zjGongyefenxiSrc.setDataStatus("作废数据");
				this.update(zjGongyefenxiSrc);
			}
		}	
	}
	@Override
	public void recovery(String[] ids) throws Exception {
		for (String id : ids) {
			ZjGongyefenxiSrc zjGongyefenxiSrc = this.getById(id);
			if ("作废数据".equals(zjGongyefenxiSrc.getDataStatus())) {
				zjGongyefenxiSrc.setDataStatus("原始数据");
				this.update(zjGongyefenxiSrc);
			}
		}		
	}

}
