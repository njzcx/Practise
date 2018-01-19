package com.demo.redis;

import redis.clients.jedis.Jedis;

public class Redis {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1");//连接jedis数据库，如果包含密码可以使用其他new方法
		jedis.set("name", "zhangchx"); //存入数据库
		jedis.lpush("address", "黑龙江"); //存入list数据
		jedis.lpush("address", "嫩江");
		jedis.save(); //持久化到磁盘
		//输出
		System.out.println(jedis.get("name")+","+jedis.lrange("address", 0, 5));
		jedis.del("address");//删除对象
	}

}
