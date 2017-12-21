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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.alibaba.fastjson.JSONObject;
import com.helon.common.config.UniformConfigBean;
import com.helon.common.utils.CookieUtils;
import com.helon.sso.service.UserService;


/** 
 * CreateDate:2017年12月7日下午5:23:53 
 * @Description: sso校验登录过滤器  
 * @author:Helon
 * @version V1.0   
 */
public class ValidateLoginFilter extends AbstractSsoFilter {
	/**日志*/
	private static final Logger logger = LoggerFactory.getLogger(ValidateLoginFilter.class);
	/**白名单分隔符*/
	private static final String IGNORE_URL_SEPARATOR = ";";//白名单分隔符

	@Resource
	private UniformConfigBean clientConfigBean;
	/**以分隔符拼接的白名单*/
	private String whiteUrl = null;
	/**路径匹配对象*/
	/**
	 * ? 匹配1个字符
	 * * 匹配0个或多个字符
	 * ** 匹配路径中的0个或多个目录
	 * {spring:[a-z]+} 将正则表达式[a-z]+匹配到的值,赋值给名为 spring 的路径变量
	 * */
	private static PathMatcher pathMatcher = new AntPathMatcher();
	@Autowired
	private UserService userService;
	
	
	
	/**
	 * 
	 * @Description:初始化白名单内容到全局实例变量overUrl中，client应用可重写该方法
	 * @author:Helon
	 * @throws ServletException
	 */
	protected void init() throws ServletException {
		//拼接白名单
		String ignoreUrl = initIgnoreUrl();
		if(StringUtils.isNotBlank(ignoreUrl)){
			setWhiteUrl(ignoreUrl);
		}
	}
	/**
     * 
     * @Description:初始化白名单，以分隔符拼接成字符串 
     * @author shl
     * @return
     */
	private String initIgnoreUrl() {
		String[] excludeUrls = clientConfigBean.getSsoClientConfig().getExcludePaths();
		if(excludeUrls == null || excludeUrls.length == 0) {
			return null;
		}
        return StringUtils.join(excludeUrls, IGNORE_URL_SEPARATOR);
	}
	
	/**
	 * 
	 * @Description:判断当前请求url是否在白名单配置中，是为true 否为false
	 * client应用可以重写该方法
	 * @author:Helon
	 * @return
	 */
	protected boolean isWhiteUrl(HttpServletRequest request){
		boolean result = false;
		if (StringUtils.isNotBlank(whiteUrl)) {
			String[] urlArr = whiteUrl.split(IGNORE_URL_SEPARATOR);
			StringBuffer reqUrl = new StringBuffer(request.getServletPath());
			for (int i = 0; i < urlArr.length; i++) {
				//如果匹配上白名单的配置项则跳出循环
				if(pathMatcher.match(urlArr[i], reqUrl.toString())){
					result = true;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void doSsoFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
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
        //过滤白名单，如果请求url在白名单中，则放行到下一个过滤器
    	if (isWhiteUrl(httpRequest)) {
			filterChain.doFilter(httpRequest, httpResponse);
			return ;
		}
    	//需要校验登录状态
    	//从cookie中获取ssotoken
    	String ssoToken = CookieUtils.getCookieValue(httpRequest, clientConfigBean.getSsoClientConfig().getSsoTokenName());
    	//如果token不为空
    	if(StringUtils.isNotBlank(ssoToken)){
    		try {
    			Long userId = userService.getUserIdByToken(ssoToken);
    			if(userId != null){
    				//Client根据客户编号查询用户信息，写入session操作
    				if(addUserToSession(httpRequest, userId)){
    					filterChain.doFilter(httpRequest, httpResponse);
        				return;
    				}
    			}
			} catch (Exception e) {
				logger.error("[校验登录状态]-出现异常", e);
			}
    	}
    	//校验当前未登录或登录已超时
    	StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(clientConfigBean.getSsoClientConfig().getSsoServerSite());
		stringBuffer.append(clientConfigBean.getSsoClientConfig().getSsoToLoginUrl());
		stringBuffer.append("?redirectUrl=");
    	//首先判断是否ajax请求
    	boolean isAjaxRequest = isAjaxRequest(httpRequest);
    	if(isAjaxRequest){//如果为ajax请求，将响应json到页面
    		httpRequest.setCharacterEncoding(COMMON_ENCODING);
    		httpResponse.setContentType("application/json;charset=UTF-8");
    		//跳转url
    		stringBuffer.append(URLEncoder.encode(getRealReturnUrl(httpRequest, true), COMMON_ENCODING));
    		JSONObject ssoJson = new JSONObject();
    		ssoJson.put("status", "1");//1表示失败
    		ssoJson.put("code", "CF0004");
    		ssoJson.put("msg", "请重新登录！");
    		ssoJson.put("data", stringBuffer.toString());
    		httpResponse.getOutputStream().write(ssoJson.toJSONString().getBytes(COMMON_ENCODING));
    		return;
    	}else{//非ajax的普通请求，直接跳转
    		//跳转url
    		stringBuffer.append(URLEncoder.encode(getRealReturnUrl(httpRequest, false), COMMON_ENCODING));
    		httpResponse.sendRedirect(stringBuffer.toString());
    		return;
    	}
	}
	
	
	/**
	 * 
	 * @Description:如果client需要，可根据客户编号查询用户信息，并设置到Client应用的本地session中
	 * client可根据需要重写该方法
	 * @author:Helon
	 * @return
	 */
	protected boolean addUserToSession(HttpServletRequest reqeust, Long userId) throws Exception{
		return true;
	}

	public void setWhiteUrl(String whiteUrl) {
		this.whiteUrl = whiteUrl;
	}
}
