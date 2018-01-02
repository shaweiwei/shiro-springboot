package com.tieasy.entity;

import java.io.Serializable;

/**
 * 在一些场景中，比如某个领导因为一些原因不能进行登录网站进行一些操作，他想把他网站上的工作委托给他
 * 的秘书，但是他不想把帐号/密码告诉他秘书，只是想把工作委托给他；此时和我们可以使用Shiro的RunAs功
 * 能，即允许一个用户假装为另一个用户（如果他们允许）的身份进行访问
 * 
 * 该实体定义了授予身份帐号（A）与被授予身份帐号（B）的关系，意思是B帐号将可以假装为A帐号的身份进行访问
 * @author ko
 *
 */
public class UserRunAs implements Serializable {  
    private int fromUserId;//授予身份帐号  
    private int toUserId;//被授予身份帐号  
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public int getToUserId() {
		return toUserId;
	}
	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}
    
    
} 
