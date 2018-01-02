package com.tieasy.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tieasy.entity.Oauth2Client;

public interface Oauth2ClientMapper {

	@Insert("insert into oauth2_client(client_name, client_id, client_secret) values(#{clientName},#{clientId},#{clientSecret})")
	Oauth2Client createClient(final Oauth2Client oauth2Client);
	
	@Update("update oauth2_client set client_name=#{clientName}, client_id=#{clientId}, client_secret=#{clientSecret} where id=#{id}")
	Oauth2Client updateClient(Oauth2Client client);
	
	@Delete("delete from oauth2_client where id=#{id}")
	void deleteClient(int id);
	
	@Select("select id, client_name, client_id, client_secret from oauth2_client where id=#{id}")
	@Results({
		@Result(property = "clientName",  column = "client_name", javaType = Oauth2Client.class),
		@Result(property = "clientId",  column = "client_id", javaType = Oauth2Client.class),
		@Result(property = "clientSecret",  column = "client_secret", javaType = Oauth2Client.class)
	})
	Oauth2Client findOne(int id);
	
	@Select("select id, client_name, client_id, client_secret from oauth2_client")
	@Results({
		@Result(property = "clientName",  column = "client_name", javaType = Oauth2Client.class),
		@Result(property = "clientId",  column = "client_id", javaType = Oauth2Client.class),
		@Result(property = "clientSecret",  column = "client_secret", javaType = Oauth2Client.class)
	})
	List<Oauth2Client> findAll();
	
	@Select("select id, client_name, client_id, client_secret from oauth2_client where client_id=#{clientId}")
	@Results({
		@Result(property = "clientName",  column = "client_name", javaType = Oauth2Client.class),
		@Result(property = "clientId",  column = "client_id", javaType = Oauth2Client.class),
		@Result(property = "clientSecret",  column = "client_secret", javaType = Oauth2Client.class)
	})
	Oauth2Client findByClientId(String clientId);
	
	@Select("select id, client_name, client_id, client_secret from oauth2_client where client_secret=#{clientSecret}")
	@Results({
		@Result(property = "clientName",  column = "client_name", javaType = Oauth2Client.class),
		@Result(property = "clientId",  column = "client_id", javaType = Oauth2Client.class),
		@Result(property = "clientSecret",  column = "client_secret", javaType = Oauth2Client.class)
	})
	Oauth2Client findByClientSecret(String clientSecret);
}
