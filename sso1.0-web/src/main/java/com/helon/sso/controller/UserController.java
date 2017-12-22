package com.helon.sso.controller;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.helon.common.pojo.SsoResult;
import com.helon.common.pojo.UserLoginStatus;
import com.helon.common.utils.CookieUtils;
import com.helon.common.utils.UrlUtils;
import com.helon.sso.pojo.SsoRequestBean;
import com.helon.sso.pojo.TUserinfo;
import com.helon.sso.service.UserService;

/** 
 * CreateDate:2017年11月12日下午9:38:34 
 * @Description: 用户Controller  
 * @author:Helon
 * @version V1.0   
 */
@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Value("${sso.token.Name}")
	private String tokenCookieName;
	@Value("${sso.client.ssoMiddleFilter}")
	private String clientSsoMiddleFilterUrl;
	
	/**
	 * 
	 * @Description:根据用户id查询用户信息
	 * @author:Helon
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{userId}")
	@ResponseBody
	public TUserinfo getUserById(@PathVariable Long userId) throws Exception{
		TUserinfo userInfo = userService.getUserById(userId);
		return userInfo;
	}
	
	/**
	 * 
	 * @Description:用户登录提交
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param accountNo
	 * @param pwd
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response,
			String accountNo, String pwd, Model model) throws Exception{
		//跳转url
		String redirectUrl = "user/login"; 
		SsoResult<Object> ssoResult = userService.login(accountNo, pwd);
		//如果登录校验成功，写token到cookie中
		if(SsoResult.SUCCESS_STATUS == ssoResult.getStatus()){
			CookieUtils.setCookie(request, response, tokenCookieName, ssoResult.getData().toString(), true);
			//从session中获取登录请求信息
			SsoRequestBean ssoRequestBean = (SsoRequestBean)request.getSession().getAttribute("ssoReqeustInfo");
			//判断旧token是否为空，不为空则删除redis中的token信息
			if(StringUtils.isNotBlank(ssoRequestBean.getSsoToken())){
				userService.invalidToken(ssoRequestBean.getSsoToken());
			}
			//赋值新登录的token
			ssoRequestBean.setSsoToken(ssoResult.getData().toString());
			//封装跳转url信息 
			redirectUrl = toMiddleHandlerInfo(ssoRequestBean);
		}else{
			model.addAttribute("errorMsg", ssoResult.getMsg());
		}
		//TODO异步记录登录用户轨迹(比如：删除、登录、登出、修改、操作时间、用户ip、地址、浏览器User-Agent信息等)
		
		return redirectUrl;
	}
	
	/**
	 * 
	 * @Description:封装跳转url信息 
	 * @author:Helon
	 * @param ssoRequestBean
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String toMiddleHandlerInfo(SsoRequestBean ssoRequestBean) throws UnsupportedEncodingException{
		//跳转URL拼接
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("redirect:");
		//client应用域名
		stringBuilder.append(UrlUtils.getUrlProtocolAndHost(ssoRequestBean.getRedirectUrl()));
		//跳转到客户端cookie处理器
		stringBuilder.append(clientSsoMiddleFilterUrl+"?ssoResponseInfo=");
		//携带cookie和跳转url信息的json串(经过base64转码，为安全性考虑最好改用加密方式)
		stringBuilder.append(Base64Utils.encodeToString(JSONObject.toJSONString(ssoRequestBean).getBytes("UTF-8")));
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @Description:跳转到登录页面或跳转到中间处理器来绑定cookie信息
	 * @author:Helon
	 * @param redirectUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request, String redirectUrl) throws Exception{
		//从cookie中获取token，判断是否已登录过
		String ssoToken = CookieUtils.getCookieValue(request, tokenCookieName);
		SsoRequestBean ssoRequestBean = new SsoRequestBean();
		ssoRequestBean.setSsoToken(ssoToken);
		ssoRequestBean.setRedirectUrl(redirectUrl);
		//如果token不为空且redis中还存在，则说明当前用户是登录状态的，只是当前请求的站点还没有绑定cookie信息，直接跳转到middleHandler绑定cookie
		if(StringUtils.isNotBlank(ssoToken) 
				&& userService.getUserByToken(ssoToken).getStatus()==1){
			return toMiddleHandlerInfo(ssoRequestBean);
		}
		//存入到当前session中，跳转到登录页面，登录提交后使用
		request.getSession().setAttribute("ssoReqeustInfo", ssoRequestBean);
		return "user/login";
	}
	
	/**
	 * 
	 * @Description:sso登出
	 * @author:Helon
	 * @param request
	 * @param redirectUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, String redirectUrl) throws Exception{
		//从cookie中获取token
		String ssoToken = CookieUtils.getCookieValue(request, tokenCookieName);
		//使token失效
		userService.invalidToken(ssoToken);
		//跳转页面
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("redirect:");
		stringBuilder.append(redirectUrl);
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @Description:校验当前登录状态-支持jsonp
	 * @author:Helon
	 * @param callback
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getUserByToken", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(String callback, HttpServletRequest request) throws Exception{
		//从请求头中获取token
		String token = CookieUtils.getCookieValue(request, tokenCookieName);
		//根据token获取当前用户状态
		UserLoginStatus userLoginStatus = userService.getUserByToken(token);
		//是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			String retResult = callback + "(" + JSONObject.toJSONString(SsoResult.Success(userLoginStatus)) + ");";
			return retResult;
		}
		return JSONObject.toJSONString(SsoResult.Success(userLoginStatus));
	}
}
