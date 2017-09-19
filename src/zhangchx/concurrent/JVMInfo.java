package zhangchx.concurrent;

import java.util.Iterator;
import java.util.Map;

public class JVMInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> envmap = System.getenv();
		Iterator it = envmap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			String value = envmap.get(key);
			System.out.println(key.toString() + " : " + value);
		}
	}
	
}
