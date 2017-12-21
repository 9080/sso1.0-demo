package com.helon.sso.pojo;

import java.io.Serializable;

public class TUserinfo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别
     */
    private Boolean sex;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", password=").append(password);
        sb.append(", userName=").append(userName);
        sb.append(", sex=").append(sex);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}