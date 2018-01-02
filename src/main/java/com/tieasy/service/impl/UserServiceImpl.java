package com.tieasy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tieasy.dao.UserDao;
import com.tieasy.entity.UserInfo;
import com.tieasy.entity.UserRolePermission;
import com.tieasy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	public UserInfo createUser(UserInfo user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void changePassword(int userId, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	public void correlationRoles(int userId, int... roleIds) {
		// TODO Auto-generated method stub
		
	}

	public void uncorrelationRoles(int userId, int... roleIds) {
		// TODO Auto-generated method stub
		
	}

	public UserInfo findByUsername(String username) {
		// TODO Auto-generated method stub
		UserInfo userInfo = new UserInfo();
		userInfo.setUname(username);
		List<UserInfo> ulist = userDao.findUserByParam(userInfo);
		if (ulist == null || ulist.size() < 1) {
			return null;
		}else{
			if (ulist.size() > 1) throw new RuntimeException("预期每个用户只存在一条数据");
			return ulist.get(0);
		}
	}
	
	public UserInfo findOne(int userId){
		return userDao.findOne(userId);
	}

//	public Set<String> findRoles(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public List<UserRolePermission> findUserRolePermissions(String username) {
		return userDao.findUserRolePermissions(username);
	}

	public void setUserIsLock(UserInfo user) {
		if (user.getIslock() != 0 && user.getIslock() != 1) throw new IllegalArgumentException("user.isLock参数不对");
		userDao.updateUser(user);
	}

}
