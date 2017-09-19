package zhangchx.concurrent;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 批量访问某环境，压力测试
 * @author Snow
 *
 */
public class BatRequestWeb {
	
	static CountDownLatch latch;
	String loginwebpath = "http://172.17.1.151:8080/bi2.3/login.sa?id=admin&pw=admin";
	
	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		int capacity = 100;
//		String webpath = "http://172.17.1.151:8080/bi2.3/login.sa?id=admin&pw=admin&url=js/reqmgr.sa?action=calc%26rptid=12$YP63U9QSWULW9A8PYLYTOO0NTYP2RT3N$1$MSLITWK0U04UTTUS05SNLXT1TMS65URO.rpt%26startindex=0%26taskid=YP63U9QSWULW9A8PYLYTOO0NTYP2RT3N";
		String webpath = "http://172.17.1.151:8080/bi2.3/js/reqmgr.sa?action=calc&rptid=12$YP63U9QSWULW9A8PYLYTOO0NTYP2RT3N$1$MSLITWK0U04UTTUS05SNLXT1TMS65URO.rpt&startindex=0&taskid=YP63U9QSWULW9A8PYLYTOO0NTYP2RT3N";
		latch = new CountDownLatch(capacity);
		if (args.length > 0) {
			capacity = Integer.parseInt(args[0]);
			System.out.println(capacity);
		}
		if (args.length > 1) {
			webpath = args[1];
			System.out.println(webpath);
		}
		
		for (int i = 0; i < capacity; i++) {
			new Thread(new BatRequestWeb().new WebVistor(webpath), "BatThread" + i).start();
		}
		latch.await(); //等待所有线程都执行结束，算出最后时间
		long end = System.currentTimeMillis();
		System.out.println((end - start)/1000f);
		
	}
	
	class WebVistor implements Runnable {
		
		private String url = null;
		
		public WebVistor(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			for (int i = 0; i < 2; i++) {
				HttpClient client = HttpClients.createDefault();
				HttpPost paramHttpHost = new HttpPost(this.url);
				HttpPost loginHttpHost = new HttpPost(loginwebpath);
				try {
					HttpResponse loginresponse = client.execute(loginHttpHost);
					HttpResponse response = client.execute(paramHttpHost);
					HttpEntity entity = response.getEntity();
					System.out.println(EntityUtils.toString(entity, "UTF-8"));
					System.out.println(Thread.currentThread().getName());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			latch.countDown();
		}
		
	}
}
