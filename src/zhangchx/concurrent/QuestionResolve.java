package zhangchx.concurrent;

public class QuestionResolve {

	public void forAndForeach() {
		for (int i = 0; i < 10; i++) {
			System.out.println("for");
		}
		String[] str = new String[10];
		for(String i : str) {
			System.out.println("foreach");
		}
		int i = 0;
		while(i < 10) {
			i++;
		}
	}
}
