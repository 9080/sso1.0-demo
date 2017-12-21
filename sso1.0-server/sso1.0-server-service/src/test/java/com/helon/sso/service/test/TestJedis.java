package com.helon.sso.service.test;

import org.junit.Test;



import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/** 
 * CreateDate:2017年11月26日下午10:09:55 
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author:Helon
 * @version V1.0   
 */
public class TestJedis {
	
	@Test
	public void testJedis() throws Exception{
		//创建一个jedis对象，需要制定服务的ip和端口号
		Jedis jedis = new Jedis("47.93.195.131", 6379);
		//直接操作数据库
		jedis.set("test_key", "1234");
		String result = jedis.get("test_key");
		System.out.println(result);
		//关闭Jedis
		jedis.close();
	}
	
	@Test
	public void testJedisPool() throws Exception{
		//第一步：创建一个JedisPool对象，需要指定服务端的ip及端口
		JedisPool jedisPool = new JedisPool("47.93.195.131", 6379);
		//第二步：从JedisPool中获得Jedis对象
		Jedis jedis = jedisPool.getResource();
		//第三步：使用Jedis操作redis服务器
		jedis.set("jedisPool", "test");
		System.out.println(jedis.get("jedisPool"));
		//第四步：操作完毕后关闭Jedis对象，连接池回收资源
		jedis.close();
		//第五步：关闭JedisPool对象
		jedisPool.close();
	}
	
	@Test
	public void testGetKey() throws Exception{
		//第一步：创建一个JedisPool对象，需要指定服务端的ip及端口
		JedisPool jedisPool = new JedisPool("47.93.195.131", 6379);
		//第二步：从JedisPool中获得Jedis对象
		Jedis jedis = jedisPool.getResource();
		//第三步：使用Jedis操作redis服务器
		System.out.println(jedis.get("test7"));
		System.out.println(JSONObject.parseObject(jedis.get("test7"), String.class));
		//第四步：操作完毕后关闭Jedis对象，连接池回收资源
		jedis.close();
		//第五步：关闭JedisPool对象
		jedisPool.close();
	}
	
}
