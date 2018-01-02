package com.tieasy.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tieasy.dao.Oauth2UserDao;
import com.tieasy.entity.Oauth2User;
import com.tieasy.mybatis.mapper.Oauth2UserMapper;

@Repository
public class Oauth2UserDaoImpl implements Oauth2UserDao {

	@Autowired
	private Oauth2UserMapper oauth2UserMapper;
	
	@Override
	public Oauth2User createUser(Oauth2User user) {
		// TODO Auto-generated method stub
		return oauth2UserMapper.createUser(user);
	}

	@Override
	public Oauth2User updateUser(Oauth2User user) {
		// TODO Auto-generated method stub
		return oauth2UserMapper.updateUser(user);
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		oauth2UserMapper.deleteUser(id);
	}

	@Override
	public Oauth2User findOne(int id) {
		// TODO Auto-generated method stub
		return oauth2UserMapper.findOne(id);
	}

	@Override
	public List<Oauth2User> findAll() {
		// TODO Auto-generated method stub
		return oauth2UserMapper.findAll();
	}

	@Override
	public Oauth2User findByUsername(String userName) {
		// TODO Auto-generated method stub
		return oauth2UserMapper.findByUsername(userName);
	}

}
