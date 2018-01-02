package com.tieasy.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tieasy.dao.Oauth2ClientDao;
import com.tieasy.entity.Oauth2Client;
import com.tieasy.mybatis.mapper.Oauth2ClientMapper;

@Repository
public class Oauth2ClientDaoImpl implements Oauth2ClientDao {

	@Autowired
	private Oauth2ClientMapper oauth2ClientMapper;
	
	@Override
	public Oauth2Client createClient(Oauth2Client client) {
		return oauth2ClientMapper.createClient(client);
	}

	@Override
	public Oauth2Client updateClient(Oauth2Client client) {
		return oauth2ClientMapper.updateClient(client);
	}

	@Override
	public void deleteClient(int id) {
		oauth2ClientMapper.deleteClient(id);
	}

	@Override
	public Oauth2Client findOne(int id) {
		return oauth2ClientMapper.findOne(id);
	}

	@Override
	public List<Oauth2Client> findAll() {
		return oauth2ClientMapper.findAll();
	}

	@Override
	public Oauth2Client findByClientId(String clientId) {
		// TODO Auto-generated method stub
		return oauth2ClientMapper.findByClientId(clientId);
	}

	@Override
	public Oauth2Client findByClientSecret(String clientSecret) {
		// TODO Auto-generated method stub
		return oauth2ClientMapper.findByClientSecret(clientSecret);
	}

}
