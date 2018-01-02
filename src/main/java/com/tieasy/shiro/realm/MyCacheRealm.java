package com.tieasy.shiro.realm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.CachingRealm;
import org.apache.shiro.subject.SimplePrincipalMap;

import com.tieasy.config.ShiroConfiguration;
import com.tieasy.entity.UserInfo;
import com.tieasy.service.UserService;
import com.tieasy.shiro.exception.MyException;
import com.tieasy.util.CryptographyUtil;

/**
 * 这是一个CachingRealm
 * @author ko
 *
 */
public class MyCacheRealm extends CachingRealm {
	
	@Resource
	private UserService userService;

	public boolean supports(AuthenticationToken token) {
		// TODO Auto-generated method stub
		return false;
	}

	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("----------doGetAuthenticationInfo方法被调用----------");
        //用户名
        String username = (String) token.getPrincipal();
        System.out.println("username:"+username);
        //密码
        String password = new String((char[])token.getCredentials());
        System.out.println("password:"+password);
        // 加密后的密码
        String md5Pwd = CryptographyUtil.md5(password, ShiroConfiguration.SALT_ADMIN);
        
        //从数据库获取用户信息进行身份判断,这里的验证对应登陆的时候currentUser.login捕获的各种异常
        UserInfo user = userService.findByUsername(username);
        if(user == null){
            throw new UnknownAccountException();
        }
        if(!user.getPassword().equals(md5Pwd)){
            throw new IncorrectCredentialsException();
        }
        if(user.getIslock() == 1){
        	throw new LockedAccountException();
        }
        if(new Random().nextInt(10) > 8){// 弄个随机条件，测试自定义异常,实际项目中根据业务需求编写
        	throw new MyException();
        }
        
        //身份验证通过,返回一个身份信息
        AuthenticationInfo aInfo = new SimpleAuthenticationInfo(username,password,getName());
        
        return aInfo;
	}
	


}
