package com.tieasy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.tieasy.bean.Result;
import com.tieasy.entity.UserInfo;
import com.tieasy.service.UserRunAsService;
import com.tieasy.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class UserRunAsController {

	@Autowired
	private UserRunAsService userRunAsService;
	@Autowired
	private UserService userService;
	
    /**
     * 登陆
     * @return
     */
    @ApiOperation(value="runaslist",notes="requires userid")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "int")
    })
	@RequestMapping(value="/runaslist/{userid}",method=RequestMethod.GET)
    @ResponseBody
	public Result runaslist(@PathVariable int userid){
    	
        Result rs = new Result();
        String msg = "";
        
        Subject subject = SecurityUtils.getSubject();
        msg+="当前用户isRunAs："+subject.isRunAs()+",";// 表示当前用户是否是RunAs用户，即已经切换身份了
        
        if(subject.isRunAs()) msg+=""+(String)subject.getPreviousPrincipals().getPrimaryPrincipal(); // 得到切换身份之前的身份，一个用户可以切换很多次身份，之前的身份使用栈数据结构来存储；
        
        String frommsg = "以当前用户为授予身份的所有用户:";
        List<Integer> fromids = userRunAsService.findFromUserIds(userid);
        if(fromids != null){
        	for (Integer fromid : fromids) {
				UserInfo fromuser = userService.findOne(fromid);
				frommsg+=fromuser.getUname()+",";
			}
        }
        msg+=frommsg;
        
        String tomsg = "以当前用户为被授予身份的所有用户:";
        List<Integer> toids = userRunAsService.findToUserIds(userid);
        if(toids != null){
        	for (Integer toid : toids) {
				UserInfo touser = userService.findOne(toid);
				tomsg+=touser.getUname()+",";
			}
        }
        msg+=tomsg;
        
        rs.setType(0);
        rs.setMessage("操作成功");
        rs.setData(msg);
        
		return rs;
	}
    
    /**
     * 授予身份
     * @param
     * @return
     */
    @ApiOperation(value="grant",notes="requires fromUserId and toUserId")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "fromUserId", value = "授予身份的用户id", required = true, dataType = "int"),
        @ApiImplicitParam(name = "toUserId", value = "被授予身份的用户id", required = true, dataType = "int")
    })
	@RequestMapping(value="/grant/{fromUserId}/{toUserId}",method=RequestMethod.GET)
    @ResponseBody
    public Result grant(@PathVariable int fromUserId, @PathVariable int toUserId){
    	Result rs = new Result();
    	if (fromUserId == toUserId) {
    		rs.setType(1);
    		rs.setMessage("自己不能授予身份给自己");
		}else{
			userRunAsService.grantRunAs(fromUserId, toUserId);
			rs.setType(0);
    		rs.setMessage("授予身份成功");
		}
    	return rs;
    }
    
    /**
     * 回收身份
     * @param
     * @return
     */
    @ApiOperation(value="revoke",notes="requires fromUserId and toUserId")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "fromUserId", value = "回收身份的用户id", required = true, dataType = "int"),
        @ApiImplicitParam(name = "toUserId", value = "被回收身份的用户id", required = true, dataType = "int")
    })
	@RequestMapping(value="/revoke/{fromUserId}/{toUserId}",method=RequestMethod.GET)
    @ResponseBody
    public Result revoke(@PathVariable int fromUserId, @PathVariable int toUserId){
    	Result rs = new Result();
    	if (fromUserId == toUserId) {
    		rs.setType(1);
    		rs.setMessage("自己不能从自己回收身份");
		}else{
			userRunAsService.revokeRunAs(fromUserId, toUserId);
			rs.setType(0);
    		rs.setMessage("回收身份成功");
		}
    	return rs;
    }
    
    /**
     * 切换身份
     * @param
     * @return
     */
    @ApiOperation(value="switchTo",notes="requires fromUserId and toUserId")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "loginUserId", value = "当前登陆用户id", required = true, dataType = "int"),
        @ApiImplicitParam(name = "toUserId", value = "身份要切换到的用户id", required = true, dataType = "int")
    })
	@RequestMapping(value="/switchTo/{loginUserId}/{touserid}",method=RequestMethod.GET)
    @ResponseBody
    public Result switchTo(@PathVariable int loginUserId, @PathVariable int toUserId){
    	Result rs = new Result();
    	if (loginUserId == toUserId) {
    		rs.setType(1);
    		rs.setMessage("自己不能切换自己");
		}
    	if(!userRunAsService.exists(loginUserId, toUserId)){
			rs.setType(1);
    		rs.setMessage("之前没有授予过身份，不能切换");
		}
    	
    	UserInfo switchtoUser = userService.findOne(toUserId);
    	
    	Subject subject = SecurityUtils.getSubject();
    	subject.runAs(new SimplePrincipalCollection(switchtoUser.getUname(),""));
    	
    	return rs;
    }
    
    /**
     * 切换到上一个身份
     * @param
     * @return
     */
    @ApiOperation(value="switchToPrev",notes="switchToPrev la la la")
	@RequestMapping(value="/switchToPrev",method=RequestMethod.GET)
    @ResponseBody
    public Result switchToPrev(){
    	Result rs = new Result();
    	Subject subject = SecurityUtils.getSubject();  
        if(subject.isRunAs()) {  
           subject.releaseRunAs();  
        } 
        rs.setType(0);
		rs.setMessage("切换到上一个身份成功");
    	return rs;
    }
    
}
