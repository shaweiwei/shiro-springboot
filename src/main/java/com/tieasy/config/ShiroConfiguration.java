package com.tieasy.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.Filter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import com.tieasy.credentials.RetryLimitHashedCredentialsMatcher;
import com.tieasy.shiro.filter.KickoutSessionControlFilter;
import com.tieasy.shiro.listener.MySessionListener;
import com.tieasy.shiro.realm.MyRealm;
import com.tieasy.shiro.session.dao.CustomShiroCacheSessionDAO;

/**
 * shiro的配置类
 * @author ko
 *
 */
@Configuration
@EnableCaching
public class ShiroConfiguration {
	
	public final static String SALT_ADMIN = "shiro-test";
	
	/**
	 * Shiro Filter 拦截器相关配置
	 * @param manager
	 * @return
	 */
	@Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager,KickoutSessionControlFilter kickoutSessionControlFilter) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login*");// 登陆url
//        bean.setUnauthorizedUrl("/");// 访问没有访问权限时跳转的页面
//        bean.setSuccessUrl("/home");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/jsp/login.jsp*", "anon"); //表示可以匿名访问
//        filterChainDefinitionMap.put("/loginUser", "anon"); 
//        filterChainDefinitionMap.put("/logout*","anon");
//        filterChainDefinitionMap.put("/jsp/error.jsp*","anon");
//        filterChainDefinitionMap.put("/jsp/index.jsp*","authc");
//        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
//        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "kickout");// 走kickout过滤器
//        filterChainDefinitionMap.put("/*.*", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
        filterMap.put("kickout", kickoutSessionControlFilter);
        bean.setFilters(filterMap);
        
        return bean;
    }
	
