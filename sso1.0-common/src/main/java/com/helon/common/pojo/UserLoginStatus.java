package com.helon.common.pojo;

import java.io.Serializable;

/** 
 * CreateDate:2017年12月3日下午5:40:36 
 * @Description: 用户当前登录状态bean  
 * @author:Helon
 * @version V1.0   
 */
public class UserLoginStatus implements Serializable {
	
	/**
	 * serialVersionUID:序列化
	 */
	private static final long serialVersionUID = -5643203763134886376L;
	//用户名
	private String userName;
	//登录状态(1.登录 2.未登录)
	private Integer status;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
