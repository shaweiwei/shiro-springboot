package com.tieasy.service.impl;

import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.tieasy.service.OAuthService;
import com.tieasy.service.Oauth2ClientService;


/**
 * 
 * @author ko
 *
 */
@Service
public class OAuthServiceImpl implements OAuthService {

	@Autowired
    private Cache<String, String> codeCache;

    @Autowired
    private Oauth2ClientService oauth2ClientService;

    @Override
    public void addAuthCode(String authCode, String username) {
    	codeCache.put(authCode, username);
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
    	codeCache.put(accessToken, username);
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return codeCache.get(authCode);
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return codeCache.get(accessToken);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return codeCache.get(authCode) != null;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return codeCache.get(accessToken) != null;
    }

    @Override
    public boolean checkClientId(String clientId) {
        return oauth2ClientService.findByClientId(clientId) != null;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return oauth2ClientService.findByClientSecret(clientSecret) != null;
    }

    @Override
    public long getExpireIn() {
        return 3600L;
    }
}
