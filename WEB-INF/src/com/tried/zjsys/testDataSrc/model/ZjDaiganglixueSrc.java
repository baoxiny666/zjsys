package com.tried.zjsys.testDataSrc.model;
import java.io.Serializable;
import java.util.Date;
/**
* @Description -
* @author sunlunan
* @date 2020-07-01 10:39:20
* @version V1.0
*/
public class ZjDaiganglixueSrc implements Serializable {
//;
private java.lang.String id;
//配置表ID（设备名称，设备ID）;
private java.lang.String circleId;
//;
private java.lang.String filename;
//设备编号;
private java.lang.String deviceNum;
//观看曲线;
private java.lang.String gk_curve;
//日期;
private java.lang.String dataTime;
//班组;
private java.lang.String classGroup;
//炉号;
private java.lang.String lunum;
//钢种;
private java.lang.String steeltype;
//规格;
private java.lang.String steelGuige;
//分厂;
private java.lang.String branchFactory;
//试样厚度;
private java.lang.String sample_thick;
//样品宽度;
private java.lang.String sample_width;
//平行长度;
private java.lang.String sample_long;
//原始标距;
private java.lang.String orig_biaojv;
//断后标距;
private java.lang.String duanhou_biaojv;
//试样面积;
private java.lang.String sample_area;
//最大力;
private java.lang.String max_force;
//下屈服力;
private java.lang.String yieldDown_force;

//上屈服力;
private java.lang.String yieldUp_force;
//下屈服强度;
private java.lang.String yieldDown_streng;
//下屈服强度_修约;
private java.lang.String yieldDown_streng_xiuye;

//上屈服强度;
private java.lang.String yieldUp_streng;
//抗拉强度;
private java.lang.String kangla_streng;

//下屈服强度_修约;
private java.lang.String kangla_streng_xiuye;
//断后伸长率;
private java.lang.String duanhouLong_rate;
//断后伸长率_修约
private String duanhouLong_rate_xiuye;
//断面收缩率;
private java.lang.String duareaShrink_rate;
//试验时间;
private java.lang.String testTime;
//;
private java.lang.String dataStatus;
//试验序号;
private java.lang.Long testNo;

private Date recordTime;
private String recordUser;

 public void setId(java.lang.String  id){
 this.id=id;
}

 public java.lang.String  getId(){
 return this.id;  
}

 public void setCircleId(java.lang.String  circleId){
 this.circleId=circleId;
}

 public java.lang.String  getCircleId(){
 return this.circleId;  
}

 public void setFilename(java.lang.String  filename){
 this.filename=filename;
}

 public java.lang.String  getFilename(){
 return this.filename;  
}

 public void setDeviceNum(java.lang.String  deviceNum){
 this.deviceNum=deviceNum;
}

 public java.lang.String  getDeviceNum(){
 return this.deviceNum;  
}

 public void setGk_curve(java.lang.String  gk_curve){
 this.gk_curve=gk_curve;
}

 public java.lang.String  getGk_curve(){
 return this.gk_curve;  
}

 public void setDataTime(java.lang.String  dataTime){
 this.dataTime=dataTime;
}

 public java.lang.String  getDataTime(){
 return this.dataTime;  
}

 public void setClassGroup(java.lang.String  classGroup){
 this.classGroup=classGroup;
}

 public java.lang.String  getClassGroup(){
 return this.classGroup;  
}

 public void setLunum(java.lang.String  lunum){
 this.lunum=lunum;
}

 public java.lang.String  getLunum(){
 return this.lunum;  
}

 public void setSteeltype(java.lang.String  steeltype){
 this.steeltype=steeltype;
}

 public java.lang.String  getSteeltype(){
 return this.steeltype;  
}

 public void setSteelGuige(java.lang.String  steelGuige){
 this.steelGuige=steelGuige;
}

 public java.lang.String  getSteelGuige(){
 return this.steelGuige;  
}

 public void setBranchFactory(java.lang.String  branchFactory){
 this.branchFactory=branchFactory;
}

 public java.lang.String  getBranchFactory(){
 return this.branchFactory;  
}

 public void setSample_thick(java.lang.String  sample_thick){
 this.sample_thick=sample_thick;
}

 public java.lang.String  getSample_thick(){
 return this.sample_thick;  
}

 public void setSample_width(java.lang.String  sample_width){
 this.sample_width=sample_width;
}

 public java.lang.String  getSample_width(){
 return this.sample_width;  
}

