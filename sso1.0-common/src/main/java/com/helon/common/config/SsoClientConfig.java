package com.helon.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** 
 * CreateDate:2017年12月8日下午6:03:18 
 * @Description: sso客户端所需配置项  
 * @author:Helon
 * @version V1.0   
 */
@Component
public class SsoClientConfig {
	/**登录校验白名单*/
	@Value("${exclude.path}")
	private String excludePath;
	/**sso存放到cookie中的cookieName，要和sso服务端保持一致*/
	@Value("${sso.token.Name}")
	private String ssoTokenName;
	/**sso站点域名*/
	@Value("${sso.server.site}")
	private String ssoServerSite;
	/**sso跳转登录页url*/
	@Value("${sso.tologin.url}")
	private String ssoToLoginUrl;
	
	public void setExcludePath(String excludePath) {
		this.excludePath = excludePath;
	}
	public String getExcludePath() {
		return excludePath;
	}
	
	public String[] getExcludePaths() {
		return excludePath.split(",");
	}
	public String getSsoTokenName() {
		return ssoTokenName;
	}
	public void setSsoTokenName(String ssoTokenName) {
		this.ssoTokenName = ssoTokenName;
	}
	public String getSsoServerSite() {
		return ssoServerSite;
	}
	public void setSsoServerSite(String ssoServerSite) {
		this.ssoServerSite = ssoServerSite;
	}
	public String getSsoToLoginUrl() {
		return ssoToLoginUrl;
	}
	public void setSsoToLoginUrl(String ssoToLoginUrl) {
		this.ssoToLoginUrl = ssoToLoginUrl;
	}

}
