package com.yjf;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yjf.common.net.HttpUtil;
import com.yjf.common.net.HttpUtil.HttpResult;

public class MYTest {
	public static void main(String[] args) {

		System.out.println(35000+3500+30000+40000+20000);

	}
	
	public static class myThread implements Runnable {
		
		public myThread() {
			
		}
		
		@Override
		public void run() {
			
			long x = System.currentTimeMillis();
			System.out.println("sss=" + x);
			for (int i = 0; i < 100; i++) {
				HttpResult httpResult = HttpUtil.getInstance().connectTimeout(10000)
					.readTimeout(60000).maxPerRoute(500).maxTotal(1000)
					.post("http://127.0.0.1:29440/bornbpm/bornsso/islogin.do", null, "utf-8");
				
				System.out.println(httpResult.getBody());
				
			}
			System.out.println("==================" + (x - System.currentTimeMillis()));
		}
		
	}
}
