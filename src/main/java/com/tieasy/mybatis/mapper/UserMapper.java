package com.tieasy.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.tieasy.entity.UserInfo;
import com.tieasy.entity.UserRolePermission;
import com.tieasy.sqlproviders.UserDynaSqlProvider;

public interface UserMapper {

    @Select("SELECT * FROM user")
    @Results({
    	// 如果实体类的属性和数据库里的字段名不一致时，需要这样加上
//        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
    })
    List<UserInfo> getAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    @Results({
    })
    UserInfo getOne(int id);
    
    /**
     * 根据不同的条件查询用户，用到@selectprovider
     * @param user
     * @return
     */
//    @SelectProvider(type=UserDynaSqlProvider.class,method="getUserByParamSql")
    @Select("select * from user where uname = #{uname}")
    List<UserInfo> getUserByParam(UserInfo user);

    @Insert("INSERT INTO user(uname,islock,password) VALUES(#{uname}, #{islock}, #{password})")
    void insert(UserInfo user);

    @Update("UPDATE user SET uname=#{uname},islock=#{islock},password=#{password} WHERE id =#{id}")
    void update(UserInfo user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(int id);
    
    @Select("SELECT a.uname,c.pname,e.rname FROM user as a LEFT JOIN gl_user_permission as b ON a.id = b.user_id LEFT JOIN permission as c ON b.permission_id = c.id LEFT JOIN gl_permission_role as d ON d.permission_id = c.id LEFT JOIN role as e ON e.id = d.role_id WHERE a.uname = #{uname}")
    List<UserRolePermission> findUserRolePermissions(String uname);

}
