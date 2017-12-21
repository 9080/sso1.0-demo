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

import com.helon.common.config.UniformConfigBean;

/** 
 * CreateDate:2017年12月15日上午10:17:28 
 * @Description: sso登录过滤器  
 * @author:Helon
 * @version V1.0   
 */
public class SsoLoginFilter extends AbstractSsoFilter {
	/**日志*/
	private static final Logger logger = LoggerFactory.getLogger(SsoLoginFilter.class);
	@Resource
	private UniformConfigBean clientConfigBean;
	
	@Override
	protected void doSsoFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {
		logger.debug("[sso登出过滤器]-登录开始");
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
        //获取redirectUrl校验是否为空
        String redirectUrl = httpRequest.getParameter("redirectUrl");
        StringBuffer stringBuffer = new StringBuffer();
        //sso服务器地址
        stringBuffer.append(clientConfigBean.getSsoClientConfig().getSsoServerSite());
        //跳转登录地址
        stringBuffer.append(clientConfigBean.getSsoClientConfig().getSsoToLoginUrl());
        stringBuffer.append("?redirectUrl=");
        stringBuffer.append(URLEncoder.encode(StringUtils.isNotBlank(redirectUrl)?redirectUrl:getDomainName(httpRequest), COMMON_ENCODING));
        //跳转到去登录逻辑
        httpResponse.sendRedirect(stringBuffer.toString());
	}
}
