package com.helon.sso.redis.service.impl;

import com.helon.sso.redis.service.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientPool implements JedisClient {

	@Autowired
	private JedisPool jedisPool;

	public String set(String key, String value) {
		Jedis jedis = this.jedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	public String get(String key) {
		Jedis jedis = this.jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	public Boolean exists(String key) {
		Jedis jedis = this.jedisPool.getResource();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

	public Long expire(String key, int seconds) {
		Jedis jedis = this.jedisPool.getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	public Long ttl(String key) {
		Jedis jedis = this.jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	public Long incr(String key) {
		Jedis jedis = this.jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	public Long hset(String key, String field, String value) {
		Jedis jedis = this.jedisPool.getResource();
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	public String hget(String key, String field) {
		Jedis jedis = this.jedisPool.getResource();
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}

	public Long hdel(String key, String[] fields) {
		Jedis jedis = this.jedisPool.getResource();
		Long result = jedis.hdel(key, fields);
		jedis.close();
		return result;
	}

	public String set(String key, String value, Integer expireSeconds) {
		Jedis jedis = this.jedisPool.getResource();
		String result = jedis.set("TL:" + key, value);

		jedis.expire("TL:" + key, expireSeconds.intValue());
		jedis.close();
		return result;
	}
}