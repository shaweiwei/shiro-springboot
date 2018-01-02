package com.tieasy.bean;

/**
 * 接口返回类
 * @author ko
 *
 */
public class Result {

	private int type;// 0成功   
	private String message;
	private String data;
	private String datalist;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDatalist() {
		return datalist;
	}
	public void setDatalist(String datalist) {
		this.datalist = datalist;
	}
	
	
}
