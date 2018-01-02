package com.tieasy.shiro.session.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tieasy.entity.ShiroSession;
import com.tieasy.mybatis.mapper.SessionMapper;
import com.tieasy.util.SerializableUtils;

public class CustomShiroCacheSessionDAO extends CachingSessionDAO {
	
	@Autowired
	private SessionMapper sessionMapper;

	@Override
	public Serializable create(Session session) {
		// TODO Auto-generated method stub
		return doCreate(session);
	}

	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
		// TODO Auto-generated method stub
		return doReadSession(sessionId);
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		// TODO Auto-generated method stub
		doUpdate(session);
	}

	@Override
	public void delete(Session session) {
		// TODO Auto-generated method stub
		doDelete(session);
	}

	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession || !((ValidatingSession)session).isValid()) {
			return;// 如果会话过期/停止，就没有必要更新了
		}
        sessionMapper.update(new ShiroSession(session.getId().toString(), SerializableUtils.serialize(session)));
	}

	@Override
	protected void doDelete(Session session) {
        sessionMapper.delete(session.getId().toString());
	}

	@Override
	protected Serializable doCreate(Session session) {
        sessionMapper.insert(new ShiroSession(session.getId().toString(), SerializableUtils.serialize(session)));
		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		ShiroSession shiroSession = sessionMapper.getOne(sessionId.toString());
		return SerializableUtils.deserialize(shiroSession.getSession());
	}

}
