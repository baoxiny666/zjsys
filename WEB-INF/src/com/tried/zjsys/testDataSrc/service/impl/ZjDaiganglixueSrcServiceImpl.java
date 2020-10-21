package com.tried.zjsys.testDataSrc.service.impl;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.security.auth.login.ConfigFile;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.common.FileCommonUtils;
import com.tried.zjsys.basics.model.DataCircle;
import com.tried.zjsys.basics.thread.ThreadStaticVariable;
import com.tried.zjsys.testDataSrc.model.ZjDaiganglixueFly;
import com.tried.zjsys.testDataSrc.model.ZjDaiganglixueSrc;
import com.tried.zjsys.testDataSrc.model.ZjGongyefenxiSrc;
import com.tried.zjsys.testDataSrc.model.ZjTanliuSrc;
import com.tried.zjsys.testDataSrc.service.ZjDaiganglixueFlyService;
import com.tried.zjsys.testDataSrc.service.ZjDaiganglixueSrcService;
/**
 * @Description - 服务接口实现
 * @author sunlunan
 * @date 2020-07-01 10:39:20
 * @version V1.0
 */
@Service
public class ZjDaiganglixueSrcServiceImpl extends BaseServiceImpl<ZjDaiganglixueSrc> implements ZjDaiganglixueSrcService {
	private static Logger logger = Logger.getLogger(ZjDaiganglixueSrcServiceImpl.class);
	@Resource
	ZjDaiganglixueFlyService zjDaiganglixueFlyService;
	@Override
	public void synCollect031(String deviceNum,String dtime) throws Exception {				
		 if(dtime==null){
			 dtime=ConfigUtils.getCurrentDate();;
		  }
		    Map<Long,ZjDaiganglixueSrc> midMap=new HashMap<Long, ZjDaiganglixueSrc>();
			try{
				List<ZjDaiganglixueSrc> srclist=this.findAll("from ZjDaiganglixueSrc where deviceNum='"+deviceNum+"'  and fileName like '%"+dtime+"%' order by testNo  desc");			
				for(ZjDaiganglixueSrc zjObj:srclist){					
					midMap.put(zjObj.getTestNo(),zjObj);	
				}				
				
				//遍历数据库	
				if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
					 DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
					   String jdbcUrl=dataCircle.getJdbcUrl();
					   int dbqIndex=jdbcUrl.indexOf("DBQ=");
					   String srcFile=jdbcUrl.substring(dbqIndex+4);//源文件路径
				  	 List<String> fileNmaes=FileCommonUtils.getFiles(srcFile);
					   for(String name:fileNmaes){
							if(name.contains(dtime)){
	                          String srcPath=srcFile+"/"+name;                         
	                          inserDataToSqlServer031(srcPath,name,deviceNum,midMap,dataCircle,dtime);	
							 }					
						  }
				  }
		
				 }catch(Exception e){
					 logger.error(deviceNum+"："+e.getMessage());
				 }
	 }
	
	private void inserDataToSqlServer031(String srcPath, String name,String deviceNum, Map<Long,ZjDaiganglixueSrc> midMap, DataCircle dataCircle,String dtime) {   
	  try{
			File file=new File(srcPath);
			if(file.exists()){
			  //无法读取共享目录 带用户密码的的数据库，所以先把共享文件采集到本地，再本地打开
			   String jdbcUrl=dataCircle.getJdbcUrl();
			   int dbqIndex=jdbcUrl.indexOf("DBQ=");
			   String  srcFile=jdbcUrl.substring(dbqIndex+4);
			 if(FileCommonUtils.copyFile(srcFile+"/"+name, dataCircle.getDataSavePath()+"/"+name, true)){	
				jdbcUrl=jdbcUrl.substring(0,dbqIndex+4)+dataCircle.getDataSavePath();
	    	    Class.forName(dataCircle.getDriverClass());
				Connection	conn = java.sql.DriverManager.getConnection(jdbcUrl+"/"+name, dataCircle.getUsername(), dataCircle.getPassword());
				String sql="SELECT  TestNo,Name,TheValue,Unit FROM ParamFactValue";
				List<Object[]> objList=ConfigUtils.dbFindList(conn,sql);
				Map<String,ZjDaiganglixueSrc> objMap=new HashMap<String,ZjDaiganglixueSrc>();				
				for(Object[] obj:objList){	
		    	     String tnum=obj[0].toString().trim();
		    	     if(!tnum.isEmpty()){
			    	    	 if(!objMap.containsKey(tnum)){
			    	    		 ZjDaiganglixueSrc dglxy=new ZjDaiganglixueSrc();
			    	    		 dglxy.setDeviceNum(deviceNum);
			    	    		 dglxy.setCircleId(dataCircle.getId());
			    	    		 dglxy.setDataStatus("原始数据");
			    	    		 dglxy.setFilename(name);
			    	    		 dglxy.setTestNo(Long.parseLong(tnum));
			    	    		 dglxy.setRecordTime(new Date());
			    	    		 objMap.put(tnum, dglxy);
			    	    	 }  	 	    	    
		    	    	  setObjFieldValue_31(objMap.get(tnum),obj); 	    	     
		    	       }									
				 } 
				//保存到数据库
				for(String key:objMap.keySet()){
					ZjDaiganglixueSrc cjObj=objMap.get(key);					
					if(midMap.containsKey(Long.parseLong(key))){						
						ZjDaiganglixueSrc dbObj=midMap.get(Long.parseLong(key));						
						dbObj.setDataTime(cjObj.getDataTime()!=null?cjObj.getDataTime():"");
						dbObj.setClassGroup(cjObj.getClassGroup()!=null?cjObj.getClassGroup():"");
						dbObj.setLunum(cjObj.getLunum()!=null?cjObj.getLunum():"");
						dbObj.setSteeltype(cjObj.getSteeltype()!=null?cjObj.getSteeltype():"");
						dbObj.setSteelGuige(cjObj.getSteelGuige()!=null?cjObj.getSteelGuige():"");
						dbObj.setBranchFactory(cjObj.getBranchFactory()!=null?cjObj.getBranchFactory():"");
						dbObj.setYieldDown_streng(cjObj.getYieldDown_streng()!=null?cjObj.getYieldDown_streng():"");
						dbObj.setKangla_streng(cjObj.getKangla_streng()!=null?cjObj.getKangla_streng():"");
						dbObj.setDuanhouLong_rate(cjObj.getDuanhouLong_rate()!=null?cjObj.getDuanhouLong_rate():"");
						this.update(dbObj);
					}else{
						this.add(cjObj);
					}	
				}					
	      }
	  }
		}catch(Exception e){
			logger.error(dataCircle.getDeviceNum()+"："+e.getMessage());
		}	
	}
	public void setObjFieldValue_31(ZjDaiganglixueSrc src,Object[] obj){
	      
	      String testNo=obj[0].toString().trim(); 
	      String name=obj[1].toString().replaceAll(" ", "");
	      String  theValue=obj[2]!=null?obj[2].toString().trim():"";
	      String  unitdw=obj[3].toString().trim();//单位
		  if(name.isEmpty()){return ;}	
		   if(name.trim().equals("日期")){
			    src.setDataTime(theValue);
		   } if(name.trim().equals("班组")){
			    src.setClassGroup(theValue);
		   } if(name.trim().equals("炉号")){
			    src.setLunum(theValue); 
		   } if(name.trim().equals("钢种")){
			   src.setSteeltype(theValue);
		   } if(name.trim().equals("规格")){
			   src.setSteelGuige(theValue);
		   } if(name.trim().equals("分厂")){
			   src.setBranchFactory(theValue);
		   } if(name.trim().equals("试样厚度（ao）")){
			   src.setSample_thick(theValue);
		   } if(name.trim().equals("上屈服强度（ReH）")){
			   src.setYieldUp_streng(theValue);
		   } if(name.trim().equals("下屈服强度（ReL）")){
			   src.setYieldDown_streng(theValue);
		   } if(name.trim().equals("抗拉强度（Rm）")){
			   src.setKangla_streng(theValue);
		   } if(name.trim().equals("断后伸长率（A）")){
			   src.setDuanhouLong_rate(theValue);
		   } if(name.trim().equals("断面收缩率（Z）")){
			   src.setDuareaShrink_rate(theValue);
		   } if(name.trim().equals("试验时间")){
			   src.setTestTime(theValue);
		   } if(name.trim().equals("试样宽度（bo）")){
			   src.setSample_width(theValue);
		   } if(name.trim().equals("平行长度（Lc）")){
			   src.setSample_long(theValue);
		   } if(name.trim().equals("原始标距（Lo）")){
			   src.setOrig_biaojv(theValue);
		   } if(name.trim().equals("断后标距（Lu）")){
			   src.setDuanhou_biaojv(theValue); 
		   } if(name.trim().equals("试样面积（So）")){
			   src.setSample_area(theValue);
		   } if(name.trim().equals("最大力（Fm）")){
			   src.setMax_force(theValue);
		   }if(name.trim().equals("下屈服力（FeL）")){
			   src.setYieldDown_force(theValue);
		   }if(name.trim().equals("上屈服力（FeH）")){
			   src.setYieldUp_force(theValue);
		   }	
		}


