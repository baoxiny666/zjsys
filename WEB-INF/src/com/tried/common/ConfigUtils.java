
package com.tried.common;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import com.tried.zjsys.testDataSrc.service.impl.ZjDaiganglixueSrcServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class ConfigUtils {
	private static Logger logger = Logger.getLogger(ConfigUtils.class);
	/*static SimpleDateFormat dateFullFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat datePartFormat = new SimpleDateFormat("MM-dd HH:mm");
	static SimpleDateFormat dateSimpleFormat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat dateSimpleNolineFormat = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat dateSimpleFormatZh = new SimpleDateFormat("yyyy年MM月dd日");
	static SimpleDateFormat dateSimpleFormatPoint = new SimpleDateFormat("yyyy.MM.dd");
	static SimpleDateFormat dateYearMonthFormat = new SimpleDateFormat("yyyy-MM");
	static SimpleDateFormat dateYearFormat = new SimpleDateFormat("yyyy");
	static SimpleDateFormat dateMonthFormat = new SimpleDateFormat("MM");
	static SimpleDateFormat dateDayFormat = new SimpleDateFormat("dd");*/
	static java.text.DecimalFormat noPointDecimalFormat =new   java.text.DecimalFormat("#");  
	
	static DecimalFormat df3strFormat = new DecimalFormat("000");
	static DecimalFormat df4strFormat = new DecimalFormat("0000");
	public static String RETURN_STATUS = "STATUS";
	public static String RETURN_DATA = "RETURN_DATA";
	
	private static ThreadLocal<DateFormat> dateFullFormatLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	private static ThreadLocal<DateFormat> datePartFormatLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("MM-dd HH:mm");
		}
	};
	private static ThreadLocal<DateFormat> dateSimpleFormatLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	private static ThreadLocal<DateFormat> dateSimpleNolineFormatLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyyMMdd");
		}
	};
	private static ThreadLocal<DateFormat> dateSimpleFormatZhLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyy年MM月dd日");
		}
	};
	private static ThreadLocal<DateFormat> dateSimpleFormatPointLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyy.MM.dd");
		}
	};
	private static ThreadLocal<DateFormat> dateYearMonthFormatLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyy-MM");
		}
	};
	private static ThreadLocal<DateFormat> dateYearFormatLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyy");
		}
	};
	private static ThreadLocal<DateFormat> dateMonthFormatLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("MM");
		}
	};
	private static ThreadLocal<DateFormat> dateDayFormatLocal = new ThreadLocal<DateFormat>(){
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("dd");
		}
	};
	
	public static String noPointNum(Float num ){
		if(num!=null){
			return noPointDecimalFormat.format(num);
		}else{
			return "0";
		}
	}
	
	public static String noPointNum(Double num ){
		if(num!=null){
			return noPointDecimalFormat.format(num);
		}else{
			return "0";
		}
	}
	public static String random32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
		 * @Description 判断对象是否为空，不为空 返回 ture ；否则false
		 * @author liuxd
		 * @date 2018-12-17 下午8:07:20
		 * @version V1.0
	 */
	public static boolean strIsNotNull(String obj){
		if(obj!=null&&!obj.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	  * @Description 数据加密
	  * @author liuxd
	  * @date 2017-3-23 下午4:36:56
	  * @version V1.0
	 */
	public static String dataEncrypt(String num) throws Exception{
		String keyCode=ConfigUtils.getPropertyByName("config.properties", "keyCode");
		return EncryptUtil.encrypt(num, keyCode);
	}
	/**
	 * 数据解密
	  * @Description
	  * @author liuxd
	  * @date 2017-3-27 下午2:46:44
	  * @version V1.0
	 */
	public static String dataDecrypt(String num) throws Exception{
		String keyCode=ConfigUtils.getPropertyByName("config.properties", "keyCode");
		return EncryptUtil.decrypt(num, keyCode);
	}

	/**
	 * 
	 * @Description SUCCESS：成功、FAIL：失败、EXCEPTION：异常、NOLOGIN：未登录、NOAUTHORITY：没有权限,ALERT:警告
	 * @author liuxd
	 * @date 2016-3-28 上午11:20:56
	 * @version V1.0
	 */
	public static enum RETURN_STATUS_VALUE {
		SUCCESS, FAIL, EXCEPTION, NOLOGIN, NOAUTHORITY,ALERT
	};
	
 

	/**
	 * @Description NOSUBMIT:待提交；SUBMIT:提交 ；PASS：通过；NOPASS：不通过,RETURN,驳回
	 * @author liuxd
	 * @date 2016-3-28 上午11:23:24
	 * @version V1.0
	 */
	public static enum TASKSTATUS {
		NOSUBMIT, SUBMIT, PASS, NOPASS, RETURN
	}
	
	/**
	 * 获取当前日期  年 月 日
	 * @title: getCurrentDate
	 * @author: lyw
	 * @date : 2018-12-7 上午10:57:56
	 * @version: v1.0
	 */
	public static String getCurrentDate(){
		return dateSimpleFormatLocal.get().format(new Date());
	}

	/**
	 * 将数字转化为长度为decimal的字符串:decimal
	 * 
	 * @param number
	 * @return
	 */
	public static String decimalNumToString(int number,int decimal) {
		String dec="";
		for(int i=0;i<decimal;i++){
			dec+="0";
		}
		DecimalFormat dfstrFormat = new DecimalFormat(dec);
		return dfstrFormat.format(number);
	}
	
	/**
	 * 将数字转化为长度为3的字符串
	 * 
	 * @param number
	 * @return
	 */
	public static String num3ToString(int number) {
		return df3strFormat.format(number);
	}

	/**
	 * 将数字转化为长度为4的字符串
	 * 
	 * @param number
	 * @return
	 */
	public static String num4ToString(int number) {
		return df4strFormat.format(number);
	}

	/**
	 * 年份转换成:yyyy
	 * 
	 * @param date
	 * @return
	 */
	public static String dateYearToString(Date date) {
		if (date != null) {
			return dateYearFormatLocal.get().format(date);
		} else {
			return dateYearFormatLocal.get().format(new Date());
		}
	}
	/**
	 * 月份转换成:MM
	 * 
	 * @param date
	 * @return
	 */
	public static String dataToMonthString(Date date) {
		if (date != null) {
			return dateMonthFormatLocal.get().format(date);
		} else {
			return "";
		}
	}
 
	/**
	 * 日期转换成:日
	 * 
	 * @param date
	 * @return
	 */
	public static String dataToDayString(Date date) {
		if (date != null) {
			return dateDayFormatLocal.get().format(date);
		} else {
			return "";
		}
	}
	/**
	 * @Description 日期转换成字符串yyyy-MM-dd HH24:mm:ss
	 * @author liuxd
	 * @date 2015-8-1 下午4:22:12
	 * @version V1.0
	 */
	public static String dataToString(Date date) {
		if (date != null) {
			return dateFullFormatLocal.get().format(date);
		} else {
			return "";
		}
	}
	
	/**
	 *
	  * @Description MM-dd HH:mm 日期 转字符串
	  * @author liuxd
	  * @date 2016-11-27 下午9:02:01
	  * @version V1.0
	 */
	public static String dataPartToString(Date date) {
		if (date != null) {
			return datePartFormatLocal.get().format(date);
		} else {
			return "";
		}
	}
	

	/**
	 * @Description 日期转换成字符串yyyy-MM-dd
	 * @author liuxd
	 * @date 2015-8-1 下午4:22:24
	 * @version V1.0
	 */
	public static String dataToSimpleString(Date date) {
		if (date != null) {
			return dateSimpleFormatLocal.get().format(date);
		} else {
			return "";
		}
	}
	/**
	 * @Description 日期转换成字符串yyyyMMdd
	 * @author liuxd
	 * @date 2015-8-1 下午4:22:24
	 * @version V1.0
	 */
	public static String dataNoLineToSimpleString(Date date) {
		if (date != null) {
			return dateSimpleNolineFormatLocal.get().format(date);
		} else {
			return "";
		}
	}
	
	public static String dataToSimpleStringZh(Date date){
		if(date != null){
			return dateSimpleFormatZhLocal.get().format(date);
		}else{
			return "";
		}
	}
	
	public static String dataToSimpleStringPoint(Date date){
		if(date != null){
			return dateSimpleFormatPointLocal.get().format(date);
		}else{
			return "";
		}
	}
	
	 
	/**
	 * @Description 日期转换成字符串yyyy-MM
	 * @author liuxd
	 * @date 2015-8-1 下午4:22:24
	 * @version V1.0
	 */
	public static String dataToYearMonthString(Date date) {
		if (date != null) {
			return dateYearMonthFormatLocal.get().format(date);
		} else {
			return "";
		}
	}
	
	public static Date StingToDataYearMonthString(String date) throws Exception {
		if (date != null) {
			return dateYearMonthFormatLocal.get().parse(date);
		} else {
			return null;
		}
	}
	/**
	 * 日期yyyy-MM转换成LONG
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	public static Long yearmonthToLong(String date) throws Exception {
		if (date != null) {
			return dateYearMonthFormatLocal.get().parse(date).getTime();
		} else {
			return null;
		}
	}
	/**
	 * 字符串转换成日期yyyy-MM-dd HH24:mm:ss
	 * 
	 * @author liuxd
	 * @date 2014-2-18 上午11:20:56
	 * @version V1.0
	 */
	public static Date stringToData(String stringDate) {
		Date date = null;
		if (stringDate != null) {
			try {
				date = dateFullFormatLocal.get().parse(stringDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			date = new Date();
		}
		return date;
	}

	/**
	 * 字符串转换成日期yyyy-MM-dd
	 * 
	 * @author liuxd
	 * @date 2014-2-18 上午11:20:56
	 * @version V1.0
	 */
	public static Date stringToSimpleData(String stringDate) {
		Date date = null;
		if (stringDate != null) {
			try {
				date = dateSimpleFormatLocal.get().parse(stringDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			date = new Date();
		}
		return date;
	}

	/**
	 * 处理json中存在日期类型值
	 * 
	 * @author liuxd
	 * @date 2014-2-27 上午10:40:27
	 * @version V1.0
	 */
	public static Object jsonWorkDate(Object obj) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		return JSONArray.fromObject(obj, jsonConfig);
	}

	/**
	 * @Description 32位随机数
	 * @author liuxd
	 * @date 2015-9-14 下午9:16:17
	 * @version V1.0
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String getPropertyByName(String path, String name) {
		String result = "";
		InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream(path);
		Properties prop = new Properties();
		try {
			prop.load(in);
			result = prop.getProperty(name).trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @Description 根据年月 和 num 获取num个不同年份相同月份 的年月
	 * @author liuxd
	 * @date 2016-4-26 下午3:40:36
	 * @version V1.0
	 */
	public static List<String> getSameMonthYearList(String yearMonth, int num) throws Exception {
		List<String> yearMonths = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date monthDate = sdf.parse(yearMonth);
		for (int i = 0; i < num; i++) {
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(monthDate);
			calBegin.add(Calendar.YEAR, -i);
			yearMonth = sdf.format(calBegin.getTime());
			yearMonths.add(yearMonth);
		}
		return yearMonths;
	}

	/**
	 * 
	 * @Description 根据年月日 和 num 获取num个不同月份相同日 的年月日
	 * @author liuxd
	 * @date 2016-4-26 下午3:40:36
	 * @version V1.0
	 */
	public static List<String> getSameDayMonthList(String yearMonthDay, int num) throws Exception {
		List<String> yearMonthDays = new ArrayList<String>();
		Date monthDate = dateSimpleFormatLocal.get().parse(yearMonthDay);
		for (int i = 0; i < num; i++) {
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(monthDate);
			calBegin.add(Calendar.MONTH, -i);
			yearMonthDay = dateSimpleFormatLocal.get().format(calBegin.getTime());
			yearMonthDays.add(yearMonthDay);
		}
		return yearMonthDays;
	}

	/**
	 * @Description 根据年和 num 获取num个年
	 * @author liuxd
	 * @date 2016-4-26 下午3:40:36
	 * @version V1.0
	 */
	public static List<String> getNumYearList(String year, int num) throws Exception {
		List<String> years = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date monthDate = dateFormat.parse(year);
		for (int i = 0; i < num; i++) {
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(monthDate);
			calBegin.add(Calendar.YEAR, -i);
			year = dateFormat.format(calBegin.getTime());
			years.add(year);
		}
		return years;
	}

	/**
	 * @Description 根据年月和 num 获取num个年月
	 * @author liuxd
	 * @date 2016-4-26 下午3:40:36
	 * @version V1.0
	 */
	public static List<String> getNumMonthList(String yearMonth, int num) throws Exception {
		List<String> years = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date monthDate = dateFormat.parse(yearMonth);
		for (int i = 0; i < num; i++) {
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(monthDate);
			calBegin.add(Calendar.MONTH, -i);
			yearMonth = dateFormat.format(calBegin.getTime());
			years.add(yearMonth);
		}
		return years;
	}
	
	/**
	 * @Description 根据年月和 num 获取num个年月
	 * @author liuxd
	 * @date 2016-4-26 下午3:40:36
	 * @version V1.0
	 */
	public static List<String> getAddMonthList(String yearMonth, int num) throws Exception {
		List<String> years = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date monthDate = dateFormat.parse(yearMonth);
		for (int i = 0; i < num; i++) {
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(monthDate);
			calBegin.add(Calendar.MONTH, +i);
			yearMonth = dateFormat.format(calBegin.getTime());
			years.add(yearMonth);
		}
		return years;
	}
	/**
	 * @Description 根据startDate,endDate
	 * @author liuxd
	 * @date 2016-4-26 下午3:40:36
	 * @version V1.0
	 */
	public static List<String> getNumMonthList(String startDate,String endDate) throws Exception{
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM");
		int result = 1;
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(dateFormate.parse(startDate));
		end.setTime(dateFormate.parse(endDate));
		result =(end.get(Calendar.YEAR)-start.get(Calendar.YEAR))*12 + end.get(Calendar.MONTH)-start.get(Calendar.MONTH)+1;
		if(result<0){
			 result = 1;
		}
		return getAddMonthList(startDate,result);
	}
	
 
 

	/**
	 * @Description 根据年月日和 num 获取num个年月日
	 * @author liuxd
	 * @date 2016-4-26 下午4:30:56
	 * @version V1.0
	 */
	public static List<String> getNumDayList(String yearMonthDay, int num) throws Exception {
		List<String> years = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date monthDate = dateFormat.parse(yearMonthDay);
		for (int i = 0; i < num; i++) {
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(monthDate);
			calBegin.add(Calendar.DAY_OF_MONTH, -i);
			yearMonthDay = dateFormat.format(calBegin.getTime());
			years.add(yearMonthDay);
		}
		return years;
	}

	/**
	 * 
		 * @Description 根据开始日期和结束日期获取中间日期数组
		 * @author liuxd
		 * @date 2019-3-19 下午5:10:09
		 * @version V1.0
	 */
	public static List<String> getNumData(String startDate,String endDate)throws Exception {
		
	 	List<String> result=new LinkedList<String>();
	 	if(null!=startDate&&null!=endDate&&!startDate.isEmpty()&&!endDate.isEmpty()){
		Date stime=ConfigUtils.stringToSimpleData(startDate);
		Date etime=ConfigUtils.stringToSimpleData(endDate);
		for(Date d=new Date(stime.getTime());d.before(etime) ;d.setDate(d.getDate()+1)){
			result.add(ConfigUtils.dataToSimpleString(d));
		}
		result.add(endDate);
	 	}
		return result;
	}
	/**
	 * 进行日期加减
	 * @title: getDate
	 * @author: lyw
	 * @date : 2018-12-10 下午12:50:25
	 * @version: v1.0
	 */
	public static String getDate(Date curDate,Integer cycle,String unit){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		if("年".equals(unit)){
			calendar.add(Calendar.YEAR, cycle);
		}else if("月".equals(unit)){
			calendar.add(Calendar.MONTH, cycle);
		}else if("日".equals(unit)){
			calendar.add(Calendar.DAY_OF_YEAR, cycle);
		}
		Date resDate = calendar.getTime();
		String date = dateSimpleFormatLocal.get().format(resDate);
		return date;
	}
	
	 
	/**
	 * 根据日期和循环天数，获取新日期
	 * @throws ParseException 
	 */
	
	public static String  getDateByDays(String date,int num) throws ParseException{
		String resultTime="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateSimpleFormatLocal.get().parse(date));
		cal.add(Calendar.DATE, num);
		resultTime=dateSimpleFormatLocal.get().format(cal.getTime());
		System.out.println(resultTime);
		return resultTime;
		
	}
	
	/**
	 * 生产长度为length的随机数
	 */
	public static String getFixLengthCode(int length) {
		Random rm = new Random();
		double pross = (1 + rm.nextDouble()) * Math.pow(10, length);
		String fixString = String.valueOf(pross);
		return fixString.substring(1, length + 1);
	}

	public  static String formateData(String status,Object model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("STATUS", status);
		result.put("DATA", model);
		return JSONObject.fromObject(result).toString();
	}
	
	
	/**
	 * 将对象以json数据格式输出
	 * 
	 * @param obj
	 *            对象
	 * @throws Exception
	 */
	public static String outJsonData(Object obj) {
		return JSONObject.fromObject(obj).toString();
	}

	/**
	 * 
	  * @Description 无分页列表
	  * @author liuxd
	  * @date 2016-11-16 下午2:27:34
	  * @version V1.0
	 */
	public static String outRowsData(Object obj) {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("rows", obj);
		resultMap.put("total", 0);
		return JSONObject.fromObject(resultMap).toString();
	}
	
	public static String digitalToChar(int digital){
		String result = "";
		switch (digital) {
			case 0:
				result = "A";
				break;
			case 1:
				result = "B";
				break;
			case 2:
				result = "C";
				break;
			case 3:
				result = "D";
				break;
			case 4:
				result = "E";
				break;
			case 5:
				result = "F";
				break;
			case 6:
				result = "G";
				break;
			case 7:
				result = "H";
				break;
			case 8:
				result = "I";
				break;
			case 9:
				result = "J";
				break;
			case 10:
				result = "K";
				break;
			case 11:
				result = "L";
				break;
			case 12:
				result = "M";
				break;
			case 13:
				result = "N";
				break;
			case 14:
				result = "O";
				break;
			case 15:
				result = "P";
				break;
			case 16:
				result = "Q";
				break;
			case 17:
				result = "R";
				break;
			case 18:
				result = "S";
				break;
			case 19:
				result = "T";
				break;
			case 20:
				result = "U";
				break;
			case 21:
				result = "V";
				break;
			case 22:
				result = "W";
				break;
			case 23:
				result = "X";
				break;
			case 24:
				result = "Y";
				break;
			case 25:
				result = "Z";
				break;
			default:
				result = "A";
				break;
		}
		return result;
	}
	
	public static int halfNum(String psArray){
		int reNum=0;
		int psArrayLength= psArray.split(";").length;
		if(psArrayLength<=2){return psArrayLength;}
		int part=psArrayLength/2;
		double yu=psArrayLength*0.5;
		if(part<=yu){
			reNum=part+1;
		}
		return reNum;
	}
	
	public static boolean isNumeric(String str){ 
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		 Matcher isNum = pattern.matcher(str);
		 if( !isNum.matches() ){
		     return false; 
		 } 
		 return true; 
	}
	/**
	 * 
	  * @Description num:分子，totle：分母
	  * @author liuxd
	  * @date 2017-2-28 上午11:51:33
	  * @version V1.0
	 */
	public static String getNumberPercentage(Long num,Long total){
		if(num==null||total==null){return "";}
		 DecimalFormat df = new DecimalFormat("0%");  
	     //可以设置精确几位小数  
	     df.setMaximumFractionDigits(1);  
	     //模式 例如四舍五入  
	     df.setRoundingMode(RoundingMode.HALF_UP);  
	     double accuracy_num = num * 1.0/ total * 1.0;  
	     return df.format(accuracy_num); 
	}
	/**
	 * 获取根路径
	 * @return
	 */
	public static String getRootPath(){
		return ConfigUtils.getPropertyByName("config.properties","uppath")+"\\newProject";
	}
	/**
	 * 获取历史版本路径
	 * @return
	 */
	public static String getDustPath(){
		return ConfigUtils.getPropertyByName("config.properties","uppath")+"\\oldProject";
	}
	/**
	 * 获取删除版本路径
	 * @return
	 */
	public static String getDelPath(){
		return ConfigUtils.getPropertyByName("config.properties","uppath")+"\\delProject";
	}
	
	/**
	 * 获取临时目录路径
	 * @param tempDir
	 * @return
	 */
	public static String getTempDirPath(){
		String tempfile = ConfigUtils.getPropertyByName("config.properties","tempfile");
		Date currentDate = new Date();
		
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(currentDate);
		calBegin.add(Calendar.DAY_OF_MONTH, -1);
		String beforeDate = dateSimpleFormatLocal.get().format(calBegin.getTime());
		String beforePath = tempfile + File.separator + beforeDate + File.separator;
		File beforeFile = new File(beforePath);
		deleteAllFilesOfDir(beforeFile);
		String dirPath = tempfile + File.separator + dateSimpleFormatLocal.get().format(currentDate) + File.separator;
		File newDirPath = new File(dirPath);
		if(!newDirPath.isDirectory()){
			newDirPath.mkdirs();
		}
		return dirPath;
	}
	/**
	 * 递归删除目录
	 * @param path
	 */
	private static void deleteAllFilesOfDir(File path) {
		if(!path.exists())
			return;
		if(path.isFile()){
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for(int i=0;i<files.length;i++){
			deleteAllFilesOfDir(files[i]);
		}
		path.delete();
	}
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public static Date AfterTime(Date data,long num){
		long time=data.getTime();
		long aftertime=time+num;
		return new Date(aftertime);
	}
	public static boolean isDigit(String str){
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()){
			return false;
		}
		return true;
	}

	public static Date AfterTime(Date data,int num,String type) {	   
		Calendar calendar = new java.util.GregorianCalendar();
		 calendar.setTime(data);
		if(type.equals("天")){
			  calendar.add(Calendar.DATE, num);		
		}
	    if(type.equals("时")){
	    	 calendar.add(Calendar.HOUR_OF_DAY, num);			
	    }		
	    if(type.equals("分")){
	    	 calendar.add(Calendar.MINUTE, num);			  
	    }	  
	    
	    return  calendar.getTime();
	}
	
	
	

	public static String getExtensionName(String filename) {
		if((filename != null)&&(filename.length()>0)){
			int dot = filename.lastIndexOf('.');
			if((dot > -1)&&(dot<(filename.length()-1))){
				return filename.substring(dot+1);
			}
		}
		return filename;
	}
	/*
	 * 消除换行和空格
	 */
	public static String replaceBlank(String str) {  
        String dest = "";  
        if (str!=null) {  
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
            Matcher m = p.matcher(str);  
            dest = m.replaceAll("");  
        }  
        return dest;  
    }  
	
	/**
	 * 根据范围获取指定范围内的随机数
	 * @param num 0-num内
	 * @return
	 */
	public static int getRandomNum(int num){
	java.util.Random random=new java.util.Random();// 定义随机类
	int result=random.nextInt(num);// 返回[0,10)集合中的整数，注意不包括10
	return result+1; 
	}  
 
	/**
		 * @Description 判断是否是json字符串
		 * @author liuxd
		 * @date  2018-11-26下午2:04:00
		 * @version V1.0
	 */
	public static boolean isJson(String content) {
        try {
            JSONObject.fromObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	/**
	 *  月度最小值
		 * @Description 
		 * @author liuxd
		 * @date 2019-4-2 上午9:27:58
		 * @version V1.0
	 */
	public static String monthMinTime(String month){
		if (month != null && !month.isEmpty()) {
			return month + "-01 00:00:00";
		} else {
			return null;
		}
	}
	/**
	 * 月度最大值
		 * @Description 
		 * @author liuxd
		 * @date 2019-4-2 上午9:28:22
		 * @version V1.0
	 * @throws Exception 
	 */
	public static String monthMaxTime(String month) throws Exception{
		if(month != null && !month.isEmpty()){
			Calendar calendar = Calendar.getInstance();
			Date monthdate=dateYearMonthFormatLocal.get().parse(month);
			calendar.setTime(monthdate);
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			return month+"-"+lastDay+" 23:59:59";
		}else{
			return null;
		}
		 
	}
	/**
	 * 数字转人民币大写
	 * @title: digitToRmbUpper
	 * @author: lyw
	 * @date : 2019-4-26 上午11:44:06
	 * @version: v1.0 
	 */
	public static String digitToRmbUpper(double n){
		String fraction[] = {"角","分"};
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};  
		String unit[][] = { { "元", "万", "亿"}, { "", "拾", "佰", "仟"}}; 
		
		String head = n<0?"负":"";
		n = Math.abs(n);
		
		String s = "";
		for(int i=0;i<fraction.length;i++){
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");  
		}
		if(s.length()<1){
			s="整";
		}
		int integerPart = (int)Math.floor(n);
		
		for(int i=0;i<unit[0].length && integerPart>0;i++){
			String p = "";
			for(int j=0;j<unit[1].length && n>0;j++){
				p = digit[integerPart%10]+unit[1][j] + p;
				integerPart = integerPart /10;
			}
			s =  p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;  
		}
		
		return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");  
	}
	
	/**
	 * 判断在字符串Str中s串出现的次数
	 * @Description:getStrCount
	 * @author SUNLUNAN
	 * @date 2019-6-18 下午12:57:58
	 * @version V1.0
	 */
	public static int getStrCount(String str,String s){
		String str1 = str.replaceAll(s, "");
		int len1 = str.length(),len2 = str1.length(),len3 = s.length();
		int count = (len1 - len2) / len3;
		return count;
	}
	
	/**
	 * 获取单元格各类型值，返回字符串类型
	 * 判断Excel单元格数据类型并返回字符串数据
	 */
    public static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType=cell.getCellType();
        switch (cellType) {
        case Cell.CELL_TYPE_NUMERIC: // 数字
            short format = cell.getCellStyle().getDataFormat();
            if (DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = null;  
                //System.out.println("cell.getCellStyle().getDataFormat()="+cell.getCellStyle().getDataFormat());
                if (format == 20 || format == 32) {  
                    sdf = new SimpleDateFormat("HH:mm");  
                } else if (format == 14 || format == 31 || format == 57 || format == 58|| format == 177) {  
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                    sdf = new SimpleDateFormat("yyyy-MM-dd");  
                    double value = cell.getNumericCellValue();  
                    Date date = org.apache.poi.ss.usermodel.DateUtil  
                            .getJavaDate(value);  
                    cellValue = sdf.format(date);  
                }else {// 日期  
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                }  
                try {
                    cellValue = sdf.format(cell.getDateCellValue());// 日期
                } catch (Exception e) {
                    try {
                        throw new Exception("exception on get date data !".concat(e.toString()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }finally{
                    sdf = null;
                }
            }  else {
                BigDecimal bd = new BigDecimal(cell.getNumericCellValue()); 
                cellValue = bd.toPlainString();// 数值 这种用BigDecimal包装再获取plainString，可以防止获取到科学计数值
            }
            break;
        case Cell.CELL_TYPE_STRING: // 字符串
            cellValue = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_BOOLEAN: // Boolean
            cellValue = cell.getBooleanCellValue()+"";;
            break;
        case Cell.CELL_TYPE_FORMULA: // 公式
            cellValue = cell.getCellFormula();
            break;
        case Cell.CELL_TYPE_BLANK: // 空值
            cellValue = "";
            break;
        case Cell.CELL_TYPE_ERROR: // 故障
            cellValue = "ERROR VALUE";
            break;
        default:
            cellValue = "UNKNOW VALUE";
            break;
        }
        return cellValue;
    }

    /**
     * 获取天数
     * @param arriveTime
     * @param planCompleteTime
     * @param weekend
     * @param holiday
     * @return
     */
	public static int getTotalDaysByTerm(String arriveTime,String planCompleteTime, String weekend, String holiday) {
		LinkedHashMap<String, Date>dayMap=getDaysbyWeek(arriveTime,planCompleteTime,weekend);
		 if(holiday!=null&&holiday.trim().length()!=0&&!holiday.isEmpty()){
			String [] holidayArry= holiday.split(",");
			for(String day:holidayArry){				
				if(dayMap.containsKey(day)){
					dayMap.remove(day);
				}
			} 			 
		 } 		
		return dayMap.size();
	}

	/**
	 * 返回时间List
	 * @param arriveTime
	 * @param planCompleteTime
	 * @param weekend
	 * @return
	 */
	private static LinkedHashMap<String, Date> getDaysbyWeek(String arriveTime,String planCompleteTime, String weekend) {
		LinkedHashMap<String, Date>dayMap= new LinkedHashMap<String, Date>();
		 Date startTime=stringToSimpleData(arriveTime);
		 Date endTime=stringToSimpleData(planCompleteTime);
		 Calendar calendar = Calendar.getInstance();	    
			 while(startTime.compareTo(endTime)!=1){ // startTime不大于endTime
				 calendar.setTime(startTime);		
				 
				 int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				 
				 if(week == 0 || week == 6){//0为周日，6为周六					
					 if(weekend.equals("含周六日")){
						 dayMap.put(ConfigUtils.dataToSimpleString(startTime), startTime);				 
					  }                   
				       
				  }else{
					  dayMap.put(ConfigUtils.dataToSimpleString(startTime), startTime);	  					   
				  }
				 
				//跳出循环进入下一个日期
			  	 calendar.add(Calendar.DAY_OF_MONTH, +1);
			     startTime = calendar.getTime();
			 }
		return dayMap;
	}
	public static List<Object[]> dbFindList(Connection conn ,String sql) throws Exception{
		List<Object[]> resList = new ArrayList<Object[]>();
		PreparedStatement preStat =null;
		ResultSet result =null;
		try{
			
			 preStat = conn.prepareStatement(sql);
			 result = preStat.executeQuery();
			int count = result.getMetaData().getColumnCount();
			while(result.next()){
				Object[] array = new Object[count];
				for(int i=0;i<count;i++){
					array[i] = result.getString(i+1);
				}
				resList.add(array);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			new Exception("数据库异常");
		}
		finally{
			try {
	            if(result !=null)result.close();
	        } catch (Exception e) {
	            logger.error(e.getMessage());
	        }

	        try {
	            if(preStat !=null)preStat.close();
	        } catch (Exception e) {
	            logger.error(e.getMessage());
	        }

	        try {
	            if(conn !=null)conn.close();
	        } catch (Exception e) {
	            logger.error(e.getMessage());
	        }
		}
		return resList;
	}
	
	public static boolean accessExcuet(Connection conn ,String sql) throws Exception{
		boolean flag=false;
		PreparedStatement preStat =null;
		try{
			 preStat = conn.prepareStatement(sql);
			 flag= preStat.execute(); 
			
		}catch(Exception e){
			logger.error(e.getMessage());
			new Exception("数据库异");
		}finally{
	        try {
	            if(preStat !=null)preStat.close();
	        } catch (Exception e) {
	            logger.error(e.getMessage());
	        }
	        try {
	            if(conn !=null)conn.close();
	        } catch (Exception e) {
	            logger.error(e.getMessage());
	        }
		}
		return flag;
	}
}
