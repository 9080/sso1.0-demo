package com.helon.sso.pojo;

import java.io.Serializable;

/**
 * 
 * CreateDate:2017年12月22日下午1:48:47 
 * @Description: Sso Client请求bean
 * @author:Helon
 * @version V1.0
 */
public class SsoRequestBean implements Serializable {
	private static final long serialVersionUID = 1532739957824142938L;
	private String redirectUrl;
	private String ssoToken;

	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getSsoToken() {
		return this.ssoToken;
	}

	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}
}