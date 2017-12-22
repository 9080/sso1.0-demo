package com.helon.sso.redis.service;

/**
 * 
 * CreateDate:2017年12月22日上午10:24:18 
 * @Description: redis客户端接口 
 * @author:Helon
 * @version V1.0
 */
public abstract interface JedisClient {
	public abstract String set(String paramString1, String paramString2);

	public abstract String set(String paramString1, String paramString2,
			Integer paramInteger);

	public abstract String get(String paramString);

	public abstract Boolean exists(String paramString);

	public abstract Long expire(String paramString, int paramInt);

	public abstract Long ttl(String paramString);

	public abstract Long incr(String paramString);

	public abstract Long hset(String paramString1, String paramString2,
			String paramString3);

	public abstract String hget(String paramString1, String paramString2);

	public abstract Long hdel(String paramString, String[] paramArrayOfString);
}