/**
 * ZJ-ZX-032
 * @param src
 * @param obj
 * @return
 */
	@Override
	public void synCollect032(String deviceNum, String dtime) throws Exception {
		
		 if(dtime==null){
			 dtime=ConfigUtils.getCurrentDate();;
		  }
		     Map<Long,ZjDaiganglixueSrc> midMap=new HashMap<Long, ZjDaiganglixueSrc>();
				try{
					List<ZjDaiganglixueSrc> srclist=this.findAll("from ZjDaiganglixueSrc where deviceNum='"+deviceNum+"'  and fileName like '%"+dtime+"%' order by testNo  desc");			
					for(ZjDaiganglixueSrc zjObj:srclist){					
						midMap.put(zjObj.getTestNo(),zjObj);	
					}									
					//遍历数据库	
					if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
						 DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
						 String jdbcUrl=dataCircle.getJdbcUrl();
						 int dbqIndex=jdbcUrl.indexOf("DBQ=");
						 String  srcFile=jdbcUrl.substring(dbqIndex+4);//源文件路径
					  	 List<String> fileNmaes=FileCommonUtils.getFiles(srcFile);
						   for(String name:fileNmaes){
								if(name.contains(dtime)){
		                          String srcPath=srcFile+"/"+name;                         
		                          inserDataToSqlServer031(srcPath,name,deviceNum,midMap,dataCircle,dtime);
		
								 }					
							  }
					  }
	
			 }catch(Exception e){
				 logger.error(deviceNum+"："+e.getMessage());
			 }	
	}





	@Override
	public void synCollect033(String deviceNum, String dtime) throws Exception {
		
		 if(dtime==null){
			 dtime=ConfigUtils.getCurrentDate();;
		  }
		    Map<Long,ZjDaiganglixueSrc> midMap=new HashMap<Long, ZjDaiganglixueSrc>();
			try{
				List<ZjDaiganglixueSrc> srclist=this.findAll("from ZjDaiganglixueSrc where deviceNum='"+deviceNum+"'  and fileName like '%"+dtime+"%' order by testNo  desc");			
				for(ZjDaiganglixueSrc zjObj:srclist){					
					midMap.put(zjObj.getTestNo(),zjObj);	
				}								
				//遍历数据库	
				if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
					 DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
					 String jdbcUrl=dataCircle.getJdbcUrl();
					 int dbqIndex=jdbcUrl.indexOf("DBQ=");
					 String  srcFile=jdbcUrl.substring(dbqIndex+4);//源文件路径
				  	 List<String> fileNmaes=FileCommonUtils.getFiles(srcFile);
				  	 List<String> nameList=new ArrayList<String>();
					   for(String name:fileNmaes){
							if(name.contains(dtime)){
	                          String srcPath=srcFile+"/"+name;                         
	                          inserDataToSqlServer031(srcPath,name,deviceNum,midMap,dataCircle,dtime);
	
							 }					
						  }
				  }
	
			 }catch(Exception e){
				 logger.error(deviceNum+"："+e.getMessage());
			 }		
	}


	
	
	
	@Override
	public void synCollect034(String deviceNum, String dtime) throws Exception {
		
		 if(dtime==null){
			 dtime=ConfigUtils.getCurrentDate();;
		  }
		    Map<Long,ZjDaiganglixueSrc> midMap=new HashMap<Long, ZjDaiganglixueSrc>();
			try{
				List<ZjDaiganglixueSrc> srclist=this.findAll("from ZjDaiganglixueSrc where deviceNum='"+deviceNum+"'  and fileName like '%"+dtime+"%' order by testNo  desc");			
				for(ZjDaiganglixueSrc zjObj:srclist){					
					midMap.put(zjObj.getTestNo(),zjObj);	
				}				
				
				//遍历数据库	
				if(ThreadStaticVariable.DataCircleMap.get(deviceNum)!=null){
					 DataCircle dataCircle=ThreadStaticVariable.DataCircleMap.get(deviceNum);
					 String jdbcUrl=dataCircle.getJdbcUrl();
					 int dbqIndex=jdbcUrl.indexOf("DBQ=");
					 String  srcFile=jdbcUrl.substring(dbqIndex+4);//源文件路径
				  	 List<String> fileNmaes=FileCommonUtils.getFiles(srcFile);
				  	 List<String> nameList=new ArrayList<String>();
					   for(String name:fileNmaes){
							if(name.contains(dtime)){
	                          String srcPath=srcFile+"/"+name;                         
	                          inserDataToSqlServer031(srcPath,name,deviceNum,midMap,dataCircle,dtime);
	
							 }					
						  }
				  }
	
			 }catch(Exception e){
				 e.printStackTrace();
				 logger.error(deviceNum+"："+e.getMessage());
			 }		
	}

	

	@Override
	public void flySrcData(String[] ids, ZjDaiganglixueSrc model)throws Exception {
	 
		StringBuilder idstr=new StringBuilder("'-1'");
	      for(String id:ids){
	    	  idstr.append(",'"+id+"'");	
	      } 
		 List<ZjDaiganglixueSrc> ylist=this.findAll(" from ZjDaiganglixueSrc where id in ("+idstr.toString()+")");
		 for(ZjDaiganglixueSrc obj:ylist){		 
			 obj.setDataStatus("已发送"); 	 
			 this.update(obj);			 
			 ZjDaiganglixueFly newObj=new ZjDaiganglixueFly();
			 newObj.setCircleId(obj.getCircleId());
			 newObj.setDataStatus("已发送");
			 newObj.setClassGroup(obj.getClassGroup()!=null?obj.getClassGroup():"");
			 newObj.setSrcId(obj.getId());
			 newObj.setRecordTime(model.getRecordTime());
			 newObj.setRecordUser(model.getRecordUser());	
			 newObj.setDeviceNum(obj.getDeviceNum());
			 newObj.setFilename(obj.getFilename());
			 newObj.setTestTime(obj.getTestTime()!=null?obj.getTestTime():"");
			 newObj.setLunum(obj.getLunum()!=null?obj.getLunum():"");
			 newObj.setSteeltype(obj.getSteeltype()!=null?obj.getSteeltype():"");
			 newObj.setSteelGuige(obj.getSteelGuige()!=null?obj.getSteelGuige():"");
			 newObj.setBranchFactory(obj.getBranchFactory()!=null?obj.getBranchFactory():"");
			 newObj.setYieldDown_streng(obj.getYieldDown_streng_xiuye()!=null?obj.getYieldDown_streng_xiuye():"");
			 newObj.setKangla_streng(obj.getKangla_streng_xiuye()!=null?obj.getKangla_streng_xiuye():"");
			 newObj.setDuanhouLong_rate(obj.getDuanhouLong_rate_xiuye()!=null?obj.getDuanhouLong_rate_xiuye():"");		
			 zjDaiganglixueFlyService.add(newObj);			 
		 }
	
	}
	
	
	
	@Override
	public void recovery(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		for (String id : ids) {
			ZjDaiganglixueSrc zjDaiganglixueSrc = this.getById(id);
			if ("作废数据".equals(zjDaiganglixueSrc.getDataStatus())) {
				zjDaiganglixueSrc.setDataStatus("原始数据");
				this.update(zjDaiganglixueSrc);
			}
		}
	}

	@Override
	public void del(String[] ids) throws Exception {
		for (String id : ids) {
			ZjDaiganglixueSrc zjDaiganglixueSrc = this.getById(id);
			if ("原始数据".equals(zjDaiganglixueSrc.getDataStatus())) {
				zjDaiganglixueSrc.setDataStatus("作废数据");
				this.update(zjDaiganglixueSrc);
			}
		}
	}

	
	
	




	
}