 public void setSample_long(java.lang.String  sample_long){
 this.sample_long=sample_long;
}

 public java.lang.String  getSample_long(){
 return this.sample_long;  
}

 public void setOrig_biaojv(java.lang.String  orig_biaojv){
 this.orig_biaojv=orig_biaojv;
}

 public java.lang.String  getOrig_biaojv(){
 return this.orig_biaojv;  
}

 public void setDuanhou_biaojv(java.lang.String  duanhou_biaojv){
 this.duanhou_biaojv=duanhou_biaojv;
}

 public java.lang.String  getDuanhou_biaojv(){
 return this.duanhou_biaojv;  
}

 public void setSample_area(java.lang.String  sample_area){
 this.sample_area=sample_area;
}

 public java.lang.String  getSample_area(){
 return this.sample_area;  
}

 public void setMax_force(java.lang.String  max_force){
 this.max_force=max_force;
}

 public java.lang.String  getMax_force(){
 return this.max_force;  
}

 public void setYieldDown_force(java.lang.String  yieldDown_force){
 this.yieldDown_force=yieldDown_force;
}

 public java.lang.String  getYieldDown_force(){
 return this.yieldDown_force;  
}

 public void setYieldUp_force(java.lang.String  yieldUp_force){
 this.yieldUp_force=yieldUp_force;
}

 public java.lang.String  getYieldUp_force(){
 return this.yieldUp_force;  
}

 public void setYieldDown_streng(java.lang.String  yieldDown_streng){
 this.yieldDown_streng=yieldDown_streng;
}

 public java.lang.String  getYieldDown_streng(){
 return this.yieldDown_streng;  
}

 public void setYieldUp_streng(java.lang.String  yieldUp_streng){
 this.yieldUp_streng=yieldUp_streng;
}

 public java.lang.String  getYieldUp_streng(){
 return this.yieldUp_streng;  
}

 public void setKangla_streng(java.lang.String  kangla_streng){
 this.kangla_streng=kangla_streng;
}

 public java.lang.String  getKangla_streng(){
 return this.kangla_streng;  
}

 public void setDuanhouLong_rate(java.lang.String  duanhouLong_rate){
 this.duanhouLong_rate=duanhouLong_rate;
}

 public java.lang.String  getDuanhouLong_rate(){
 return this.duanhouLong_rate;  
}

 public void setDuareaShrink_rate(java.lang.String  duareaShrink_rate){
 this.duareaShrink_rate=duareaShrink_rate;
}

 public java.lang.String  getDuareaShrink_rate(){
 return this.duareaShrink_rate;  
}

 public void setTestTime(java.lang.String  testTime){
 this.testTime=testTime;
}

 public java.lang.String  getTestTime(){
 return this.testTime;  
}

 public void setDataStatus(java.lang.String  dataStatus){
 this.dataStatus=dataStatus;
}

 public java.lang.String  getDataStatus(){
 return this.dataStatus;  
}

 public void setTestNo(java.lang.Long  testNo){
 this.testNo=testNo;
}

