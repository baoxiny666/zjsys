package com.test;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import java.io.FileInputStream;
import java.io.InputStream;


public class TestDbf {

	    public static void readDBF(String path) {
	        InputStream fis = null;
	        try {
	            // 读取文件的输入流
	            fis = new FileInputStream(path);
	            // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
	            DBFReader reader = new DBFReader(fis);
	            // 调用DBFReader对实例方法得到path文件中字段的个数
	            int fieldsCount = reader.getFieldCount();	           
	            // 取出表头信息
	            for (int i = 0; i < fieldsCount; i++) {
	                DBFField field = reader.getField(i);
	                System.out.print(field.getName()+",");
	            }
	            System.out.println("");
	            Object[] rowValues;
	            // 分行取出path文件中记录	            
	            int rowNum=0;
	            while ((rowValues = reader.nextRecord()) != null) {
	            	  System.out.print(rowNum+":");
	            	for (int i = 0; i < rowValues.length; i++) {
	                    System.out.print(rowValues[i]+",");
	                }
	            	System.out.println("");
	                rowNum++;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                fis.close();
	            } catch (Exception e) {
	            }
	        }
	    }
	    
	    public static void main(String[] args) {
	    	TestDbf.readDBF("c:/test/F2020-06.DBF");
	    }

}