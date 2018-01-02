package com.tieasy.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import com.tieasy.entity.UserRunAs;

public interface UserRunAsMapper {

    @Select("SELECT * FROM user_run_as WHERE from_user_id = #{fromUserId} and to_user_id = #{toUserId}")
    @Results({
    	@Result(property = "fromUserId",  column = "from_user_id", javaType = Integer.class),
    	@Result(property = "toUserId",  column = "to_user_id", javaType = Integer.class)
    })
    UserRunAs getOne(int fromUserId, int toUserId);

    @Insert("INSERT INTO user_run_as(from_user_id,to_user_id) VALUES(#{fromUserId}, #{toUserId})")
    void insert(int fromUserId, int toUserId);

//    @Update("UPDATE user SET uname=#{uname},islock=#{islock},password=#{password} WHERE id =#{id}")
//    void update(UserInfo user);

    @Delete("DELETE FROM user_run_as WHERE from_user_id = #{fromUserId} and to_user_id = #{toUserId}")
    void delete(int fromUserId, int toUserId);
    
    @Select("SELECT from_user_id FROM user_run_as WHERE to_user_id = #{toUserId}")
    @Results({
    	@Result(property = "fromUserId",  column = "from_user_id", javaType = Integer.class)
    })
    List<Integer> getFromsByTo(int toUserId);
    
    @Select("SELECT to_user_id FROM user_run_as WHERE from_user_id = #{fromUserId}")
    @Results({
    	@Result(property = "toUserId",  column = "to_user_id", javaType = Integer.class)
    })
    List<Integer> getTosByFrom(int fromUserId);
}
