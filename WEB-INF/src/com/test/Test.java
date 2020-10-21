package com.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import com.tried.common.ConfigUtils;

public class Test {

	/**
	 * @Description:main
	 * @author SUNLUNAN
	 * @date 2019-7-16 上午9:44:35
	 * @version V1.0
	 * @throws IOException
	 */
	public static void main(String[] args) {
		try {
//			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//			String jdbcUrl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=E:\\2020-06-13-21-32-47.mdb;READONLY=true";
//		 int i=0;
//			while(true){
//				
//			Connection conn = java.sql.DriverManager.getConnection(jdbcUrl, null, null);
//			String sql = "SELECT top 10  TestNo,Name,TheValue,Unit FROM ParamFactValue";
//			List<Object[]> objList = ConfigUtils.dbFindList(conn, sql);
//			//System.out.println(objList.size());
//			Thread.sleep(1);
//			System.out.println(i++);
//			}
			//int i=0;
			/*while(true){
				String line=Integer.toBinaryString(i++); 
				System.out.println("转换为二进制："+line);
			
				DatagramSocket socket =new DatagramSocket();  
				DatagramPacket packet =new DatagramPacket(line.getBytes(), line.getBytes().length, InetAddress.getByName("127.0.0.1"), 6666);
				socket.send(packet);
				Thread.sleep(1000);
			}*/
			
			String tt="2020-09-08";
			System.out.println(tt.substring(0, 10));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