 public java.lang.Long  getTestNo(){
 return this.testNo;  
}

public Date getRecordTime() {
	return recordTime;
}

public void setRecordTime(Date recordTime) {
	this.recordTime = recordTime;
}

public String getRecordUser() {
	return recordUser;
}

public void setRecordUser(String recordUser) {
	this.recordUser = recordUser;
}
/**
 * 下屈服强度值
 * @return
 */
public java.lang.String getYieldDown_streng_xiuye() {	
	if(this.yieldDown_streng!=null&&this.yieldDown_streng.contains(".")){
		String tmpStr=this.yieldDown_streng+"0000";		
        String []num=tmpStr.split("\\.");
        long zs=Long.parseLong(num[0]);
		long xs=Long.parseLong(num[1]);
		String  xs_1=(String)num[1].substring(0, 1);
		long  xs_234=Long.parseLong(num[1].substring(1)) ;
			if(xs_1.compareTo("5")>0){
				this.yieldDown_streng_xiuye=(zs+1)+"";
			}
			if(xs_1.compareTo("5")<0){
				this.yieldDown_streng_xiuye=(zs)+"";	
			}
			if(xs_1.compareTo("5")==0){
				if(xs_234==0){
					//整数为奇数
					if(zs%2==1){
						this.yieldDown_streng_xiuye=(zs+1)+"";	 
					}else{
						this.yieldDown_streng_xiuye=(zs)+""; 
					 }
					
				}else{
					this.yieldDown_streng_xiuye=(zs+1)+"";		
				}			
			}		
	}else{
		this.yieldDown_streng_xiuye=this.yieldDown_streng;
	}
	
	
	return this.yieldDown_streng_xiuye;
}

/**
 * 抗拉强度
 * @return
 */
public java.lang.String getKangla_streng_xiuye() {		
	if(this.kangla_streng!=null&&this.kangla_streng.contains(".")){
		String tmpStr=this.kangla_streng+"0000";			
        String []num=tmpStr.split("\\.");
        long zs=Long.parseLong(num[0]);
		long xs=Long.parseLong(num[1]);
		String  xs_1=(String)num[1].substring(0, 1);
		long  xs_234=Long.parseLong(num[1].substring(1).isEmpty()?"0":num[1].substring(1)) ;
			if(xs_1.compareTo("5")>0){
				this.kangla_streng_xiuye=(zs+1)+"";
			}
			if(xs_1.compareTo("5")<0){
				this.kangla_streng_xiuye=(zs)+"";	
			}
			if(xs_1.compareTo("5")==0){
				if(xs_234==0){
					//整数为奇数
					if(zs%2==1){
						this.kangla_streng_xiuye=(zs+1)+"";	 
					}else{
						this.kangla_streng_xiuye=(zs)+""; 
					 }
					
				}else{
					this.kangla_streng_xiuye=(zs+1)+"";		
				}			
			}		
	}else{
		this.kangla_streng_xiuye=this.kangla_streng;
	}	
	return this.kangla_streng_xiuye;
}


/**
 * 断后伸长率——修约
 * @return
 */

public String getDuanhouLong_rate_xiuye() {
	//截取
	if(this.duanhouLong_rate!=null&&this.duanhouLong_rate.contains(".")){
		    String tmpStr=this.duanhouLong_rate+"0000";
	        String []num=tmpStr.split("\\.");
	        long zs=Long.parseLong(num[0]);
			//long xs=Long.parseLong(num[1]);
			String cxs=num[1];
			String xs=cxs.substring(0, 4);
			int xs_2=Integer.parseInt(xs.substring(1,2));
			int xs_3=Integer.parseInt(xs.substring(2,3));
			String xs_12=xs.substring(0,2);
			String xs_4=xs.substring(3);
			double xs_last=0.0;
			if(xs_3>5){
				xs_last=Double.parseDouble(xs_12)+1;
			}
			if(xs_3<5){
				xs_last=Double.parseDouble(xs_12);
			}
			if(xs_3==5){				
				if(xs_4.compareTo("0")==0){					
					if(xs_2%2==0){
						xs_last=Double.parseDouble(xs_12);
						
					}else if(xs_2%2==1){
						xs_last=Double.parseDouble(xs_12)+1;
					}					
				}else{
					xs_last=Double.parseDouble(xs_12)+1;	
				} 
			}	
		//整数+小数
			double midNum=(zs+(xs_last*0.01))*2;
			
			double resNum=Double.parseDouble(getzsXiuyue(midNum+""))/2;
			
			this.duanhouLong_rate_xiuye=String.format("%.1f", resNum)+"";
			 
	}else{	
	  this.duanhouLong_rate_xiuye=this.duanhouLong_rate;	
	 }		
		
		
	return duanhouLong_rate_xiuye;
}


  public  String getzsXiuyue(String srcnum){
	  String reStr=srcnum;
		if(srcnum!=null&&srcnum.contains(".")){
			String temStr=srcnum+"0000";
	        String []num=temStr.split("\\.");
	        long zs=Long.parseLong(num[0]);
			long xs=Long.parseLong(num[1]);
			String  xs_1=(String)num[1].substring(0, 1);
			long  xs_234=Long.parseLong(num[1].substring(1).isEmpty()?"0":num[1].substring(1)) ;
				if(xs_1.compareTo("5")>0){
					reStr=(zs+1)+"";
				}
				if(xs_1.compareTo("5")<0){
					reStr=(zs)+"";	
				}
				if(xs_1.compareTo("5")==0){
					if(xs_234==0){
						//整数为奇数
						if(zs%2==1){
							reStr=(zs+1)+"";	 
						}else{
							reStr=(zs)+""; 
						 }
						
					}else{
						reStr=(zs+1)+"";		
					}			
				}		
		}else if(srcnum==null){
			reStr=0.0+"";
		}
				
   return reStr;  
	  
  } 





}
