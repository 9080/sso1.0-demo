package com.helon.sso.client.filters;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.helon.common.config.UniformConfigBean;
import com.helon.common.utils.CookieUtils;
import com.helon.sso.service.UserService;

/** 
 * CreateDate:2017年12月10日下午8:05:02 
 * @Description: sso登录退出过滤器  
 * @author:Helon
 * @version V1.0   
 */
public class SsoLogoutFilter extends AbstractSsoFilter {
	/**日志*/
	private static final Logger logger = LoggerFactory.getLogger(SsoLogoutFilter.class);
	@Resource
	private UniformConfigBean clientConfigBean;
	/**用户信息服务*/
	@Autowired
	private UserService userService;

	@Override
	protected void doSsoFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {

		logger.debug("[sso登出过滤器]-登出开始");
		//servletRequest请求类型校验
	    if(!(servletRequest instanceof HttpServletRequest)){
        	throw new ServletException("只支持HttpServletRequest类型，实际类型为" + servletRequest.getClass().getName());
        }
	    //servletResponse响应类型校验
        if(!(servletResponse instanceof HttpServletResponse)){
        	throw new ServletException("只支持HttpServletResponse类型，实际类型为" + servletResponse.getClass().getName());
        }
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        //获取跳转url
        String redirectUrl = httpRequest.getParameter("redirectUrl");
        //获取当前请求域名
        String domainName = getDomainName(httpRequest);
		//获取当前cookie中的token
		String ssoToken = CookieUtils.getCookieValue(httpRequest, clientConfigBean.getSsoClientConfig().getSsoTokenName());
		try {
			//清除token
			userService.invalidToken(ssoToken);
		} catch (Exception e) {
			logger.error("[sso登出过滤器]-登出异常", e);
			return;
		}
		//首先判断是否ajax请求
    	boolean isAjaxRequest = isAjaxRequest(httpRequest);
    	if(isAjaxRequest){
    		httpRequest.setCharacterEncoding(COMMON_ENCODING);
    		httpResponse.setContentType("application/json;charset=UTF-8");
    		JSONObject ssoJson = new JSONObject();
    		ssoJson.put("status", "0");//1表示成功
    		ssoJson.put("code", "CS0000");//表示成功
    		ssoJson.put("msg", "退出成功！");
    		ssoJson.put("data", URLEncoder.encode(StringUtils.isNotBlank(redirectUrl)?redirectUrl:domainName, COMMON_ENCODING));
    		httpResponse.getOutputStream().write(ssoJson.toJSONString().getBytes(COMMON_ENCODING));
    		return;
    	}else{
    		httpResponse.sendRedirect(StringUtils.isNotBlank(redirectUrl)?redirectUrl:domainName);
    		return;
    	}
	}
}
