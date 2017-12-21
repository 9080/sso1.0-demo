package com.helon.common.exception;

/** 
 * CreateDate:2017年12月3日下午3:31:35 
 * @Description: 参数校验异常  
 * @author:Helon
 * @version V1.0   
 */
public class ParameterException extends RuntimeException {
	
	/**
	 * serialVersionUID:序列化
	 */
	private static final long serialVersionUID = 679400117166159433L;

	private String code;
	
	private String msg;
	
	public ParameterException(){
		
	}
	
	public ParameterException(String code, String msg){
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public ParameterException(String msg){
		super(msg);
	}
	
	public ParameterException(String code, Throwable cause){
		super(code, cause);
	}
	
	public ParameterException(Throwable cause){
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
