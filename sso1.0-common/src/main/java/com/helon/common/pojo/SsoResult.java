package com.helon.common.pojo;

import java.io.Serializable;

/** 
 * CreateDate:2017年11月26日下午4:46:55 
 * @Description: web响应结果集  
 * @author:Helon
 * @version V1.0   
 */
public class SsoResult<T> implements Serializable {
	private static final long serialVersionUID = 3358074917727719238L;
	//默认失败状态码
	public static final int FAILURE_STATUS = 9999;
	//默认失败msg
	private static final String FAILURE_MSG = "操作失败";
	//成功状态码
	public static final int SUCCESS_STATUS = 200;
	//成功msg
	private static final String SUCCESS_MSG = "操作成功";
	//响应业务状态
	private Integer status;
	//响应信息
	private String msg;
	//业务数据
	private T data;

	
	public SsoResult(){
		
	}
	
	public SsoResult(Integer status, String msg, T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	public SsoResult(Integer status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	//默认成功
	public static <T> SsoResult<T> defaultSuccess(){
		return new SsoResult<T>(SsoResult.SUCCESS_STATUS, SsoResult.SUCCESS_MSG);
	}
	//默认失败
	public static <T> SsoResult<T> defaultFailure(){
		return new SsoResult<T>(SsoResult.FAILURE_STATUS, SsoResult.FAILURE_MSG);
	}
	//自定义状态码及msg
	public static <T> SsoResult<T> build(Integer status, String msg){
		return new SsoResult<T>(status, msg);
	}
	//自定义data数据
	public static <T> SsoResult<T> build(Integer status, String msg, T data){
		return new SsoResult<T>(status, msg, data);
	}
	
	//成功并返回数据
	public static <T> SsoResult<T> Success(T data){
		return new SsoResult<T>(SsoResult.SUCCESS_STATUS, SsoResult.SUCCESS_MSG, data);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
