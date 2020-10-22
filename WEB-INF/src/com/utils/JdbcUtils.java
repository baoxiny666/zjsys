package com.utils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
/**
    *  创建JDBC工具类
 * @author Administrator
 *
 */
public class JdbcUtils {
	    static String driverName;
	    static String url;
	    static String user;
	    static String pwd;

	    //不论外面调用多少次getConnection方法,驱动都只要注册一次
	    static {
	        try {
	            Properties p = new Properties();
	          
	            InputStream in = JdbcUtils.class.getResourceAsStream("/jdbc.properties");
	 
	            //加载文件
	            p.load(in);

	         //   p.load(new FileInputStream("jdbc.properties"));
	            //给参数赋值
	            driverName = p.getProperty("driverClass");
	            url = p.getProperty("jdbcUrl");
	            user = p.getProperty("username");
	            pwd = p.getProperty("password");
	            Class.forName(driverName);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    //获取连接
	    public static Connection getConnection() throws SQLException {
	        Connection con = DriverManager.getConnection(url,user,pwd);
	        return con;
	    }
	    //释放资源
	    public static  void release(Connection con, Statement statement, ResultSet resultSet){
	        if (resultSet!=null){
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (statement!=null){
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (con!=null){
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
