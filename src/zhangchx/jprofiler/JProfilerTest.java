package zhangchx.jprofiler;

/**
 * 运行该测试类，使用JProfiler或者其他工具可以获得线程信息
 * @author Snow
 *
 */
public class JProfilerTest {
	
	static class JProfilerInstance {
		public static JProfilerTest instance = new JProfilerTest();
	}
	
	public static void main(String[] args) throws Exception {
		JProfilerTest.getInstance().createThreads(10);
	}
	
	public static JProfilerTest getInstance() {
		return JProfilerInstance.instance;
	}
	
	public void createThreads(int size) throws Exception {
		if (size < 0) {
			throw new Exception("参数错误");
		}
		while (size > 0) {
			new Thread(new Worker()).start();
			size--;
		}
	}
	
	class Worker implements Runnable {

		public void run() {
			int k = (int) (Math.random() * 5 + 25);
			while (true) {
				k++;
				System.out.println(Thread.currentThread().getName() + "--" + k);
			}
		}
		
	}
}
