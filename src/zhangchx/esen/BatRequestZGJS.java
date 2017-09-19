package zhangchx.esen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class BatRequestZGJS {
	
	static CountDownLatch latch;
	static String loginwebpath = "http://172.17.1.102:8080/i_r4.3.5_zg/i/login.do?id=%s&pw=%s&group=ZDSY2017";
	
	public static void main(String[] args) throws InterruptedException {
		List<String> users = new BatRequestZGJS().queryAllUser();
		long start = System.currentTimeMillis();
//		int capacity = users.size();
		int capacity = 5;
		String webpath = "http://172.17.1.102:8080/i_r4.3.5_zg/i/test/jstest.do?cmd=test";
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
			List<String> list = new ArrayList<String>();
			String userid = users.get(i);
			String password = userid;
			list.add(userid);
			list.add(password);
			WebVistor worker = new BatRequestZGJS().new WebVistor(webpath, null);
			worker.setLoginWebPath(loginwebpath, list.toArray(new String[list.size()]));
			new Thread(worker, "BatThread" + i).start();
		}
		latch.await(); //等待所有线程都执行结束，算出最后时间
		long end = System.currentTimeMillis();
		System.out.println((end - start)/1000f);
	}
	
	private List<String> queryAllUser() {
		List<String> list = new ArrayList<String>();
		String url = "jdbc:oracle:thin:@172.17.1.151:1521:orcl";
		String user = "zg";
		String pwd = "zg";
		
		String sql = "select t.* from UL_ZDSY2017 t where rownum <= 2000";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.createConnection(url, user, pwd);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String userid = rs.getString("userid");
				list.add(userid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	static class ConnectionFactory {
		public static Connection createConnection(String url, String user, String password) {
			Connection conn = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return conn;
		}
	}
	
	class WebVistor implements Runnable {
		
		private String url = null;
		
		private String loginwebpath = null;
		
		public WebVistor(String url, String[] params) {
			this.url = String.format(url, params);
		}
		
		public void setLoginWebPath(String loginwebpath, String[] params) {
			this.loginwebpath = String.format(loginwebpath, params);
		}

		@Override
		public void run() {
			HttpClient client = HttpClients.createDefault();
			//先登陆
			HttpPost loginHttpHost = new HttpPost(this.loginwebpath);
			try {
				HttpResponse loginresponse = client.execute(loginHttpHost);
				HttpEntity loginentity = loginresponse.getEntity();
				System.out.println(EntityUtils.toString(loginentity, "UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//循环执行业务操作
			for (int i = 0; i < 2; i++) {
				HttpPost paramHttpHost = new HttpPost(this.url);
				try {
					HttpResponse response = client.execute(paramHttpHost);
					HttpEntity entity = response.getEntity();
					System.out.println(EntityUtils.toString(entity, "UTF-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName());
			latch.countDown();
		}
		
	}
}
