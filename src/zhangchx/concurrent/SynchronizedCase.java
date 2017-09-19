package zhangchx.concurrent;

/**
 * static和非static锁定的是什么对象
 * @author Snow
 *
 */
public class SynchronizedCase {
	
	public static synchronized void sleep1() throws InterruptedException {
		System.out.println("线程" + Thread.currentThread().getName() + "开始休息5s种：" + System.currentTimeMillis());
		Thread.sleep(5000);
		System.out.println("线程" + Thread.currentThread().getName() + "休息结束" + System.currentTimeMillis());
	}
	
	public synchronized void sleep2() throws InterruptedException {
		System.out.println("线程" + Thread.currentThread().getName() + "开始休息3s种" + System.currentTimeMillis());
		Thread.sleep(3000);
		System.out.println("线程" + Thread.currentThread().getName() + "休息结束" + System.currentTimeMillis());
		SynchronizedCase.sleep1();
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new TestThread1());
		thread1.setName("线程1");
		Thread thread2 = new Thread(new TestThread2());
		thread2.setName("线程2");
		thread1.start();
		thread2.start();
		thread2.join(); //先启动，再join
		System.out.println("main进程完成");
	}
}

class TestThread1 implements Runnable {
	
	@Override
	public void run() {
		try {
			SynchronizedCase.sleep1();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class TestThread2 implements Runnable {
	
	@Override
	public void run() {
		try {
			new SynchronizedCase().sleep2();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
