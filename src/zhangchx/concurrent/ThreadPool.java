package zhangchx.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * 继承testcase之后，注解就不好使了，ignore标注的方法还是会执行
 * @author Snow
 *
 */
public class ThreadPool /*extends TestCase*/{
	
	ExecutorService fixedService = Executors.newFixedThreadPool(10);
	ExecutorService cachedService = Executors.newCachedThreadPool();
	ExecutorService singleService = Executors.newSingleThreadExecutor();
	ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(10);
	
	CountDownLatch latch = new CountDownLatch(20);

	public static void main(String[] args) throws Exception {
		new ThreadPool().runSchedule();
	}
	
	private void runSchedule() {
		scheduleService.scheduleAtFixedRate(new Work(), 0, 1000, TimeUnit.MILLISECONDS);

	}
	
	public void getInstance() throws Exception {
		for (int i = 0; i < 20; i++) {
			singleService.execute(new Work());
		}
		/*
		 * 为避免TestRunner在执行完主方法之后，直接结束jvm，导致线程没执行完的问题，需要保持主线程。
		 * 可见代码TestRunner.main方法
		 */
		latch.await();
	}
	
	class Work implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				System.out.println("我是工作者" + Thread.currentThread().getName());
				//超过线程池的线程会在执行时间上与前面有个大的差距
				System.out.println(System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			latch.countDown();
		}
		
	}
}
