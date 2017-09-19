package zhangchx.concurrent;

/**
 * 做计算操作会是cpu使用率升高
 * @author Snow
 *
 */
public class CPUController {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new CPUController().new testThread());
			thread.start();
		}
	}
	
	private void cycleCalculate() {
		int i = 0;
		while(true) {
			i++;
			System.out.println(i);
		}
	}
	
	class testThread implements Runnable {
		@Override
		public void run() {
			new CPUController().cycleCalculate();
		}
	}
}
