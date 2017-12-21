package com.helon.common.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * CreateDate:2017年12月12日下午4:23:48 
 * @Description: URL相关操作的工具类  
 * @author:Helon
 * @version V1.0   
 */
public class UrlUtils {
	/**日志*/
	private static final Logger logger = LoggerFactory.getLogger(UrlUtils.class);
	
	/**
	 * 
	 * @Description:获取全URL中的域名(含协议)
	 * 例如：https://www.baidu.com?a=1&b=1
	 * 	   返回结果为:https://www.baidu.com
	 * @author:Helon
	 * @param wholeUrl
	 * @return
	 */
	public static String getUrlProtocolAndHost(String wholeUrl){
		logger.debug("[获取全URL字符串中的域名]-URL路径:{}", wholeUrl);
		StringBuilder stringBuilder = new StringBuilder();
		if(StringUtils.isNotBlank(wholeUrl)){
			try {
				URL url = new URL(wholeUrl);
				stringBuilder.append(url.getProtocol());//协议，例如：http/https
				stringBuilder.append("://");
				stringBuilder.append(url.getHost());
			} catch (MalformedURLException e) {
				logger.error("[获URL中域名方法]-出现异常", e);
			}
		}
		logger.debug("[获取全URL字符串中的域名]-解析到的域名:{}", stringBuilder.toString());
		return stringBuilder.toString();
	}
	public static void main(String[] args) {
		System.out.println(getUrlProtocolAndHost("http://wwww.baidu.com"));
	}
}
