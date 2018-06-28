package zhangchx;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mysql.jdbc.Connection;

import zhangchx.spring.bean.User;

class A {
	public static String name = "zhangchx";
	
	static {
		System.out.println("我是父类");
	}
}

class B extends A{
	static {
		System.out.println("我是子类");
	}
}
public class Test {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {
//		System.out.println(Integer.reverse(123456));
		System.out.println(B.name);
	}
	
	/*
     * 背包问题，动态规划：将复杂问题拆分成递归小问题
     */
    public static int correctMethod(int[] arr) {
        //计算数组和，平分出背包重量
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int packageWeight = sum/2;
        //定义动态规划的背包最大价值数组
        int[] f = new int[packageWeight+1];
        for (int n = 0; n < arr.length; n++) {
            for (int v = packageWeight; v >= arr[n]; v--) {
                f[v] = Math.max(f[v], f[v-arr[n]] + arr[n]);
            }
            System.out.println(Arrays.toString(f));
        }
        return Math.abs(sum - 2*f[packageWeight]);
    }
	
	public static void initSpringContext() throws SQLException {
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:config/applicationContext.xml");
		User user = (User)ac.getBean("user");
		System.out.println(user.toString());
		DataSource datasource = (DataSource) ac.getBean("dataSource");
		Connection conn = null;
		try {
			datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}

class Interval {
	int start, end;
	Interval(int start, int end) {
		this.start = start;
		this.end = end;
	}
}
	 
class LFUCache {
    Map<Integer, Integer> cache = new HashMap<Integer, Integer>();
    Map<Integer, Integer> cacheCount = new HashMap<Integer, Integer>();
    Map<Integer, Long> cacheRecent = new HashMap<Integer, Long>();
    int capacity;
    /*
    * @param capacity: An integer
    */public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        if (cache.size() >= capacity) {
            int lfuKey = getLFU();
            cache.remove(lfuKey);
            cacheCount.remove(lfuKey);
            cacheRecent.remove(lfuKey);
        }
        cache.put(key, value);
        cacheCount.put(key, 0);
        cacheRecent.put(key, System.nanoTime());
    }
    
    private int getLFU() {
        int minKey = 0;
        Set keys = cacheCount.keySet();
        Iterator<Integer> it = keys.iterator();
        while (it.hasNext()) {
            int key = it.next();
            int val = cacheCount.get(key);
            if (!cacheCount.containsKey(minKey)) {
                minKey = key;
            }
            if (cacheCount.get(minKey) > val) {
                minKey = key;
            } else if (cacheCount.get(minKey) == val && cacheRecent.get(minKey) > cacheRecent.get(key)) {
                minKey = key;
            }
        }
        return minKey;
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key, boolean flag) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        int count = cacheCount.get(key);
        cacheCount.put(key, count + 1);
        cacheRecent.put(key, System.nanoTime());
        return cache.get(key);
    }
    
    public int get(int key) {
    	int value = get(key, true);
    	System.out.print(value + ",");
    	return value;
    }
}
