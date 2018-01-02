package com.tieasy.sqlproviders;

import org.apache.ibatis.jdbc.SQL;

import com.tieasy.entity.UserInfo;

public class UserDynaSqlProvider {

	public String getUserByParamSql(UserInfo user){
		return new SQL(){
			{
				SELECT("*");  
                FROM("user");  
                WHERE("uname=" + user.getUname());
                AND();
                WHERE("password=" + user.getPassword());
                
			}
		}.toString();
		
	}
}
