package com.helon.sso.pojo;

import java.io.Serializable;

/** 
 * CreateDate:2017年12月10日下午6:17:00 
 * @Description: sso请求存放信息使用的bean  
 * @author:Helon
 * @version V1.0   
 */
public class SsoRequestBean implements Serializable{
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
	 */
	private static final long serialVersionUID = 1532739957824142938L;

	private String redirectUrl;
	
	private String ssoToken;

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getSsoToken() {
		return ssoToken;
	}

	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}
}
