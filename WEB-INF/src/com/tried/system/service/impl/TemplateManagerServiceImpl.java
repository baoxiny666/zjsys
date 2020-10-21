package com.tried.system.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.common.ConfigUtils;
import com.tried.common.FileCommonUtils;
import com.tried.common.WordUtil;
import com.tried.system.model.FileManager;
import com.tried.system.model.TemplateManager;
import com.tried.system.service.FileManagerService;
import com.tried.system.service.TemplateManagerService;
/**
 * @Description - 服务接口实现
 * @author lyw
 * @date 2018-12-24 11:30:17
 * @version V1.0
 */
@Service
public class TemplateManagerServiceImpl extends BaseServiceImpl<TemplateManager> implements TemplateManagerService {

	@Resource
	FileManagerService fileManagerService;
	@Override
	public void addModel(TemplateManager template, String pkId) throws Exception {
		this.add(template);
		fileManagerService.add("templateFile", template.getTemplateFile(), pkId,template.getId());
	}
	@Override
	public void updateModel(TemplateManager template, String pkId)
			throws Exception {
		this.update(template);
		fileManagerService.add("templateFile", template.getTemplateFile(), pkId,template.getId());
	}
	@Override
	public TemplateManager getByType(String type) throws Exception {
		List<TemplateManager> list = this.findAll("from TemplateManager where templateType = '"+type+"' order by recordTime desc");
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public TemplateManager getByName(String name) throws Exception {
		List<TemplateManager> list = this.findAll("from TemplateManager where templateName = '"+name+"' order by recordTime desc");
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	@Override
	public String changeKeyWord(TemplateManager model, String pkId) throws Exception {
		List<FileManager> managerList=fileManagerService.findAll(" from FileManager where pkColumn ='test_Model_file'  and pkid='"+pkId+"' order by recordTime desc");
		if(managerList.size()==0){throw new Exception("请上传文件！");}
		   //替换
		  Map<String, Object> param =getKeyMap(model.getTemplateType());
			
		for(FileManager manager:managerList){
			String file_name=manager.getFileSaveName();
			String srcPath=manager.getFileSavePath()+manager.getFileSaveName();
			String desPath=ConfigUtils.getTempDirPath()+file_name.substring(0, file_name.indexOf(".docx"))+"_new.docx";
			WordUtil wu = new WordUtil();			
			wu.replaceWordObject(param, srcPath, desPath);
			FileManager filem=new FileManager();
			filem.setFileSavePath(ConfigUtils.getTempDirPath());
			filem.setFileSaveName(file_name.substring(0, file_name.indexOf(".docx"))+"_new.docx");
			filem.setPkColumn("test_change_file");
			filem.setPkId("-100");
			filem.setFilename(model.getTemplateType()+".docx");
			fileManagerService.add(filem);
			fileManagerService.delete(manager.getId());
		}
		return "-100";
	}
	private Map<String, Object> getKeyMap(String templateType) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//超级管理员电子签名
		 FileManager adminSign=fileManagerService.getFirstRecordByField("from FileManager where pkId='1' and pkColumn='system_user_sign_name' ");		 	 
		 if(templateType.equals("任务卡")){
			 if(adminSign!=null){
				    //制卡人
					singleUserSignSet(adminSign.getFileSavePath()+adminSign.getFileSaveName(),param,"${cardPerson}"); 
					//制卡校核人
					singleUserSignSet(adminSign.getFileSavePath()+adminSign.getFileSaveName(),param,"${cardCheckPerson}"); 
				  }	
				 param.put("${applyNum}", "sqNum0001") ;
				 param.put("${proxyPerson}", "张三") ;
				 param.put("${sampleName}", "集成电气柜090") ;
				 param.put("${testType}", "3C（CQC）") ;
				 param.put("${sampleGuige}", "g001;g002;g003") ;
				 param.put("${testYj}", "yj001002") ;
				 param.put("${reportNum}", "2019-CCC-0716");
				 param.put("${cardDate}", "2019-07-16") ;			 
				 param.put("${sjCompany}", "天津XXXX公司");
				 param.put("${scCompany}","天津XXXX公司");
				 param.put("${edingV}","280");
				 param.put("${edingA}","50");
				 param.put("${sampleNum}", "2019-G-0106-1");
				 param.put("${syDate}", "2019-08-27");			 
			 
			}else if(templateType.equals("原始记录")){	
				if(adminSign!=null){
					//原始记录人
					singleUserSignSet(adminSign.getFileSavePath()+adminSign.getFileSaveName(),param,"${recordUser}"); 
					//原始记录校核人
					singleUserSignSet(adminSign.getFileSavePath()+adminSign.getFileSaveName(),param,"${checkUser}"); 
					//${test1}	                  
					singleUserSignSet(adminSign.getFileSavePath()+adminSign.getFileSaveName(),param,"${test1}"); 
				}
				 param.put("${applyNum}", "sqNum0001") ;
				 param.put("${proxyPerson}", "张三") ;
				 param.put("${sampleName}", "集成电气柜090") ;
				 param.put("${testType}", "3C（CQC）") ;
				 param.put("${sampleGuige}", "g001;g002;g003") ;
				 param.put("${testYj}", "yj001002") ;
				 param.put("${reportNum}", "2019-CCC-0716");
				 param.put("${cardDate}", "2019-07-16") ;			 
				 param.put("${sjCompany}", "天津XXXX公司");
				 param.put("${scCompany}","天津XXXX公司");
				 param.put("${edingV}","280");
				 param.put("${edingA}","50");
				 param.put("${sampleNum}", "2019-G-0106-1");
				 param.put("${syDate}", "2019-08-27");	
				 param.put("${test2}", "");	
				 param.put("${test3}", "");	
				 param.put("${test4}", "");	
				 		       
			}else if(templateType.indexOf("计量")!=-1){
				if(adminSign!=null){
					//计量批准人
					singleUserSignSet(adminSign.getFileSavePath()+adminSign.getFileSaveName(),param,"${pzUser}"); 
					//计量核验员
					singleUserSignSet(adminSign.getFileSavePath()+adminSign.getFileSaveName(),param,"${jhUser}"); 
					//计量检定员
					singleUserSignSet(adminSign.getFileSavePath()+adminSign.getFileSaveName(),param,"${syUser}"); 
				}				
				 param.put("${meterReportNum}", "27W2019电D-012") ;
				 param.put("${proxyCompany}", "天津市百利纽泰克电气科技有限公司") ;
				 param.put("${meterName}", "变压器变比组别测试仪") ;
				 param.put("${meterModel}", "NDBC-IV") ;
				 param.put("${meterChuchangNum}", "ND160301") ;
				 param.put("${madeCompany}", "武汉诺顿电气有限公司") ;
				 param.put("${meterJcyj}", "JJG 780-1992《交流数字功率表》");
				 param.put("${meterJl}", "符合0.5级要求") ;			 
				 param.put("${yd}", "2019") ;
				 param.put("${md}", "01");
				 param.put("${dd}","01");
				 param.put("${yx}","2020");
				 param.put("${mx}","01");
				 param.put("${dx}", "01");	
			}		
		return param;
	}
	
	
	
	
	
	
	public static void singleUserSignSet(String signFile,Map<String,Object> param,String Tag) throws Exception{
		   if(!param.containsKey(Tag)){
		        if(null!=signFile){
		        	//判断电子签名文件是否存在
		        	File signImgFile=new File(signFile);
		        	if(signImgFile.exists()){
		        		String destFile = ConfigUtils.getTempDirPath()+File.separator + signImgFile.getName();
		        		FileCommonUtils.copyFile(signFile, destFile);
			            Map<String,Object> header = new HashMap<String, Object>();  
			        	    header.put("width", 76);  
					        header.put("height", 38);  
					        header.put("type", "png");  
			        	    header.put("content", WordUtil.inputStream2ByteArray(new FileInputStream(destFile), true));  
					        param.put(Tag,header);
			        	}
		         }
		   }
	}	
	

}
