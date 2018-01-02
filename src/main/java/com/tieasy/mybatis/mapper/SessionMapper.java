package com.tieasy.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.tieasy.entity.ShiroSession;
import com.tieasy.entity.UserInfo;
import com.tieasy.entity.UserRolePermission;
import com.tieasy.sqlproviders.UserDynaSqlProvider;

public interface SessionMapper {

    @Select("SELECT * FROM sessions")
    @Results({
    	// 如果实体类的属性和数据库里的字段名不一致时，需要这样加上
//        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
    })
    List<ShiroSession> getAll();

    @Select("SELECT * FROM sessions WHERE id = #{id}")
    @Results({
    })
    ShiroSession getOne(String id);
    
    @Insert("INSERT INTO sessions(id,session) VALUES(#{id}, #{session})")
    void insert(ShiroSession shiroSession);

    @Update("UPDATE sessions SET session=#{session} WHERE id =#{id}")
    void update(ShiroSession shiroSession);

    @Delete("DELETE FROM sessions WHERE id =#{id}")
    void delete(String id);

}
