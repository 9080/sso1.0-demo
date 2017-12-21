package com.helon.application2.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.helon.common.exception.ParameterException;

/** 
 * CreateDate:2017年12月3日下午2:57:08 
 * @Description: 统一异常处理器  
 * @author:Helon
 * @version V1.0   
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	/** 
	 * 统一异常处理器 
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception) 
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.info("[统一异常处理]-进入异常处理器...");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		if(ex instanceof ParameterException){
			return new ModelAndView("error-parameter", model);
		}
		return new ModelAndView("exception/error", model);
	}

}
