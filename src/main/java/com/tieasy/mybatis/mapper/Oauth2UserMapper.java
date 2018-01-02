package com.tieasy.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tieasy.entity.Oauth2User;

public interface Oauth2UserMapper {

	@Insert("insert into oauth2_user(username, password, salt) values(#{userName},#{passWord},#{salt})")
	Oauth2User createUser(final Oauth2User oauth2User);
	
	@Update("update oauth2_user set username=#{userName}, password=#{passWord}, salt=#{salt} where id=#{id}")
	Oauth2User updateUser(Oauth2User oauth2User);
	
	@Delete("delete from oauth2_user where id=#{id}")
	void deleteUser(int id);
	
	@Select("select id, username, password, salt from oauth2_user where id=#{id}")
	@Results({
		@Result(property = "userName",  column = "username", javaType = Oauth2User.class),
		@Result(property = "password",  column = "password", javaType = Oauth2User.class)
	})
	Oauth2User findOne(int id);
	
	@Select("select id, username, password, salt from oauth2_user")
	@Results({
		@Result(property = "userName",  column = "username", javaType = Oauth2User.class),
		@Result(property = "password",  column = "password", javaType = Oauth2User.class)
	})
	List<Oauth2User> findAll();
	
	@Select("select id, username, password, salt from oauth2_user where username=#{userName}")
	@Results({
		@Result(property = "userName",  column = "username", javaType = Oauth2User.class),
		@Result(property = "password",  column = "password", javaType = Oauth2User.class)
	})
	Oauth2User findByUsername(String userName);
}
