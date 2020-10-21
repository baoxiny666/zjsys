package com.tried.common;

public class AlertData {
private String id;
public static void main(String[] args) {
	
	 System.out.println("空闲内存"+Runtime.getRuntime().freeMemory()/1024/1024 + " M");
     System.out.println(Runtime.getRuntime().totalMemory()/1024/1024 + " M");
     System.out.println(Runtime.getRuntime().maxMemory()/1024/1024 + " M");

}
}
