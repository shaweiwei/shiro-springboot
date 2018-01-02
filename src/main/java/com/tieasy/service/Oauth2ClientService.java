package com.tieasy.service;

import java.util.List;

import com.tieasy.entity.Oauth2Client;

public interface Oauth2ClientService {

	public Oauth2Client createClient(Oauth2Client client);
    public Oauth2Client updateClient(Oauth2Client client);
    public void deleteClient(int id);

    Oauth2Client findOne(int id);

    List<Oauth2Client> findAll();

    Oauth2Client findByClientId(String clientId);
    Oauth2Client findByClientSecret(String clientSecret);
}
