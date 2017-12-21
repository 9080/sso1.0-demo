package com.helon.sso.client.filters;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSONObject;
import com.helon.common.config.UniformConfigBean;
import com.helon.common.utils.CookieUtils;
import com.helon.sso.pojo.SsoRequestBean;

/** 
 * CreateDate:2017年12月12日下午3:19:32 
 * @Description: sso登录成功之后，或其他域名sso校验通过后，跳转到该filter进行cookie绑定操作 
 * @author:Helon
 * @version V1.0   
 */
public class SsoMiddleFilter implements Filter {
	/**日志*/
	private static final Logger logger = LoggerFactory.getLogger(SsoMiddleFilter.class);
	@Resource
	private UniformConfigBean clientConfigBean;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
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
		//获取携带的token和跳转url的json串信息
		String ssoResponseInfo = httpRequest.getParameter("ssoResponseInfo");
		if(StringUtils.isBlank(ssoResponseInfo)){
			logger.error("[sso回调过滤器]-非法参数，未携带ssoResponseInfo信息或携带内容为空");
			httpResponse.sendRedirect(clientConfigBean.getSsoClientConfig().getSsoToLoginUrl());
			return;
		}
		ssoResponseInfo = new String(Base64Utils.decodeFromString(ssoResponseInfo), "UTF-8");
		//绑定token到当前client应用的域名下
		//解析携带cookie信息
		SsoRequestBean ssoRequestBean = JSONObject.parseObject(ssoResponseInfo, SsoRequestBean.class); 
		//写入cookie到客户端
		CookieUtils.setCookie(httpRequest, httpResponse, clientConfigBean.getSsoClientConfig().getSsoTokenName(), ssoRequestBean.getSsoToken());
		//跳转到原请求URL
		httpResponse.sendRedirect(ssoRequestBean.getRedirectUrl());
		return;
	}

	@Override
	public void destroy() {
		
	}

}
