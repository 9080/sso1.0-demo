package com.helon.sso.client.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * CreateDate:2017年12月10日下午8:32:32 
 * @Description: 过滤器工具类
 * @author:Helon
 * @version V1.0   
 */
public class AbstractSsoFilter implements Filter{
	/**日志*/
	private static final Logger logger = LoggerFactory.getLogger(AbstractSsoFilter.class);
	/**统一编码*/
	protected static final String COMMON_ENCODING = "UTF-8";
	/**
	 * 
	 * @Description:获取当前请求URL 
	 * @author:Helon
	 * @param req
	 * @param isAjax
	 * @return
	 */
	protected String getRealReturnUrl(HttpServletRequest request, boolean isAjax) {
		StringBuffer stringBuffer = new StringBuffer();
		if(!isAjax) {
			stringBuffer.append(request.getRequestURL().toString());
		}else{//ajax请求获取当前请求地址栏中的地址
			stringBuffer.append(request.getHeader("referer"));
		}
		//获取请求后携带的参数
		String queryString = request.getQueryString();
		//如果请求地址，且携带的参数都不为空，拼接参数
		if(StringUtils.isNotBlank(stringBuffer.toString()) 
				&& StringUtils.isNotBlank(queryString)) {
			stringBuffer.append("?");
			stringBuffer.append(queryString);
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 
	 * @Description:判断当前请求是否为ajax请求
	 * @author:Helon
	 * @param request
	 * @return
	 */
	protected boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		return requestType != null && requestType.equals("XMLHttpRequest");
	}
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		doSsoFilter(servletRequest, servletResponse, chain);
	}

	@Override
	public void destroy() {
		
	}
	
	/**
	 * 
	 * @Description:sso过滤器方法，由子类实现
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doSsoFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{
		
	}
	
	/**
	 * 
	 * @Description:通过request请求获取域名 
	 * 例如：可以获取到http://www.test.com/
	 * @author:Helon
	 * @param request
	 * @return
	 */
	protected String getDomainName(HttpServletRequest request){
		logger.debug("[获取request中URL信息]-ServletPath:{}", request.getServletPath());
		logger.debug("[获取request中URL信息]-ContextPath:{}", request.getContextPath());
		logger.debug("[获取request中URL信息]-RequestURI:{}", request.getRequestURI());
		logger.debug("[获取request中URL信息]-PathInfo:{}", request.getPathInfo());
		logger.debug("[获取request中URL信息]-RequestURL:{}", request.getRequestURL());
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		if(StringUtils.length(pathInfo) > 0){
			servletPath = servletPath + pathInfo;
		}
		int endIndex = request.getRequestURL().length() - servletPath.length() + 1;
		return request.getRequestURL().substring(0, endIndex);
	}
	
}
