package com.helon.sso.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.helon.common.pojo.SsoResult;
import com.helon.common.pojo.UserLoginStatus;
import com.helon.sso.mapper.TUserinfoMapper;
import com.helon.sso.pojo.TUserinfo;
import com.helon.sso.pojo.TUserinfoExample;
import com.helon.sso.redis.service.JedisClient;
import com.helon.sso.service.UserService;
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

@Service("userService")
public class UserServiceImpl implements UserService {
	/**日志*/
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private TUserinfoMapper userinfoMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${USER_KEY_PREFIX}")
	private String USER_KEY_PREFIX;

	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	/**
	 * 
	 * TODO 通过用户id查询用户信息 
	 * @see com.helon.sso.service.UserService#getUserById(long)
	 */
	public TUserinfo getUserById(long userId) {
		TUserinfo userinfo = userinfoMapper.selectByPrimaryKey(Long
				.valueOf(userId));
		return userinfo;
	}
	
	/**
	 * 
	 * 用户登录校验
	 * @see com.helon.sso.service.UserService#checkLogin(java.lang.String, java.lang.String)
	 */
	public TUserinfo checkLogin(String accountNo, String pwd) {
		TUserinfoExample userinfoExample = new TUserinfoExample();
		TUserinfoExample.Criteria criteria = userinfoExample.createCriteria();
		criteria.andAccountNoEqualTo(accountNo);
		
		criteria.andPasswordEqualTo(pwd);

		List<TUserinfo> list = userinfoMapper.selectByExample(userinfoExample);

		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	/**
	 * 用户登录，并存储Token信息
	 *  
	 * @see com.helon.sso.service.UserService#login(java.lang.String, java.lang.String)
	 */
	public SsoResult<Object> login(String accountNo, String pwd)
			throws Exception {
		TUserinfo userinfo = checkLogin(accountNo, pwd);

		if (userinfo == null) {
			return SsoResult.build(Integer.valueOf(400), "用户名或密码不正确");
		}

		String token = UUID.randomUUID().toString();

		userinfo.setPassword(null);

		jedisClient.set(USER_KEY_PREFIX + token,
				JSONObject.toJSONString(userinfo), SESSION_EXPIRE);
		return SsoResult.Success(token);
	}
	
	/**
	 * 
	 * 根据token查询用户信息
	 * @see com.helon.sso.service.UserService#getUserByToken(java.lang.String)
	 */
	public UserLoginStatus getUserByToken(String token) throws Exception {
		UserLoginStatus userLoginStatus = new UserLoginStatus();
		if (StringUtils.isNotBlank(token)) {
			String userJson = jedisClient.get("TL:" + USER_KEY_PREFIX
					+ token);
			if (StringUtils.isNotBlank(userJson)) {
				TUserinfo userinfo = (TUserinfo) JSONObject.parseObject(
						userJson, TUserinfo.class);
				userLoginStatus.setStatus(Integer.valueOf(1));
				userLoginStatus.setUserName(userinfo.getUserName());

				jedisClient.expire(USER_KEY_PREFIX + token,
						SESSION_EXPIRE.intValue());
			} else {
				logger.info("[redis中取当前用户信息]-session超时");

				userLoginStatus.setStatus(Integer.valueOf(2));
			}
		} else {
			logger.info("[redis中取当前用户信息]-token为空");

			userLoginStatus.setStatus(Integer.valueOf(2));
		}
		return userLoginStatus;
	}
	
	/**
	 * 
	 * 通过token获取用户ID 
	 * @see com.helon.sso.service.UserService#getUserIdByToken(java.lang.String)
	 */
	public Long getUserIdByToken(String token) throws Exception {
		if (StringUtils.isNotBlank(token)) {
			String userJson = jedisClient.get("TL:" + USER_KEY_PREFIX
					+ token);
			if (StringUtils.isNotBlank(userJson)) {
				TUserinfo userinfo = (TUserinfo) JSONObject.parseObject(
						userJson, TUserinfo.class);

				jedisClient.expire(USER_KEY_PREFIX + token,
						SESSION_EXPIRE.intValue());
				return userinfo.getId();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 置token失效 
	 * @see com.helon.sso.service.UserService#invalidToken(java.lang.String)
	 */
	public Long invalidToken(String token) throws Exception {
		return jedisClient.expire("TL:" + USER_KEY_PREFIX + token, -1);
	}
}