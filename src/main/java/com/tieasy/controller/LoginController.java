package com.tieasy.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.tieasy.bean.Result;
import com.tieasy.entity.UserInfo;
import com.tieasy.service.UserService;
import com.tieasy.service.impl.UserServiceImpl;
import com.tieasy.shiro.exception.MyException;
import com.tieasy.shiro.session.dao.CustomShiroCacheSessionDAO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class LoginController {

	@Resource
	private UserService userService;
	
//	@Resource(name="sessionDAO")
//	private CustomShiroCacheSessionDAO sessionDAO;

    /**
     * 登陆
     *restful风格返回
     *REST 指的是一组架构约束条件和原则。满足这些约束条件和原则的应用程序或设计就是 RESTful。
	 *此外，有一款RESTFUL接口的文档在线自动生成+功能测试功能软件——Swagger UI，具体配置过程可移步《Spring Boot 利用 Swagger 实现restful测试》
     * @return
     */
    @ApiOperation(value="login platform",notes="requires username and password and rememberMe")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
        @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "rememberMe", value = "记住用户", required = true, dataType = "Boolean")
    })
    @ApiResponses({
    	@ApiResponse(response=Result.class, message="错误的请求参数", code = 400),
    	@ApiResponse(response=Result.class, message="请求地址没有找到", code = 404),
    	@ApiResponse(response=Result.class, message="内部错误", code = 500),
    })
	@RequestMapping(value="/login/{username}/{password}/{rememberMe}",method=RequestMethod.GET)
    @ResponseBody
	public Result login(@PathVariable String username,@PathVariable String password,@PathVariable Boolean rememberMe){
    	
        Result rs = new Result();
        Subject currentUser = SecurityUtils.getSubject();
        
        if (currentUser.isAuthenticated()){
        	System.out.println( "User [" + currentUser.getPrincipal() + "] logged in successfully." );
        	rs.setType(0);
        	rs.setMessage(username+"已经登陆过。。。不需要进行密码校验了");
		} else{
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			token.setRememberMe(rememberMe);
			try {
				
				currentUser.login(token);// 用户登陆
				
				rs.setType(0);
				rs.setMessage(username+"登陆成功");
			} catch ( UnknownAccountException uae ) {
				rs.setType(1);
				rs.setMessage("系统里没有"+username+"用户，请核查");
			} catch ( IncorrectCredentialsException ice ) {
				rs.setType(1);
				rs.setMessage("输入密码不匹配，请重试");
			} catch ( LockedAccountException lae ) {
				rs.setType(1);
				rs.setMessage("该账户被锁定，请联系管理员");
			} catch ( ExcessiveAttemptsException er ) {
				rs.setType(1);
				rs.setMessage("密码重试超过5次，该账户已被锁定");
			} catch ( AuthenticationException ae ) {
				rs.setType(1);
				rs.setMessage("未知错误，请联系管理员");
			}  catch (MyException e) {
				rs.setType(1);
				rs.setMessage("这是自定义异常");
			} 
		}
        
//		UserInfo user = userService.findByUsername(username);
//		System.out.println("rememberMe:"+rememberMe);
		return rs;
	}
    
    /**
     * 登出
     * @return
     */
    @ApiOperation(value="login out platform",notes="requires username and password")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
    })
	@RequestMapping(value="/logout/{username}",method=RequestMethod.GET)
    @ResponseBody
	public UserInfo logout(String username){
		UserInfo user = userService.findByUsername(username);
		return user;
	}
    
    /**
     * 超出限制的相同账户被踢出后跳转的地址
     * @return
     */
    @RequestMapping(value="/login_kickout",method=RequestMethod.GET)
    @ResponseBody
    public Result login_kickout(){
    	Result rs = new Result();
    	rs.setMessage("您的账户在其它地方登陆，您被踢出");
    	rs.setType(1);
    	return rs;
    } 
	
}
