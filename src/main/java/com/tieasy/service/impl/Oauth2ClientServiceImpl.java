package com.tieasy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tieasy.dao.Oauth2ClientDao;
import com.tieasy.entity.Oauth2Client;
import com.tieasy.service.Oauth2ClientService;

@Service
public class Oauth2ClientServiceImpl implements Oauth2ClientService {

	@Autowired
	private Oauth2ClientDao oauth2ClientDao;
	
	@Override
	public Oauth2Client createClient(Oauth2Client client) {
		// TODO Auto-generated method stub
		return oauth2ClientDao.createClient(client);
	}

	@Override
	public Oauth2Client updateClient(Oauth2Client client) {
		// TODO Auto-generated method stub
		return oauth2ClientDao.updateClient(client);
	}

	@Override
	public void deleteClient(int id) {
		// TODO Auto-generated method stub
		oauth2ClientDao.deleteClient(id);
	}

	@Override
	public Oauth2Client findOne(int id) {
		// TODO Auto-generated method stub
		return oauth2ClientDao.findOne(id);
	}

	@Override
	public List<Oauth2Client> findAll() {
		// TODO Auto-generated method stub
		return oauth2ClientDao.findAll();
	}

	@Override
	public Oauth2Client findByClientId(String clientId) {
		// TODO Auto-generated method stub
		return oauth2ClientDao.findByClientId(clientId);
	}

	@Override
	public Oauth2Client findByClientSecret(String clientSecret) {
		// TODO Auto-generated method stub
		return oauth2ClientDao.findByClientSecret(clientSecret);
	}

}
