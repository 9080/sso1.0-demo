package com.helon.sso.redis.service;

/** 
 * CreateDate:2017年11月28日下午9:40:30 
 * @Description: jedisClient接口  
 * @author:Helon
 * @version V1.0   
 */
public interface JedisClient {
	/**
	 * 
	 * @Description:redis存值
	 * @author:Helon
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key, String value);
	
	/**
	 * 
	 * @Description:redis存值，并设置超时时间(单位：s)
	 * @author:Helon
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	String set(String key, String value, Integer expireSeconds);
	
	/**
	 * 
	 * @Description:从redis中获取key的值 
	 * @author:Helon
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 
	 * @Description:是否存在key
	 * @author:Helon
	 * @param key
	 * @return
	 */
	Boolean exists(String key);
	
	/**
	 * 
	 * @Description:设置超时时间
	 * @author:Helon
	 * @param key
	 * @param seconds - 单位s
	 * @return
	 */
	Long expire(String key, int seconds);
	
	/**
	 * 
	 * @Description:获取超时时间，没有超时时间返回-1
	 * @author:Helon
	 * @param key
	 * @return
	 */
	Long ttl(String key);
	
	/**
	 * 
	 * @Description:增1
	 * @author:Helon
	 * @param key
	 * @return
	 */
	Long incr(String key);
	
	/**
	 * 
	 * @Description:设置hash类型的值
	 * @author:Helon
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset(String key, String field, String value);
	
	/**
	 * 
	 * @Description:获取hash中的属性值
	 * @author:Helon
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, String field);
	
	/**
	 * 
	 * @Description:删除hash列表中多个值
	 * @author:Helon
	 * @param key
	 * @param field
	 * @return
	 */
	Long hdel(String key, String... field);
}
