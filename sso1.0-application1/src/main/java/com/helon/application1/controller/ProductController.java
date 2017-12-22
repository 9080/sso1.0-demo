package com.helon.application1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helon.common.pojo.SsoResult;
import com.helon.sso.pojo.TUserinfo;

/** 
 * CreateDate:2017年12月10日下午1:44:55 
 * @Description: client应用Controller  
 * @author:Helon
 * @version V1.0   
 */
@Controller
public class ProductController {
	
	@RequestMapping("/product/queryProduct")
	@ResponseBody
	public SsoResult<Object> queryProduct(HttpSession session){
		TUserinfo userInfo = (TUserinfo)session.getAttribute("USER_SESSION");
		return SsoResult.Success("application1:"+userInfo.getAccountNo());
	}
}
