package zhangchx.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 基于HashMap+双向链表实现LRUCache
 * 使用HashMap+DelayQueue实现热点数据的判断
 * 访问命中热点数据时，可以立即返回数据。
 * 热点数据只能被缓存500ms，过期后不能再使用。
 * 缓存容量上限可配置，默认是1000。
 * @author Snow
 *
 */
public class LRUCache<K,V> {
	
	private final int MAX_CACHE_CAPACITY = Integer.MAX_VALUE;
	
	private int capacity;
	
	private Map<K,Entry> cache = new HashMap<K,Entry>();
	
	private Map<K,DelayQueue> hotRecord = new HashMap<K,DelayQueue>();
	
	Entry<K,V> head = new Entry(null, null);
	
	final long period;
	
	final int count;
	
	final long timeout;
	
	/**
	 * LRU构造函数
	 * @param capacity 缓存容量
	 * @param period 热点数据访问间隔；和count相关联，表示该缓存容器只将period时间段内的访问count次的数据缓存
	 * @param count 热点数据访问次数；和period相关联，表示该缓存容器只将period时间段内的访问count次的数据缓存
	 * @param timeout 缓存数据超时时间
	 */
	public LRUCache(int capacity, long period, int count, long timeout) {
		super();
		if (capacity < 1 || period <= 0 || count <= 0 || timeout < 0 ) {
			throw new IllegalArgumentException("参数错误");
		}
		this.capacity = capacity;
		this.period = period;
		this.count = count;
		this.timeout = timeout;
		head.before = head.after = head;
	}

	public LRUCache() {
		this(1000, 500, 3, 500);
	}
	
	public void put(K key, V value) {
		synchronized (this) {
			if (!cache.containsKey(key) && cache.size() >= capacity) {
				removeOldest();
			}
			if (cache.containsKey(key)) {
				cache.get(key).remove();
			}
			Entry entry = new Entry(key, value);
			cache.put(key, entry);
			entry.addBefore(head);
		}
	}
	
	private void removeOldest() {
		cache.remove(head.after.key).remove();
	}
	
	public V get(K key) {
		synchronized (this) {
			Entry entry = (Entry) cache.get(key);
			if (entry != null) {
				if (!entry.isExpire()) {
					entry.remove();
					entry.addBefore(head);
					entry.updateTime();
					return (V) entry.value;
				} else {
					entry.remove();
					cache.remove(key);
				}
			}
			addHot(key);
			return null;
		}
	}
	
	/**
	 * 当对key进行获取时，会记录到hotRecord中。
	 * 使用DelayQueue实现
	 * @param key
	 */
	private void addHot(K key) {
		synchronized (this) {
			DelayQueue dq = hotRecord.get(key);
			if (dq == null) {
				dq = new DelayQueue<Hot>();
			}
			dq.add(new Hot<K>(key));
			hotRecord.put(key, dq);
		}
	}
	
	/**
	 * 判断key值是否为热点数据
	 * @param key
	 * @return
	 */
	public boolean isHot(K key) {
		DelayQueue dq = hotRecord.get(key);
		while (dq.poll() != null) {/*将超时的元素全部弹出，留下未超时的元素*/}
		if (dq.size() >= count) {
			return true;
		}
		return false;
	}
	
	
	class Entry<K,V> {
		Entry before;
		Entry after;
		K key;
		V value;
		private long time; //超时时间
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
			updateTime();
		}
		
		public boolean isExpire() {
			return System.currentTimeMillis() - time > period;
		}
		
		public void updateTime() {
			this.time = System.currentTimeMillis();
		}
		
		public void remove() {
            before.after = after;
            after.before = before;
        }

		public void addBefore(Entry<String,String> existingEntry) {
			after  = existingEntry;
            before = existingEntry.before;
            before.after = this;
            after.before = this;
        }
	}
	
	class Hot<K> implements Delayed {
		
		K key;
		
		long expiretime;
		
		public Hot(K key) {
			this.key = key;
			this.expiretime = System.currentTimeMillis() + period;
		}

		@Override
		public int compareTo(Delayed o) {
			if (o == this) {
				return 0;
			}
			if (o instanceof Hot) {
				Hot o1 = (Hot)o;
				return expiretime < o1.expiretime ? -1 : 1;
			}
			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(this.expiretime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}
		
	}
	
	/**
	 * 测试
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		LRUCache<String,String> lrucache = new LRUCache<String,String>();
		System.out.println(lrucache.get("1"));
		//db查询
		lrucache.put("1", "测试1-1");
		lrucache.put("1", "测试1-2");
		lrucache.put("2", "测试2-1");
		lrucache.put("3", "测试3-1");
		long begin = System.currentTimeMillis();
		System.out.println(lrucache.get("1"));
		lrucache.put("3", "测试3-2");
		System.out.println(lrucache.get("3"));
		Thread.currentThread().sleep(600);
		System.out.println(lrucache.get("1"));
		long end = System.currentTimeMillis();
		System.out.println("时间差：" + Long.toString(end - begin));
		System.out.println(lrucache.isHot("1"));
	}
	
	/**
	 * 测试，遍历链表
	 */
	private void list() {
		Entry<K,V> temp = (Entry) head;
		while(temp.after != head) {
			temp = temp.after;
			System.out.println(temp.key + "," + temp.value);
		}
	}
}

