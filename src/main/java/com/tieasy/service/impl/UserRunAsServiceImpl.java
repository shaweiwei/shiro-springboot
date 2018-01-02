package com.tieasy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tieasy.dao.UserRunAsDao;
import com.tieasy.service.UserRunAsService;

import java.util.List;

/**
 * 
 * @author ko
 *
 */
@Service
public class UserRunAsServiceImpl implements UserRunAsService {
    @Autowired
    private UserRunAsDao userRunAsDao;

    @Override
    public void grantRunAs(int fromUserId, int toUserId) {
        userRunAsDao.grantRunAs(fromUserId, toUserId);
    }

    @Override
    public void revokeRunAs(int fromUserId, int toUserId) {
        userRunAsDao.revokeRunAs(fromUserId, toUserId);
    }

    @Override
    public boolean exists(int fromUserId, int toUserId) {
        return userRunAsDao.exists(fromUserId, toUserId);
    }

    @Override
    public List<Integer> findFromUserIds(int toUserId) {
        return userRunAsDao.findFromUserIds(toUserId);
    }

    @Override
    public List<Integer> findToUserIds(int fromUserId) {
        return userRunAsDao.findToUserIds(fromUserId);
    }
}
