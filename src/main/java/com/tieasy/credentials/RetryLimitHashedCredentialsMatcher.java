package com.tieasy.credentials;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;

import com.tieasy.entity.UserInfo;
import com.tieasy.service.UserService;
import com.tieasy.shiro.authen.MyAuthenticationInfo;

/**
 * 密码重试次数限制
 * 
 * @author ko
 *
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	
	@Autowired
	private UserService userService;
	@Autowired
	private Cache<String, AtomicInteger> passwordRetryCache;// 对应password-ehcache.xml里的缓存名称

//	public RetryLimitHashedCredentialsMatcher() {
//		CacheManager cacheManager = CacheManager
//				.create(CacheManager.class.getClassLoader().getResource("password-ehcache.xml"));// 对应该xml中<ehcache name="passwordcache">
//		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
//	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		MyAuthenticationInfo minfo = (MyAuthenticationInfo) info;
		UserInfo user = minfo.getUser();
		
		String username = (String) token.getPrincipal();
		boolean matches = super.doCredentialsMatch(token, info);// 调用shiro默认的密码校验规则

		if (matches) {
			passwordRetryCache.remove(username);// 若匹配，从缓存里清除
			
			// 不是必须
			user.setIslock(0);
			userService.setUserIsLock(user);
		}else{
//			如果密码不匹配，这里不需要想MyRealm里手动抛出异常  throw new IncorrectCredentialsException();
			//retry count + 1
			AtomicInteger retryCount = passwordRetryCache.get(username);
			if (retryCount == null) {
				retryCount = new AtomicInteger(0);
				passwordRetryCache.put(username, retryCount);
			}
			
			if (retryCount.incrementAndGet() > 5) {
				System.out.println("重试密码超过5次");
				
				// 锁定该用户，其实也可以不把锁定操作写到数据库里，因为passwordRetryCache缓存的有效期是1小时
				// 只要passwordRetryCache还有效，这一小时内该账户就会被锁定
				user.setIslock(1);
				userService.setUserIsLock(user);
				
				throw new ExcessiveAttemptsException("重试密码超过5次");// 重试超过5次抛异常
			}
			
		}
		
		return matches;
	}
	
}
