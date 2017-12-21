package com.helon.sso.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.helon.sso.redis.service.JedisClient;

/** 
 * CreateDate:2017年11月29日下午3:27:06 
 * @Description: jedis客户端实现类  
 * @author:Helon
 * @version V1.0   
 */
public class JedisClientPool implements JedisClient {
	@Autowired
	private JedisPool jedisPool;
	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#set(java.lang.String, java.lang.String) 
	 */
	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#get(java.lang.String) 
	 */
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#exists(java.lang.String) 
	 */
	@Override
	public Boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#expire(java.lang.String, int) 
	 */
	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#ttl(java.lang.String) 
	 */
	@Override
	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#incr(java.lang.String) 
	 */
	@Override
	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#hset(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#hget(java.lang.String, java.lang.String) 
	 */
	@Override
	public String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see com.helon.sso.redis.service.JedisClient#hdel(java.lang.String, java.lang.String[]) 
	 */
	@Override
	public Long hdel(String key, String... fields) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(key, fields);
		jedis.close();
		return result;
	}
	
	/**
	 * 
	 * 存储redis值，并设置超时时间，key前自动加TL前缀
	 * @see com.helon.sso.redis.service.JedisClient#set(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public String set(String key, String value, Integer expireSeconds) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set("TL:"+key, value);
		//设置超时时间
		jedis.expire("TL:"+key, expireSeconds);
		jedis.close();
		return result;
	}

}
