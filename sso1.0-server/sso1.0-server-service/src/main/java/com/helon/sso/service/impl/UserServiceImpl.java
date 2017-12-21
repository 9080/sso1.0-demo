package com.helon.sso.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.helon.common.pojo.SsoResult;
import com.helon.common.pojo.UserLoginStatus;
import com.helon.sso.mapper.TUserinfoMapper;
import com.helon.sso.pojo.TUserinfo;
import com.helon.sso.pojo.TUserinfoExample;
import com.helon.sso.pojo.TUserinfoExample.Criteria;
import com.helon.sso.redis.service.JedisClient;
import com.helon.sso.service.UserService;

/** 
 * CreateDate:2017年11月12日下午8:21:20 
 * @Description: 用户Service实现类  
 * @author:Helon
 * @version V1.0   
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	/**日志*/
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Resource
	private TUserinfoMapper userinfoMapper;
	@Autowired
	private JedisClient jedisClient;
	/**redis存储token的前缀*/
	@Value("${USER_KEY_PREFIX}")
	private String USER_KEY_PREFIX;
	/**session超时时间*/
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	/** 
	 * TODO 根据用户ID获取用户信息
	 * @see com.helon.sso.service.UserService#getUserById(long) 
	 */
	@Override
	public TUserinfo getUserById(long userId) {
		TUserinfo userinfo = userinfoMapper.selectByPrimaryKey(userId);
		return userinfo;
	}
	
	/**
	 * 
	 * TODO 根据用户账号和密码校验用户信息 
	 * @see com.helon.sso.service.UserService#checkLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public TUserinfo checkLogin(String accountNo, String pwd) {
		//声明查询条件
		TUserinfoExample userinfoExample = new TUserinfoExample();
		Criteria criteria = userinfoExample.createCriteria();
		criteria.andAccountNoEqualTo(accountNo);
		//TODO pwd需要md5加密
		criteria.andPasswordEqualTo(pwd);
		//根据账号和密码查询用户信息
		List<TUserinfo> list = userinfoMapper.selectByExample(userinfoExample);
		//如果查询出来结果为空返回NUll，否则将用户ID返回
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@Override
	public SsoResult<Object> login(String accountNo, String pwd) throws Exception {
		//校验用户账号密码
		TUserinfo userinfo = checkLogin(accountNo, pwd);
		//判断用户是否为空
		if(userinfo == null){
			return SsoResult.build(400, "用户名或密码不正确");
		}
		//TODO 使用UUID生成token，可以再优化防止重复
		String token = UUID.randomUUID().toString();
		//基于安全性考虑，将密码置空
		userinfo.setPassword(null);
		//将用户id保存在redis中，key就是token，value是用户ID,并设置超时时间
		jedisClient.set(USER_KEY_PREFIX + token, JSONObject.toJSONString(userinfo), SESSION_EXPIRE);
		return SsoResult.Success(token);
	}
	
	@Override
	public UserLoginStatus getUserByToken(String token) throws Exception {
		UserLoginStatus userLoginStatus = new UserLoginStatus();
		if(StringUtils.isNotBlank(token)){
			//从redis中取出user信息
			String userJson = jedisClient.get("TL:"+USER_KEY_PREFIX + token);
			if(StringUtils.isNotBlank(userJson)){
				TUserinfo userinfo = JSONObject.parseObject(userJson, TUserinfo.class);
				userLoginStatus.setStatus(1);
				userLoginStatus.setUserName(userinfo.getUserName());
				//重置session失效时间
				jedisClient.expire(USER_KEY_PREFIX + token, SESSION_EXPIRE);
			}else{
				logger.info("[redis中取当前用户信息]-session超时");
				//未登录
				userLoginStatus.setStatus(2);
			}
		}else{
			logger.info("[redis中取当前用户信息]-token为空");
			//未登录
			userLoginStatus.setStatus(2);
		}
		return userLoginStatus;
	}

	@Override
	public Long getUserIdByToken(String token) throws Exception {
		if(StringUtils.isNotBlank(token)){
			//从redis中取出user信息
			String userJson = jedisClient.get("TL:"+USER_KEY_PREFIX + token);
			if(StringUtils.isNotBlank(userJson)){
				TUserinfo userinfo = JSONObject.parseObject(userJson, TUserinfo.class);
				//重置session失效时间
				jedisClient.expire(USER_KEY_PREFIX + token, SESSION_EXPIRE);
				return userinfo.getId();
			}
		}
		return null;
	}

	@Override
	public Long invalidToken(String token) throws Exception {
		return jedisClient.expire("TL:"+USER_KEY_PREFIX + token, -1);
	}

}