//	@Bean
//	public LogoutFilter logout(){
//		LogoutFilter logout = new LogoutFilter();
//		logout.setRedirectUrl("/toLogin");
//		return logout;
//	}
	
	/**
	 * 并发登陆控制过滤器
	 * @return
	 */
	@Bean(name="kickoutSessionControlFilter")
	public KickoutSessionControlFilter kickoutSessionControlFilter(){
		KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
		kickoutSessionControlFilter.setKickoutAfter(false);
		kickoutSessionControlFilter.setMaxSession(1);
		kickoutSessionControlFilter.setKickoutUrl("/login_kickout");
		return kickoutSessionControlFilter;
	}
	
	/**
	 * 配置自定义的错误页面
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	   return (container -> {
		   ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400.html");
	       ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
	       ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
	       ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

	       container.addErrorPages(error400Page, error401Page, error404Page, error500Page);
	   });
	}
	
    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm,DefaultWebSessionManager sessionManager
    		,CacheManager ehCacheManager) {
        System.out.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        manager.setSessionManager(sessionManager);// 给shiro加上sessionManager的功能
        manager.setCacheManager(ehCacheManager);// 给shiro加上ehCacheManager的功能
        return manager;
    }
    
    //配置自定义的权限登录器
    @Bean(name="myRealm")
    public MyRealm myRealm(@Qualifier("credentialsMatcher") RetryLimitHashedCredentialsMatcher matcher) {
    	MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }
    //配置自定义的密码比较器
    @Bean(name="credentialsMatcher")
    public RetryLimitHashedCredentialsMatcher credentialsMatcher() {
    	RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
//    	retryLimitHashedCredentialsMatcher.setHashIterations(1);// 默认就是1，循环加密几次
    	retryLimitHashedCredentialsMatcher.setHashAlgorithmName("MD5");// 加密算法  必填啊，不然会报错啊
//    	retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);// 默认true, false means Base64-encoded
        return retryLimitHashedCredentialsMatcher;
    }
    
    // 配置shiro的WebSessionManager
    @Bean(name="sessionManager")
    public DefaultWebSessionManager sessionManager(EnterpriseCacheSessionDAO sessionDAO,CacheManager ehCacheManager,
    		SimpleCookie sessionIdCookie,ExecutorServiceSessionValidationScheduler sessionValidationScheduler){
    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    	sessionManager.setGlobalSessionTimeout(1800000);// 全局session过期时间
    	sessionManager.setDeleteInvalidSessions(true);
    	sessionManager.setSessionDAO(sessionDAO);
    	sessionManager.setSessionIdCookieEnabled(true);
    	sessionManager.setSessionIdCookie(sessionIdCookie);
    	sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
		sessionManager.setCacheManager(ehCacheManager);
		
		// 添加session 创建、删除的监听器
		ArrayList<SessionListener> mySessionListeners = new ArrayList<>();
		mySessionListeners.add(new MySessionListener());
		sessionManager.setSessionListeners(mySessionListeners);

		return sessionManager;
    }
    
    /**
     * 据shared与否的设置,Spring分别通过CacheManager.create()
     * 或new CacheManager()方式来创建一个ehcache基地.
     * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
     */
    @Bean(name="ehCacheManagerFactoryBean")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
      EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
      cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
      cacheManagerFactoryBean.setShared(true);
      return cacheManagerFactoryBean;
    }
    
    /**
     * @param bean
     * @return  org.apache.shiro.cache.ehcache.EhCacheManager
     */
    @Bean(name="ehCacheManager")
    public CacheManager ehCacheManager(){
    	EhCacheManager ehCacheManager = new EhCacheManager();
    	ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml"); // 下面一行和这行的两个写法都可以
    	// 要注意区分net.sf.ehcache.CacheManager和org.apache.shiro.cache.CacheManager的区别
//    	ehCacheManager.setCacheManager(net.sf.ehcache.CacheManager.create(net.sf.ehcache.CacheManager.class.getClassLoader().getResource("ehcache.xml")));
    	return ehCacheManager;
    }
    
    @Bean(name="passwordRetryCache")
    public Cache<String, AtomicInteger> passwordRetryCache(CacheManager ehCacheManager){
    	return ehCacheManager.getCache("passwordRetryCache");
    }
    
    @Bean(name="shiroActiveSessionCache")
    public Cache<String, Deque<Serializable>> shiroActiveSessionCache(CacheManager ehCacheManager){
    	return ehCacheManager.getCache("shiroActiveSessionCache");
    }
    
    @Bean(name="codeCache")
    public Cache<String, String> codeCache(CacheManager ehCacheManager){
    	return ehCacheManager.getCache("codeCache");
    }
    
    
    /**
     * 定义 session 的cookie
     * @return
     */
    @Bean(name="sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
    	SimpleCookie sessionIdCookie = new SimpleCookie("sid");
    	sessionIdCookie.setHttpOnly(true);
    	sessionIdCookie.setMaxAge(-1);
    	return sessionIdCookie;
    }
    
    /**
     * 会话验证调度器  DefaultWebSessionManager sessionManager
     * @return
     */
    @Bean
    public ExecutorServiceSessionValidationScheduler sessionValidationScheduler(){
    	ExecutorServiceSessionValidationScheduler sessionScheduler = new ExecutorServiceSessionValidationScheduler();
    	sessionScheduler.setInterval(1800000);
//    	sessionScheduler.setSessionManager(sessionManager); // 循环调用  启动不了
    	return sessionScheduler;
    }
    
    /**
     * 会话DAO，sessionManager里面的session需要保存在会话Dao里，没有会话Dao，session是瞬时的，没法从  
     * sessionManager里面拿到session
     * @param sessionIdGenerator
     * @return
     */
    @Bean(name="sessionDAO")
    public EnterpriseCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator){
    	EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
    	sessionDAO.setActiveSessionsCacheName("shiroActiveSessionCache");
    	sessionDAO.setSessionIdGenerator(sessionIdGenerator);
		return sessionDAO;
    }
    
    /**
     * 用自定义的sessionDao替换上面注释的，这样方便将来分布式共享session
     * @param sessionIdGenerator
     * @return
     */
//    @Bean(name="sessionDAO")
//    public CustomShiroCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator){
//    	CustomShiroCacheSessionDAO sessionDAO = new CustomShiroCacheSessionDAO();
//    	sessionDAO.setActiveSessionsCacheName("shiroActiveSessionCache");
//    	sessionDAO.setSessionIdGenerator(sessionIdGenerator);
//		return sessionDAO;
//    }
    
    /**
     * session id 生成策略
     * @return
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
    	return new JavaUuidSessionIdGenerator();
    }
    
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}
