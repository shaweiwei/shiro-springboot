package com.tieasy.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tieasy.dao.UserRunAsDao;
import com.tieasy.entity.UserRunAs;
import com.tieasy.mybatis.mapper.UserRunAsMapper;

import java.util.List;

/**
 * 
 * @author ko
 *
 */
@Repository
public class UserRunAsDaoImpl implements UserRunAsDao {

    @Autowired
    private UserRunAsMapper userRunAsMapper;

    @Override
    public void grantRunAs(int fromUserId, int toUserId) {
        userRunAsMapper.insert(fromUserId,toUserId);
    }

    public boolean exists(int fromUserId, int toUserId) {
    	UserRunAs userRunAs = userRunAsMapper.getOne(fromUserId, toUserId);
    	if (userRunAs == null) {
			return false;
		} else {
			return true;
		}
    }

    @Override
    public void revokeRunAs(int fromUserId, int toUserId) {
        userRunAsMapper.delete(fromUserId, toUserId);
    }

    @Override
    public List<Integer> findFromUserIds(int toUserId) {
        return userRunAsMapper.getFromsByTo(toUserId);
    }

    @Override
    public List<Integer> findToUserIds(int fromUserId) {
    	return userRunAsMapper.getTosByFrom(fromUserId);
    }
}
