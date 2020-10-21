package org.tried.demo.model.db;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


public class Config {
	private static Config config;
	private Properties props;
	private static String jdbcUrl, driverClass, username, password;
	private Config() {
		props = new Properties();
		try{
			InputStream in = Config.class.getResourceAsStream("/jdbc.properties");    
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static Config getInstance() {
		if (config == null) {
			config = new Config();
		}
		return config;
	}


	public String getJdbcUrl() {
		if (jdbcUrl == null) {
			if (props.get("jdbcUrl") != null) {
				jdbcUrl = props.get("jdbcUrl").toString();
			}
		}
		return jdbcUrl;
	}

	public String getDriverClass() {
		if (driverClass == null) {
			if (props.get("driverClass") != null) {
				driverClass = props.get("driverClass").toString();
			}
		}
		return driverClass;
	}

	public String getJdbcUsername() {
		if (username == null) {
			if (props.get("username") != null) {
				username = props.get("username").toString();
			}
		}
		return username;
	}

	public String getJdbcPassword() {
		if (password == null) {
			if (props.get("password") != null) {
				password = props.get("password").toString();
			}
		}
		return password;
	}
	private String startDate,schedule;
	public String getStartDate(){
		if (startDate == null) {
			if (props.get("startDate") != null) {
				startDate = props.get("startDate").toString();
			}
		}
		return startDate;
	}
	public String getSchedule(){
		if (schedule == null) {
			if (props.get("schedule") != null) {
				schedule = props.get("schedule").toString();
			}
		}
		return schedule;
	}
	
	//根据日期名字获取日期型时间
	 public static String getFileToDate(String fileName){
		 fileName= fileName.substring(fileName.indexOf("(")+1,fileName.indexOf(")"));
		 String[] fileNames= fileName.split("-");
		 String dateName=fileNames[0]+"-"+fileNames[1]+"-"+fileNames[2]+" "+fileNames[3]+":10:00";
		return dateName;
	 }
	 //获取文件名前后几天的日期
	 public static String getPreNumDateFileName(int Num,String fileName) throws Exception{
		 
		 String tempName= fileName.substring(fileName.indexOf("(")+1,fileName.indexOf(")"));
		 String[] tempNames= tempName.split("-");
		 String dateName=tempNames[0]+"-"+tempNames[1]+"-"+tempNames[2];
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar rightNow = Calendar.getInstance();
		 rightNow.setTime(format.parse(dateName));
		 rightNow.add(Calendar.DAY_OF_YEAR, Num);
		 String riqi=format.format(rightNow.getTime());
		 fileName=fileName.replace(dateName, riqi);
		 
		 return  fileName;
	 }
	 
	
	
}
