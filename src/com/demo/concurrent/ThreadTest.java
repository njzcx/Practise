package com.demo.concurrent;

public class ThreadTest {
	public static void main(String[] args) throws Exception {
		System.out.println("开始");
		Thread threada = new Thread(new ThreadRunner("aaa"));
		Thread threadb = new Thread(new ThreadRunner("bbb"));
		threada.start();
		threada.wait();
		threada.notify();
		threadb.start();
		System.out.println("结束");
	}
}

class ThreadRunner implements Runnable {
	
	private String name;

	public ThreadRunner(String name) {
		this.name = name;
	}

	public void run() {
		try {
			for(int i = 0; i < 1000; i++) {
				System.out.println(name + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
