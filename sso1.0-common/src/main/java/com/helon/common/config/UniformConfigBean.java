package com.helon.common.config;


import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/** 
 * CreateDate:2017年12月7日下午6:39:05 
 * @Description: 读取配置文件的bean  
 * 1. ApplicationContextAware作用：实现该接口，声明静态变量applicationContext，可使用该
 * 							变量获取到spring容器中的其他bean对象，实现setApplicationContext()方法
 * 2. InitializingBean作用：实现该接口，实现afterPropertiesSet()方法，spring在初始化bean的时候会自动调用该方法，会在init-method方法前调用
 * 3. EmbeddedValueResolverAware：实现该接口，必须实现setEmbeddedValueResolver方法，把方法的resolver参数，赋值给当前工具类的私有属性，同时
 * 暴露出对外的提取函数getPropertiesValue,通过名称获取当前对应的值。注：一定是转换成${name}的方式才可以
 * @author:Helon
 * @version V1.0   
 */
@Component
public class UniformConfigBean implements ApplicationContextAware,InitializingBean,EmbeddedValueResolverAware {
	/***********************配置模块config start******************************/
	/**
	 * sso客户端配置项
	 */
	@Resource
	private SsoClientConfig ssoClientConfig;
	
	
	
	
	
	
	
	public SsoClientConfig getSsoClientConfig() {
		return ssoClientConfig;
	}
	/***********************配置模块config end******************************/
	
	
	
	
	//声明静态ApplicationContext变量
	private static ApplicationContext applicationContext;
	//声明StringValueResolver对象
	private StringValueResolver resolver;
	
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.resolver = resolver;
	}
	//参数为配置文件中的key
	public String getPropertiesValue(String name){
		return resolver.resolveStringValue("${"+name+"}");
	}
	/**
	 * 
	 * TODO bean初始化时首先会执行的方法
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	public StringValueResolver getResolver() {
		return resolver;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		UniformConfigBean.applicationContext =  applicationContext;
	}
	public static <T> T getBean(String beanName, Class<T> clazz){
		return applicationContext.getBean(beanName, clazz);
	}
	/**
	 * 
	 * @Description:如果在非spring管理的类中获取ClientConfigBean对象，可以使用该方法
	 * @author:Helon
	 * @return
	 */
	public static UniformConfigBean getUniformConfigBean(){
		return getBean("clientConfigBean", UniformConfigBean.class);
	}
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
