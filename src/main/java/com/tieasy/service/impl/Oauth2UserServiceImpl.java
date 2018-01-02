package com.tieasy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tieasy.dao.Oauth2UserDao;
import com.tieasy.entity.Oauth2User;
import com.tieasy.service.Oauth2UserService;

@Service
public class Oauth2UserServiceImpl implements Oauth2UserService {

	@Autowired
	private Oauth2UserDao oauth2UserDao;
	
	@Override
	public Oauth2User createUser(Oauth2User user) {
		// TODO Auto-generated method stub
		return oauth2UserDao.createUser(user);
	}

	@Override
	public Oauth2User updateUser(Oauth2User user) {
		// TODO Auto-generated method stub
		return oauth2UserDao.updateUser(user);
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		oauth2UserDao.deleteUser(id);
	}

	@Override
	public Oauth2User findOne(int id) {
		// TODO Auto-generated method stub
		return oauth2UserDao.findOne(id);
	}

	@Override
	public List<Oauth2User> findAll() {
		// TODO Auto-generated method stub
		return oauth2UserDao.findAll();
	}

	@Override
	public Oauth2User findByUsername(String userName) {
		// TODO Auto-generated method stub
		return oauth2UserDao.findByUsername(userName);
	}

}
