package com.helon.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * CreateDate:2017年11月22日上午11:11:04 
 * @Description: Cookies工具类  
 * @author:Helon
 * @version V1.0
 */
public final class CookieUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

	/**
	 * 
	 * @Description:得到Cookie的值, 不编码 
	 * @author:Helon
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {
		return getCookieValue(request, cookieName, false);
	}

	/**
	 * 
	 * @Description:得到Cookie的值
	 * @author:Helon
	 * @param request
	 * @param cookieName
	 * @param isDecoder - 是否对value值进行编码
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,
			String cookieName, boolean isDecoder) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null) {
			return null;
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					if (isDecoder) {
						retValue = URLDecoder.decode(cookieList[i].getValue(),
								"UTF-8");
					} else {
						retValue = cookieList[i].getValue();
					}
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("[getCookieValue]-不支持的编码格式", e);
		}
		return retValue;
	}

	/**
	 * 
	 * @Description:得到Cookie的值，并按照指定的编码格式对value进行编码
	 * @author:Helon
	 * @param request
	 * @param cookieName
	 * @param encodeString - 编码格式
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,
			String cookieName, String encodeString) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null) {
			return null;
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					retValue = URLDecoder.decode(cookieList[i].getValue(),
							encodeString);
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("[getCookieValue]-不支持的编码格式", e);
		}
		return retValue;
	}

	/**
	 * 
	 * @Description:设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 */
	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName, String cookieValue) {
		setCookie(request, response, cookieName, cookieValue, -1);
	}

	/**
	 * 
	 * @Description:设置Cookie的值 在指定时间内生效,但不编码 
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage - cookie生效时间
	 */
	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage) {
		setCookie(request, response, cookieName, cookieValue, cookieMaxage,
				false);
	}

	/**
	 * 
	 * @Description:设置Cookie的值 不设置生效时间,但编码
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param isEncode - 是否编码
	 */
	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName,
			String cookieValue, boolean isEncode) {
		setCookie(request, response, cookieName, cookieValue, -1, isEncode);
	}

	/**
	 * 
	 * @Description:设置Cookie的值 在指定时间内生效, 编码参数
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage
	 * @param isEncode - 是否编码
	 */
	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, boolean isEncode) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage,
				isEncode);
	}

	/**
	 * 
	 * @Description:设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage
	 * @param encodeString - 编码格式
	 */
	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage,
				encodeString);
	}

	/**
	 * 
	 * @Description:删除Cookie带cookie域名
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public static void deleteCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName) {
		doSetCookie(request, response, cookieName, "", -1, false);
	}

	/**
	 * 
	 * @Description:设置Cookie的值，并使其在指定时间内生效
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage - 生效时间
	 * @param isEncode - 是否编码
	 */
	private static final void doSetCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, boolean isEncode) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else if (isEncode) {
				cookieValue = URLEncoder.encode(cookieValue, "utf-8");
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if (null != request) {// 设置域名的cookie
				String domainName = getDomainName(request);
				logger.debug("[domainName]-{}", domainName);
				if (!"localhost".equals(domainName)) {
					cookie.setDomain(domainName);
				}
			}
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("[doSetCookie]-处理异常:", e);
		}
	}

	/**
	 * 
	 * @Description:设置Cookie的值，并使其在指定时间内生效
	 * @author:Helon
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage - 生效时间
	 * @param encodeString - 编码格式
	 */
	private static final void doSetCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else {
				cookieValue = URLEncoder.encode(cookieValue, encodeString);
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if (null != request) {// 设置域名的cookie
				String domainName = getDomainName(request);
				logger.debug("[domainName]-{}", domainName);
				if (!"localhost".equals(domainName)) {
					cookie.setDomain(domainName);
				}
			}
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("[doSetCookie]-处理异常:", e);
		}
	}

	/**
	 * 
	 * @Description:获取domain名
	 * @author:Helon
	 * @param request
	 * @return
	 */
	private static final String getDomainName(HttpServletRequest request) {
		String domainName = null;

		String serverName = request.getRequestURL().toString();
		if (serverName == null || serverName.equals("")) {
			domainName = "";
		} else {
			serverName = serverName.toLowerCase();
			serverName = serverName.substring(7);
			final int end = serverName.indexOf("/");
			serverName = serverName.substring(0, end);
			final String[] domains = serverName.split("\\.");
			int len = domains.length;
			if (len > 3) {
				// www.xxx.com.cn
				domainName = "." + domains[len - 3] + "." + domains[len - 2]
						+ "." + domains[len - 1];
			} else if (len <= 3 && len > 1) {
				// xxx.com or xxx.cn
				domainName = "." + domains[len - 2] + "." + domains[len - 1];
			} else {
				domainName = serverName;
			}
		}

		if (domainName != null && domainName.indexOf(":") > 0) {
			String[] ary = domainName.split("\\:");
			domainName = ary[0];
		}
		return domainName;
	}

}
