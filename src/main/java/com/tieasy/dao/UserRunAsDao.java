package com.tieasy.dao;

import java.util.List;

/**
 * 
 * @author ko
 *
 */
public interface UserRunAsDao {

    public void grantRunAs(int fromUserId, int toUserId);
    
    public void revokeRunAs(int fromUserId, int toUserId);

    public boolean exists(int fromUserId, int toUserId);

    public List<Integer> findFromUserIds(int toUserId);
    
    public List<Integer> findToUserIds(int fromUserId);

}
