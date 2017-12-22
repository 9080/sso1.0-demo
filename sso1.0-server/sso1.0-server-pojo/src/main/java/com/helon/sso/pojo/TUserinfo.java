package com.helon.sso.pojo;

import java.io.Serializable;

/**
 * 
 * CreateDate:2017年12月22日下午1:50:07 
 * @Description: 用户信息bean  
 * @author:Helon
 * @version V1.0
 */
public class TUserinfo implements Serializable {
	private Long id;
	private String accountNo;
	private String password;
	private String userName;
	private Boolean sex;
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = (accountNo == null ? null : accountNo.trim());
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = (password == null ? null : password.trim());
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = (userName == null ? null : userName.trim());
	}

	public Boolean getSex() {
		return this.sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(this.id);
		sb.append(", accountNo=").append(this.accountNo);
		sb.append(", password=").append(this.password);
		sb.append(", userName=").append(this.userName);
		sb.append(", sex=").append(this.sex);
		sb.append(", serialVersionUID=").append(1L);
		sb.append("]");
		return sb.toString();
	}
}