package com.tieasy.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.tieasy.dao.UserDao;
import com.tieasy.entity.UserInfo;
import com.tieasy.entity.UserRolePermission;
import com.tieasy.mybatis.mapper.UserMapper;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
    private UserMapper userMapper;
	
	public UserInfo createUser(UserInfo user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateUser(UserInfo user) {
		userMapper.update(user);
	}

	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		
	}

	public void correlationRoles(int userId, int... roleIds) {
		// TODO Auto-generated method stub
		
	}

	public void uncorrelationRoles(int userId, int... roleIds) {
		// TODO Auto-generated method stub
		
	}

	public UserInfo findOne(int userId) {
		return userMapper.getOne(userId);
	}

	public List<UserInfo> findUserByParam(UserInfo user) {
		return userMapper.getUserByParam(user);
	}

	public List<UserRolePermission> findUserRolePermissions(String username) {
		return userMapper.findUserRolePermissions(username);
	}

}
