package com.helon.sso.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.helon.sso.redis.service.JedisClient;

/** 
 * CreateDate:2017年12月1日下午10:36:32 
 * @Description: jedisClient测试类
 * @author:Helon
 * @version V1.0   
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class TestJedisClient {
	@Autowired
	JedisClient jedisClient;
	
	@Test
	public void testJedisClient() throws Exception{
		jedisClient.set("springtest", "100");
		String getValue = jedisClient.get("springtest");
		System.out.println(getValue);
	}
}
