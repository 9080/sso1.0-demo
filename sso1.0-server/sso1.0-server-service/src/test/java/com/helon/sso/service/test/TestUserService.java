package com.helon.sso.service.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.helon.common.pojo.SsoResult;
import com.helon.sso.pojo.TUserinfo;
import com.helon.sso.service.UserService;


/** 
 * CreateDate:2017年11月12日下午11:09:07 
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author:Helon
 * @version V1.0   
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/application*.xml")
public class TestUserService {
	
	private static Logger logger = LoggerFactory.getLogger(TestUserService.class);
	@Autowired
	private UserService userServiceImpl;
	
	@Test
	public void test() throws Exception{
		TUserinfo userinfo = userServiceImpl.getUserById(1);
		logger.info("====={}", userinfo.toString());
	}
	
	@Test
	public void testCheckUser() throws Exception{
		SsoResult<Object> ssoResult = userServiceImpl.login("18600210664", "123");
		System.out.println("testCheckUser======"+ssoResult.getData());
	}
}
