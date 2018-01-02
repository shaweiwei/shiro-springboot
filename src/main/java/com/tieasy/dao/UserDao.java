package com.tieasy.dao;

import com.tieasy.entity.UserInfo;
import com.tieasy.entity.UserRolePermission;

import java.util.List;

/**
 * 
 * @author ko
 *
 */
public interface UserDao {

	public UserInfo createUser(UserInfo user);

	public void updateUser(UserInfo user);

	public void deleteUser(int userId);

	public void correlationRoles(int userId, int... roleIds);

	public void uncorrelationRoles(int userId, int... roleIds);

	public UserInfo findOne(int userId);

	public List<UserInfo> findUserByParam(UserInfo user);

	public List<UserRolePermission> findUserRolePermissions(String username);

}
