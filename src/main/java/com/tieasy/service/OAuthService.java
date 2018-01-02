package com.tieasy.service;

public interface OAuthService {

	/**
	 * 添加 auth code
	 * @param authCode
	 * @param username
	 */
	public void addAuthCode(String authCode, String username);

	/**
	 * 添加 access token
	 * @param accessToken
	 * @param username
	 */
	public void addAccessToken(String accessToken, String username);

	/**
	 * 验证auth code是否有效
	 * @param authCode
	 * @return
	 */
	boolean checkAuthCode(String authCode);

	/**
	 * 验证access token是否有效
	 * @param accessToken
	 * @return
	 */
	boolean checkAccessToken(String accessToken);

	/**
	 * 根据auth code获取用户名
	 * @param authCode
	 * @return
	 */
	String getUsernameByAuthCode(String authCode);

	/**
	 * 根据access token获取用户名
	 * @param accessToken
	 * @return
	 */
	String getUsernameByAccessToken(String accessToken);

	/**
	 * auth code / access token 过期时间
	 * @return
	 */
	long getExpireIn();

	/**
	 * 检查客户端id是否存在
	 * @param clientId
	 * @return
	 */
	public boolean checkClientId(String clientId);

	/**
	 * 检查客户端安全KEY是否存在
	 * @param clientSecret
	 * @return
	 */
	public boolean checkClientSecret(String clientSecret);
}
