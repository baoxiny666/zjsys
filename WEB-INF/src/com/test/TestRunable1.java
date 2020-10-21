package com.test;

public class TestRunable1 implements Runnable {

	@Override
	public void run() {
		while(true){
			System.out.println(Thread.currentThread().getName()+"----");
			try {
	            Thread.sleep(1000); // 休息1000ms
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		
	}

}
