package com.helon.sso.service;

import com.helon.common.pojo.SsoResult;
import com.helon.common.pojo.UserLoginStatus;
import com.helon.sso.pojo.TUserinfo;

/** 
 * CreateDate:2017年11月12日下午8:15:56 
 * @Description: 用户信息Service  
 * @author:Helon
 * @version V1.0   
 */
public interface UserService {
	/**
	 * 
	 * @Description:根据id查询用户信息
	 * @author:Helon
	 * @param userId
	 * @return
	 */
	TUserinfo getUserById(long userId) throws Exception;
	
	/**
	 * 
	 * @Description:校验登录信息
	 * @author:Helon
	 * @param accountNo
	 * @param pwd
	 * @return
	 */
	TUserinfo checkLogin(String accountNo, String pwd) throws Exception;
	
	/**
	 * 
	 * @Description:用户登录
	 * @author:Helon
	 * @return
	 * @throws Exception
	 */
	SsoResult<Object> login(String accountNo, String pwd) throws Exception;
	
	/**
	 * 
	 * @Description:根据token从redis中取出当前登录的用户信息
	 * @author:Helon
	 * @param token
	 * @return
	 * @throws Exception
	 */
	UserLoginStatus getUserByToken(String token) throws Exception;
	
	/**
	 * 
	 * @Description:通过token获取用户id
	 * @author:Helon
	 * @return
	 * @throws Exception
	 */
	Long getUserIdByToken(String token) throws Exception;
	
	/**
	 * 
	 * @Description:使token失效
	 * @author:Helon
	 * @param token
	 * @return
	 * @throws Exception
	 */
	Long invalidToken(String token) throws Exception;
}
