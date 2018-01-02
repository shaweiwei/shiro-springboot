package com.tieasy.entity;

import java.io.Serializable;

public class ShiroSession implements Serializable {

	private String id;
	private String session;
	
	public ShiroSession(String id, String session) {
		super();
		this.id = id;
		this.session = session;
	}
	
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
