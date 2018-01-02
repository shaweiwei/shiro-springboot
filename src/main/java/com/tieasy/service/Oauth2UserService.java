package com.tieasy.service;

import java.util.List;

import com.tieasy.entity.Oauth2User;

public interface Oauth2UserService {

	public Oauth2User createUser(Oauth2User user);
	
    public Oauth2User updateUser(Oauth2User user);
    
    public void deleteUser(int id);

    Oauth2User findOne(int id);

    List<Oauth2User> findAll();

    Oauth2User findByUsername(String userName);
}
