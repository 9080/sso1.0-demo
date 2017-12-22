package com.helon.application1.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.helon.sso.client.filters.ValidateLoginFilter;
import com.helon.sso.pojo.TUserinfo;
import com.helon.sso.service.UserService;

/** 
 * CreateDate:2017年12月15日下午5:47:55 
 * @Description: 客户端过滤器自定义写入session方法  
 * @author:Helon
 * @version V1.0   
 */
public class Client2ValidateFilter extends ValidateLoginFilter {
	@Autowired
	private UserService userServer;
	
	@Override
	protected boolean addUserToSession(HttpServletRequest reqeust, Long userId)
			throws Exception {
		//根据用户id获取用户信息
		TUserinfo userInfo = userServer.getUserById(userId);
		reqeust.getSession().setAttribute("USER_SESSION", userInfo);
		return true;
	}
}
